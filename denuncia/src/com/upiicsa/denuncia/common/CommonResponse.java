package com.upiicsa.denuncia.common;

import java.io.Serializable;

public class CommonResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ls;
	private String ds;

	/**
	 * @param ls
	 * @param ds
	 */
	public CommonResponse(String ls, String ds) {
		this.ls = ls;
		this.ds = ds;
	}

	/**
	 * @return the ls
	 */
	public String getLs() {
		return ls;
	}

	/**
	 * @param ls
	 *            the ls to set
	 */
	public void setLs(String ls) {
		this.ls = ls;
	}

	/**
	 * @return the ds
	 */
	public String getDs() {
		return ds;
	}

	/**
	 * @param ds
	 *            the ds to set
	 */
	public void setDs(String ds) {
		this.ds = ds;
	}

}
