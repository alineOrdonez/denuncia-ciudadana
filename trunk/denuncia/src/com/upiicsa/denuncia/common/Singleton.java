package com.upiicsa.denuncia.common;

import java.util.ArrayList;
import java.util.List;

import android.util.SparseArray;

import com.upiicsa.denuncia.util.Util;

public class Singleton {

	private static List<CatDenuncia> denuncias;
	private static List<CatIntTiempo> intervalos;
	private static List<Denuncia> ITEMS;
	private static SparseArray<Denuncia> ITEM_MAP;
	private static String image;
	private static Denuncia den;

	private static Singleton instance = null;

	protected Singleton() {
	}

	public static Singleton getInstance() {
		if (instance == null) {
			instance = new Singleton();
			denuncias = new ArrayList<CatDenuncia>();
			intervalos = new ArrayList<CatIntTiempo>();
			image = new String();
			ITEMS = new ArrayList<Denuncia>();
			ITEM_MAP = new SparseArray<Denuncia>();
		}
		return instance;
	}

	public void listaDeDenuncias(String string) {
		if (!ITEMS.isEmpty() && ITEM_MAP != null) {
			ITEMS.clear();
			ITEM_MAP.clear();
		}
		List<String> stringList = Util.stringToList(string);
		for (int i = 0; i < stringList.size(); i++) {
			List<String> list = Util.detailList(stringList.get(i));
			Denuncia item = new Denuncia();
			int idDenuncia = Integer.valueOf(list.get(0));
			item.setIdDenuncia(idDenuncia);
			int idCategoria = Integer.valueOf(list.get(1));
			item.setIdCategoria(idCategoria);
			item.setDescripcion(list.get(2));
			item.setCorreo(list.get(3));
			item.setDireccion(list.get(4));
			double latitud = Double.valueOf(list.get(5));
			item.setLatitud(latitud);
			double longitud = Double.valueOf(list.get(6));
			item.setLongitud(longitud);
			ITEMS.add(item);
			ITEM_MAP.put(idDenuncia, item);
		}
		if (den != null) {
			ITEMS.add(den);
			ITEM_MAP.put(4, den);
		}
	}

	public void addItems(Denuncia denuncia) {
		den = denuncia;
		ITEMS.add(denuncia);
		ITEM_MAP.put(4, denuncia);
	}

	/**
	 * @return the denuncias
	 */
	public List<CatDenuncia> getDenuncias() {
		return denuncias;
	}

	/**
	 * @param denuncias
	 *            the denuncias to set
	 */
	public void setDenuncias(List<CatDenuncia> denuncias) {
		Singleton.denuncias = denuncias;
	}

	/**
	 * @return the intervalos
	 */
	public List<CatIntTiempo> getIntervalos() {
		return intervalos;
	}

	/**
	 * @param intervalos
	 *            the intervalos to set
	 */
	public void setIntervalos(List<CatIntTiempo> intervalos) {
		Singleton.intervalos = intervalos;
	}

	/**
	 * @return the iTEMS
	 */
	public List<Denuncia> getITEMS() {
		return ITEMS;
	}

	/**
	 * @param iTEMS
	 *            the iTEMS to set
	 */
	public void setITEMS(List<Denuncia> iTEMS) {
		ITEMS = iTEMS;
	}

	/**
	 * @return the iTEM_MAP
	 */
	public SparseArray<Denuncia> getITEM_MAP() {
		return ITEM_MAP;
	}

	/**
	 * @param iTEM_MAP
	 *            the iTEM_MAP to set
	 */
	public void setITEM_MAP(SparseArray<Denuncia> iTEM_MAP) {
		ITEM_MAP = iTEM_MAP;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(String image) {
		Singleton.image = image;
	}

}
