/**
 * 
 */
package com.denuncia.ciudadana.persistencia.dao;

import java.math.BigDecimal;
import java.util.List;

import com.denuncia.ciudadana.dominio.Denuncia;
import com.denuncia.ciudadana.persistencia.PersistenciaException;

/**
 * @author amaro
 *
 */
public interface DenunciaDAO extends BaseDAO<Denuncia, Integer> {

	List<Denuncia> findNear(BigDecimal longitud, BigDecimal latitud)
			throws PersistenciaException;

}
