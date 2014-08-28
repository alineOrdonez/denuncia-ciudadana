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
 * @author amaro
 *
 */
public class TransformUtils {

	public static DenunciaDTO transformDenuncia(CatCategoriaDenuncia dominio){
		DenunciaDTO dto = new DenunciaDTO();
			dto.setIs(dominio.getIdCatDenuncia());
			dto.setDs(dominio.getDescripcion());
		return dto;
	}
	
	public static IntervaloTiempoDTO transformIntervaloTiempo(Configuracion dominio){
		IntervaloTiempoDTO dto = new IntervaloTiempoDTO();
			dto.setIs(dominio.getIdConfiguracion());
			dto.setDs(dominio.getDescripcion());
			dto.setVa(dominio.getValor());
		return dto;
	}
	
	public static DenunciaItemListaDTO transformDenuncia(Denuncia dominio, String ds, String ic){
		DenunciaItemListaDTO dto = new DenunciaItemListaDTO();
			dto.setDd(dominio.getDireccion());
			dto.setDs(ds);
			dto.setEm(dominio.getEmailUsuario());
			dto.setId(String.valueOf(dominio.getIdDenuncia()));
			dto.setIc(ic);
		return dto;
	}
	
}
