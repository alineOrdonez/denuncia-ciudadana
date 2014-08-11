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
		String str = string.replaceAll("[\\[{}\\]]", "");
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

	public static List<Object> denuncias() throws JSONException {
		List<Object> list = new ArrayList<Object>();
		String[] descripcion = new String[] {
				"Incendio en la casa del vecino.",
				"El basurero de la esquina se quema.", "Fuego en el parque." };
		String[] email = new String[] { "me@mail.com", "me@mail.com",
				"me@mail.com" };
		String[] direccion = new String[] { "fsdfsdfsd", "xfsdfsdf",
				"sdfsdfsdfsd" };
		for (int i = 1; i < 4; i++) {
			Map<String, String> obj = new HashMap<String, String>();
			String s = String.valueOf(i);
			obj.put("id", s);
			obj.put("descripcion", descripcion[i - 1]);
			obj.put("email", email[i - 1]);
			obj.put("direccion", direccion[i - 1]);
			list.add(obj);
		}
		return list;
	}

	public static String configResult() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("is", "01");
		json.put("ds", "Exitoso");
		json.put("ld", categories());
		json.put("lt", range());
		return json.toString();
	}

	public static String complaintResult() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("is", "01");
		json.put("ds", "Exitoso");
		json.put("ld", denuncias());
		return json.toString();
	}
}
