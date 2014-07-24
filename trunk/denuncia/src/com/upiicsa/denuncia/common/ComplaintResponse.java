package com.upiicsa.denuncia.common;

import java.io.Serializable;
import java.util.List;

public class ComplaintResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String is;
	private String ds;
	private List<String> ld;

	/**
	 * @param is
	 * @param ds
	 * @param ld
	 */
	public ComplaintResponse(String is, String ds, List<String> ld) {
		this.is = is;
		this.ds = ds;
		this.ld = ld;
	}

	/**
	 * @return the is
	 */
	public String getIs() {
		return is;
	}

	/**
	 * @param is
	 *            the is to set
	 */
	public void setIs(String is) {
		this.is = is;
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
