package com.upiicsa.denuncia.common;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class Util {

	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> convertJsonToMap(String json) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		Object object = JSONObject.toBean(jsonObject, Map.class);
		HashMap<String, Object> map = (HashMap<String, Object>) object;
		return map;
	}

	public static JSONObject convertMapToJson(HashMap<String, Object> map) {
		JSONObject jsonObject = JSONObject.fromObject(map);
		return jsonObject;
	}

	public static JSONObject convertStringtoJson(String json) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		return jsonObject;
	}
}
