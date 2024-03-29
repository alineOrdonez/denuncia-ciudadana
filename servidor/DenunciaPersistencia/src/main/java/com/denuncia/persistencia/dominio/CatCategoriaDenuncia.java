package com.denuncia.persistencia.dominio;

// Generated 24/08/2014 12:41:26 AM by Hibernate Tools 3.6.0

import java.util.HashSet;
import java.util.Set;

/**
 * CatCategoriaDenuncia generated by hbm2java
 */
public class CatCategoriaDenuncia implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idCatDenuncia;
	private String descripcion;
	private short envioEmail;
	private Set<InstConDel> instConDels = new HashSet<InstConDel>(0);
	private Set<Denuncia> denuncias = new HashSet<Denuncia>(0);

	public CatCategoriaDenuncia() {
	}

	public CatCategoriaDenuncia(String descripcion, short envioEmail) {
		this.descripcion = descripcion;
		this.envioEmail = envioEmail;
	}

	public CatCategoriaDenuncia(Integer idCatDenuncia, String descripcion,
			short envioEmail, Set<InstConDel> instConDels,
			Set<Denuncia> denuncias) {
		this.idCatDenuncia = idCatDenuncia;
		this.descripcion = descripcion;
		this.envioEmail = envioEmail;
		this.instConDels = instConDels;
		this.denuncias = denuncias;
	}

	public Integer getIdCatDenuncia() {
		return this.idCatDenuncia;
	}

	public void setIdCatDenuncia(Integer idCatDenuncia) {
		this.idCatDenuncia = idCatDenuncia;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public short getEnvioEmail() {
		return this.envioEmail;
	}

	public void setEnvioEmail(short envioEmail) {
		this.envioEmail = envioEmail;
	}

	public Set<InstConDel> getInstConDels() {
		return instConDels;
	}

	public void setInstConDels(Set<InstConDel> instConDels) {
		this.instConDels = instConDels;
	}

	public Set<Denuncia> getDenuncias() {
		return denuncias;
	}

	public void setDenuncias(Set<Denuncia> denuncias) {
		this.denuncias = denuncias;
	}

}
