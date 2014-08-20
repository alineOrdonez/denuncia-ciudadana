package com.upiicsa.denuncia.service;

import org.apache.http.client.methods.HttpUriRequest;
import com.upiicsa.denuncia.service.GeneralHttpTask;

public abstract class HttpHandler {

	public abstract HttpUriRequest getHttpRequestMethod();

	public abstract void onResponse(String result);

	public void execute() {
		new GeneralHttpTask(this).execute();
	}
}
