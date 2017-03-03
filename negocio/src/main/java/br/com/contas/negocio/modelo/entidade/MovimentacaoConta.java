package br.com.contas.negocio.modelo.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.contas.negocio.modelo.constantes.TipoMovimentacao;

@Entity
@Table(name = "mov_movimentacao_conta")
@SuppressWarnings("serial")
public class MovimentacaoConta implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "mov_id")
	private Long id;

	@Column(name = "mov_tipo_movimentacao", nullable = false)
	private TipoMovimentacao tipoMovimentacao;

	@Column(name = "mov_valor", nullable = false)
	private Double valor;

	@Column(name = "mov_data", nullable = false)
	private String data;

	@Column(name = "mov_descricao")
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "con_id", foreignKey = @ForeignKey(name = "fk_con_id"), nullable = false)
	private Conta conta;

	public MovimentacaoConta() {
		super();
	}
	
	public MovimentacaoConta(Conta conta) {
		this.conta = conta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

}
