package org.mycode.controller.it;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.mycode.model.Account;
import org.mycode.model.AccountStatus;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AccountControllerIT extends BaseIT {
    private static final Account READ_ACCOUNT = new Account(1L, "LiXiao", AccountStatus.ACTIVE);
    private static final Account CREATED_ACCOUNT = new Account(5L, "Lord", AccountStatus.ACTIVE);
    private static final Account UPDATED_ACCOUNT = new Account(1L, "Ming", AccountStatus.BANNED);
    private static final List<Account> ACCOUNTS_LIST = Arrays.asList(READ_ACCOUNT,
            new Account(2L, "Din", AccountStatus.DELETED),
            new Account(3L, "Geek", AccountStatus.BANNED),
            new Account(4L, "Ford", AccountStatus.ACTIVE));

    @Before
    public void connectAndInitDB() {
        connectionAndInitDB.reInitDB();
    }

    @Test
    public void shouldReturnJsonEntryFromGivenLong() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/accounts/1")
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(READ_ACCOUNT.getId().intValue())))
                .andExpect(jsonPath("$.name", is(READ_ACCOUNT.getName())))
                .andExpect(jsonPath("$.status", is(READ_ACCOUNT.getStatus().toString())));
    }

    @Test
    public void shouldReturnJsonAllEntries() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/accounts")
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))

                .andExpect(jsonPath("$[0].id", is(ACCOUNTS_LIST.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(ACCOUNTS_LIST.get(0).getName())))
                .andExpect(jsonPath("$[0].status", is(ACCOUNTS_LIST.get(0).getStatus().toString())))

                .andExpect(jsonPath("$[1].id", is(ACCOUNTS_LIST.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(ACCOUNTS_LIST.get(1).getName())))
                .andExpect(jsonPath("$[1].status", is(ACCOUNTS_LIST.get(1).getStatus().toString())))

                .andExpect(jsonPath("$[2].id", is(ACCOUNTS_LIST.get(2).getId().intValue())))
                .andExpect(jsonPath("$[2].name", is(ACCOUNTS_LIST.get(2).getName())))
                .andExpect(jsonPath("$[2].status", is(ACCOUNTS_LIST.get(2).getStatus().toString())))

                .andExpect(jsonPath("$[3].id", is(ACCOUNTS_LIST.get(3).getId().intValue())))
                .andExpect(jsonPath("$[3].name", is(ACCOUNTS_LIST.get(3).getName())))
                .andExpect(jsonPath("$[3].status", is(ACCOUNTS_LIST.get(3).getStatus().toString())));
    }

    @Test
    public void shouldInvokeCreateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(post("http://localhost:8080/api/v1/accounts")
                .with(user("admin").password("nimda").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(CREATED_ACCOUNT)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("http://localhost:8080/api/v1/accounts/5")
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(CREATED_ACCOUNT.getId().intValue())))
                .andExpect(jsonPath("$.name", is(CREATED_ACCOUNT.getName())))
                .andExpect(jsonPath("$.status", is(CREATED_ACCOUNT.getStatus().toString())));
    }

    @Test
    public void shouldInvokeUpdateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(put("http://localhost:8080/api/v1/accounts")
                .with(user("admin").password("nimda").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(UPDATED_ACCOUNT)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("http://localhost:8080/api/v1/accounts/1")
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(UPDATED_ACCOUNT.getId().intValue())))
                .andExpect(jsonPath("$.name", is(UPDATED_ACCOUNT.getName())))
                .andExpect(jsonPath("$.status", is(UPDATED_ACCOUNT.getStatus().toString())));
    }

    @Test
    public void shouldInvokeDeleteInServiceWithGivenLong() throws Exception {
        mockMvc.perform(delete("http://localhost:8080/api/v1/accounts/4")
                .with(user("admin").password("nimda").roles("ADMIN")))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("http://localhost:8080/api/v1/accounts/4")
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isNotFound());
    }
}
