package io.github.roussel030.currency.exception;

import io.github.roussel030.shared.exception.BusinessException;
import jakarta.ws.rs.core.Response;

public class CurrencyAlreadyExistsException extends BusinessException {

    public CurrencyAlreadyExistsException(String message) {
        super(message, Response.Status.CONFLICT);
    }

}
