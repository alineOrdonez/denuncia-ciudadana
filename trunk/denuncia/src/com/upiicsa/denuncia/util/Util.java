package com.upiicsa.denuncia.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Util {

	public static boolean isConnected(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}

	public static JSONObject convertStringtoJson(String json)
			throws JSONException {
		JSONObject jsonObject = new JSONObject(json);
		return jsonObject;
	}

	public static JSONObject convertMapToJson(Map<String, Object> map)
			throws JSONException {
		JSONObject obj = new JSONObject();
		Set<String> set = map.keySet();
		Iterator<String> iter = set.iterator();

		while (iter.hasNext()) {
			String key = (String) iter.next();
			obj.accumulate(key, map.get(key));
		}

		return obj;
	}

	public static List<String> convertStringToList(String string) {
		int length = string.length();
		String str = string.substring(1, length - 1);
		List<String> list = new ArrayList<String>(Arrays.asList(str.split(",")));
		return list;
	}

	public static List<Object> categories() throws JSONException {
		List<Object> list = new ArrayList<Object>();
		String[] descripcion = new String[] { "Fuga de agua", "Incendio",
				"Robo" };
		for (int i = 1; i < 4; i++) {
			Map<String, String> obj = new HashMap<String, String>();
			String s = String.valueOf(i);
			obj.put("id", s);
			obj.put("descripcion", descripcion[i - 1]);
			list.add(obj);
		}
		return list;
	}

	public static List<Object> range() throws JSONException {
		List<Object> list = new ArrayList<Object>();
		String[] descripcion = new String[] { "2 hrs.", "4 hrs.", "12 hrs." };
		for (int i = 1; i < 4; i++) {
			Map<String, String> obj = new HashMap<String, String>();
			String s = String.valueOf(i);
			obj.put("id", s);
			obj.put("descripcion", descripcion[i - 1]);
			list.add(obj);
		}
		return list;
	}
}
