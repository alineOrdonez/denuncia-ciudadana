package com.denuncia.ciudadana.dto;

import java.io.Serializable;
import java.util.List;

public class ResultadoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String is;
	private String ds;
	private List<IntervaloTiempoDTO> lt;
	private List<DenunciaDTO> ld;
	
	/**
	 * @return the is
	 */
	public String getIs() {
		return is;
	}
	/**
	 * @param is the is to set
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
	 * @param ds the ds to set
	 */
	public void setDs(String ds) {
		this.ds = ds;
	}
	/**
	 * @return the lt
	 */
	public List<IntervaloTiempoDTO> getLt() {
		return lt;
	}
	/**
	 * @param lt the lt to set
	 */
	public void setLt(List<IntervaloTiempoDTO> lt) {
		this.lt = lt;
	}
	/**
	 * @return the ld
	 */
	public List<DenunciaDTO> getLd() {
		return ld;
	}
	/**
	 * @param ld the ld to set
	 */
	public void setLd(List<DenunciaDTO> ld) {
		this.ld = ld;
	}
	
	
}
