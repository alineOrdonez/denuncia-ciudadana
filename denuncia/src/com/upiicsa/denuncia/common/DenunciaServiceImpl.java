package com.upiicsa.denuncia.common;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import net.sf.json.JSONObject;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import com.upiicsa.denuncia.util.Constants;
import com.upiicsa.denuncia.util.HttpHandler;
import com.upiicsa.denuncia.util.Util;

public class DenunciaServiceImpl implements DenunciaService {

	private JSONObject jsonResult;

	@Override
	public JSONObject setRequest(HashMap<String, Object> map) {
		// 1. build jsonObject
		final JSONObject jsonObject = Util.convertMapToJson(map);
		new HttpHandler() {
			@Override
			public HttpUriRequest getHttpRequestMethod() {
				// 2. make POST request to the given URL
				HttpPost httpPost = new HttpPost(Constants.URL);
				try {
					// 3. convert JSONObject to String
					String json = jsonObject.toString();
					// 4. set json to StringEntity
					StringEntity stringEntity = new StringEntity(json);
					// 5. set httpPost Entity
					httpPost.setEntity(stringEntity);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return httpPost;
			}

			@Override
			public void onResponse(String result) {
				jsonResult = Util.convertStringtoJson(result);
			}

		}.execute();
		return jsonResult;
	}

}
