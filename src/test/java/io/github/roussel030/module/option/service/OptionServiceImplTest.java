package io.github.roussel030.module.option.service;

import io.github.roussel030.module.option.dto.OptionRequest;
import io.github.roussel030.module.option.dto.OptionResponse;
import io.github.roussel030.module.option.entity.Option;
import io.github.roussel030.module.option.exception.OptionAlreadyExistsException;
import io.github.roussel030.module.option.exception.OptionNotFoundException;
import io.github.roussel030.module.option.repository.OptionRepository;
import io.github.roussel030.shared.dto.PageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OptionServiceImplTest {

    @Mock
    private OptionRepository optionRepository;

    private OptionServiceImpl optionService;

    @BeforeEach
    void setUp() {
        optionService = new OptionServiceImpl(optionRepository);
    }

    @Test
    void createOption_Success() {
        OptionRequest request = new OptionRequest("WiFi", "wifi-icon");
        when(optionRepository.existsByName(request.name())).thenReturn(false);

        OptionResponse response = optionService.createOption(request);

        assertNotNull(response);
        assertEquals(request.name(), response.name());
        assertEquals(request.icon(), response.icon());
        verify(optionRepository).save(any(Option.class));
    }

    @Test
    void createOption_AlreadyExists_ThrowsException() {
        OptionRequest request = new OptionRequest("WiFi", "wifi-icon");
        when(optionRepository.existsByName(request.name())).thenReturn(true);

        assertThrows(OptionAlreadyExistsException.class, () -> optionService.createOption(request));
    }

    @Test
    void getOptions_Success() {
        String search = "";
        int page = 0;
        int size = 10;
        Option option = new Option();
        option.setId(1L);
        option.setName("WiFi");

        when(optionRepository.findAllPaginated(search, page, size)).thenReturn(List.of(option));
        when(optionRepository.countAll(search)).thenReturn(1L);

        PageResponse<OptionResponse> response = optionService.getOptions(search, page, size);

        assertNotNull(response);
        assertEquals(1, response.items().size());
        assertEquals(1, response.total());
    }

    @Test
    void updateOption_Success() {
        Long id = 1L;
        OptionRequest request = new OptionRequest("Pool", "pool-icon");
        Option option = new Option();
        option.setId(id);
        option.setName("WiFi");

        when(optionRepository.findByIdOptional(id)).thenReturn(Optional.of(option));
        when(optionRepository.existsByName(request.name())).thenReturn(false);

        OptionResponse response = optionService.updateOption(id, request);

        assertNotNull(response);
        assertEquals(request.name(), response.name());
        verify(optionRepository).update(option);
    }

    @Test
    void deleteOption_Success() {
        Long id = 1L;
        Option option = new Option();
        option.setId(id);

        when(optionRepository.findByIdOptional(id)).thenReturn(Optional.of(option));

        optionService.deleteOption(id);

        verify(optionRepository).deleteOption(option);
    }

    @Test
    void deleteOption_NotFound_ThrowsException() {
        Long id = 1L;
        when(optionRepository.findByIdOptional(id)).thenReturn(Optional.empty());

        assertThrows(OptionNotFoundException.class, () -> optionService.deleteOption(id));
    }
}
