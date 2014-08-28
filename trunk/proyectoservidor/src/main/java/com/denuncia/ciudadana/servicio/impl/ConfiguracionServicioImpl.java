/**
 * 
 */
package com.denuncia.ciudadana.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.denuncia.ciudadana.dominio.Configuracion;
import com.denuncia.ciudadana.persistencia.PersistenciaException;
import com.denuncia.ciudadana.persistencia.dao.ConfiguracionDAO;
import com.denuncia.ciudadana.servicio.ConfiguracionServicio;
import com.denuncia.ciudadana.servicio.ServicioException;

/**
 * @author amaro
 *
 */
@Service
public class ConfiguracionServicioImpl implements ConfiguracionServicio {

	@Autowired
	private ConfiguracionDAO daoConfig;
	
	@Override
	@Transactional(readOnly=true)
	public List<Configuracion> findAllConfig() throws ServicioException{
		try{
			List<Configuracion> list = daoConfig.getAll();
			return list;
		}catch(PersistenciaException e){
			throw new ServicioException("Error en findAllConfig:"+e.getMessage(),e );
		}
	}
	
}
