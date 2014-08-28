/**
 * 
 */
package com.denuncia.ciudadana.servicio;

import java.util.List;

import com.denuncia.ciudadana.dominio.Configuracion;

/**
 * @author amaro
 *
 */
public interface ConfiguracionServicio {

	List<Configuracion> findAllConfig() throws ServicioException;

}
