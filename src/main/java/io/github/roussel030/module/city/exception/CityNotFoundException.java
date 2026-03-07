package io.github.roussel030.module.city.exception;

import io.github.roussel030.shared.exception.BusinessException;
import jakarta.ws.rs.core.Response;

public class CityNotFoundException extends BusinessException {

    public CityNotFoundException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }

}
