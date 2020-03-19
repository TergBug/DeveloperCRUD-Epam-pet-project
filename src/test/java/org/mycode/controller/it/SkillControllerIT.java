package org.mycode.controller.it;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.mycode.model.Skill;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SkillControllerIT extends BaseIT {
    private static final Skill READ_SKILL = new Skill(1L, "Java");
    private static final Skill CREATED_SKILL = new Skill(5L, "HTML");
    private static final Skill UPDATED_SKILL = new Skill(1L, "JavaScript");
    private static final List<Skill> SKILLS_LIST = Arrays.asList(READ_SKILL,
            new Skill(2L, "C#"),
            new Skill(3L, "JDBC"),
            new Skill(4L, "JSON"));

    @Before
    public void connectAndInitDB() {
        connectionAndInitDB.reInitDB();
    }

    @Test
    public void shouldReturnJsonEntryFromGivenLong() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/skills/1")
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(READ_SKILL.getId().intValue())))
                .andExpect(jsonPath("$.name", is(READ_SKILL.getName())));
    }

    @Test
    public void shouldReturnJsonAllEntries() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/skills")
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))

                .andExpect(jsonPath("$[0].id", is(SKILLS_LIST.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(SKILLS_LIST.get(0).getName())))
                .andExpect(jsonPath("$[1].id", is(SKILLS_LIST.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(SKILLS_LIST.get(1).getName())))

                .andExpect(jsonPath("$[2].id", is(SKILLS_LIST.get(2).getId().intValue())))
                .andExpect(jsonPath("$[2].name", is(SKILLS_LIST.get(2).getName())))
                .andExpect(jsonPath("$[3].id", is(SKILLS_LIST.get(3).getId().intValue())))
                .andExpect(jsonPath("$[3].name", is(SKILLS_LIST.get(3).getName())));
    }

    @Test
    public void shouldInvokeCreateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(post("http://localhost:8080/api/v1/skills")
                .with(user("admin").password("nimda").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(CREATED_SKILL)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("http://localhost:8080/api/v1/skills/5")
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(CREATED_SKILL.getId().intValue())))
                .andExpect(jsonPath("$.name", is(CREATED_SKILL.getName())));
    }

    @Test
    public void shouldInvokeUpdateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(put("http://localhost:8080/api/v1/skills")
                .with(user("admin").password("nimda").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(UPDATED_SKILL)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("http://localhost:8080/api/v1/skills/1")
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(UPDATED_SKILL.getId().intValue())))
                .andExpect(jsonPath("$.name", is(UPDATED_SKILL.getName())));
    }

    @Test
    public void shouldInvokeDeleteInServiceWithGivenLong() throws Exception {
        mockMvc.perform(delete("http://localhost:8080/api/v1/skills/4")
                .with(user("admin").password("nimda").roles("ADMIN")))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("http://localhost:8080/api/v1/skills/4")
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isNotFound());
    }
}