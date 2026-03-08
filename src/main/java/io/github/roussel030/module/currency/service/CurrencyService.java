package io.github.roussel030.module.currency.service;

import io.github.roussel030.module.currency.dto.CurrencyRequest;
import io.github.roussel030.module.currency.dto.CurrencyResponse;
import io.github.roussel030.shared.dto.PageResponse;

public interface CurrencyService {

    CurrencyResponse createCurrency(CurrencyRequest request);
    PageResponse<CurrencyResponse> getCurrencies(String search, int page, int size);
    CurrencyResponse updateCurrency(Long id, CurrencyRequest request);
    void deleteCurrency(Long id);

}
