package br.com.contas.negocio.servico;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.contas.negocio.dao.ContaDAO;
import br.com.contas.negocio.modelo.entidade.Conta;

@SuppressWarnings("serial")
@Service
@Transactional(readOnly = true)
public class ServicoConta implements Serializable {

	@Autowired
	private ServicoMovimentacaoConta servicoMovimentacaoConta;
	
	@Autowired
	private ContaDAO contaDAO;
	
	public List<Conta> listar() {
		return contaDAO.listar();
	}
	
	@Transactional
	public void salvar(Conta conta) {
		contaDAO.saveOrUpdate(conta);
	}
	
	@Transactional
	public void excluir(Conta conta) {
		servicoMovimentacaoConta.deletar(conta);
		contaDAO.delete(conta);
	}
	
}
