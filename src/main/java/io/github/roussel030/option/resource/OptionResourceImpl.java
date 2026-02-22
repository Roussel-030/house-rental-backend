package io.github.roussel030.option.resource;

import io.github.roussel030.option.dto.OptionRequest;
import io.github.roussel030.option.dto.OptionResponse;
import io.github.roussel030.option.service.OptionService;
import io.github.roussel030.shared.dto.PageResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class OptionResourceImpl implements OptionResource {

    private final OptionService optionService;

    public OptionResourceImpl(OptionService optionService) {
        this.optionService = optionService;
    }

    @Override
    public Response createOption(@Valid OptionRequest request) {
        OptionResponse response = optionService.createOption(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @Override
    public Response getOptions(int page, int size) {
        PageResponse<OptionResponse> responses = optionService.getOptions(page, size);
        return Response.ok(responses).build();
    }

    @Override
    public Response updateOption(Long id, @Valid OptionRequest request) {
        OptionResponse response = optionService.updateOption(id, request);
        return Response.ok(response).build();
    }

    @Override
    public Response deleteOption(Long id) {
        optionService.deleteOption(id);
        return Response.noContent().build();
    }

}
