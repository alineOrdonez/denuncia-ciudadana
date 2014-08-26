package com.denuncia.persistencia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenceException extends Exception {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersistenceException.class);

	public PersistenceException() {
		super();
		LOGGER.error("Constants.ERROR_GENERAL_MSG_LOGGER");
	}

	public PersistenceException(String msg) {
		super(msg);
		LOGGER.error("Constants.ERROR_GENERAL_MSG_LOGGER");
		LOGGER.error(msg);
	}

	public PersistenceException(String msg, Throwable cause) {
		super(msg, cause);
		LOGGER.error("Constants.ERROR_GENERAL_MSG_LOGGER");
		LOGGER.error(msg, cause);
	}
}
