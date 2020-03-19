package org.mycode.controller.ut;

import com.google.gson.Gson;
import org.junit.Test;
import org.mycode.controller.AccountController;
import org.mycode.model.Account;
import org.mycode.model.AccountStatus;
import org.mycode.service.AccountService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AccountControllerTest {
    private static final Account ACCOUNT = new Account(1L, "Frog", AccountStatus.ACTIVE);
    private static final List<Account> ACCOUNTS_LIST = Arrays.asList(ACCOUNT,
            new Account(2L, "Fred", AccountStatus.BANNED));
    private AccountService mockedService = mock(AccountService.class);
    private AccountController sut = new AccountController(mockedService);
    private MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sut).build();

    @Test
    public void shouldReturnJsonEntryFromGivenLong() throws Exception {
        when(mockedService.getById(1L)).thenReturn(ACCOUNT);
        doCallRealMethod().when(mockedService).doService(any());
        mockMvc.perform(get("http://localhost:8080/api/v1/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(ACCOUNT.getId().intValue())))
                .andExpect(jsonPath("$.name", is(ACCOUNT.getName())))
                .andExpect(jsonPath("$.status", is(ACCOUNT.getStatus().toString())));
    }

    @Test
    public void shouldReturnJsonAllEntries() throws Exception {
        when(mockedService.getAll()).thenReturn(ACCOUNTS_LIST);
        doCallRealMethod().when(mockedService).doService(any());
        mockMvc.perform(get("http://localhost:8080/api/v1/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(ACCOUNTS_LIST.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(ACCOUNTS_LIST.get(0).getName())))
                .andExpect(jsonPath("$[0].status", is(ACCOUNTS_LIST.get(0).getStatus().toString())))
                .andExpect(jsonPath("$[1].id", is(ACCOUNTS_LIST.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(ACCOUNTS_LIST.get(1).getName())))
                .andExpect(jsonPath("$[1].status", is(ACCOUNTS_LIST.get(1).getStatus().toString())));
    }

    @Test
    public void shouldInvokeCreateInServiceWithGivenJson() throws Exception {
        doCallRealMethod().when(mockedService).doService(any());
        mockMvc.perform(post("http://localhost:8080/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(ACCOUNT)))
                .andExpect(status().isCreated());
        verify(mockedService, times(1)).create(ACCOUNT);
    }

    @Test
    public void shouldInvokeUpdateInServiceWithGivenJson() throws Exception {
        doCallRealMethod().when(mockedService).doService(any());
        mockMvc.perform(put("http://localhost:8080/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(ACCOUNT)))
                .andExpect(status().isNoContent());
        verify(mockedService, times(1)).update(ACCOUNT);
    }

    @Test
    public void shouldInvokeDeleteInServiceWithGivenLong() throws Exception {
        doCallRealMethod().when(mockedService).doService(any());
        mockMvc.perform(delete("http://localhost:8080/api/v1/accounts/1"))
                .andExpect(status().isNoContent());
        verify(mockedService, times(1)).delete(1L);
    }
}
