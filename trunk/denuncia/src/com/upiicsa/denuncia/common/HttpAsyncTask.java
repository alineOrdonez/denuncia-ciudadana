package com.upiicsa.denuncia.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

import com.upiicsa.denuncia.util.HttpHandler;

public class HttpAsyncTask extends AsyncTask<String, Void, String> {

	private HttpHandler httpHandler;

	/**
	 * @param httpHandler
	 */
	public HttpAsyncTask(HttpHandler httpHandler) {
		this.httpHandler = httpHandler;
	}

	@Override
	protected String doInBackground(String... params) {
		String result = null;
		try {
			// 1. create HttpClient
			HttpClient httpclient = new DefaultHttpClient();
			// 2. make the http request
			HttpResponse httpResponse = httpclient.execute(httpHandler
					.getHttpRequestMethod());
			// 3. receive response as inputStream
			InputStream inputStream = httpResponse.getEntity().getContent();
			// 4. convert inputstream to string
			if (inputStream != null) {
				result = convertInputStreamToString(inputStream);
			} else {
				result = "Did not work!";
			}
		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}

		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		httpHandler.onResponse(result);

	}

	private static String convertInputStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
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
