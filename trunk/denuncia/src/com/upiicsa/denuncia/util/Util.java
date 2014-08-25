package com.upiicsa.denuncia.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Util {

	public static List<String> stringToList(String string) {
		String s = string.substring(2, string.length() - 2);
		String[] split = s.split("\\], \\[");
		List<String> list = Arrays.asList(split);
		return list;
	}

	public static List<String> detailList(String row) {
		String string = row.trim();
		String[] split = string.split(", ");
		List<String> list = Arrays.asList(split);
		return list;
	}

	public static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0) {
			return false;
		}
		int i = 0;
		if (str.charAt(0) == '-') {
			if (length == 1) {
				return false;
			}
			i = 1;
		}
		for (; i < length; i++) {
			char c = str.charAt(i);
			if (c <= '/' || c >= ':') {
				return false;
			}
		}
		return true;
	}

	public static boolean isValidEmail(String email) {
		String regularExpression = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+"
				+ "(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
				+ "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+"
				+ "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
		return email.matches(regularExpression) ? true : false;
	}

	public static List<Object> categories() throws JSONException {
		List<Object> list = new ArrayList<Object>();
		String[] descripcion = new String[] { "Fuga de agua", "Incendio",
				"Robo" };
		for (int i = 1; i < 4; i++) {
			List<Object> obj = new ArrayList<Object>();
			obj.add(i);
			obj.add(descripcion[i - 1]);
			list.add(obj);
		}
		return list;
	}

	public static List<Object> range() throws JSONException {
		List<Object> list = new ArrayList<Object>();
		String[] descripcion = new String[] { "2 hrs.", "4 hrs.", "12 hrs." };
		for (int i = 1; i < 4; i++) {
			List<Object> obj = new ArrayList<Object>();
			obj.add(i);
			obj.add(descripcion[i - 1]);
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
		String[] direccion = new String[] { "Hortaliza No. 49", "Wetter 16",
				"Homero No 12" };
		double[] latitude = new double[] { 19.3952204, 19.4952204, 19.4952204 };
		double[] longitude = new double[] { -99.0907235, -99.1917235,
				-99.2907235, -98.292741 };
		for (int i = 1; i < 4; i++) {
			List<Object> obj = new ArrayList<Object>();
			obj.add(i);
			obj.add(1);
			obj.add(descripcion[i - 1]);
			obj.add(email[i - 1]);
			obj.add(direccion[i - 1]);
			// Para consulta
			obj.add(latitude[i - 1]);
			obj.add(longitude[i - 1]);
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

	public static String sendComplaintAgain() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("is", "01");
		json.put("ds", "Exitoso");
		json.put("ld", denuncias());
		return json.toString();
	}
}
