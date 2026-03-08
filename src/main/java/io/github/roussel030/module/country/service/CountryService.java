package io.github.roussel030.module.country.service;

import io.github.roussel030.module.country.dto.CountryRequest;
import io.github.roussel030.module.country.dto.CountryResponse;
import io.github.roussel030.shared.dto.PageResponse;

public interface CountryService {

    CountryResponse createCountry(CountryRequest request);
    PageResponse<CountryResponse> getCountries(String search, int page, int size);
    CountryResponse updateCountry(Long id, CountryRequest request);
    void deleteCountry(Long id);

}
