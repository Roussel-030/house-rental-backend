package io.github.roussel030.category.exception;

import io.github.roussel030.shared.exception.BusinessException;
import jakarta.ws.rs.core.Response;

public class CategoryNotFoundException extends BusinessException {

    public CategoryNotFoundException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }

}
