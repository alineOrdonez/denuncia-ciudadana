/**
 * 
 */
package com.denuncia.ciudadana.servicio.impl;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.denuncia.ciudadana.dominio.CatCategoriaDenuncia;
import com.denuncia.ciudadana.persistencia.PersistenciaException;
import com.denuncia.ciudadana.persistencia.dao.CategoriaDenunciaDAO;
import com.denuncia.ciudadana.servicio.CategoriaDenunciaServicio;
import com.denuncia.ciudadana.servicio.ServicioException;

/**
 * @author Aline Ordoñez
 *
 */
@Service
public class CategoriaDenunciaServicioImpl implements CategoriaDenunciaServicio {

	@Autowired
	private CategoriaDenunciaDAO daoCatDen;

	@Override
	@Transactional(readOnly = true)
	public List<CatCategoriaDenuncia> findAllCategorias()
			throws ServicioException {
		try {
			List<CatCategoriaDenuncia> list = daoCatDen.getAll();
			return list;
		} catch (PersistenciaException e) {
			throw new ServiceException("Error en findAllCategorias:"
					+ e.getMessage(), e);
		}
	}

}
