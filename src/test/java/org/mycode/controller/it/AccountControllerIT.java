package org.mycode.controller.it;

import com.google.gson.Gson;
import org.junit.Test;
import org.mycode.BaseIT;
import org.mycode.dto.AccountDto;
import org.mycode.testutil.TestedEntities;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AccountControllerIT extends BaseIT {
    @Test
    @Transactional
    @Rollback
    public void shouldReturnJsonEntryFromGivenLong() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/accounts/" +
                util.getDto(AccountDto.class, TestedEntities.ENTITY_TO_GET).getId().toString())
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(
                        util.getDto(AccountDto.class, TestedEntities.ENTITY_TO_GET).getId().toString())))
                .andExpect(jsonPath("$.name", is(
                        util.getDto(AccountDto.class, TestedEntities.ENTITY_TO_GET).getName())))
                .andExpect(jsonPath("$.status", is(
                        util.getDto(AccountDto.class, TestedEntities.ENTITY_TO_GET).getStatus())));
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

                .andExpect(jsonPath("$[0].id", is(util.getDtoList(AccountDto.class).get(0).getId().toString())))
                .andExpect(jsonPath("$[0].name", is(util.getDtoList(AccountDto.class).get(0).getName())))
                .andExpect(jsonPath("$[0].status", is(util.getDtoList(AccountDto.class).get(0).getStatus())))

                .andExpect(jsonPath("$[1].id", is(util.getDtoList(AccountDto.class).get(1).getId().toString())))
                .andExpect(jsonPath("$[1].name", is(util.getDtoList(AccountDto.class).get(1).getName())))
                .andExpect(jsonPath("$[1].status", is(util.getDtoList(AccountDto.class).get(1).getStatus())))

                .andExpect(jsonPath("$[2].id", is(util.getDtoList(AccountDto.class).get(2).getId().toString())))
                .andExpect(jsonPath("$[2].name", is(util.getDtoList(AccountDto.class).get(2).getName())))
                .andExpect(jsonPath("$[2].status", is(util.getDtoList(AccountDto.class).get(2).getStatus())))

                .andExpect(jsonPath("$[3].id", is(util.getDtoList(AccountDto.class).get(3).getId().toString())))
                .andExpect(jsonPath("$[3].name", is(util.getDtoList(AccountDto.class).get(3).getName())))
                .andExpect(jsonPath("$[3].status", is(util.getDtoList(AccountDto.class).get(3).getStatus())));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldInvokeCreateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(post("http://localhost:8080/api/v1/accounts")
                .with(user("admin").password("nimda").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(util.getDto(AccountDto.class, TestedEntities.ENTITY_TO_CREATE))))
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
                .content(new Gson().toJson(util.getDto(AccountDto.class, TestedEntities.ENTITY_TO_UPDATE))))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("http://localhost:8080/api/v1/accounts/" +
                util.getDto(AccountDto.class, TestedEntities.ENTITY_TO_UPDATE).getId().toString())
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(
                        util.getDto(AccountDto.class, TestedEntities.ENTITY_TO_UPDATE).getId().toString())))
                .andExpect(jsonPath("$.name", is(
                        util.getDto(AccountDto.class, TestedEntities.ENTITY_TO_UPDATE).getName())))
                .andExpect(jsonPath("$.status", is(
                        util.getDto(AccountDto.class, TestedEntities.ENTITY_TO_UPDATE).getStatus())));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldInvokeDeleteInServiceWithGivenLong() throws Exception {
        mockMvc.perform(delete("http://localhost:8080/api/v1/accounts/" +
                util.getDto(AccountDto.class, TestedEntities.ENTITY_TO_DELETE).getId().toString())
                .with(user("admin").password("nimda").roles("ADMIN")))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("http://localhost:8080/api/v1/accounts/" +
                util.getDto(AccountDto.class, TestedEntities.ENTITY_TO_DELETE).getId().toString())
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isNotFound());
    }
}
