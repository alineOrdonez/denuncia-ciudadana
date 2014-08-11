package com.upiicsa.denuncia.service;

import org.json.JSONException;


public interface TaskCompleted {
	// Define data you like to return from AysncTask
	public void onTaskComplete(String result) throws JSONException;
}
