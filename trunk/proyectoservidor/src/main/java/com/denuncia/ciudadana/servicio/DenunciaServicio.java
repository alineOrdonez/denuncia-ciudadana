/**
 * 
 */
package com.denuncia.ciudadana.servicio;

import java.math.BigDecimal;
import java.util.List;

import com.denuncia.ciudadana.dominio.Denuncia;

/**
 * @author amaro
 *
 */
public interface DenunciaServicio {

	List<Denuncia> findNear(BigDecimal latitud, BigDecimal longitud)
			throws ServicioException;

	int save(Denuncia dominio) throws ServicioException;

	String saveImage(String image) throws ServicioException;

	boolean addDenuncia(int idDenuncia) throws ServicioException;

}
