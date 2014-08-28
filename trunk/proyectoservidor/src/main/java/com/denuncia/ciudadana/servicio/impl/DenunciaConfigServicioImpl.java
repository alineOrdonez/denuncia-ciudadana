/**
 * 
 */
package com.denuncia.ciudadana.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.denuncia.ciudadana.dominio.DenunciaConfig;
import com.denuncia.ciudadana.persistencia.PersistenciaException;
import com.denuncia.ciudadana.persistencia.dao.DenunciaConfigDAO;
import com.denuncia.ciudadana.servicio.DenunciaConfigServicio;

/**
 * @author amaro
 *
 */
@Service
public class DenunciaConfigServicioImpl implements DenunciaConfigServicio {

	@Autowired
	private DenunciaConfigDAO daoDenunciaConfig;
	
	@Override
	@Transactional(readOnly=true)
	public DenunciaConfig findConfig(){
		try{
			DenunciaConfig config = daoDenunciaConfig.getAll().get(0);
			return config;
		}catch(PersistenciaException e){
			return null;
		}
	}
	
}
