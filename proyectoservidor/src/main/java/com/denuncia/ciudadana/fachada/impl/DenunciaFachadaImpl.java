/**
 * 
 */
package com.denuncia.ciudadana.fachada.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.denuncia.ciudadana.dominio.CatCategoriaDenuncia;
import com.denuncia.ciudadana.dominio.Configuracion;
import com.denuncia.ciudadana.dominio.Denuncia;
import com.denuncia.ciudadana.dto.DenunciaDTO;
import com.denuncia.ciudadana.dto.DenunciaItemListaDTO;
import com.denuncia.ciudadana.dto.IntervaloTiempoDTO;
import com.denuncia.ciudadana.dto.ListadoDenunciasDTO;
import com.denuncia.ciudadana.dto.ResultadoDTO;
import com.denuncia.ciudadana.fachada.DenunciaFachada;
import com.denuncia.ciudadana.servicio.CategoriaDenunciaServicio;
import com.denuncia.ciudadana.servicio.ConfiguracionServicio;
import com.denuncia.ciudadana.servicio.DenunciaServicio;
import com.denuncia.ciudadana.servicio.ServicioException;
import com.denuncia.ciudadana.util.Constantes;
import com.denuncia.ciudadana.util.TransformUtils;

/**
 * @author Aline Ordoñez
 *
 */
@Component
public class DenunciaFachadaImpl implements DenunciaFachada {

	@Autowired
	private CategoriaDenunciaServicio servicioCatDenuncia;
	@Autowired
	private ConfiguracionServicio servicioConfig;
	@Autowired
	private DenunciaServicio servicioDenuncia;

	@Override
	public ResultadoDTO loadInitialConfig() {
		try {
			ResultadoDTO resultado = new ResultadoDTO();
			resultado.setIs(Constantes.ESTATUS_EXITOSO);
			resultado.setDs(Constantes.DSC_ESTATUS_EXITOSO);
			List<CatCategoriaDenuncia> listCatDenucia = servicioCatDenuncia
					.findAllCategorias();
			List<Configuracion> listConfig = servicioConfig.findAllConfig();
			resultado.setLd(getDenuncias(listCatDenucia));
			resultado.setLt(getIntervalos(listConfig));
			return resultado;
		} catch (ServicioException e) {
			return null;
		}
	}

	@Override
	public ListadoDenunciasDTO findNear(BigDecimal latitud,
			BigDecimal longitud, String ds, String ic) {
		try {
			ListadoDenunciasDTO dto = new ListadoDenunciasDTO();
			dto.setIs(Constantes.ESTATUS_EXITOSO);
			dto.setDs(Constantes.DSC_ESTATUS_EXITOSO);
			List<Denuncia> listDenuncia = servicioDenuncia.findNear(latitud,
					longitud);
			dto.setLd(getDenuncias(listDenuncia, ds, ic));
			return dto;
		} catch (ServicioException e) {
			return null;
		}

	}

	@Override
	public boolean aumentaContador(int idDenuncia, String email) {
		boolean result;
		try {
			Denuncia dominio = new Denuncia();
			dominio.setIdDenuncia(idDenuncia);
			dominio.setEmailUsuario(email);
			result = servicioDenuncia.addDenuncia(dominio);
		} catch (ServicioException e) {
			return false;
		}
		return result;
	}

	@Override
	public String getImage(int idDenuncia) {
		String imagen;
		try {
			imagen = servicioDenuncia.getImage(idDenuncia);
		} catch (ServicioException e) {
			return null;
		}
		return imagen;
	}

	@Override
	public ListadoDenunciasDTO findWithPeriod(BigDecimal latitud,
			BigDecimal longitud, String ic, int pt) {
		ListadoDenunciasDTO dto = new ListadoDenunciasDTO();
		try {
			dto.setIs(Constantes.ESTATUS_EXITOSO);
			dto.setDs(Constantes.DSC_ESTATUS_EXITOSO);
			List<Denuncia> listDenuncia = servicioDenuncia
					.findByDateAndLocation(latitud, longitud, pt);
			dto.setLd(getDenuncias(listDenuncia, null, ic));
		} catch (ServicioException e) {
			return null;
		}
		return dto;
	}

	@Override
	public int save(int idCatDenuncia, String descripcion, String direccion,
			String email, String imagen, BigDecimal latitud, BigDecimal longitud) {
		try {
			Denuncia dominio = new Denuncia();
			CatCategoriaDenuncia cat = new CatCategoriaDenuncia();
			cat.setIdCatDenuncia(idCatDenuncia);
			dominio.setCatCategoriaDenuncia(cat);
			dominio.setDescripcion(descripcion);
			dominio.setDireccion(direccion);
			dominio.setEmailUsuario(email);
			dominio.setFechaHoraActCont(new Date());
			dominio.setFechaHoraDenuncia(new Date());
			dominio.setLatitud(latitud);
			dominio.setLongitud(longitud);
			dominio.setContador(0);
			String imagePath = servicioDenuncia.saveImage(imagen);
			dominio.setImgPath(imagePath);
			int pk = servicioDenuncia.save(dominio);
			return pk;
		} catch (ServicioException e) {
			return 0;
		}

	}

	private List<IntervaloTiempoDTO> getIntervalos(List<Configuracion> list) {
		List<IntervaloTiempoDTO> result = new ArrayList<IntervaloTiempoDTO>();
		list.forEach(x -> result.add(TransformUtils.transformIntervaloTiempo(x)));
		return result;
	}

	private List<DenunciaDTO> getDenuncias(List<CatCategoriaDenuncia> list) {
		List<DenunciaDTO> result = new ArrayList<DenunciaDTO>();
		list.forEach(x -> result.add(TransformUtils.transformDenuncia(x)));
		return result;
	}

	private List<DenunciaItemListaDTO> getDenuncias(List<Denuncia> list,
			String ds, String ic) {
		List<DenunciaItemListaDTO> result = new ArrayList<DenunciaItemListaDTO>();
		if (list == null || CollectionUtils.isEmpty(list)) {
			return null;
		}
		list.forEach(x -> result.add(TransformUtils
				.transformDenuncia(x, ds, ic)));
		return result;
	}

}
