package io.github.roussel030.module.activationToken.exception;

import io.github.roussel030.shared.exception.BusinessException;
import jakarta.ws.rs.core.Response;

public class ActivationTokenInvalidException extends BusinessException {

    public ActivationTokenInvalidException(String message) {
        super(message, Response.Status.BAD_REQUEST);
    }

}
