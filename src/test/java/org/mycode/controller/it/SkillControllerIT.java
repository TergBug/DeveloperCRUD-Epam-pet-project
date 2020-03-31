package org.mycode.controller.it;

import com.google.gson.Gson;
import org.junit.Test;
import org.mycode.dto.SkillDto;
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

public class SkillControllerIT extends BaseIT {
    private static final UUID ID_FOR_CREATE = UUID.randomUUID();
    private static final UUID ID_FOR_GET = UUID.fromString("e893a876-a583-42d4-99c1-a99ef99723db");
    private static final UUID ID_SECOND = UUID.fromString("77351156-9d20-405e-a0c5-24e17b4b0ad0");
    private static final UUID ID_THIRD = UUID.fromString("ad34f1da-43cd-4742-948c-f2e59a975839");
    private static final UUID ID_FOR_DELETE = UUID.fromString("a8f6eab7-f1c9-4e3a-85fb-e4a1e0504de3");
    private static final SkillDto READ_SKILL = new SkillDto(ID_FOR_GET, "Java");
    private static final SkillDto CREATED_SKILL = new SkillDto(ID_FOR_CREATE, "HTML");
    private static final SkillDto UPDATED_SKILL = new SkillDto(ID_FOR_GET, "JavaScript");
    private static final List<SkillDto> SKILLS_LIST = Arrays.asList(READ_SKILL,
            new SkillDto(ID_SECOND, "C#"),
            new SkillDto(ID_THIRD, "JDBC"));

    @Test
    @Transactional
    @Rollback
    public void shouldReturnJsonEntryFromGivenLong() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/skills/" + ID_FOR_GET.toString())
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(READ_SKILL.getId().toString())))
                .andExpect(jsonPath("$.name", is(READ_SKILL.getName())));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldReturnJsonAllEntries() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/skills")
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))

                .andExpect(jsonPath("$[0].id", is(SKILLS_LIST.get(0).getId().toString())))
                .andExpect(jsonPath("$[0].name", is(SKILLS_LIST.get(0).getName())))

                .andExpect(jsonPath("$[1].id", is(SKILLS_LIST.get(1).getId().toString())))
                .andExpect(jsonPath("$[1].name", is(SKILLS_LIST.get(1).getName())))

                .andExpect(jsonPath("$[2].id", is(SKILLS_LIST.get(2).getId().toString())))
                .andExpect(jsonPath("$[2].name", is(SKILLS_LIST.get(2).getName())));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldInvokeCreateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(post("http://localhost:8080/api/v1/skills")
                .with(user("admin").password("nimda").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(CREATED_SKILL)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("http://localhost:8080/api/v1/skills")
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldInvokeUpdateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(put("http://localhost:8080/api/v1/skills")
                .with(user("admin").password("nimda").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(UPDATED_SKILL)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("http://localhost:8080/api/v1/skills/" + ID_FOR_GET.toString())
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(UPDATED_SKILL.getId().toString())))
                .andExpect(jsonPath("$.name", is(UPDATED_SKILL.getName())));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldInvokeDeleteInServiceWithGivenLong() throws Exception {
        mockMvc.perform(delete("http://localhost:8080/api/v1/skills/" + ID_FOR_DELETE.toString())
                .with(user("admin").password("nimda").roles("ADMIN")))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("http://localhost:8080/api/v1/skills/" + ID_FOR_DELETE.toString())
                .with(user("user").password("resu").roles("USER")))
                .andExpect(status().isNotFound());
    }
}