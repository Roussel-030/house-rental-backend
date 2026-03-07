package io.github.roussel030.module.city.resource;

import io.github.roussel030.module.city.dto.CityRequest;
import io.github.roussel030.module.city.dto.CityResponse;
import io.github.roussel030.module.city.service.CityService;
import io.github.roussel030.shared.dto.PageResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class CityResourceImpl implements CityResource {

    private final CityService cityService;

    public CityResourceImpl(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public Response createCity(@Valid CityRequest request) {
        CityResponse response = cityService.createCity(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @Override
    public Response getCities(int page, int size) {
        PageResponse<CityResponse> responses = cityService.getCities(page, size);
        return Response.ok(responses).build();
    }

    @Override
    public Response updateCity(Long id, @Valid CityRequest request) {
        CityResponse response = cityService.updateCity(id, request);
        return Response.ok(response).build();
    }

    @Override
    public Response deleteCity(Long id) {
        cityService.deleteCity(id);
        return Response.noContent().build();
    }

}
