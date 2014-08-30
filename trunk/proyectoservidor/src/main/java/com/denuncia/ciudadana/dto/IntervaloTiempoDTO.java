package com.denuncia.ciudadana.dto;

import java.io.Serializable;

public class IntervaloTiempoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String ds;
	private String va;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}

	/**
	 * @return the va
	 */
	public String getVa() {
		return va;
	}

	/**
	 * @param va
	 *            the va to set
	 */
	public void setVa(String va) {
		this.va = va;
	}
}
