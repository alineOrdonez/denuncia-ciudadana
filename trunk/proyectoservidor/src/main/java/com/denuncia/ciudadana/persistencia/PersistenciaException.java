package com.denuncia.ciudadana.persistencia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenciaException extends Exception{

	/**
     * Serial
     */
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenciaException.class);

    public PersistenciaException() {
        super();
        LOGGER.error("Constants.ERROR_GENERAL_MSG_LOGGER");
    }

    public PersistenciaException(String msg) {
        super(msg);
        LOGGER.error("Constants.ERROR_GENERAL_MSG_LOGGER");
        LOGGER.error(msg);
    }

    public PersistenciaException(String msg, Throwable cause) {
        super(msg, cause);
        LOGGER.error("Constants.ERROR_GENERAL_MSG_LOGGER");
        LOGGER.error(msg, cause);
    }
}
