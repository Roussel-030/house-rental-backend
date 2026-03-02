package io.github.roussel030.module.country.resource;

import io.github.roussel030.module.country.dto.CountryRequest;
import io.github.roussel030.module.country.dto.CountryResponse;
import io.github.roussel030.module.country.service.CountryService;
import io.github.roussel030.shared.dto.PageResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class CountryResourceImpl implements CountryResource {

    private final CountryService countryService;

    public CountryResourceImpl(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    public Response createCountry(@Valid CountryRequest request) {
        CountryResponse response = countryService.createCountry(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @Override
    public Response getCountries(int page, int size) {
        PageResponse<CountryResponse> responses = countryService.getCountries(page, size);
        return Response.ok(responses).build();
    }

    @Override
    public Response updateCountry(Long id, @Valid CountryRequest request) {
        CountryResponse response = countryService.updateCountry(id, request);
        return Response.ok(response).build();
    }

    @Override
    public Response deleteCountry(Long id) {
        countryService.deleteCountry(id);
        return Response.noContent().build();
    }

}
