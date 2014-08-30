package com.denuncia.ciudadana.dto;

import java.io.Serializable;
import java.util.List;

public class ListadoDenunciasDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String is;
	private String ds;
	private List<DenunciaItemListaDTO> ld;
	private String im;

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
	public List<DenunciaItemListaDTO> getLd() {
		return ld;
	}

	/**
	 * @param ld
	 *            the ld to set
	 */
	public void setLd(List<DenunciaItemListaDTO> ld) {
		this.ld = ld;
	}

	/**
	 * @return the im
	 */
	public String getIm() {
		return im;
	}

	/**
	 * @param im
	 *            the im to set
	 */
	public void setIm(String im) {
		this.im = im;
	}

}
