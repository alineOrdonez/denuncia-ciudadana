package com.upiicsa.denuncia.common;

import java.io.Serializable;

public class Denuncia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idDenuncia;
	private int idOperacion;
	private int idCategoria;
	private int idCatIntTiempo;
	private String descripcion;
	private String correo;
	private String imagen;
	private String direccion;
	private double latitud;
	private double longitud;

	public Denuncia() {
	}

	/**
	 * @param idOperacion
	 * @param idCategoria
	 * @param descripcion
	 * @param latitud
	 * @param longitud
	 */
	public Denuncia(int idCategoria, String descripcion, double latitud,
			double longitud) {
		this.idOperacion = 2;
		this.idCategoria = idCategoria;
		this.descripcion = descripcion;
		this.latitud = latitud;
		this.longitud = longitud;
	}

	/**
	 * @param idOperacion
	 * @param idCategoria
	 * @param descripcion
	 * @param correo
	 * @param imagen
	 * @param direccion
	 * @param latitud
	 * @param longitud
	 */
	public Denuncia(int idCategoria, String descripcion, String correo,
			String imagen, String direccion, double latitud, double longitud) {
		this.idOperacion = 3;
		this.idCategoria = idCategoria;
		this.descripcion = descripcion;
		this.correo = correo;
		this.imagen = imagen;
		this.direccion = direccion;
		this.latitud = latitud;
		this.longitud = longitud;
	}

	/**
	 * @param idDenuncia
	 * @param idOperacion
	 * @param idCategoria
	 * @param correo
	 */
	public Denuncia(int idDenuncia, int idCategoria, String correo) {
		this.idDenuncia = idDenuncia;
		this.idOperacion = 4;
		this.idCategoria = idCategoria;
		this.correo = correo;
	}

	/**
	 * @param idOperacion
	 * @param idCategoria
	 * @param idCatIntTiempo
	 * @param descripcion
	 * @param latitud
	 * @param longitud
	 */
	public Denuncia(int idCategoria, int idCatIntTiempo, String descripcion,
			double latitud, double longitud) {
		this.idOperacion = 5;
		this.idCategoria = idCategoria;
		this.idCatIntTiempo = idCatIntTiempo;
		this.descripcion = descripcion;
		this.latitud = latitud;
		this.longitud = longitud;
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
	 * @return the idOperacion
	 */
	public int getIdOperacion() {
		return idOperacion;
	}

	/**
	 * @param idOperacion
	 *            the idOperacion to set
	 */
	public void setIdOperacion(int idOperacion) {
		this.idOperacion = idOperacion;
	}

	/**
	 * @return the idCategoria
	 */
	public int getIdCategoria() {
		return idCategoria;
	}

	/**
	 * @param idCategoria
	 *            the idCategoria to set
	 */
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	/**
	 * @return the idCatIntTiempo
	 */
	public int getIdCatIntTiempo() {
		return idCatIntTiempo;
	}

	/**
	 * @param idCatIntTiempo
	 *            the idCatIntTiempo to set
	 */
	public void setIdCatIntTiempo(int idCatIntTiempo) {
		this.idCatIntTiempo = idCatIntTiempo;
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
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * @param correo
	 *            the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * @return the imagen
	 */
	public String getImagen() {
		return imagen;
	}

	/**
	 * @param imagen
	 *            the imagen to set
	 */
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion
	 *            the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the latitud
	 */
	public double getLatitud() {
		return latitud;
	}

	/**
	 * @param latitud
	 *            the latitud to set
	 */
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	/**
	 * @return the longitud
	 */
	public double getLongitud() {
		return longitud;
	}

	/**
	 * @param longitud
	 *            the longitud to set
	 */
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

}
