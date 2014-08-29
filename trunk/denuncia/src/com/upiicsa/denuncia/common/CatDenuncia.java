package com.upiicsa.denuncia.common;

import java.io.Serializable;
import java.util.Map;

public class CatDenuncia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idCatDenuncia;
	private String descripcion;

	public CatDenuncia() {
	}

	public CatDenuncia(Map<String, Object> map) {
		this.idCatDenuncia = (Integer) map.get("id");
		this.descripcion = (String) map.get("ds");
	}

	/**
	 * @param idCatDenuncia
	 * @param descripcion
	 */
	public CatDenuncia(int idCatDenuncia, String descripcion) {
		this.idCatDenuncia = idCatDenuncia;
		this.descripcion = descripcion;
	}

	/**
	 * @return the idCatDenuncia
	 */
	public int getIdCatDenuncia() {
		return idCatDenuncia;
	}

	/**
	 * @param idCatDenuncia
	 *            the idCatDenuncia to set
	 */
	public void setIdCatDenuncia(int idCatDenuncia) {
		this.idCatDenuncia = idCatDenuncia;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
