package io.github.roussel030.module.city.service;

import io.github.roussel030.module.city.dto.CityRequest;
import io.github.roussel030.module.city.dto.CityResponse;
import io.github.roussel030.shared.dto.PageResponse;

public interface CityService {

    CityResponse createCity(CityRequest request);
    PageResponse<CityResponse> getCities(String search, int page, int size);
    CityResponse updateCity(Long id, CityRequest request);
    void deleteCity(Long id);

}
