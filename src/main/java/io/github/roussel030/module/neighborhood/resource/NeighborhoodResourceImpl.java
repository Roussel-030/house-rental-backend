package io.github.roussel030.module.neighborhood.resource;

import io.github.roussel030.module.neighborhood.dto.NeighborhoodRequest;
import io.github.roussel030.module.neighborhood.dto.NeighborhoodResponse;
import io.github.roussel030.module.neighborhood.service.NeighborhoodService;
import io.github.roussel030.shared.dto.PageResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class NeighborhoodResourceImpl implements NeighborhoodResource {

    private final NeighborhoodService neighborhoodService;

    public NeighborhoodResourceImpl(NeighborhoodService neighborhoodService) {
        this.neighborhoodService = neighborhoodService;
    }

    @Override
    public Response createNeighborhood(@Valid NeighborhoodRequest request) {
        NeighborhoodResponse response = neighborhoodService.createNeighborhood(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @Override
    public Response getNeighborhoods(String search, int page, int size) {
        PageResponse<NeighborhoodResponse> responses = neighborhoodService.getNeighborhoods(search, page, size);
        return Response.ok(responses).build();
    }

    @Override
    public Response updateNeighborhood(Long id, @Valid NeighborhoodRequest request) {
        NeighborhoodResponse response = neighborhoodService.updateNeighborhood(id, request);
        return Response.ok(response).build();
    }

    @Override
    public Response deleteNeighborhood(Long id) {
        neighborhoodService.deleteNeighborhood(id);
        return Response.noContent().build();
    }

}
