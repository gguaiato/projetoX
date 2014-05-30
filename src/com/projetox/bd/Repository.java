package com.projetox.bd;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

public class Repository<T> implements Serializable {

	private static final String PERSISTENCE_UNIT_NAME = "projetox";
	protected EntityManager em;
	private EntityManagerFactory emf;

	private final Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	protected Repository() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected List<T> list(int qtd) {
		if (qtd > 1)
			return findByCriteria(createCriteria().setMaxResults(qtd));
		return findByCriteria();
	}

	private Class<T> getEntityClass() {
		return persistentClass;
	}

	private EntityManager getEntityManager() {
		if (em != null)
			return em;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = emf.createEntityManager();
		return em;
	}

	protected List<T> findByCriteria(Criterion... criterion) {
		return findByCriteria(createCriteria(), criterion);
	}

	protected T findUniqueByCriteria(Criterion... criterion) {
		List<T> list = findByCriteria(createCriteria(), criterion);
		if (list.size() == 0)
			return null;
		if (list.size() > 1)
			throw new RuntimeException(
					"Consulta retornou mais que um registro!!");
		return list.get(0);
	}

	protected List<T> findByCriteria(Criteria criteria, Criterion... criterion) {
		for (final Criterion c : criterion) {
			criteria.add(c);
		}

		List<T> result = criteria.list();
		return result;
	}

	protected Criteria createCriteria() {
		Session session = (Session) getEntityManager().getDelegate();
		Criteria crit = session.createCriteria(getEntityClass());
		return crit;
	}

	protected void delete(T entity) {
		getEntityManager().remove(entity);
	}

	protected T save(T entity) {
		T savedEntity = getEntityManager().merge(entity);
		return savedEntity;
	}
}