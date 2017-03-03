package br.com.contas.web.config;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import br.com.contas.web.wicket.ListaContaPage;

public class ConfiguracaoAplicacaoWeb extends WebApplication {

    @Override
    public Class<? extends WebPage> getHomePage() {
        return ListaContaPage.class;
    }
    
    @Override
	protected void init() {

		super.init();
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		

	}
}
