package io.github.roussel030.module.country.exception;

import io.github.roussel030.shared.exception.BusinessException;
import jakarta.ws.rs.core.Response;

public class CountryNotFoundException extends BusinessException {

    public CountryNotFoundException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }

}
