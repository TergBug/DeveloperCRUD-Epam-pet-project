package org.mycode.controller.ut;

import com.google.gson.Gson;
import org.junit.Test;
import org.mycode.controller.CustomerController;
import org.mycode.dto.CustomerDto;
import org.mycode.service.impl.CustomerServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CustomerControllerTest {
    private static final UUID ID_FOR_GET = UUID.fromString("2936f525-5b05-47e2-8c08-f681c9ab1139");
    private static final UUID ID_FOR_LIST = UUID.fromString("3025faf1-dc64-40ae-840b-58d5f711eeb6");
    private static final CustomerDto CUSTOMER = new CustomerDto(ID_FOR_GET, "Customer1");
    private static final List<CustomerDto> CUSTOMERS_LIST = Arrays.asList(CUSTOMER,
            new CustomerDto(ID_FOR_LIST, "C#"));
    private CustomerServiceImpl mockedService = mock(CustomerServiceImpl.class);
    private CustomerController sut = new CustomerController(mockedService);
    private MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sut).build();

    @Test
    public void shouldReturnJsonEntryFromGivenLong() throws Exception {
        when(mockedService.getById(ID_FOR_GET)).thenReturn(CUSTOMER);
        mockMvc.perform(get("http://localhost:8080/api/v1/customers/2936f525-5b05-47e2-8c08-f681c9ab1139"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(CUSTOMER.getId().toString())))
                .andExpect(jsonPath("$.name", is(CUSTOMER.getName())));
    }

    @Test
    public void shouldReturnJsonAllEntries() throws Exception {
        when(mockedService.getAll()).thenReturn(CUSTOMERS_LIST);
        mockMvc.perform(get("http://localhost:8080/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(CUSTOMERS_LIST.get(0).getId().toString())))
                .andExpect(jsonPath("$[0].name", is(CUSTOMERS_LIST.get(0).getName())))
                .andExpect(jsonPath("$[1].id", is(CUSTOMERS_LIST.get(1).getId().toString())))
                .andExpect(jsonPath("$[1].name", is(CUSTOMERS_LIST.get(1).getName())));
    }

    @Test
    public void shouldInvokeCreateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(post("http://localhost:8080/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(CUSTOMER)))
                .andExpect(status().isCreated());
        verify(mockedService, times(1)).create(CUSTOMER);
    }

    @Test
    public void shouldInvokeUpdateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(put("http://localhost:8080/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(CUSTOMER)))
                .andExpect(status().isNoContent());
        verify(mockedService, times(1)).update(CUSTOMER);
    }

    @Test
    public void shouldInvokeDeleteInServiceWithGivenLong() throws Exception {
        mockMvc.perform(delete("http://localhost:8080/api/v1/customers/2936f525-5b05-47e2-8c08-f681c9ab1139"))
                .andExpect(status().isNoContent());
        verify(mockedService, times(1)).delete(ID_FOR_GET);
    }
}
