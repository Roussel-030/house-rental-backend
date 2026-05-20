package io.github.roussel030.module.country.service;

import io.github.roussel030.module.city.repository.CityRepository;
import io.github.roussel030.module.country.dto.CountryRequest;
import io.github.roussel030.module.country.dto.CountryResponse;
import io.github.roussel030.module.country.entity.Country;
import io.github.roussel030.module.country.exception.CountryAlreadyExistsException;
import io.github.roussel030.module.country.exception.CountryNotFoundException;
import io.github.roussel030.module.country.repository.CountryRepository;
import io.github.roussel030.module.currency.dto.CurrencyResponse;
import io.github.roussel030.module.currency.entity.Currency;
import io.github.roussel030.module.currency.exception.CurrencyNotFoundException;
import io.github.roussel030.module.currency.repository.CurrencyRepository;
import io.github.roussel030.module.neighborhood.repository.NeighborhoodRepository;
import io.github.roussel030.shared.dto.PageResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final CurrencyRepository currencyRepository;
    private final CityRepository cityRepository;
    private final NeighborhoodRepository neighborhoodRepository;

    public CountryServiceImpl(
            CountryRepository countryRepository,
            CurrencyRepository currencyRepository,
            CityRepository cityRepository,
            NeighborhoodRepository neighborhoodRepository
    ) {
        this.countryRepository = countryRepository;
        this.currencyRepository = currencyRepository;
        this.cityRepository = cityRepository;
        this.neighborhoodRepository = neighborhoodRepository;
    }

    @Override
    @Transactional
    public CountryResponse createCountry(CountryRequest request) {
        if(countryRepository.existsByName(request.name())) {
            throw new CountryAlreadyExistsException("Country already exists");
        }

        Currency currency = currencyRepository.findByIdOptional(request.currencyId())
                .orElseThrow(() -> new CurrencyNotFoundException("Currency not found with id: " + request.currencyId()));

        Country country = new Country();
        country.setName(request.name());
        country.setCurrency(currency);
        countryRepository.save(country);

        return CountryResponse.builder()
                .id(country.getId())
                .name(country.getName())
                .currency(
                        CurrencyResponse.builder()
                                .id(currency.getId())
                                .code(currency.getCode())
                                .name(currency.getName())
                                .symbol(currency.getSymbol())
                                .build()
                )
                .build();
    }

    @Override
    public PageResponse<CountryResponse> getCountries(String search, int page, int size) {
        List<CountryResponse> countries = countryRepository.findAllPaginated(search, page, size)
                .stream()
                .map(country -> CountryResponse.builder()
                        .id(country.getId())
                        .name(country.getName())
                        .currency(
                                CurrencyResponse.builder()
                                        .id(country.getCurrency().getId())
                                        .code(country.getCurrency().getCode())
                                        .name(country.getCurrency().getName())
                                        .symbol(country.getCurrency().getSymbol())
                                        .build()
                        )
                        .build())
                .toList();

        long total = getCountTotalCountry(search);

        return PageResponse.<CountryResponse>builder()
                .items(countries)
                .page(page)
                .size(size)
                .total(total)
                .build();
    }

    @Override
    @Transactional
    public CountryResponse updateCountry(Long id, CountryRequest request) {
        Country country = countryRepository.findByIdOptional(id)
                .orElseThrow(() -> new CountryNotFoundException("Country not found with id: " + id));

        if(!country.getName().equals(request.name()) &&
                countryRepository.existsByName(request.name())) {
            throw new CountryAlreadyExistsException("Country with this name already exists");
        }

        Currency currency = currencyRepository.findByIdOptional(request.currencyId())
                .orElseThrow(() -> new CurrencyNotFoundException("Currency not found with id: " + request.currencyId()));

        country.setName(request.name());
        country.setCurrency(currency);
        countryRepository.update(country);

        return CountryResponse.builder()
                .id(country.getId())
                .name(country.getName())
                .currency(
                        CurrencyResponse.builder()
                                .id(currency.getId())
                                .code(currency.getCode())
                                .name(currency.getName())
                                .symbol(currency.getSymbol())
                                .build()
                )
                .build();
    }

    @Override
    @Transactional
    public void deleteCountry(Long id) {
        Country country = countryRepository.findByIdOptional(id)
                .orElseThrow(() -> new CountryNotFoundException("Country not found with id: " + id));

        neighborhoodRepository.deleteByCountry(country);
        cityRepository.deleteByCountry(country);
        countryRepository.deleteCountry(country);
    }

    private Long getCountTotalCountry(String search) {
        return countryRepository.countAll(search);
    }

}
