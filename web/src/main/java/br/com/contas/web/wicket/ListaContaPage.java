package br.com.contas.web.wicket;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.contas.negocio.modelo.entidade.Conta;
import br.com.contas.negocio.servico.ServicoConta;

@SuppressWarnings("serial")
public class ListaContaPage extends WebPage {

	@SpringBean
	private ServicoConta servicoConta;
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		List<Conta> contas = servicoConta.listar();
		
		Form<?> form = new Form<>("form");

		form.add(new DataView<Conta>("dataview", new ListDataProvider<Conta>(contas)) {
			public void populateItem(final Item<Conta> item) {
				Conta conta = item.getModelObject();
				CompoundPropertyModel<Conta> modelo = new CompoundPropertyModel<Conta>(conta);
				item.add(new Label("titular", modelo.bind("titular")));
				item.add(new Label("numero", modelo.bind("numero")));
				item.add(new Label("agencia", modelo.bind("agencia")));
				item.add(new Label("banco", modelo.bind("banco")));
				item.add(new AjaxButton("btnEditarConta") {
					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
						setResponsePage(new ContaPage(conta));
					}
				});
				item.add(new AjaxButton("btnExcluirConta") {
					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
						servicoConta.excluir(conta);
						setResponsePage(ListaContaPage.class);
					}
				});
				
				item.add(new AjaxButton("btnMovimentacoesConta") {
					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
						setResponsePage(new MovimentacaoContaPage(conta));
					}
				});
			}
		});
		form.add(new AjaxButton("btnNovaConta") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				setResponsePage(new ContaPage(new Conta()));
			}
		});
		add(form);
	}
}
