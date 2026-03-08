package io.github.roussel030.module.option.service;

import io.github.roussel030.module.option.dto.OptionRequest;
import io.github.roussel030.module.option.dto.OptionResponse;
import io.github.roussel030.shared.dto.PageResponse;

public interface OptionService {

    OptionResponse createOption(OptionRequest request);
    PageResponse<OptionResponse> getOptions(String search, int page, int size);
    OptionResponse updateOption(Long id, OptionRequest request);
    void deleteOption(Long id);

}
