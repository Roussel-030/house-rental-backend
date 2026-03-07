package io.github.roussel030.module.city.exception;

import io.github.roussel030.shared.exception.BusinessException;
import jakarta.ws.rs.core.Response;

public class CityAlreadyExistsException extends BusinessException {

    public CityAlreadyExistsException(String message) {
        super(message, Response.Status.CONFLICT);
    }

}
