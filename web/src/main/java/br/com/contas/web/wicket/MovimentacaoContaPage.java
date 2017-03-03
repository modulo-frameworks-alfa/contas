package br.com.contas.web.wicket;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.contas.negocio.modelo.constantes.TipoMovimentacao;
import br.com.contas.negocio.modelo.entidade.Conta;
import br.com.contas.negocio.modelo.entidade.MovimentacaoConta;
import br.com.contas.negocio.servico.ServicoMovimentacaoConta;
import edu.emory.mathcs.backport.java.util.Arrays;

@SuppressWarnings("serial")
public class MovimentacaoContaPage extends WebPage {
	
	@SpringBean
	private ServicoMovimentacaoConta servicoMovimentacaoConta;
	
	private Conta conta;

	public MovimentacaoContaPage(Conta conta) {
		super(new CompoundPropertyModel<MovimentacaoConta>(new MovimentacaoConta(conta)));
		this.conta = conta;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onInitialize() {
		super.onInitialize();
		CompoundPropertyModel<MovimentacaoConta> modelo = (CompoundPropertyModel<MovimentacaoConta>) getDefaultModel();
		Form<MovimentacaoConta> form = new Form<MovimentacaoConta>("form", modelo);
		
		CompoundPropertyModel<Conta> modeloConta = new CompoundPropertyModel<Conta>(conta);
		form.add(new Label("titular", modeloConta.bind("titular")));
		form.add(new Label("numero", modeloConta.bind("numero")));
		form.add(new Label("agencia", modeloConta.bind("agencia")));
		form.add(new Label("banco", modeloConta.bind("banco")));
		
		form.add(new DropDownChoice<>("tipoMovimentacao", modelo.bind("tipoMovimentacao"), Arrays.asList(TipoMovimentacao.values()), new ChoiceRenderer<>("descricao")));
		form.add(new NumberTextField<>("valor", modelo.bind("valor"), Double.class));
		form.add(new TextField<>("descricao", modelo.bind("descricao")));
		form.add(new DataView<MovimentacaoConta>("dataview", new ListDataProvider<MovimentacaoConta>(servicoMovimentacaoConta.listar(conta))) {
			public void populateItem(final Item<MovimentacaoConta> item) {
				CompoundPropertyModel<MovimentacaoConta> modelo = new CompoundPropertyModel<MovimentacaoConta>(item.getModelObject());
				item.add(new Label("tipoMovimentacao", modelo.bind("tipoMovimentacao")));
				item.add(new Label("valor", modelo.bind("valor")));
				item.add(new Label("descricao", modelo.bind("descricao")));
				item.add(new Label("data", modelo.bind("data")));
			}
		});
		
		form.add(new Button("btnMovimentar"){
			@Override
			public void onSubmit() {
				CompoundPropertyModel<MovimentacaoConta> modelo = (CompoundPropertyModel<MovimentacaoConta>) MovimentacaoContaPage.this.getDefaultModel();
				servicoMovimentacaoConta.salvar(modelo.getObject());
				setResponsePage(new MovimentacaoContaPage(conta));
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
