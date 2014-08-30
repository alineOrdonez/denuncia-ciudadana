/*
 * Peycash 2014 - All rights reserved
 */
package com.denuncia.ciudadana.servicio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception for services tier
 * @author Aline Ordoñez
 *
 */
public class ServicioException extends Exception{

	/**
     * Serial
     */
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ServicioException.class);

    
    public ServicioException() {
        super();
        LOGGER.error("Error in service tier");
    }

    public ServicioException(String msg) {
        super(msg);
        LOGGER.error(msg);
    }

    public ServicioException(String msg, Throwable cause) {
        super(msg, cause);
        LOGGER.error(msg, cause);
    }
}
