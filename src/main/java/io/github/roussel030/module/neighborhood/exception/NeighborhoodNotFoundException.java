package io.github.roussel030.module.neighborhood.exception;

import io.github.roussel030.shared.exception.BusinessException;
import jakarta.ws.rs.core.Response;

public class NeighborhoodNotFoundException extends BusinessException {

    public NeighborhoodNotFoundException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }

}
