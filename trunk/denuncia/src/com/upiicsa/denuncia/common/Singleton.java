package com.upiicsa.denuncia.common;

import java.util.ArrayList;
import java.util.List;

public class Singleton {

	private static List<CatDenuncia> denuncias;
	private static List<CatIntTiempo> intervalos;

	private static Singleton instance = null;

	protected Singleton() {
		// Exists only to defeat instantiation.
	}

	public static Singleton getInstance() {
		if (instance == null) {
			instance = new Singleton();
			denuncias = new ArrayList<CatDenuncia>();
			intervalos = new ArrayList<CatIntTiempo>();
		}
		return instance;
	}

	/**
	 * @return the denuncias
	 */
	public static List<CatDenuncia> getDenuncias() {
		return denuncias;
	}

	/**
	 * @param denuncias
	 *            the denuncias to set
	 */
	public static void setDenuncias(List<CatDenuncia> denuncias) {
		Singleton.denuncias = denuncias;
	}

	/**
	 * @return the intervalos
	 */
	public static List<CatIntTiempo> getIntervalos() {
		return intervalos;
	}

	/**
	 * @param intervalos
	 *            the intervalos to set
	 */
	public static void setIntervalos(List<CatIntTiempo> intervalos) {
		Singleton.intervalos = intervalos;
	}

}
