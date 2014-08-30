package com.denuncia.ciudadana.dto;

import java.io.Serializable;

public class DenunciaItemListaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String ic;
	private String ds;
	private String em;
	private String dd;
	private String la;
	private String lo;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the ic
	 */
	public String getIc() {
		return ic;
	}

	/**
	 * @param ic
	 *            the ic to set
	 */
	public void setIc(String ic) {
		this.ic = ic;
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
	 * @return the em
	 */
	public String getEm() {
		return em;
	}

	/**
	 * @param em
	 *            the em to set
	 */
	public void setEm(String em) {
		this.em = em;
	}

	/**
	 * @return the dd
	 */
	public String getDd() {
		return dd;
	}

	/**
	 * @param dd
	 *            the dd to set
	 */
	public void setDd(String dd) {
		this.dd = dd;
	}

	/**
	 * @return the la
	 */
	public String getLa() {
		return la;
	}

	/**
	 * @param la
	 *            the la to set
	 */
	public void setLa(String la) {
		this.la = la;
	}

	/**
	 * @return the lo
	 */
	public String getLo() {
		return lo;
	}

	/**
	 * @param lo
	 *            the lo to set
	 */
	public void setLo(String lo) {
		this.lo = lo;
	}

}
