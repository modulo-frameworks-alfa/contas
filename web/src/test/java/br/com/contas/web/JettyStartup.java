package br.com.contas.web;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyStartup {

    public static void main(String[] args) {
        System.setProperty("wicket.configuration", "deployment");

        Server server = new Server();
        HttpConfiguration httpConfig = new HttpConfiguration();
        httpConfig.setSecureScheme("https");
        httpConfig.setSecurePort(8443);
        httpConfig.setOutputBufferSize(32768);

        ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(httpConfig));
        http.setPort(8080);
        http.setIdleTimeout(1000 * 60 * 60);

        server.addConnector(http);

        Resource keystore = Resource.newClassPathResource("/keystore");
        if (keystore != null && keystore.exists()) {
            SslContextFactory sslContextFactory = new SslContextFactory();
            sslContextFactory.setKeyStoreResource(keystore);
            sslContextFactory.setKeyStorePassword("wicket");
            sslContextFactory.setKeyManagerPassword("wicket");

            HttpConfiguration https_config = new HttpConfiguration(httpConfig);
            https_config.addCustomizer(new SecureRequestCustomizer());

            ServerConnector https = new ServerConnector(server, new SslConnectionFactory(sslContextFactory, "http/1.1"), new HttpConnectionFactory(https_config));
            https.setPort(8443);
            https.setIdleTimeout(500000);

            server.addConnector(https);
        }

        WebAppContext webApp = new WebAppContext();
        webApp.setServer(server);
        webApp.setContextPath("/contas");
        webApp.setWar("src/main/webapp");
        webApp.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*/classes/.*");

        // Set the path to the override descriptor
        webApp.setOverrideDescriptor("/jetty-web.xml");

        server.setHandler(webApp);

        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        MBeanContainer mBeanContainer = new MBeanContainer(mBeanServer);
        server.addEventListener(mBeanContainer);
        server.addBean(mBeanContainer);

        // Enable parsing of jndi-related parts of web.xml and jetty-env.xml
        Configuration.ClassList classlist = org.eclipse.jetty.webapp.Configuration.ClassList.setServerDefault(server);
        classlist.addAfter(FragmentConfiguration.class.getName(), EnvConfiguration.class.getName(), PlusConfiguration.class.getName());
        classlist.addBefore(JettyWebXmlConfiguration.class.getName(), AnnotationConfiguration.class.getName());

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(100);
        }

    }
}
