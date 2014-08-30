/**
 * 
 */
package com.denuncia.ciudadana.util;

import com.denuncia.ciudadana.dominio.CatCategoriaDenuncia;
import com.denuncia.ciudadana.dominio.Configuracion;
import com.denuncia.ciudadana.dominio.Denuncia;
import com.denuncia.ciudadana.dto.DenunciaDTO;
import com.denuncia.ciudadana.dto.DenunciaItemListaDTO;
import com.denuncia.ciudadana.dto.IntervaloTiempoDTO;

/**
 * @author Aline Ordoñez
 *
 */
public class TransformUtils {

	public static DenunciaDTO transformDenuncia(CatCategoriaDenuncia dominio) {
		DenunciaDTO dto = new DenunciaDTO();
		dto.setId(dominio.getIdCatDenuncia());
		dto.setDs(dominio.getDescripcion());
		return dto;
	}

	public static IntervaloTiempoDTO transformIntervaloTiempo(
			Configuracion dominio) {
		IntervaloTiempoDTO dto = new IntervaloTiempoDTO();
		dto.setId(dominio.getIdConfiguracion());
		dto.setDs(dominio.getDescripcion());
		dto.setVa(dominio.getValor());
		return dto;
	}

	public static DenunciaItemListaDTO transformDenuncia(Denuncia dominio,
			String ds, String ic) {
		CatCategoriaDenuncia cat = dominio.getCatCategoriaDenuncia();
		DenunciaItemListaDTO dto = new DenunciaItemListaDTO();
		dto.setDd(dominio.getDireccion());
		dto.setDs(dominio.getDescripcion());
		dto.setEm(dominio.getEmailUsuario());
		dto.setIc(String.valueOf(cat.getIdCatDenuncia()));
		dto.setLa(String.valueOf(dominio.getLatitud()));
		dto.setLo(String.valueOf(dominio.getLongitud()));
		dto.setId(String.valueOf(dominio.getIdDenuncia()));
		dto.setIc(ic);
		return dto;
	}

}
