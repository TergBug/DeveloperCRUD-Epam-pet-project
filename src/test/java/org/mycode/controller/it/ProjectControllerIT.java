package org.mycode.controller.it;

import com.google.gson.Gson;
import org.junit.Test;
import org.mycode.BaseIT;
import org.mycode.dto.ProjectDto;
import org.mycode.testutil.TestedEntities;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProjectControllerIT extends BaseIT {
    @Test
    @Transactional
    @Rollback
    public void shouldReturnJsonEntryFromGivenLong() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/projects/" +
                util.getDto(ProjectDto.class, TestedEntities.ENTITY_TO_GET).getId().toString())
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(
                        util.getDto(ProjectDto.class, TestedEntities.ENTITY_TO_GET).getId().toString())))
                .andExpect(jsonPath("$.name", is(
                        util.getDto(ProjectDto.class, TestedEntities.ENTITY_TO_GET).getName())))
                .andExpect(jsonPath("$.developersAccounts", hasSize(1)))
                .andExpect(jsonPath("$.developersAccounts[0]", is(
                        util.getDto(ProjectDto.class, TestedEntities.ENTITY_TO_GET).getDevelopersAccounts()
                                .toArray(new String[0])[0])))
                .andExpect(jsonPath("$.customerId", is(
                        util.getDto(ProjectDto.class, TestedEntities.ENTITY_TO_GET).getCustomerId().toString())))
                .andExpect(jsonPath("$.status", is(
                        util.getDto(ProjectDto.class, TestedEntities.ENTITY_TO_GET).getStatus())));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldReturnJsonAllEntries() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/projects")
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))

                .andExpect(jsonPath("$[0].id", is(
                        util.getDtoList(ProjectDto.class).get(0).getId().toString())))
                .andExpect(jsonPath("$[0].name", is(
                        util.getDtoList(ProjectDto.class).get(0).getName())))
                .andExpect(jsonPath("$[0].developersAccounts", hasSize(1)))
                .andExpect(jsonPath("$[0].developersAccounts[0]", is(
                        util.getDtoList(ProjectDto.class).get(0).getDevelopersAccounts().toArray(new String[0])[0])))
                .andExpect(jsonPath("$[0].customerId", is(
                        util.getDtoList(ProjectDto.class).get(0).getCustomerId().toString())))
                .andExpect(jsonPath("$[0].status", is(
                        util.getDtoList(ProjectDto.class).get(0).getStatus())))

                .andExpect(jsonPath("$[1].id", is(
                        util.getDtoList(ProjectDto.class).get(1).getId().toString())))
                .andExpect(jsonPath("$[1].name", is(
                        util.getDtoList(ProjectDto.class).get(1).getName())))
                .andExpect(jsonPath("$[1].developersAccounts", hasSize(1)))
                .andExpect(jsonPath("$[1].developersAccounts[0]", is(
                        util.getDtoList(ProjectDto.class).get(1).getDevelopersAccounts().toArray(new String[0])[0])))
                .andExpect(jsonPath("$[1].customerId", is(
                        util.getDtoList(ProjectDto.class).get(1).getCustomerId().toString())))
                .andExpect(jsonPath("$[1].status", is(
                        util.getDtoList(ProjectDto.class).get(1).getStatus())))

                .andExpect(jsonPath("$[2].id", is(
                        util.getDtoList(ProjectDto.class).get(2).getId().toString())))
                .andExpect(jsonPath("$[2].name", is(
                        util.getDtoList(ProjectDto.class).get(2).getName())))
                .andExpect(jsonPath("$[2].developersAccounts", hasSize(1)))
                .andExpect(jsonPath("$[2].developersAccounts[0]", is(
                        util.getDtoList(ProjectDto.class).get(2)
                                .getDevelopersAccounts().toArray(new String[0])[0])))
                .andExpect(jsonPath("$[2].customerId", is(
                        util.getDtoList(ProjectDto.class).get(2).getCustomerId().toString())))
                .andExpect(jsonPath("$[2].status", is(
                        util.getDtoList(ProjectDto.class).get(2).getStatus())))

                .andExpect(jsonPath("$[3].id", is(
                        util.getDtoList(ProjectDto.class).get(3).getId().toString())))
                .andExpect(jsonPath("$[3].name", is(
                        util.getDtoList(ProjectDto.class).get(3).getName())))
                .andExpect(jsonPath("$[3].developersAccounts", hasSize(1)))
                .andExpect(jsonPath("$[3].developersAccounts[0]", is(
                        util.getDtoList(ProjectDto.class).get(3)
                                .getDevelopersAccounts().toArray(new String[0])[0])))
                .andExpect(jsonPath("$[3].customerId", is(
                        util.getDtoList(ProjectDto.class).get(3).getCustomerId().toString())))
                .andExpect(jsonPath("$[3].status", is(
                        util.getDtoList(ProjectDto.class).get(3).getStatus())));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldInvokeCreateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(post("http://localhost:8080/api/v1/projects")
                .with(user("admin").password("nimda").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(util.getDto(ProjectDto.class, TestedEntities.ENTITY_TO_CREATE))))
                .andExpect(status().isCreated());

        mockMvc.perform(get("http://localhost:8080/api/v1/projects")
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldInvokeUpdateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(put("http://localhost:8080/api/v1/projects")
                .with(user("admin").password("nimda").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(util.getDto(ProjectDto.class, TestedEntities.ENTITY_TO_UPDATE))))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("http://localhost:8080/api/v1/projects/" +
                util.getDto(ProjectDto.class, TestedEntities.ENTITY_TO_UPDATE).getId().toString())
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(
                        util.getDto(ProjectDto.class, TestedEntities.ENTITY_TO_UPDATE).getId().toString())))
                .andExpect(jsonPath("$.name", is(
                        util.getDto(ProjectDto.class, TestedEntities.ENTITY_TO_UPDATE).getName())))
                .andExpect(jsonPath("$.developersAccounts", hasSize(1)))
                .andExpect(jsonPath("$.developersAccounts[0]", is(
                        util.getDto(ProjectDto.class, TestedEntities.ENTITY_TO_UPDATE).getDevelopersAccounts()
                                .toArray(new String[0])[0])))
                .andExpect(jsonPath("$.customerId", is(
                        util.getDto(ProjectDto.class, TestedEntities.ENTITY_TO_UPDATE).getCustomerId().toString())))
                .andExpect(jsonPath("$.status", is(
                        util.getDto(ProjectDto.class, TestedEntities.ENTITY_TO_UPDATE).getStatus())));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldInvokeDeleteInServiceWithGivenLong() throws Exception {
        mockMvc.perform(delete("http://localhost:8080/api/v1/projects/" +
                util.getDto(ProjectDto.class, TestedEntities.ENTITY_TO_DELETE).getId().toString())
                .with(user("admin").password("nimda").roles("ADMIN")))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("http://localhost:8080/api/v1/projects/" +
                util.getDto(ProjectDto.class, TestedEntities.ENTITY_TO_DELETE).getId().toString())
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isNotFound());
    }
}
