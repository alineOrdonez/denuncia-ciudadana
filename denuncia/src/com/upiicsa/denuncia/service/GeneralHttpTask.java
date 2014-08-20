package com.upiicsa.denuncia.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.upiicsa.denuncia.util.Util;

import android.content.Context;
import android.os.AsyncTask;

public class GeneralHttpTask extends AsyncTask<String, Integer, String> {
	private android.app.ProgressDialog progressDialog;
	private OnResponseListener responder;
	private int responseCode;

	public GeneralHttpTask(android.app.ProgressDialog progressDialog,
			OnResponseListener responder) {
		this.progressDialog = progressDialog;
		this.responder = responder;
	}

	public GeneralHttpTask(Context context, String progressMessage,
			OnResponseListener responder) {
		this.progressDialog = new ProgressDialog(context, progressMessage);
		this.responder = responder;
	}

	@Override
	protected void onPreExecute() {
		progressDialog.show();
	}

	@Override
	protected String doInBackground(String... params) {
		int attemptsCount;
		responseCode = 0;
		String result = null;
		try {
			attemptsCount = 1;

			HttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(
					"http://hmkcode.com/examples/index.php");
			StringEntity stringEntity = new StringEntity(params[0]);
			httpPost.setEntity(stringEntity);

			int executeCount = 0;
			HttpResponse httpResponse;
			do {
				publishProgress(executeCount + 1, attemptsCount);
				// Execute HTTP Post Request
				executeCount++;
				httpResponse = client.execute(httpPost);
				responseCode = httpResponse.getStatusLine().getStatusCode();
				InputStream inputStream = httpResponse.getEntity().getContent();
				// 4. convert inputstream to string
				if (inputStream != null) {
					result = convertInputStreamToString(inputStream);
				} else {
					result = "Did not work!";
				}
			} while (executeCount < attemptsCount && responseCode == 408);

		} catch (HttpHostConnectException e) {
			responseCode = 408;
		} catch (Exception e) {
			responseCode = 400;
			e.printStackTrace();
		}
		// return responseCode == desiredCode;
		return result;
	}

	@Override
	protected void onPostExecute(String message) {
		if (this.progressDialog.isShowing()) {
			this.progressDialog.dismiss();
		}
		try {
			message = Util.configResult();
			JSONObject jsonObject = new JSONObject(message);
			String code = jsonObject.getString("is");

			if (message != null && code.equals("01")) {
				jsonObject.remove("is");
				String result = jsonObject.toString();
				responder.onSuccess(result);
			} else {
				String description = jsonObject.getString("ds");
				responder.onFailure(description);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public class ProgressDialog extends android.app.ProgressDialog {
		public ProgressDialog(Context context, String progressMessage) {
			super(context);
			setCancelable(false);
			setMessage(progressMessage);
		}

	}

	private static String convertInputStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}