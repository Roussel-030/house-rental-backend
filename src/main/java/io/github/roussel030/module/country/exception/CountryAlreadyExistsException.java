package io.github.roussel030.module.country.exception;

import io.github.roussel030.shared.exception.BusinessException;
import jakarta.ws.rs.core.Response;

public class CountryAlreadyExistsException extends BusinessException {

    public CountryAlreadyExistsException(String message) {
        super(message, Response.Status.CONFLICT);
    }

}
