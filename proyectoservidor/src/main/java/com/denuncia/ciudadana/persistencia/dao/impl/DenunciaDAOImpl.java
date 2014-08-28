/**
 * 
 */
package com.denuncia.ciudadana.persistencia.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.denuncia.ciudadana.dominio.Denuncia;
import com.denuncia.ciudadana.persistencia.PersistenciaException;
import com.denuncia.ciudadana.persistencia.dao.DenunciaDAO;

/**
 * @author amaro
 *
 */
@Repository
public class DenunciaDAOImpl extends BaseDAOImpl<Denuncia, Integer> implements
		DenunciaDAO {

	@Override
	public List<Denuncia> findNear(BigDecimal longitud, BigDecimal latitud) throws PersistenciaException{
		try{
			Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Denuncia.class);
			criteria.add(Restrictions.eq("latitud", latitud));
			criteria.add(Restrictions.eq("longitud", longitud));
			List<Denuncia> result = criteria.list();
			return result;
		}catch(Exception e){
			throw new PersistenciaException("Error en findNear:"+e.getMessage(), e);
		}
	}
	
}
