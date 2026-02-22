package io.github.roussel030.currency.exception;

import io.github.roussel030.shared.exception.BusinessException;
import jakarta.ws.rs.core.Response;

public class CurrencyNotFoundException extends BusinessException {

    public CurrencyNotFoundException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }

}
