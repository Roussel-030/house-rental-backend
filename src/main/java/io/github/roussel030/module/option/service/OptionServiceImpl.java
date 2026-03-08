package io.github.roussel030.module.option.service;

import io.github.roussel030.module.option.dto.OptionRequest;
import io.github.roussel030.module.option.dto.OptionResponse;
import io.github.roussel030.module.option.entity.Option;
import io.github.roussel030.module.option.repository.OptionRepository;
import io.github.roussel030.module.option.exception.OptionAlreadyExistsException;
import io.github.roussel030.module.option.exception.OptionNotFoundException;
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

        Option option = new Option();
        option.setIcon(request.icon());
        option.setName(request.name());
        optionRepository.save(option);

        return OptionResponse.builder()
                .id(option.getId())
                .icon(option.getIcon())
                .name(option.getName())
                .build();
    }

    @Override
    public PageResponse<OptionResponse> getOptions(String search, int page, int size) {
        List<OptionResponse> options = optionRepository.findAllPaginated(search, page, size)
                .stream()
                .map(option -> OptionResponse.builder()
                        .id(option.getId())
                        .name(option.getName())
                        .icon(option.getIcon())
                        .build())
                .toList();

        long total = getCountTotalOption(search);

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

        if (!option.getName().equals(request.name()) &&
                optionRepository.existsByName(request.name())) {
            throw new OptionAlreadyExistsException("Option with this name already exists");
        }

        option.setName(request.name());
        option.setIcon(request.icon());

        optionRepository.update(option);

        return OptionResponse.builder()
                .id(option.getId())
                .name(option.getName())
                .icon(option.getIcon())
                .build();
    }

    @Override
    @Transactional
    public void deleteOption(Long id) {
        Option option = optionRepository.findByIdOptional(id)
                .orElseThrow(() -> new OptionNotFoundException("Option not found with id: " + id));
        optionRepository.deleteOption(option);
    }

    private Long getCountTotalOption(String search) {
        return optionRepository.countAll(search);
    }

}
