package io.github.roussel030.option.domain;

import io.github.roussel030.option.api.dto.OptionRequest;
import io.github.roussel030.option.api.dto.OptionResponse;
import io.github.roussel030.shared.dto.PageResponse;

public interface OptionService {

    OptionResponse createOption(OptionRequest request);
    PageResponse<OptionResponse> getOptions(int page, int size);
    OptionResponse updateOption(Long id, OptionRequest request);
    void deleteOption(Long id);

}
