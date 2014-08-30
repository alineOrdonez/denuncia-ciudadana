/**
 * 
 */
package com.denuncia.ciudadana.persistencia.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.denuncia.ciudadana.dominio.Denuncia;
import com.denuncia.ciudadana.persistencia.PersistenciaException;
import com.denuncia.ciudadana.persistencia.dao.DenunciaDAO;

/**
 * @author Aline Ordoñez
 *
 */
@Repository
public class DenunciaDAOImpl extends BaseDAOImpl<Denuncia, Integer> implements
		DenunciaDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Denuncia> findNear(BigDecimal longitud, BigDecimal latitud)
			throws PersistenciaException {
		try {
			Criteria criteria = getSessionFactory().getCurrentSession()
					.createCriteria(Denuncia.class);
			criteria.add(Restrictions.eq("latitud", latitud));
			criteria.add(Restrictions.eq("longitud", longitud));
			List<Denuncia> result = criteria.list();
			return result;
		} catch (Exception e) {
			throw new PersistenciaException("Error en findNear:"
					+ e.getMessage(), e);
		}
	}

	@Override
	public String getImagePath(int idDenuncia) throws PersistenciaException {
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(Denuncia.class);
		criteria.add(Restrictions.eq("idDenuncia", idDenuncia));
		Denuncia result = (Denuncia) criteria.uniqueResult();
		String image = result.getImgPath();
		return image;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Denuncia> findByDateAndLocation(BigDecimal longitud,
			BigDecimal latitud, Date date) throws PersistenciaException {
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(Denuncia.class);
		criteria.add(Restrictions.ge("fechaHoraDenuncia", date));
		// criteria.add(Restrictions.between("fechaHoraDenuncia", date, new
		// Date()));
		List<Denuncia> result = criteria.list();
		return result;
	}

}
