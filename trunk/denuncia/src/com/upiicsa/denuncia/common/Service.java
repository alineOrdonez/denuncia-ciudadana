package com.upiicsa.denuncia.common;

import java.util.Map;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.upiicsa.denuncia.util.HttpHandler;
import com.upiicsa.denuncia.util.Util;

public class Service {
	private TaskCompleted callback;
	private HttpHandler httpHandler;

	public Service(Context context) {
		this.callback = (TaskCompleted) context;
	}

	public void setRequest(final Map<String, String> configMap)
			throws JSONException {
		httpHandler = new HttpHandler() {
			JSONObject jsonObject = Util.convertMapToJson(configMap);
			@Override
			public HttpUriRequest getHttpRequestMethod() {
				// 1. make POST request to the given URL
				HttpPost httpPost = new HttpPost(
						"http://hmkcode.com/examples/index.php");
				try {
					// 2. convert JSONObject to String
					String json = jsonObject.toString();
					// 3. set json to StringEntity
					StringEntity stringEntity = new StringEntity(json);
					// 4. set httpPost Entity
					httpPost.setEntity(stringEntity);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return httpPost;
			}

			@Override
			public void onResponse(String result) {
				try {
					// TODO:
					JSONObject json = new JSONObject();
					json.put("is", "01");
					json.put("ds", "Exitoso");
					json.put("ld", Util.categories());
					json.put("lt", Util.range());
					callback.onTaskComplete(json.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		httpHandler.execute();
	}

}
