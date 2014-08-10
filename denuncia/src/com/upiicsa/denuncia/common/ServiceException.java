package com.upiicsa.denuncia.common;

import android.util.Log;

import com.upiicsa.denuncia.util.Constants;

public class ServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor sin argumentos, para una excepcion generica
	 */
	public ServiceException() {
		super(Constants.ERROR_GENERICO);
		Log.d("", null);
	}

	/**
	 * Constructor con mensaje descriptivo como parametro
	 * 
	 * @param msg
	 *            -Mensaje descriptivo de la excepcion
	 */
	public ServiceException(String msg) {
		super(msg);
		Log.d("", null);
	}

	/**
	 * Constructor con argumentos
	 * 
	 * @param msg
	 *            - Mensaje descriptivo
	 * @param cause
	 *            - Error que origina la excepcion
	 */
	public ServiceException(String msg, Throwable cause) {
		super(msg, cause);
		Log.d("", null);
	}
}
