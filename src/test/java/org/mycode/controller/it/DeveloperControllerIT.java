package org.mycode.controller.it;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.mycode.model.Account;
import org.mycode.model.AccountStatus;
import org.mycode.model.Developer;
import org.mycode.model.Skill;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DeveloperControllerIT extends BaseIT {
    private static final Developer READ_DEVELOPER = new Developer(1L, "Din", "Ford",
            Arrays.stream(new Skill[]{new Skill(1L, "Java"), new Skill(3L, "JDBC")})
                    .collect(Collectors.toSet()),
            new Account(2L, "Din", AccountStatus.DELETED));

    private static final Developer CREATED_DEVELOPER = new Developer(5L, "Lord", "Dog",
            Arrays.stream(new Skill[]{new Skill(2L, "C#")}).collect(Collectors.toSet()),
            new Account(3L, "Geek", AccountStatus.BANNED));

    private static final Developer UPDATED_DEVELOPER = new Developer(1L, "Dinis", "Wong",
            Arrays.stream(new Skill[]{new Skill(3L, "JDBC")}).collect(Collectors.toSet()),
            new Account(2L, "Din", AccountStatus.DELETED));

    private static final List<Developer> DEVELOPERS_LIST = Arrays.asList(READ_DEVELOPER,
            new Developer(2L, "Xiaoming", "Li",
                    Arrays.stream(new Skill[]{new Skill(2L, "C#")}).collect(Collectors.toSet()),
                    new Account(1L, "LiXiao", AccountStatus.ACTIVE)),

            new Developer(3L, "Gird", "Long",
                    Arrays.stream(new Skill[]{new Skill(1L, "Java"), new Skill(2L, "C#")})
                            .collect(Collectors.toSet()),
                    new Account(3L, "Geek", AccountStatus.BANNED)),

            new Developer(4L, "Gordon", "Fong",
                    new HashSet<>(),
                    new Account(1L, "LiXiao", AccountStatus.ACTIVE)));

    @Before
    public void connectAndInitDB() {
        connectionAndInitDB.reInitDB();
    }

    @Test
    public void shouldReturnJsonEntryFromGivenLong() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/developers/1")
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(READ_DEVELOPER.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(READ_DEVELOPER.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(READ_DEVELOPER.getLastName())))
                .andExpect(jsonPath("$.skills", hasSize(2)))
                .andExpect(jsonPath("$.skills[0].id", is(READ_DEVELOPER.getSkills()
                        .toArray(new Skill[2])[0].getId().intValue())))
                .andExpect(jsonPath("$.skills[0].name", is(READ_DEVELOPER.getSkills()
                        .toArray(new Skill[2])[0].getName())))
                .andExpect(jsonPath("$.skills[1].id", is(READ_DEVELOPER.getSkills()
                        .toArray(new Skill[2])[1].getId().intValue())))
                .andExpect(jsonPath("$.skills[1].name", is(READ_DEVELOPER.getSkills()
                        .toArray(new Skill[2])[1].getName())))
                .andExpect(jsonPath("$.account.id", is(READ_DEVELOPER.getAccount().getId().intValue())))
                .andExpect(jsonPath("$.account.name", is(READ_DEVELOPER.getAccount().getName())))
                .andExpect(jsonPath("$.account.status", is(READ_DEVELOPER.getAccount()
                        .getStatus().toString())));
    }

    @Test
    public void shouldReturnJsonAllEntries() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/developers")
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))

                .andExpect(jsonPath("$[0].id", is(DEVELOPERS_LIST.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].firstName", is(DEVELOPERS_LIST.get(0).getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(DEVELOPERS_LIST.get(0).getLastName())))
                .andExpect(jsonPath("$[0].skills", hasSize(2)))
                .andExpect(jsonPath("$[0].skills[0].id", is(DEVELOPERS_LIST.get(0).getSkills()
                        .toArray(new Skill[2])[0].getId().intValue())))
                .andExpect(jsonPath("$[0].skills[0].name", is(DEVELOPERS_LIST.get(0).getSkills()
                        .toArray(new Skill[2])[0].getName())))
                .andExpect(jsonPath("$[0].skills[1].id", is(DEVELOPERS_LIST.get(0).getSkills()
                        .toArray(new Skill[2])[1].getId().intValue())))
                .andExpect(jsonPath("$[0].skills[1].name", is(DEVELOPERS_LIST.get(0).getSkills()
                        .toArray(new Skill[2])[1].getName())))
                .andExpect(jsonPath("$[0].account.id", is(DEVELOPERS_LIST.get(0).getAccount()
                        .getId().intValue())))
                .andExpect(jsonPath("$[0].account.name", is(DEVELOPERS_LIST.get(0).getAccount().getName())))
                .andExpect(jsonPath("$[0].account.status", is(DEVELOPERS_LIST.get(0).getAccount()
                        .getStatus().toString())))

                .andExpect(jsonPath("$[1].id", is(DEVELOPERS_LIST.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].firstName", is(DEVELOPERS_LIST.get(1).getFirstName())))
                .andExpect(jsonPath("$[1].lastName", is(DEVELOPERS_LIST.get(1).getLastName())))
                .andExpect(jsonPath("$[1].skills", hasSize(1)))
                .andExpect(jsonPath("$[1].skills[0].id", is(DEVELOPERS_LIST.get(1).getSkills()
                        .toArray(new Skill[1])[0].getId().intValue())))
                .andExpect(jsonPath("$[1].skills[0].name", is(DEVELOPERS_LIST.get(1).getSkills()
                        .toArray(new Skill[1])[0].getName())))
                .andExpect(jsonPath("$[1].account.id", is(DEVELOPERS_LIST.get(1).getAccount()
                        .getId().intValue())))
                .andExpect(jsonPath("$[1].account.name", is(DEVELOPERS_LIST.get(1).getAccount().getName())))
                .andExpect(jsonPath("$[1].account.status", is(DEVELOPERS_LIST.get(1).getAccount()
                        .getStatus().toString())))

                .andExpect(jsonPath("$[2].id", is(DEVELOPERS_LIST.get(2).getId().intValue())))
                .andExpect(jsonPath("$[2].firstName", is(DEVELOPERS_LIST.get(2).getFirstName())))
                .andExpect(jsonPath("$[2].lastName", is(DEVELOPERS_LIST.get(2).getLastName())))
                .andExpect(jsonPath("$[2].skills", hasSize(2)))
                .andExpect(jsonPath("$[2].skills[0].id", is(DEVELOPERS_LIST.get(2).getSkills()
                        .toArray(new Skill[2])[0].getId().intValue())))
                .andExpect(jsonPath("$[2].skills[0].name", is(DEVELOPERS_LIST.get(2).getSkills()
                        .toArray(new Skill[2])[0].getName())))
                .andExpect(jsonPath("$[2].skills[1].id", is(DEVELOPERS_LIST.get(2).getSkills()
                        .toArray(new Skill[2])[1].getId().intValue())))
                .andExpect(jsonPath("$[2].skills[1].name", is(DEVELOPERS_LIST.get(2).getSkills()
                        .toArray(new Skill[2])[1].getName())))
                .andExpect(jsonPath("$[2].account.id", is(DEVELOPERS_LIST.get(2).getAccount()
                        .getId().intValue())))
                .andExpect(jsonPath("$[2].account.name", is(DEVELOPERS_LIST.get(2).getAccount().getName())))
                .andExpect(jsonPath("$[2].account.status", is(DEVELOPERS_LIST.get(2).getAccount()
                        .getStatus().toString())))

                .andExpect(jsonPath("$[3].id", is(DEVELOPERS_LIST.get(3).getId().intValue())))
                .andExpect(jsonPath("$[3].firstName", is(DEVELOPERS_LIST.get(3).getFirstName())))
                .andExpect(jsonPath("$[3].lastName", is(DEVELOPERS_LIST.get(3).getLastName())))
                .andExpect(jsonPath("$[3].skills", hasSize(0)))
                .andExpect(jsonPath("$[3].account.id", is(DEVELOPERS_LIST.get(3).getAccount()
                        .getId().intValue())))
                .andExpect(jsonPath("$[3].account.name", is(DEVELOPERS_LIST.get(3).getAccount().getName())))
                .andExpect(jsonPath("$[3].account.status", is(DEVELOPERS_LIST.get(3).getAccount()
                        .getStatus().toString())));
    }

    @Test
    public void shouldInvokeCreateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(post("http://localhost:8080/api/v1/developers")
                .with(user("admin").password("nimda").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(CREATED_DEVELOPER)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("http://localhost:8080/api/v1/developers/5")
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(CREATED_DEVELOPER.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(CREATED_DEVELOPER.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(CREATED_DEVELOPER.getLastName())))
                .andExpect(jsonPath("$.skills", hasSize(1)))
                .andExpect(jsonPath("$.skills[0].id", is(CREATED_DEVELOPER.getSkills()
                        .toArray(new Skill[1])[0].getId().intValue())))
                .andExpect(jsonPath("$.skills[0].name", is(CREATED_DEVELOPER.getSkills()
                        .toArray(new Skill[1])[0].getName())))
                .andExpect(jsonPath("$.account.id", is(CREATED_DEVELOPER.getAccount().getId().intValue())))
                .andExpect(jsonPath("$.account.name", is(CREATED_DEVELOPER.getAccount().getName())))
                .andExpect(jsonPath("$.account.status", is(CREATED_DEVELOPER.getAccount()
                        .getStatus().toString())));
    }

    @Test
    public void shouldInvokeUpdateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(put("http://localhost:8080/api/v1/developers")
                .with(user("admin").password("nimda").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(UPDATED_DEVELOPER)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("http://localhost:8080/api/v1/developers/1")
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(UPDATED_DEVELOPER.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(UPDATED_DEVELOPER.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(UPDATED_DEVELOPER.getLastName())))
                .andExpect(jsonPath("$.skills", hasSize(1)))
                .andExpect(jsonPath("$.skills[0].id", is(UPDATED_DEVELOPER.getSkills()
                        .toArray(new Skill[1])[0].getId().intValue())))
                .andExpect(jsonPath("$.skills[0].name", is(UPDATED_DEVELOPER.getSkills()
                        .toArray(new Skill[1])[0].getName())))
                .andExpect(jsonPath("$.account.id", is(UPDATED_DEVELOPER.getAccount().getId().intValue())))
                .andExpect(jsonPath("$.account.name", is(UPDATED_DEVELOPER.getAccount().getName())))
                .andExpect(jsonPath("$.account.status", is(UPDATED_DEVELOPER.getAccount()
                        .getStatus().toString())));
    }

    @Test
    public void shouldInvokeDeleteInServiceWithGivenLong() throws Exception {
        mockMvc.perform(delete("http://localhost:8080/api/v1/developers/4")
                .with(user("admin").password("nimda").roles("ADMIN")))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("http://localhost:8080/api/v1/developers/4")
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isNotFound());
    }
}
