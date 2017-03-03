# Contas
Projeto para módulo de frameworks

Requerimentos:
  * Tomcat >= 9.0
  * Java >= 1.8
  * Maven >= 3.3.9
  * PostgreSQL >= 9.6

Frameworks utilizados:
  * Wicket 7.0
  * Spring 4.2.3
  * Hibernate 5.0.4  
  * Atomikos 4.0
  
Estrutura do projeto:
```
contas
|---imagens
|---negocio
|	|	src/main/java/br/com/contas/negocio
|	|	|	dao
|	|	|	|---ContaDAO.java
|	|	|	|---HibernateDAO.java
|	|	|	|---MovimentacaoContaDAO.java
|	|	|	modelo
|	|	|	|	constantes
|	|	|	|	|---TipoMovimentacao.java
|	|	|	|	entidade
|	|	|	|	|---Conta.java
|	|	|	|	|---MovimentacaoConta.java
|	|	|	servico
|	|	|	|---ServicoConta.java
|	|	|	|---ServicoMovimentacaoConta.java
|	|	pom.xml
|---web
|	|	src
|	|	|	main
|	|	|	|	java/br/com/contas/web
|	|	|	|	|	config
|	|	|	|	|	|---ConfiguracaoAplicacaoWeb.java
|	|	|	|	|	wicket
|	|	|	|	|	|---ContaPage.html
|	|	|	|	|	|---ContaPage.java
|	|	|	|	|	|---ListaContaPage.html
|	|	|	|	|	|---ListaContaPage.java
|	|	|	|	|	|---MovimentacaoContaPage.java
|	|	|	|	|	|---MovimentacaoContaPage.html
|	|	|	|	resources
|	|	|	|		spring-config
|	|	|	|		|---springAppConfig.xml
|	|	|	|		log4j.properties
|	|	|	|	webapp/WEB-INF
|	|	|	|	|---web.xml
|	|	|	test
|	|	|	|	java/br/com/contas/web
|	|	|	|	|---JettyStartup.java
|	|	|	|	resources
|	|	|	|	|---jetty-web.xml
|	|	|	|	|---keystore
|	|	pom.xml
|---.gitignore
|---README.md
|---pom.xml
```

Build para gerar os artefatos para eclipse:
  * `mvn eclipse:clean eclipse:eclipse -U install`
  
Instruções para deploy
  * As configurações com banco de dados ficam no arquivo `%contas%/web/src/main/resources/spring-config/springAppConfig.xml`
    Por padrão, é esperado que o serviço do postgres:
      * Esteja executando na porta 5432
      * Tenha o usuário `postgres` com senha `123456`
      * E que uma base de dados `contas` exista
    
  * Build para gerar o arquivo de war no diretório `%contas%/web/target/contas.war`:
    * mvn -U install -P deploy
  * O deploy do arquivo `.war` pode ser realizado copiando para o diretório `%TOMCAT_HOME%/webapps` ou através da interface que gerencia o serviço
  * Após o deploy, a aplicação pode ser acessada através do contexto `/contas`, e.g.: `http://localhost:8080/contas`
  
Para mais detalhes, o diretório `%contas%/imagens` contém imagens das telas que a aplicação fornece ao usuário.
