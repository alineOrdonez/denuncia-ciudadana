package com.upiicsa.denuncia.service;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.upiicsa.denuncia.common.Denuncia;

public class Service {
	private static final String LOG_TAG = "Service";
	private OnResponseListener responder;
	private Context context;

	/**
	 * @param responder
	 * @param context
	 */
	public Service(OnResponseListener responder, Context context) {
		this.responder = responder;
		this.context = context;
	}

	public void configService(String message) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("i", "1");
		String json = jsonObject.toString();
		httpTaskRequest(json, message);
	}

	public void findComplaintsService(Denuncia denuncia, String message)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		String lat = String.valueOf(denuncia.getLatitud());
		String lng = String.valueOf(denuncia.getLongitud());
		jsonObject.put("i", denuncia.getIdOperacion());
		jsonObject.put("ic", denuncia.getIdCategoria());
		jsonObject.put("dc", denuncia.getDescripcion());
		jsonObject.put("la", lat);
		jsonObject.put("lo", lng);
		String json = jsonObject.toString();
		httpTaskRequest(json, message);
	}

	public void newComplaintService(Denuncia nueva, String message)
			throws JSONException {
		JSONObject object = new JSONObject();
		String lat = String.valueOf(nueva.getLatitud());
		String lng = String.valueOf(nueva.getLongitud());
		object.put("i", nueva.getIdOperacion());
		object.put("ic", nueva.getIdCategoria());
		object.put("dc", nueva.getDescripcion());
		object.put("em", nueva.getCorreo());
		object.put("dd", nueva.getDireccion());
		object.put("la", lat);
		object.put("lo", lng);
		object.put("im", nueva.getImagen());
		String json = object.toString();
		httpTaskRequest(json, message);
	}

	public void selectComplaintService(Denuncia denuncia, String message)
			throws JSONException {
		JSONObject object = new JSONObject();
		object.put("i", denuncia.getIdOperacion());
		object.put("ic", denuncia.getIdCategoria());
		object.put("em", denuncia.getCorreo());
		object.put("ic", denuncia.getIdDenuncia());
		String json = object.toString();
		httpTaskRequest(json, message);
	}

	public void consultComplaintService(Denuncia denuncia, String message)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		String lat = String.valueOf(denuncia.getLatitud());
		String lng = String.valueOf(denuncia.getLongitud());
		jsonObject.put("i", denuncia.getIdOperacion());
		jsonObject.put("ic", denuncia.getIdCategoria());
		jsonObject.put("dc", denuncia.getDescripcion());
		jsonObject.put("pt", denuncia.getIdCatIntTiempo());
		jsonObject.put("la", lat);
		jsonObject.put("lo", lng);
		String json = jsonObject.toString();
		httpTaskRequest(json, message);
	}

	public void httpTaskRequest(String json, String progressMessage) {
		GeneralHttpTask recoveryTask = new GeneralHttpTask(context,
				progressMessage, responder);
		Log.d(LOG_TAG, "*****httpTaskRequest:" + json);
		recoveryTask.execute(json);
	}
}
