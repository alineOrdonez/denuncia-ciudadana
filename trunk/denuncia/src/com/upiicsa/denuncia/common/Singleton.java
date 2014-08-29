package com.upiicsa.denuncia.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.util.SparseArray;

import com.upiicsa.denuncia.util.Util;

public class Singleton {

	private static List<CatDenuncia> denuncias;
	private static List<CatIntTiempo> intervalos;
	private static List<Denuncia> items;
	private static SparseArray<Denuncia> item_map;
	private static String image;

	private static Singleton instance = null;

	protected Singleton() {
	}

	public static Singleton getInstance() {
		if (instance == null) {
			instance = new Singleton();
			denuncias = new ArrayList<CatDenuncia>();
			intervalos = new ArrayList<CatIntTiempo>();
			image = new String();
			items = new ArrayList<Denuncia>();
		}
		return instance;
	}

	public void listaDeDenuncias(String string) throws JSONException {
		if (!items.isEmpty() && item_map != null) {
			items.clear();
			item_map.clear();
		}
		List<Map<String, Object>> mapStr = Util.jsonToList(string, null);
		for (int i = 0; i < mapStr.size(); i++) {
			Denuncia item = new Denuncia(mapStr.get(i));
			items.add(item);
			item_map.put(item.getIdCategoria(), item);
		}

	}

	public void addItems(Denuncia denuncia) {
		items.add(denuncia);
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
		return items;
	}

	/**
	 * @param iTEMS
	 *            the iTEMS to set
	 */
	public void setITEMS(List<Denuncia> iTEMS) {
		items = iTEMS;
	}

	/**
	 * @return the item_map
	 */
	public SparseArray<Denuncia> getItem_map() {
		return item_map;
	}

	/**
	 * @param item_map
	 *            the item_map to set
	 */
	public void setItem_map(SparseArray<Denuncia> item_map) {
		Singleton.item_map = item_map;
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
