package io.github.roussel030.module.neighborhood.service;

import io.github.roussel030.module.neighborhood.dto.NeighborhoodRequest;
import io.github.roussel030.module.neighborhood.dto.NeighborhoodResponse;
import io.github.roussel030.shared.dto.PageResponse;

public interface NeighborhoodService {

    NeighborhoodResponse createNeighborhood(NeighborhoodRequest request);
    PageResponse<NeighborhoodResponse> getNeighborhoods(String search, int page, int size);
    NeighborhoodResponse updateNeighborhood(Long id, NeighborhoodRequest request);
    void deleteNeighborhood(Long id);

}
