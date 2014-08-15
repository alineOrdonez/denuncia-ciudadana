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
	public static List<Denuncia> ITEMS = new ArrayList<Denuncia>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<Integer, Denuncia> ITEM_MAP = new HashMap<Integer, Denuncia>();

	private void listaDeDenuncias(String string) {
		List<String> stringList = Util.stringToList(string);
		for (int i = 0; i < stringList.size(); i++) {
			List<String> list = Util.detailList(stringList.get(i));
			Denuncia item = new Denuncia();
			int id = Integer.valueOf(list.get(0));
			item.setIdDenuncia(id);
			item.setDescripcion(list.get(1));
			item.setCorreo(list.get(2));
			item.setDireccion(list.get(3));
			ITEMS.add(item);
			ITEM_MAP.put(id, item);
		}
	}
}
