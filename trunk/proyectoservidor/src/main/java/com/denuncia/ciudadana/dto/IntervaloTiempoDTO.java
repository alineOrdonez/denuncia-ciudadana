package com.denuncia.ciudadana.dto;

import java.io.Serializable;

public class IntervaloTiempoDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int is;
	private String ds;
	private String va;
	
	public int getIs() {
		return is;
	}
	public void setIs(int is) {
		this.is = is;
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
	 * @param va the va to set
	 */
	public void setVa(String va) {
		this.va = va;
	}
}
