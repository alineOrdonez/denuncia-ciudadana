/**
 * 
 */
package com.denuncia.ciudadana.persistencia.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.denuncia.ciudadana.dominio.Denuncia;
import com.denuncia.ciudadana.persistencia.PersistenciaException;

/**
 * @author Aline Ordoñez
 *
 */
public interface DenunciaDAO extends BaseDAO<Denuncia, Integer> {

	List<Denuncia> findNear(BigDecimal longitud, BigDecimal latitud)
			throws PersistenciaException;

	String getImagePath(int idDenuncia) throws PersistenciaException;

	List<Denuncia> findByDateAndLocation(BigDecimal longitud,
			BigDecimal latitud, Date date) throws PersistenciaException;
}
