package io.github.roussel030.shared.handler;

import io.github.roussel030.shared.exception.BusinessException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BusinessExceptionHandler implements ExceptionMapper<BusinessException> {

  @Override
  public Response toResponse(BusinessException e) {
    return Response.status(e.getStatus())
            .entity(e.getMessage())
            .build();
  }

}

