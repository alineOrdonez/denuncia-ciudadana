package com.upiicsa.denuncia.service;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.upiicsa.denuncia.common.Denuncia;
import com.upiicsa.denuncia.util.Util;

public class Service {
	private static final String LOG_TAG = "Service";
	private HttpHandler httpHandler;
	private OnResponseListener responder;

	/**
	 * @param responder
	 * @param context
	 */
	public Service(OnResponseListener responder) {
		this.responder = responder;
	}

	public void configService(String message) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("i", "1");
		String json = jsonObject.toString();
		httpTaskRequest(json);
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
		httpTaskRequest(json);
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
		if (nueva.getImagen() != null) {
			object.put("im", nueva.getImagen());
		}
		String json = object.toString();
		httpTaskRequest(json);
	}

	public void selectComplaintService(Denuncia denuncia) throws JSONException {
		JSONObject object = new JSONObject();
		object.put("i", denuncia.getIdOperacion());
		object.put("ic", denuncia.getIdCategoria());
		object.put("em", denuncia.getCorreo());
		object.put("ic", denuncia.getIdDenuncia());
		String json = object.toString();
		httpTaskRequest(json);
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
		httpTaskRequest(json);
	}

	public void httpTaskRequest(final String json) {
		Log.d(LOG_TAG, "*****httpTaskRequest:" + json);

		httpHandler = new HttpHandler() {
			@Override
			public HttpUriRequest getHttpRequestMethod() {

				HttpPost httpPost = new HttpPost(
						"http://hmkcode.com/examples/index.php");
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
				try {
					message = Util.configResult();
					JSONObject jsonObject = new JSONObject(message);
					String code = jsonObject.getString("is");

					if (message != null && code.equals("01")) {
						jsonObject.remove("is");
						String result = json;// jsonObject.toString();
						responder.onSuccess(result);
					} else {
						String description = jsonObject.getString("ds");
						responder.onFailure(description);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		httpHandler.execute();
	}

}
