/*
 * Peycash 2014 - All rights reserved
 */
package com.denuncia.persistencia.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.denuncia.persistencia.dao.BaseDAO;

/**
 * Implementation BaseDAO class for generic persistence operations.
 * 
 * @author Jorge Amaro Coria
 * @author Aline Ordo&ntilde;ez
 * @version 1.1
 * @since Build 1.0
 */
public class BaseDAOImpl<T, PK extends Serializable> implements BaseDAO<T, PK> {

	@Autowired
	private SessionFactory sessionFactory;

	private final static Logger LOGGER = LoggerFactory.getLogger(BaseDAO.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.peycash.persistence.dao.BaseDAO#getAll()
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() throws PersistenceException {
		List<T> entities = new ArrayList<T>();
		try {
			entities = getSessionFactory().getCurrentSession()
					.createCriteria(this.getPersistentClass()).list();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return entities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.peycash.persistence.dao.BaseDAO#findListByIdPartner(int)
	 */
	@SuppressWarnings("unchecked")
	public List<T> findListByIdPartner(int idPartner)
			throws PersistenceException {
		List<T> entities = new ArrayList<T>();
		try {
			Criteria criteria = getSessionFactory().getCurrentSession()
					.createCriteria(this.getPersistentClass())
					.add(Restrictions.eq("partner.idpartner", idPartner));
			entities = criteria.list();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return entities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.peycash.persistence.dao.BaseDAO#findByPK(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	public T findByPK(PK id) throws PersistenceException {
		try {
			return (T) getSessionFactory().getCurrentSession().get(
					this.getPersistentClass(), id);
		} catch (Exception e) {
			throw new PersistenceException("Excepci&oacute;n en BD", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.peycash.persistence.dao.BaseDAO#update(java.lang.Object)
	 */
	public void update(T object) throws PersistenceException {
		try {
			getSessionFactory().getCurrentSession().update(object);
		} catch (Exception e) {
			LOGGER.debug("Cayo en exception al actualizar el objeto", e);
			throw new PersistenceException("Excepci&oacute;n en BD", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.peycash.persistence.dao.BaseDAO#remove(java.lang.Object)
	 */
	public void remove(T object) throws PersistenceException {
		try {
			getSessionFactory().getCurrentSession().delete(object);
			LOGGER.debug("Se ejecuto el borrado del objeto");
		} catch (Exception e) {
			LOGGER.debug("Cayo en exception al borrar el objeto", e);
			throw new PersistenceException("Excepci&oacute;n en BD", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.peycash.persistence.dao.BaseDAO#save(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public PK save(T object) throws PersistenceException {
		try {
			return (PK) getSessionFactory().getCurrentSession().save(object);
		} catch (Exception e) {
			throw new PersistenceException("Excepci&oacute;n en BD", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.peycash.persistence.dao.BaseDAO#saveOrUpdate(java.lang.Object)
	 */
	public void saveOrUpdate(T object) throws PersistenceException {
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(object);
		} catch (Exception e) {
			throw new PersistenceException("Excepci&oacute;n en BD", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.peycash.persistence.dao.BaseDAO#exists(java.io.Serializable)
	 */
	public boolean exists(PK paramPK) throws PersistenceException {
		try {
			Object entity = this.findByPK(paramPK);
			return (entity != null);
		} catch (Exception e) {
			throw new PersistenceException("Excepcion en BD", e);
		}
	}

	/**
	 * Method that obtains the DAO's persistence class.
	 * 
	 * @return <T> clase a persistir.
	 */
	@SuppressWarnings("unchecked")
	private Class<T> getPersistentClass() {

		Class<T> type = null;

		Class<?> clazz = getClass();

		while (!(clazz.getGenericSuperclass() instanceof ParameterizedType)) {
			clazz = clazz.getSuperclass();
		}

		type = (Class<T>) ((ParameterizedType) clazz.getGenericSuperclass())
				.getActualTypeArguments()[0];

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("type DO :: " + type);
		}
		return type;
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
