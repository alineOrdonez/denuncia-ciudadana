package com.upiicsa.denuncia.util;

import org.apache.http.client.methods.HttpUriRequest;

import com.upiicsa.denuncia.common.HttpAsyncTask;

public abstract class HttpHandler {

	public abstract HttpUriRequest getHttpRequestMethod();

	public abstract void onResponse(String result);

	public void execute() {
		new HttpAsyncTask(this).execute();
	}
}
