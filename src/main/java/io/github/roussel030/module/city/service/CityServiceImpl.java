package io.github.roussel030.module.city.service;

import io.github.roussel030.module.city.dto.CityRequest;
import io.github.roussel030.module.city.dto.CityResponse;
import io.github.roussel030.module.city.entity.City;
import io.github.roussel030.module.city.exception.CityAlreadyExistsException;
import io.github.roussel030.module.city.exception.CityNotFoundException;
import io.github.roussel030.module.city.repository.CityRepository;
import io.github.roussel030.module.country.dto.CountryResponse;
import io.github.roussel030.module.country.entity.Country;
import io.github.roussel030.module.country.exception.CountryNotFoundException;
import io.github.roussel030.module.country.repository.CountryRepository;
import io.github.roussel030.shared.dto.PageResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    public CityServiceImpl(
            CityRepository cityRepository,
            CountryRepository countryRepository
    ) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    @Transactional
    public CityResponse createCity(CityRequest request) {
        Country country = countryRepository.findByIdOptional(request.countryId())
                .orElseThrow(() -> new CountryNotFoundException("Country not found with id: " + request.countryId()));

        if(cityRepository.existsByNameAndCountry(request.name(), country)) {
            throw new CityAlreadyExistsException(
                    "City '" + request.name() + "' already exists in country '" + country.getName() + "'"
            );
        }

        City city = new City();
        city.setName(request.name());
        city.setCountry(country);
        cityRepository.save(city);

        return CityResponse.builder()
                .id(city.getId())
                .name(city.getName())
                .country(
                        CountryResponse.builder()
                                .id(country.getId())
                                .name(country.getName())
                                .build()
                )
                .build();
    }

    @Override
    public PageResponse<CityResponse> getCities(int page, int size) {
        List<CityResponse> cities = cityRepository.findAllPaginated(page, size)
                .stream()
                .map(city -> CityResponse.builder()
                        .id(city.getId())
                        .name(city.getName())
                        .country(
                                CountryResponse.builder()
                                        .id(city.getCountry().getId())
                                        .name(city.getCountry().getName())
                                        .build()
                        )
                        .build())
                .toList();

        long total = getCountTotalCity();

        return PageResponse.<CityResponse>builder()
                .items(cities)
                .page(page)
                .size(size)
                .total(total)
                .build();
    }

    @Override
    @Transactional
    public CityResponse updateCity(Long id, CityRequest request) {
        City city = cityRepository.findByIdOptional(id)
                .orElseThrow(() -> new CityNotFoundException("City not found with id: " + id));

        Country country = countryRepository.findByIdOptional(request.countryId())
                .orElseThrow(() -> new CountryNotFoundException("Country not found with id: " + request.countryId()));

        if(!city.getName().equals(request.name()) &&
                cityRepository.existsByNameAndCountry(request.name(), country)) {
            throw new CityAlreadyExistsException(
                    "City '" + request.name() + "' already exists in country '" + country.getName() + "'"
            );
        }

        city.setName(request.name());
        city.setCountry(country);
        cityRepository.update(city);

        return CityResponse.builder()
                .id(city.getId())
                .name(city.getName())
                .country(
                        CountryResponse.builder()
                                .id(country.getId())
                                .name(country.getName())
                                .build()
                )
                .build();
    }

    @Override
    @Transactional
    public void deleteCity(Long id) {
        City city = cityRepository.findByIdOptional(id)
                .orElseThrow(() -> new CityNotFoundException("City not found with id: " + id));

        cityRepository.deleteCity(city);
    }

    private Long getCountTotalCity() {
        return cityRepository.countALl();
    }

}
