package com.upiicsa.denuncia.util;

import org.json.JSONException;

public interface OnResponseListener {

	public void onSuccess(String message) throws JSONException;

	public void onFailure(String message) throws JSONException;
}