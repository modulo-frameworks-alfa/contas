package br.com.contas.negocio.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.contas.negocio.modelo.entidade.Conta;

@SuppressWarnings("serial")
@Repository
public class ContaDAO extends HibernateDAO<Conta>{
	
	@SuppressWarnings("unchecked")
	public List<Conta> listar() {
		return getCurrentSession().createCriteria(Conta.class).list();
	}
}
