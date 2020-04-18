package org.mycode.controller.it;

import com.google.gson.Gson;
import org.junit.Test;
import org.mycode.BaseIT;
import org.mycode.dto.DeveloperDto;
import org.mycode.testutil.TestedEntities;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DeveloperControllerIT extends BaseIT {
    @Test
    @Transactional
    @Rollback
    public void shouldReturnJsonEntryFromGivenLong() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/developers/" +
                util.getDto(DeveloperDto.class, TestedEntities.ENTITY_TO_GET).getId().toString())
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(
                        util.getDto(DeveloperDto.class, TestedEntities.ENTITY_TO_GET).getId().toString())))
                .andExpect(jsonPath("$.firstName", is(
                        util.getDto(DeveloperDto.class, TestedEntities.ENTITY_TO_GET).getFirstName())))
                .andExpect(jsonPath("$.lastName", is(
                        util.getDto(DeveloperDto.class, TestedEntities.ENTITY_TO_GET).getLastName())))
                .andExpect(jsonPath("$.skills", hasSize(2)))
                .andExpect(jsonPath("$.skills[0]", is(
                        util.getDto(DeveloperDto.class, TestedEntities.ENTITY_TO_GET).getSkills()
                                .toArray(new String[2])[0])))
                .andExpect(jsonPath("$.skills[1]", is(
                        util.getDto(DeveloperDto.class, TestedEntities.ENTITY_TO_GET).getSkills()
                                .toArray(new String[2])[1])))
                .andExpect(jsonPath("$.account", is(
                        util.getDto(DeveloperDto.class, TestedEntities.ENTITY_TO_GET).getAccount())));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldReturnJsonAllEntries() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/developers")
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))

                .andExpect(jsonPath("$[0].id", is(
                        util.getDtoList(DeveloperDto.class).get(0).getId().toString())))
                .andExpect(jsonPath("$[0].firstName", is(
                        util.getDtoList(DeveloperDto.class).get(0).getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(
                        util.getDtoList(DeveloperDto.class).get(0).getLastName())))
                .andExpect(jsonPath("$[0].skills", hasSize(2)))
                .andExpect(jsonPath("$[0].skills[0]", is(
                        util.getDtoList(DeveloperDto.class).get(0).getSkills().toArray(new String[2])[0])))
                .andExpect(jsonPath("$[0].skills[1]", is(
                        util.getDtoList(DeveloperDto.class).get(0).getSkills().toArray(new String[2])[1])))
                .andExpect(jsonPath("$[0].account", is(
                        util.getDtoList(DeveloperDto.class).get(0).getAccount())))

                .andExpect(jsonPath("$[1].id", is(
                        util.getDtoList(DeveloperDto.class).get(1).getId().toString())))
                .andExpect(jsonPath("$[1].firstName", is(
                        util.getDtoList(DeveloperDto.class).get(1).getFirstName())))
                .andExpect(jsonPath("$[1].lastName", is(
                        util.getDtoList(DeveloperDto.class).get(1).getLastName())))
                .andExpect(jsonPath("$[1].skills", hasSize(0)))
                .andExpect(jsonPath("$[1].account", is(
                        util.getDtoList(DeveloperDto.class).get(1).getAccount())))

                .andExpect(jsonPath("$[2].id", is(
                        util.getDtoList(DeveloperDto.class).get(2).getId().toString())))
                .andExpect(jsonPath("$[2].firstName", is(
                        util.getDtoList(DeveloperDto.class).get(2).getFirstName())))
                .andExpect(jsonPath("$[2].lastName", is(
                        util.getDtoList(DeveloperDto.class).get(2).getLastName())))
                .andExpect(jsonPath("$[2].skills", hasSize(2)))
                .andExpect(jsonPath("$[2].skills[0]", is(
                        util.getDtoList(DeveloperDto.class).get(2).getSkills().toArray(new String[2])[0])))
                .andExpect(jsonPath("$[2].skills[1]", is(
                        util.getDtoList(DeveloperDto.class).get(2).getSkills().toArray(new String[2])[1])))
                .andExpect(jsonPath("$[2].account", is(
                        util.getDtoList(DeveloperDto.class).get(2).getAccount())))

                .andExpect(jsonPath("$[3].id", is(
                        util.getDtoList(DeveloperDto.class).get(3).getId().toString())))
                .andExpect(jsonPath("$[3].firstName", is(
                        util.getDtoList(DeveloperDto.class).get(3).getFirstName())))
                .andExpect(jsonPath("$[3].lastName", is(
                        util.getDtoList(DeveloperDto.class).get(3).getLastName())))
                .andExpect(jsonPath("$[3].skills", hasSize(1)))
                .andExpect(jsonPath("$[3].skills[0]", is(
                        util.getDtoList(DeveloperDto.class).get(3).getSkills().toArray(new String[1])[0])))
                .andExpect(jsonPath("$[3].account", is(
                        util.getDtoList(DeveloperDto.class).get(3).getAccount())));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldInvokeCreateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(post("http://localhost:8080/api/v1/developers")
                .with(user("admin").password("nimda").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(util.getDto(DeveloperDto.class, TestedEntities.ENTITY_TO_CREATE))))
                .andExpect(status().isCreated());

        mockMvc.perform(get("http://localhost:8080/api/v1/developers")
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldInvokeUpdateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(put("http://localhost:8080/api/v1/developers")
                .with(user("admin").password("nimda").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(util.getDto(DeveloperDto.class, TestedEntities.ENTITY_TO_UPDATE))))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("http://localhost:8080/api/v1/developers/" +
                util.getDto(DeveloperDto.class, TestedEntities.ENTITY_TO_UPDATE).getId().toString())
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(
                        util.getDto(DeveloperDto.class, TestedEntities.ENTITY_TO_UPDATE).getId().toString())))
                .andExpect(jsonPath("$.firstName", is(
                        util.getDto(DeveloperDto.class, TestedEntities.ENTITY_TO_UPDATE).getFirstName())))
                .andExpect(jsonPath("$.lastName", is(
                        util.getDto(DeveloperDto.class, TestedEntities.ENTITY_TO_UPDATE).getLastName())))
                .andExpect(jsonPath("$.skills", hasSize(2)))
                .andExpect(jsonPath("$.skills[0]", is(
                        util.getDto(DeveloperDto.class, TestedEntities.ENTITY_TO_UPDATE).getSkills()
                                .toArray(new String[2])[0])))
                .andExpect(jsonPath("$.skills[1]", is(
                        util.getDto(DeveloperDto.class, TestedEntities.ENTITY_TO_UPDATE).getSkills()
                                .toArray(new String[2])[1])))
                .andExpect(jsonPath("$.account", is(
                        util.getDto(DeveloperDto.class, TestedEntities.ENTITY_TO_UPDATE).getAccount())));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldInvokeDeleteInServiceWithGivenLong() throws Exception {
        mockMvc.perform(delete("http://localhost:8080/api/v1/developers/" +
                util.getDto(DeveloperDto.class, TestedEntities.ENTITY_TO_DELETE).getId().toString())
                .with(user("admin").password("nimda").roles("ADMIN")))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("http://localhost:8080/api/v1/developers/" +
                util.getDto(DeveloperDto.class, TestedEntities.ENTITY_TO_DELETE).getId().toString())
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isNotFound());
    }
}
