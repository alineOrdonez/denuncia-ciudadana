package com.denuncia.ciudadana.servicio;

import java.util.List;

import com.denuncia.ciudadana.dominio.CatCategoriaDenuncia;

public interface CategoriaDenunciaServicio {

	List<CatCategoriaDenuncia> findAllCategorias() throws ServicioException;

}
