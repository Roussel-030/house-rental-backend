package io.github.roussel030.module.option.resource;

import io.github.roussel030.module.option.dto.OptionRequest;
import io.github.roussel030.module.option.dto.OptionResponse;
import io.github.roussel030.module.option.service.OptionService;
import io.github.roussel030.shared.dto.PageResponse;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OptionResourceImplTest {

    @Mock
    private OptionService optionService;

    private OptionResourceImpl optionResource;

    @BeforeEach
    void setUp() {
        optionResource = new OptionResourceImpl(optionService);
    }

    @Test
    void createOption_ShouldReturnCreated() {
        // Given
        OptionRequest request = new OptionRequest("Wifi", "wifi-icon");
        OptionResponse response = OptionResponse.builder().id(1L).name("Wifi").icon("wifi-icon").build();
        when(optionService.createOption(any(OptionRequest.class))).thenReturn(response);

        // When
        Response result = optionResource.createOption(request);

        // Then
        assertEquals(Response.Status.CREATED.getStatusCode(), result.getStatus());
        assertEquals(response, result.getEntity());
        verify(optionService).createOption(request);
    }

    @Test
    void getOptions_ShouldReturnOk() {
        // Given
        PageResponse<OptionResponse> pageResponse = PageResponse.<OptionResponse>builder()
                .items(Collections.emptyList())
                .page(0)
                .size(10)
                .total(0)
                .build();
        when(optionService.getOptions(anyString(), anyInt(), anyInt())).thenReturn(pageResponse);

        // When
        Response result = optionResource.getOptions("search", 0, 10);

        // Then
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(pageResponse, result.getEntity());
        verify(optionService).getOptions("search", 0, 10);
    }

    @Test
    void updateOption_ShouldReturnOk() {
        // Given
        Long id = 1L;
        OptionRequest request = new OptionRequest("Wifi updated", "wifi-icon-updated");
        OptionResponse response = OptionResponse.builder().id(1L).name("Wifi updated").icon("wifi-icon-updated").build();
        when(optionService.updateOption(eq(id), any(OptionRequest.class))).thenReturn(response);

        // When
        Response result = optionResource.updateOption(id, request);

        // Then
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(response, result.getEntity());
        verify(optionService).updateOption(id, request);
    }

    @Test
    void deleteOption_ShouldReturnNoContent() {
        // Given
        Long id = 1L;
        doNothing().when(optionService).deleteOption(id);

        // When
        Response result = optionResource.deleteOption(id);

        // Then
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), result.getStatus());
        verify(optionService).deleteOption(id);
    }
}
