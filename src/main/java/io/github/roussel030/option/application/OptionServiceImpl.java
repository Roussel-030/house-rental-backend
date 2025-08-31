package io.github.roussel030.option.application;

import io.github.roussel030.option.api.dto.OptionRequest;
import io.github.roussel030.option.api.dto.OptionResponse;
import io.github.roussel030.option.domain.Option;
import io.github.roussel030.option.domain.OptionRepository;
import io.github.roussel030.option.domain.OptionService;
import io.github.roussel030.option.exception.OptionAlreadyExistsException;
import io.github.roussel030.option.exception.OptionNotFoundException;
import io.github.roussel030.shared.dto.PageResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class OptionServiceImpl implements OptionService {

    private final OptionRepository optionRepository;

    public OptionServiceImpl(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    @Override
    @Transactional
    public OptionResponse createOption(OptionRequest request) {
        if(optionRepository.existsByName(request.name())) {
            throw new OptionAlreadyExistsException("Option already exists");
        }

        Option option = Option.builder()
                .icon(request.icon())
                .name(request.name())
                .build();
        optionRepository.save(option);

        return OptionResponse.builder()
                .id(option.id)
                .icon(option.icon)
                .name(option.name)
                .build();
    }

    @Override
    public PageResponse<OptionResponse> getOptions(int page, int size) {
        List<OptionResponse> options = optionRepository.findAllPaginated(page, size)
                .stream()
                .map(option -> OptionResponse.builder()
                        .id(option.id)
                        .name(option.name)
                        .icon(option.icon)
                        .build())
                .toList();

        long total = getCountTotalOption();

        return PageResponse.<OptionResponse>builder()
                .items(options)
                .page(page)
                .size(size)
                .total(total)
                .build();
    }

    @Override
    @Transactional
    public OptionResponse updateOption(Long id, OptionRequest request) {
        Option option = optionRepository.findByIdOptional(id)
                .orElseThrow(() -> new OptionNotFoundException("Option not found with id: " + id));

        if (!option.name.equals(request.name()) &&
                optionRepository.existsByName(request.name())) {
            throw new OptionAlreadyExistsException("Option with this name already exists");
        }

        option.name = request.name();
        option.icon = request.icon();

        optionRepository.update(option);

        return OptionResponse.builder()
                .id(option.id)
                .name(option.name)
                .icon(option.icon)
                .build();
    }

    @Override
    @Transactional
    public void deleteOption(Long id) {
        if (!optionRepository.removeById(id)) {
            throw new OptionNotFoundException("Option not found with id: " + id);
        }
    }

    private Long getCountTotalOption() {
        return optionRepository.countAll();
    }

}
