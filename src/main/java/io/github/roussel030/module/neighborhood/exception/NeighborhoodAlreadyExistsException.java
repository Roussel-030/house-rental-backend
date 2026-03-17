package io.github.roussel030.module.neighborhood.exception;

import io.github.roussel030.shared.exception.BusinessException;
import jakarta.ws.rs.core.Response;

public class NeighborhoodAlreadyExistsException extends BusinessException {

    public NeighborhoodAlreadyExistsException(String message) {
        super(message, Response.Status.CONFLICT);
    }

}
