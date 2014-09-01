package com.upiicsa.denuncia.service;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.upiicsa.denuncia.common.Denuncia;
import com.upiicsa.denuncia.util.Constant;

public class RequestMessage {
	private static final String LOG_TAG = "Service";
	private HttpHandler httpHandler;
	private OnResponseListener responder;

	public RequestMessage(OnResponseListener responder) {
		this.responder = responder;
	}

	public void configService() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("i", "1");
		String json = jsonObject.toString();
		httpTaskRequest(json, Constant.CONFIG_URL);
	}

	public void findComplaintsService(Denuncia denuncia) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		String lat = String.valueOf(denuncia.getLatitud());
		String lng = String.valueOf(denuncia.getLongitud());
		jsonObject.put("i", denuncia.getIdOperacion());
		jsonObject.put("ic", denuncia.getIdCategoria());
		jsonObject.put("dc", denuncia.getDescripcion());
		jsonObject.put("la", lat);
		jsonObject.put("lo", lng);
		String json = jsonObject.toString();
		httpTaskRequest(json, Constant.LIST_URL);
	}

	public void newComplaintService(Denuncia nueva, String message)
			throws JSONException {
		JSONObject object = new JSONObject();
		String lat = String.valueOf(nueva.getLatitud());
		String lng = String.valueOf(nueva.getLongitud());
		object.put("i", nueva.getIdOperacion());
		object.put("ic", nueva.getIdCategoria());
		object.put("ds", nueva.getDescripcion());
		object.put("em", nueva.getCorreo());
		object.put("dd", nueva.getDireccion());
		object.put("la", lat);
		object.put("lo", lng);
		if (nueva.getImagen() != null) {
			object.put("im", nueva.getImagen());
		}
		String json = object.toString();
		httpTaskRequest(json, Constant.SAVE_URL);
	}

	public void selectComplaintService(Denuncia denuncia) throws JSONException {
		JSONObject object = new JSONObject();
		object.put("i", denuncia.getIdOperacion());
		object.put("ic", denuncia.getIdCategoria());
		object.put("em", denuncia.getCorreo());
		object.put("id", denuncia.getIdDenuncia());
		String json = object.toString();
		httpTaskRequest(json, Constant.UPDATE_URL);
	}

	public void consultComplaintService(Denuncia denuncia) throws JSONException {
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
		httpTaskRequest(json, Constant.CONSULT_URL);
	}

	public void showDetailService(Denuncia denuncia) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("i", denuncia.getIdOperacion());
		jsonObject.put("id", denuncia.getIdDenuncia());
		String json = jsonObject.toString();
		httpTaskRequest(json, Constant.DETAIL_URL);
	}

	public void httpTaskRequest(final String json, final String url) {
		Log.d(LOG_TAG, "*****url:" + url);
		Log.d(LOG_TAG, "*****json:" + json);

		httpHandler = new HttpHandler() {
			@Override
			public HttpUriRequest getHttpRequestMethod() {
				HttpPost httpPost = new HttpPost(url);
				try {
					StringEntity stringEntity = new StringEntity(json);
					httpPost.setEntity(stringEntity);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return httpPost;
			}

			@Override
			public void onResponse(String message) {
				Log.d(LOG_TAG, "*****message:" + message);
				try {
					if (message != null) {
						JSONObject jsonObject = new JSONObject(message);
						String code = jsonObject.getString("is");
						if (code.equals("01")) {
							responder.onSuccess(message);
						} else {
							String description = jsonObject.getString("ds");
							responder.onFailure(description);
						}
					} else {
						responder
								.onFailure("No se pudo establecer conexión con el servidor.");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		httpHandler.execute();
	}

}
