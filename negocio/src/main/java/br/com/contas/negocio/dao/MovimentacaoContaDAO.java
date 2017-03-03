package br.com.contas.negocio.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.contas.negocio.modelo.entidade.Conta;
import br.com.contas.negocio.modelo.entidade.MovimentacaoConta;

@SuppressWarnings("serial")
@Repository
public class MovimentacaoContaDAO extends HibernateDAO<MovimentacaoConta>{

	@SuppressWarnings("unchecked")
	public List<MovimentacaoConta> listar(Conta conta) {
		return getCurrentSession()//
				.createCriteria(MovimentacaoConta.class)//
				.add(Restrictions.eq("conta", conta))//
				.list();
	}
}
