package br.com.contas.negocio.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
@SuppressWarnings("serial")
public abstract class HibernateDAO<T extends Serializable> implements Serializable {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void delete(T object) {
        getCurrentSession().delete(object);
    }

    public void saveOrUpdate(T object) {
        getCurrentSession().saveOrUpdate(object);
    }

    public void save(T object) {
        getCurrentSession().save(object);
    }

}
