package io.github.roussel030.module.option.exception;

import io.github.roussel030.shared.exception.BusinessException;
import jakarta.ws.rs.core.Response;

public class OptionNotFoundException extends BusinessException {

    public OptionNotFoundException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }

}
