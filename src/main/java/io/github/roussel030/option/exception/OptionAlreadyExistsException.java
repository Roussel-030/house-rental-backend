package io.github.roussel030.option.exception;

import io.github.roussel030.shared.exception.BusinessException;
import jakarta.ws.rs.core.Response;

public class OptionAlreadyExistsException extends BusinessException {

    public OptionAlreadyExistsException(String message) {
        super(message, Response.Status.CONFLICT);
    }

}
