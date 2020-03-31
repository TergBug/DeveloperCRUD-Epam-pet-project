package org.mycode.controller.ut;

import com.google.gson.Gson;
import org.junit.Test;
import org.mycode.controller.AccountController;
import org.mycode.dto.AccountDto;
import org.mycode.service.impl.AccountServiceImpl;
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

public class AccountControllerTest {
    private static final UUID ID_FOR_GET = UUID.fromString("77845a88-c171-4cd0-a73a-3045bdbe4f30");
    private static final UUID ID_FOR_LIST = UUID.fromString("9743c5ef-4e76-4fc1-9b1b-a88f352cb22e");
    private static final AccountDto ACCOUNT = new AccountDto(ID_FOR_GET, "Frog", "ACTIVE");
    private static final List<AccountDto> ACCOUNTS_LIST = Arrays.asList(ACCOUNT,
            new AccountDto(ID_FOR_LIST, "Fred", "BANNED"));
    private AccountServiceImpl mockedService = mock(AccountServiceImpl.class);
    private AccountController sut = new AccountController(mockedService);
    private MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sut).build();

    @Test
    public void shouldReturnJsonEntryFromGivenLong() throws Exception {
        when(mockedService.getById(ID_FOR_GET)).thenReturn(ACCOUNT);
        mockMvc.perform(get("http://localhost:8080/api/v1/accounts/77845a88-c171-4cd0-a73a-3045bdbe4f30"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(ACCOUNT.getId().toString())))
                .andExpect(jsonPath("$.name", is(ACCOUNT.getName())))
                .andExpect(jsonPath("$.status", is(ACCOUNT.getStatus())));
    }

    @Test
    public void shouldReturnJsonAllEntries() throws Exception {
        when(mockedService.getAll()).thenReturn(ACCOUNTS_LIST);
        mockMvc.perform(get("http://localhost:8080/api/v1/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(ACCOUNTS_LIST.get(0).getId().toString())))
                .andExpect(jsonPath("$[0].name", is(ACCOUNTS_LIST.get(0).getName())))
                .andExpect(jsonPath("$[0].status", is(ACCOUNTS_LIST.get(0).getStatus())))
                .andExpect(jsonPath("$[1].id", is(ACCOUNTS_LIST.get(1).getId().toString())))
                .andExpect(jsonPath("$[1].name", is(ACCOUNTS_LIST.get(1).getName())))
                .andExpect(jsonPath("$[1].status", is(ACCOUNTS_LIST.get(1).getStatus())));
    }

    @Test
    public void shouldInvokeCreateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(post("http://localhost:8080/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(ACCOUNT)))
                .andExpect(status().isCreated());
        verify(mockedService, times(1)).create(ACCOUNT);
    }

    @Test
    public void shouldInvokeUpdateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(put("http://localhost:8080/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(ACCOUNT)))
                .andExpect(status().isNoContent());
        verify(mockedService, times(1)).update(ACCOUNT);
    }

    @Test
    public void shouldInvokeDeleteInServiceWithGivenLong() throws Exception {
        mockMvc.perform(delete("http://localhost:8080/api/v1/accounts/77845a88-c171-4cd0-a73a-3045bdbe4f30"))
                .andExpect(status().isNoContent());
        verify(mockedService, times(1)).delete(ID_FOR_GET);
    }
}
