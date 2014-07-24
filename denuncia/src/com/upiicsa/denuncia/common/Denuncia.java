package com.upiicsa.denuncia.common;

import java.io.Serializable;

public class Denuncia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idDenuncia;
	private String descripcion;
	private String direcci�n;
	private String emailUsuario;

	/**
	 * @param idDenuncia
	 * @param descripcion
	 * @param direcci�n
	 * @param emailUsuario
	 */
	public Denuncia(int idDenuncia, String descripcion, String direcci�n,
			String emailUsuario) {
		this.idDenuncia = idDenuncia;
		this.descripcion = descripcion;
		this.direcci�n = direcci�n;
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
	 * @return the direcci�n
	 */
	public String getDirecci�n() {
		return direcci�n;
	}

	/**
	 * @param direcci�n
	 *            the direcci�n to set
	 */
	public void setDirecci�n(String direcci�n) {
		this.direcci�n = direcci�n;
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
