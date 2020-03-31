package org.mycode.controller.it;

import com.google.gson.Gson;
import org.junit.Test;
import org.mycode.dto.DeveloperDto;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DeveloperControllerIT extends BaseIT {
    private static final UUID ID_FOR_CREATE = UUID.randomUUID();
    private static final UUID ID_FOR_GET = UUID.fromString("673c347a-ba46-4c5f-9f13-6d1a1aa29b81");
    private static final UUID ID_SECOND = UUID.fromString("9a4f7c3f-fe35-4b45-82f1-128b65cb3a92");
    private static final UUID ID_THIRD = UUID.fromString("cff6549f-b728-4ed5-857e-3027e7e6a370");
    private static final UUID ID_FOR_DELETE = UUID.fromString("6bc3db84-1930-438a-b230-63379ac93ded");
    private static final DeveloperDto READ_DEVELOPER = new DeveloperDto(ID_FOR_GET, "Gird", "Long",
            Arrays.stream(new String[]{"Java", "C#"}).collect(Collectors.toSet()), "Geek");

    private static final DeveloperDto CREATED_DEVELOPER = new DeveloperDto(ID_FOR_CREATE, "Lord", "Dog",
            Arrays.stream(new String[]{"C#"}).collect(Collectors.toSet()), "Geek");

    private static final DeveloperDto UPDATED_DEVELOPER = new DeveloperDto(ID_FOR_GET, "Dinis", "Wong",
            Arrays.stream(new String[]{"JDBC"}).collect(Collectors.toSet()), "Din");

    private static final List<DeveloperDto> DEVELOPERS_LIST = Arrays.asList(READ_DEVELOPER, new DeveloperDto(),
            new DeveloperDto(ID_SECOND, "Din", "Ford",
                    Arrays.stream(new String[]{"Java", "JDBC"}).collect(Collectors.toSet()), "Din"),

            new DeveloperDto(ID_THIRD, "Xiaoming", "Li",
                    Arrays.stream(new String[]{"C#"}).collect(Collectors.toSet()), "LiXiao"));

    @Test
    @Transactional
    @Rollback
    public void shouldReturnJsonEntryFromGivenLong() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/developers/" + ID_FOR_GET.toString())
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(READ_DEVELOPER.getId().toString())))
                .andExpect(jsonPath("$.firstName", is(READ_DEVELOPER.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(READ_DEVELOPER.getLastName())))
                .andExpect(jsonPath("$.skills", hasSize(2)))
                .andExpect(jsonPath("$.skills[0]", is(READ_DEVELOPER.getSkills().toArray(new String[2])[0])))
                .andExpect(jsonPath("$.skills[1]", is(READ_DEVELOPER.getSkills().toArray(new String[2])[1])))
                .andExpect(jsonPath("$.account", is(READ_DEVELOPER.getAccount())));
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

                .andExpect(jsonPath("$[0].id", is(DEVELOPERS_LIST.get(0).getId().toString())))
                .andExpect(jsonPath("$[0].firstName", is(DEVELOPERS_LIST.get(0).getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(DEVELOPERS_LIST.get(0).getLastName())))
                .andExpect(jsonPath("$[0].skills", hasSize(2)))
                .andExpect(jsonPath("$[0].skills[0]", is(DEVELOPERS_LIST.get(0).getSkills()
                        .toArray(new String[2])[0])))
                .andExpect(jsonPath("$[0].skills[1]", is(DEVELOPERS_LIST.get(0).getSkills()
                        .toArray(new String[2])[1])))
                .andExpect(jsonPath("$[0].account", is(DEVELOPERS_LIST.get(0).getAccount())))

                .andExpect(jsonPath("$[2].id", is(DEVELOPERS_LIST.get(2).getId().toString())))
                .andExpect(jsonPath("$[2].firstName", is(DEVELOPERS_LIST.get(2).getFirstName())))
                .andExpect(jsonPath("$[2].lastName", is(DEVELOPERS_LIST.get(2).getLastName())))
                .andExpect(jsonPath("$[2].skills", hasSize(2)))
                .andExpect(jsonPath("$[2].skills[0]", is(DEVELOPERS_LIST.get(2).getSkills()
                        .toArray(new String[2])[0])))
                .andExpect(jsonPath("$[2].skills[1]", is(DEVELOPERS_LIST.get(2).getSkills()
                        .toArray(new String[2])[1])))
                .andExpect(jsonPath("$[2].account", is(DEVELOPERS_LIST.get(2).getAccount())))

                .andExpect(jsonPath("$[3].id", is(DEVELOPERS_LIST.get(3).getId().toString())))
                .andExpect(jsonPath("$[3].firstName", is(DEVELOPERS_LIST.get(3).getFirstName())))
                .andExpect(jsonPath("$[3].lastName", is(DEVELOPERS_LIST.get(3).getLastName())))
                .andExpect(jsonPath("$[3].skills", hasSize(1)))
                .andExpect(jsonPath("$[3].skills[0]", is(DEVELOPERS_LIST.get(3).getSkills()
                        .toArray(new String[1])[0])))
                .andExpect(jsonPath("$[3].account", is(DEVELOPERS_LIST.get(3).getAccount())));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldInvokeCreateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(post("http://localhost:8080/api/v1/developers")
                .with(user("admin").password("nimda").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(CREATED_DEVELOPER)))
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
                .content(new Gson().toJson(UPDATED_DEVELOPER)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("http://localhost:8080/api/v1/developers/" + ID_FOR_GET.toString())
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(UPDATED_DEVELOPER.getId().toString())))
                .andExpect(jsonPath("$.firstName", is(UPDATED_DEVELOPER.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(UPDATED_DEVELOPER.getLastName())))
                .andExpect(jsonPath("$.skills", hasSize(1)))
                .andExpect(jsonPath("$.skills[0]", is(UPDATED_DEVELOPER.getSkills()
                        .toArray(new String[1])[0])))
                .andExpect(jsonPath("$.account", is(UPDATED_DEVELOPER.getAccount())));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldInvokeDeleteInServiceWithGivenLong() throws Exception {
        mockMvc.perform(delete("http://localhost:8080/api/v1/developers/" + ID_FOR_DELETE.toString())
                .with(user("admin").password("nimda").roles("ADMIN")))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("http://localhost:8080/api/v1/developers/" + ID_FOR_DELETE.toString())
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isNotFound());
    }
}
