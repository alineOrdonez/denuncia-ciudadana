package com.denuncia.ciudadana.fachada;

import java.math.BigDecimal;

import com.denuncia.ciudadana.dto.ListadoDenunciasDTO;
import com.denuncia.ciudadana.dto.ResultadoDTO;

public interface DenunciaFachada {

	ResultadoDTO loadInitialConfig();

	ListadoDenunciasDTO findNear(BigDecimal latitud, BigDecimal longitud,
			String ds, String ic);

	int save(int idCatDenuncia, String direccion, String email, String imagen,
			BigDecimal latitud, BigDecimal longitud);

	boolean aumentaContador(int idDenuncia);

}
