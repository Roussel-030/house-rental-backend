package io.github.roussel030.currency.resource;

import io.github.roussel030.currency.dto.CurrencyRequest;
import io.github.roussel030.currency.dto.CurrencyResponse;
import io.github.roussel030.currency.service.CurrencyService;
import io.github.roussel030.shared.dto.PageResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class CurrencyResourceImpl implements CurrencyResource {

    private final CurrencyService currencyService;

    public CurrencyResourceImpl(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public Response createCurrency(@Valid CurrencyRequest request) {
        CurrencyResponse response = currencyService.createCurrency(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @Override
    public Response getCurrencies(int page, int size) {
        PageResponse<CurrencyResponse> responses = currencyService.getCurrencies(page, size);
        return Response.ok(responses).build();
    }

    @Override
    public Response updateCurrency(Long id, @Valid CurrencyRequest request) {
        CurrencyResponse response = currencyService.updateCurrency(id, request);
        return Response.ok(response).build();
    }

    @Override
    public Response deleteCurrency(Long id) {
        currencyService.deleteCurrency(id);
        return Response.noContent().build();
    }

}
