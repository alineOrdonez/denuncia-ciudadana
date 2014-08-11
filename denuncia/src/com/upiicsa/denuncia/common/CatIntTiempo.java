package com.upiicsa.denuncia.common;

import java.io.Serializable;

public class CatIntTiempo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idCatIntTiempo;
	private String descripcion;

	public CatIntTiempo() {
	}

	/**
	 * @param idCatIntTiempo
	 * @param descripcion
	 */
	public CatIntTiempo(int idCatIntTiempo, String descripcion) {
		this.idCatIntTiempo = idCatIntTiempo;
		this.descripcion = descripcion;
	}

	/**
	 * @return the idCatIntTiempo
	 */
	public int getIdCatIntTiempo() {
		return idCatIntTiempo;
	}

	/**
	 * @param idCatIntTiempo
	 *            the idCatIntTiempo to set
	 */
	public void setIdCatIntTiempo(int idCatIntTiempo) {
		this.idCatIntTiempo = idCatIntTiempo;
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
