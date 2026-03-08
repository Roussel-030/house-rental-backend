package io.github.roussel030.module.currency.service;

import io.github.roussel030.module.currency.dto.CurrencyRequest;
import io.github.roussel030.module.currency.dto.CurrencyResponse;
import io.github.roussel030.module.currency.entity.Currency;
import io.github.roussel030.module.currency.exception.CurrencyAlreadyExistsException;
import io.github.roussel030.module.currency.exception.CurrencyNotFoundException;
import io.github.roussel030.module.currency.repository.CurrencyRepository;
import io.github.roussel030.shared.dto.PageResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    @Transactional
    public CurrencyResponse createCurrency(CurrencyRequest request) {
        if(currencyRepository.existsByCode(request.code())) {
            throw new CurrencyAlreadyExistsException("Currency already exists");
        }

        Currency currency = new Currency();
        currency.setCode(request.code());
        currency.setName(request.name());
        currency.setSymbol(request.symbol());

        return CurrencyResponse.builder()
                .id(currency.getId())
                .code(currency.getCode())
                .name(currency.getName())
                .symbol(currency.getSymbol())
                .build();
    }

    @Override
    public PageResponse<CurrencyResponse> getCurrencies(String search, int page, int size) {
        List<CurrencyResponse> currencies = currencyRepository.findAllPaginated(search, page, size)
                .stream()
                .map(currency -> CurrencyResponse.builder()
                        .id(currency.getId())
                        .code(currency.getCode())
                        .name(currency.getName())
                        .symbol(currency.getSymbol())
                        .build())
                .toList();

        long total = getCountTotalCurrency(search);

        return PageResponse.<CurrencyResponse>builder()
                .items(currencies)
                .page(page)
                .size(size)
                .total(total)
                .build();
    }

    @Override
    @Transactional
    public CurrencyResponse updateCurrency(Long id, CurrencyRequest request) {
        Currency currency = currencyRepository.findByIdOptional(id)
                .orElseThrow(() -> new CurrencyNotFoundException("Currency not found with id: " + id));

        if(!currency.getCode().equals(request.code()) &&
                currencyRepository.existsByCode(request.code())) {
            throw new CurrencyAlreadyExistsException("Currency with this name already exists");
        }

        currency.setCode(request.code());
        currency.setName(request.name());
        currency.setSymbol(request.symbol());
        currencyRepository.update(currency);

        return CurrencyResponse.builder()
                .id(currency.getId())
                .code(currency.getCode())
                .name(currency.getName())
                .symbol(currency.getSymbol())
                .build();
    }

    @Override
    @Transactional
    public void deleteCurrency(Long id) {
        Currency currency = currencyRepository.findByIdOptional(id)
                .orElseThrow(() -> new CurrencyNotFoundException("Currency not found with id: " + id));
        currencyRepository.deleteCurrency(currency);
    }

    private Long getCountTotalCurrency(String search) {
        return currencyRepository.countAll();
    }

}
