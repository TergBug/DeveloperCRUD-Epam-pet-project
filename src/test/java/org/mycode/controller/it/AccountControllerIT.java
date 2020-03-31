package org.mycode.controller.it;

import com.google.gson.Gson;
import org.junit.Test;
import org.mycode.dto.AccountDto;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AccountControllerIT extends BaseIT {
    private static final UUID ID_FOR_CREATE = UUID.randomUUID();
    private static final UUID ID_FOR_GET = UUID.fromString("77845a88-c171-4cd0-a73a-3045bdbe4f30");
    private static final UUID ID_SECOND = UUID.fromString("e86937cd-1535-4c0e-9026-e6ae27adc992");
    private static final UUID ID_THIRD = UUID.fromString("9743c5ef-4e76-4fc1-9b1b-a88f352cb22e");
    private static final UUID ID_FOR_DELETE = UUID.fromString("effaa7cc-373e-47f8-8ff6-7d227f950cfe");
    private static final AccountDto READ_ACCOUNT = new AccountDto(ID_FOR_GET, "LiXiao", "ACTIVE");
    private static final AccountDto CREATED_ACCOUNT = new AccountDto(ID_FOR_CREATE, "Lord", "ACTIVE");
    private static final AccountDto UPDATED_ACCOUNT = new AccountDto(ID_FOR_GET, "Ming", "BANNED");
    private static final List<AccountDto> ACCOUNTS_LIST = Arrays.asList(READ_ACCOUNT,
            new AccountDto(ID_SECOND, "Din", "DELETED"),
            new AccountDto(ID_THIRD, "Geek", "BANNED"));

    @Test
    @Transactional
    @Rollback
    public void shouldReturnJsonEntryFromGivenLong() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/accounts/" + ID_FOR_GET.toString())
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(READ_ACCOUNT.getId().toString())))
                .andExpect(jsonPath("$.name", is(READ_ACCOUNT.getName())))
                .andExpect(jsonPath("$.status", is(READ_ACCOUNT.getStatus())));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldReturnJsonAllEntries() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/accounts")
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))

                .andExpect(jsonPath("$[0].id", is(ACCOUNTS_LIST.get(0).getId().toString())))
                .andExpect(jsonPath("$[0].name", is(ACCOUNTS_LIST.get(0).getName())))
                .andExpect(jsonPath("$[0].status", is(ACCOUNTS_LIST.get(0).getStatus())))

                .andExpect(jsonPath("$[1].id", is(ACCOUNTS_LIST.get(1).getId().toString())))
                .andExpect(jsonPath("$[1].name", is(ACCOUNTS_LIST.get(1).getName())))
                .andExpect(jsonPath("$[1].status", is(ACCOUNTS_LIST.get(1).getStatus())))

                .andExpect(jsonPath("$[2].id", is(ACCOUNTS_LIST.get(2).getId().toString())))
                .andExpect(jsonPath("$[2].name", is(ACCOUNTS_LIST.get(2).getName())))
                .andExpect(jsonPath("$[2].status", is(ACCOUNTS_LIST.get(2).getStatus())));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldInvokeCreateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(post("http://localhost:8080/api/v1/accounts")
                .with(user("admin").password("nimda").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(CREATED_ACCOUNT)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("http://localhost:8080/api/v1/accounts")
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldInvokeUpdateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(put("http://localhost:8080/api/v1/accounts")
                .with(user("admin").password("nimda").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(UPDATED_ACCOUNT)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("http://localhost:8080/api/v1/accounts/" + ID_FOR_GET.toString())
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(UPDATED_ACCOUNT.getId().toString())))
                .andExpect(jsonPath("$.name", is(UPDATED_ACCOUNT.getName())))
                .andExpect(jsonPath("$.status", is(UPDATED_ACCOUNT.getStatus())));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldInvokeDeleteInServiceWithGivenLong() throws Exception {
        mockMvc.perform(delete("http://localhost:8080/api/v1/accounts/" + ID_FOR_DELETE.toString())
                .with(user("admin").password("nimda").roles("ADMIN")))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("http://localhost:8080/api/v1/accounts/" + ID_FOR_DELETE.toString())
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isNotFound());
    }
}
