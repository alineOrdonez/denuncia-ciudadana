package com.upiicsa.denuncia.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upiicsa.denuncia.util.Util;

public class DenunciaContent {

	public DenunciaContent(String string) {
		listaDeDenuncias(string);
	}

	/**
	 * An array of sample (dummy) items.
	 */
	public static List<Denuncia> ITEMS;

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<Integer, Denuncia> ITEM_MAP;

	private void listaDeDenuncias(String string) {
		ITEMS = new ArrayList<Denuncia>();
		ITEM_MAP = new HashMap<Integer, Denuncia>();
		List<String> stringList = Util.stringToList(string);
		for (int i = 0; i < stringList.size(); i++) {
			List<String> list = Util.detailList(stringList.get(i));
			Denuncia item = new Denuncia();
			int id = Integer.valueOf(list.get(0));
			item.setIdDenuncia(id);
			item.setDescripcion(list.get(1));
			item.setCorreo(list.get(2));
			item.setDireccion(list.get(3));
			double latitud = Double.valueOf(list.get(4));
			item.setLatitud(latitud);
			double longitud = Double.valueOf(list.get(5));
			item.setLongitud(longitud);
			ITEMS.add(item);
			ITEM_MAP.put(id, item);
		}
	}
}
