/**
 * 
 */
package com.denuncia.ciudadana.servicio;

import java.math.BigDecimal;
import java.util.List;

import com.denuncia.ciudadana.dominio.Denuncia;

/**
 * @author Aline Ordoñez
 *
 */
public interface DenunciaServicio {

	List<Denuncia> findNear(BigDecimal latitud, BigDecimal longitud)
			throws ServicioException;

	int save(Denuncia dominio) throws ServicioException;

	String saveImage(String image) throws ServicioException;

	String getImage(int idDenuncia) throws ServicioException;

	boolean addDenuncia(Denuncia dominio) throws ServicioException;

	List<Denuncia> findByDateAndLocation(BigDecimal latitud,
			BigDecimal longitud, int pt) throws ServicioException;

}
