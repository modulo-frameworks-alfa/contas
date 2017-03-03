package br.com.contas.web.wicket;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.contas.negocio.modelo.entidade.Conta;
import br.com.contas.negocio.servico.ServicoConta;

@SuppressWarnings("serial")
public class ModalConta extends Panel{
	
	@SpringBean
	private ServicoConta servicoConta;
	
	private CompoundPropertyModel<Conta> modelo;
	
	public ModalConta(String id) {
		super(id);
		modelo = new CompoundPropertyModel<Conta>(new Conta());
		setOutputMarkupId(true);
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new AjaxButton("btnNovaConta") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				limparModelo(target);
			}
		});
		add(new AjaxButton("btnFecharModal") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				limparModelo(target);
			}
		});
		
		Form<Conta> formNovaConta = new Form<Conta>("formNovaConta", modelo);
		
		formNovaConta.add(new TextField<>("titular", modelo.bind("titular")));
		formNovaConta.add(new TextField<>("numero", modelo.bind("numero")));
		formNovaConta.add(new TextField<>("agencia", modelo.bind("agencia")));
		formNovaConta.add(new TextField<>("banco", modelo.bind("banco")));
		
		formNovaConta.add(new AjaxButton("btnSalvarConta") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				servicoConta.salvar(modelo.getObject());
				setResponsePage(ListaContaPage.class);
			}

		});
		
		add(formNovaConta);
	}
	private void limparModelo(AjaxRequestTarget target) {
		modelo.setObject(new Conta());
	}
	
	public CompoundPropertyModel<Conta> getModelo() {
		return modelo;
	}

}
