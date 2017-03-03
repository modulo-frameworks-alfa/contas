package br.com.contas.web.wicket;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.contas.negocio.modelo.entidade.Conta;
import br.com.contas.negocio.servico.ServicoConta;

@SuppressWarnings("serial")
public class ContaPage extends WebPage {
	
	@SpringBean
	private ServicoConta servicoConta;

	public ContaPage(Conta conta) {
		super(new CompoundPropertyModel<Conta>(conta));
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onInitialize() {
		super.onInitialize();
		CompoundPropertyModel<Conta> modelo = (CompoundPropertyModel<Conta>) getDefaultModel();
		Form<Conta> form = new Form<Conta>("form", modelo);

		form.add(new TextField<>("titular", modelo.bind("titular")));
		form.add(new TextField<>("numero", modelo.bind("numero")));
		form.add(new TextField<>("agencia", modelo.bind("agencia")));
		form.add(new TextField<>("banco", modelo.bind("banco")));

		form.add(new AjaxButton("btnSalvarConta") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				servicoConta.salvar(modelo.getObject());
				setResponsePage(ListaContaPage.class);
			}

		});
		
		form.add(new AjaxButton("btnVoltar") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				setResponsePage(ListaContaPage.class);
			}

		});

		add(form);
	}
}
