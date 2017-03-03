package br.com.contas.negocio.servico;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.contas.negocio.dao.MovimentacaoContaDAO;
import br.com.contas.negocio.modelo.entidade.Conta;
import br.com.contas.negocio.modelo.entidade.MovimentacaoConta;

@SuppressWarnings("serial")
@Service
@Transactional(readOnly = true)
public class ServicoMovimentacaoConta implements Serializable {
	
	@Autowired
	private MovimentacaoContaDAO movimentacaoContaDAO;
	
	@Transactional
	public void deletar(Conta conta) {
		movimentacaoContaDAO.listar(conta).forEach(movimentacaoConta -> movimentacaoContaDAO.delete(movimentacaoConta));
	}
	
	public List<MovimentacaoConta> listar(Conta conta) {
		return movimentacaoContaDAO.listar(conta);
	}
	
	@Transactional
	public void salvar(MovimentacaoConta movimentacaoConta) {
		movimentacaoConta.setData(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		movimentacaoContaDAO.saveOrUpdate(movimentacaoConta);
	}
}
