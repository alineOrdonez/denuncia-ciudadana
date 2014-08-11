package com.upiicsa.denuncia.common;

import java.io.Serializable;
import java.util.List;

public class ConfigurationResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String is;
	private String ds;
	private List<CatDenuncia> ld;
	private List<CatIntTiempo> lt;
	
	public ConfigurationResponse() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param is
	 * @param ds
	 * @param ld
	 * @param lt
	 */
	public ConfigurationResponse(String is, String ds, List<CatDenuncia> ld,
			List<CatIntTiempo> lt) {
		super();
		this.is = is;
		this.ds = ds;
		this.ld = ld;
		this.lt = lt;
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
	public List<CatDenuncia> getLd() {
		return ld;
	}

	/**
	 * @param ld
	 *            the ld to set
	 */
	public void setLd(List<CatDenuncia> ld) {
		this.ld = ld;
	}

	/**
	 * @return the lt
	 */
	public List<CatIntTiempo> getLt() {
		return lt;
	}

	/**
	 * @param lt
	 *            the lt to set
	 */
	public void setLt(List<CatIntTiempo> lt) {
		this.lt = lt;
	}

}
