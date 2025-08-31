package io.github.roussel030.option.api;

import io.github.roussel030.option.api.dto.OptionRequest;
import io.github.roussel030.option.api.dto.OptionResponse;
import io.github.roussel030.option.domain.OptionService;
import io.github.roussel030.shared.dto.PageResponse;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

public class OptionResource {

    private final OptionService optionService;

    public OptionResource(OptionService optionService) {
        this.optionService = optionService;
    }

    @POST
    public Response createOption(@Valid OptionRequest request) {
        OptionResponse response = optionService.createOption(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    public Response getOptions(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size
    ) {
        PageResponse<OptionResponse> response = optionService.getOptions(page, size);
        return Response.ok(response).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateOption(
            @PathParam("id") Long id,
            @Valid OptionRequest request
    ) {
        OptionResponse response = optionService.updateOption(id, request);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOption(@PathParam("id") Long id) {
        optionService.deleteOption(id);
        return Response.noContent().build();
    }

}
