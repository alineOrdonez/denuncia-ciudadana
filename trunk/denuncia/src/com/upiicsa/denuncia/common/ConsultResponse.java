package com.upiicsa.denuncia.common;

import java.io.Serializable;
import java.util.List;

public class ConsultResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ls;
	private String ds;
	private List<String> ld;

	/**
	 * @param ls
	 * @param ds
	 * @param ld
	 */
	public ConsultResponse(String ls, String ds, List<String> ld) {
		this.ls = ls;
		this.ds = ds;
		this.ld = ld;
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

	/**
	 * @return the ld
	 */
	public List<String> getLd() {
		return ld;
	}

	/**
	 * @param ld
	 *            the ld to set
	 */
	public void setLd(List<String> ld) {
		this.ld = ld;
	}

}
