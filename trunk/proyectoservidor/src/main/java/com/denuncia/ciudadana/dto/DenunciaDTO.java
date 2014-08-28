package com.denuncia.ciudadana.dto;

import java.io.Serializable;

public class DenunciaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int is;
	private String ds;
	
	/**
	 * @return the is
	 */
	public int getIs() {
		return is;
	}
	/**
	 * @param is the is to set
	 */
	public void setIs(int is) {
		this.is = is;
	}
	/**
	 * @return the ds
	 */
	public String getDs() {
		return ds;
	}
	/**
	 * @param ds the ds to set
	 */
	public void setDs(String ds) {
		this.ds = ds;
	}
}
