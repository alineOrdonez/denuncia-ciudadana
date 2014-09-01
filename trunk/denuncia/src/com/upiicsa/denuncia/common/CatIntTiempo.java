package com.upiicsa.denuncia.common;

import java.io.Serializable;
import java.util.Map;

public class CatIntTiempo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idCatIntTiempo;
	private String descripcion;
	private String valor;

	public CatIntTiempo() {
	}

	public CatIntTiempo(Map<String, Object> map) {
		this.idCatIntTiempo = Integer.valueOf((String) map.get("id"));
		this.descripcion = (String) map.get("ds");
		this.valor = (String) map.get("va");
	}

	/**
	 * @param idCatIntTiempo
	 * @param descripcion
	 */
	public CatIntTiempo(int idCatIntTiempo, String descripcion, String valor) {
		this.idCatIntTiempo = idCatIntTiempo;
		this.descripcion = descripcion;
		this.valor = valor;
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

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

}
