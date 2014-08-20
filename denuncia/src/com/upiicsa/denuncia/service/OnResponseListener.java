package com.upiicsa.denuncia.service;

import org.json.JSONException;

public interface OnResponseListener {

	public void onSuccess(String message) throws JSONException;

	public void onFailure(String message) throws JSONException;
}