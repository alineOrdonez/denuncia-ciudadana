package com.upiicsa.denuncia.util;

import java.util.ArrayList;
import java.util.Arrays;
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

	public static JSONObject convertMapToJson(Map<String, String> map)
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

	public static List<String> convertStringToArray(String string) {
		int length = string.length();
		String str = string.substring(1, length - 1);
		List<String> list = new ArrayList<String>(Arrays.asList(str.split(",")));
		return list;
	}

	public static List<String> categories() {
		List<String> list = new ArrayList<String>();
		list.add("Fuga de agua");
		list.add("Incendio");
		list.add("Robo");
		return list;
	}

	public static List<String> range() {
		List<String> list = new ArrayList<String>();
		list.add("Hace 2 hrs.");
		list.add("Hace 4 hrs.");
		list.add("Hace 12 hrs.");
		return list;
	}
}
