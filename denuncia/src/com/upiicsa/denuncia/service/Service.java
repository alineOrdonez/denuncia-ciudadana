package com.upiicsa.denuncia.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.upiicsa.denuncia.common.Denuncia;
import com.upiicsa.denuncia.util.HttpHandler;
import com.upiicsa.denuncia.util.Util;

public class Service {
	private TaskCompleted taskCompleted;
	private HttpHandler httpHandler;

	public Service(TaskCompleted task) {
		this.taskCompleted = task;
	}

	public void configService() throws JSONException {
		Map<String, Object> configMap = new HashMap<String, Object>();
		configMap.put("i", "01");
		JSONObject jsonObject = Util.convertMapToJson(configMap);
		String json = jsonObject.toString();
		setRequest(json);
	}

	public void findComplaintsService(Denuncia denuncia) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();
		String lat = String.valueOf(denuncia.getLatitud());
		String lng = String.valueOf(denuncia.getLongitud());
		map.put("i", denuncia.getIdOperacion());
		map.put("ic", denuncia.getIdCategoria());
		map.put("dc", denuncia.getDescripcion());
		map.put("la", lat);
		map.put("lo", lng);
		JSONObject jsonObject = Util.convertMapToJson(map);
		String json = jsonObject.toString();
		setRequest(json);
	}

	public void newComplaintService(Denuncia nueva) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();
		String lat = String.valueOf(nueva.getLatitud());
		String lng = String.valueOf(nueva.getLongitud());
		map.put("i", nueva.getIdOperacion());
		map.put("ic", nueva.getIdCategoria());
		map.put("dc", nueva.getDescripcion());
		map.put("em", nueva.getCorreo());
		map.put("im", nueva.getImagen());
		map.put("dd", nueva.getDireccion());
		map.put("la", lat);
		map.put("lo", lng);
		JSONObject jsonObject = Util.convertMapToJson(map);
		String json = jsonObject.toString();
		setRequest(json);
	}

	public void selectComplaintService(Denuncia denuncia) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("i", denuncia.getIdOperacion());
		map.put("ic", denuncia.getIdCategoria());
		map.put("em", denuncia.getCorreo());
		map.put("ic", denuncia.getIdDenuncia());
		JSONObject jsonObject = Util.convertMapToJson(map);
		String json = jsonObject.toString();
		setRequest(json);
	}

	public void consultComplaintService(Denuncia denuncia) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();
		String lat = String.valueOf(denuncia.getLatitud());
		String lng = String.valueOf(denuncia.getLongitud());
		map.put("i", denuncia.getIdOperacion());
		map.put("ic", denuncia.getIdCategoria());
		map.put("dc", denuncia.getDescripcion());
		map.put("pt", denuncia.getIdCatIntTiempo());
		map.put("la", lat);
		map.put("lo", lng);
		JSONObject jsonObject = Util.convertMapToJson(map);
		String json = jsonObject.toString();
		setRequest(json);
	}

	private void setRequest(final String json) throws JSONException {
		httpHandler = new HttpHandler() {
			@Override
			public HttpUriRequest getHttpRequestMethod() {
				// 1. make POST request to the given URL
				HttpPost httpPost = new HttpPost(
						"http://hmkcode.com/examples/index.php");
				try {
					// 2. set json to StringEntity
					StringEntity stringEntity = new StringEntity(json);
					// 3. set httpPost Entity
					httpPost.setEntity(stringEntity);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return httpPost;
			}

			@Override
			public void onResponse(String result) {
				try {
					result = Util.configResult();
					//Util.complaintResult();
					taskCompleted.onTaskComplete(result);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		httpHandler.execute();
	}
}
