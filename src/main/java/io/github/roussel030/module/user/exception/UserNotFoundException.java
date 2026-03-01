package io.github.roussel030.module.user.exception;

import io.github.roussel030.shared.exception.BusinessException;
import jakarta.ws.rs.core.Response;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }

}
