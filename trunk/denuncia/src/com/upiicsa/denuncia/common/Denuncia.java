package com.upiicsa.denuncia.common;

import java.io.Serializable;

public class Denuncia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idDenuncia;
	private String descripcion;
	private String dirección;
	private String emailUsuario;

	/**
	 * @param idDenuncia
	 * @param descripcion
	 * @param dirección
	 * @param emailUsuario
	 */
	public Denuncia(int idDenuncia, String descripcion, String dirección,
			String emailUsuario) {
		this.idDenuncia = idDenuncia;
		this.descripcion = descripcion;
		this.dirección = dirección;
		this.emailUsuario = emailUsuario;
	}

	/**
	 * @return the idDenuncia
	 */
	public int getIdDenuncia() {
		return idDenuncia;
	}

	/**
	 * @param idDenuncia
	 *            the idDenuncia to set
	 */
	public void setIdDenuncia(int idDenuncia) {
		this.idDenuncia = idDenuncia;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the dirección
	 */
	public String getDirección() {
		return dirección;
	}

	/**
	 * @param dirección
	 *            the dirección to set
	 */
	public void setDirección(String dirección) {
		this.dirección = dirección;
	}

	/**
	 * @return the emailUsuario
	 */
	public String getEmailUsuario() {
		return emailUsuario;
	}

	/**
	 * @param emailUsuario
	 *            the emailUsuario to set
	 */
	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

}
