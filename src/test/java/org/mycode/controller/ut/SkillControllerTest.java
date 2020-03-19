package org.mycode.controller.ut;

import com.google.gson.Gson;
import org.junit.Test;
import org.mycode.controller.SkillController;
import org.mycode.model.Skill;
import org.mycode.service.SkillService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class SkillControllerTest {
    private static final Skill SKILL = new Skill(1L, "Java");
    private static final List<Skill> SKILLS_LIST = Arrays.asList(SKILL, new Skill(2L, "C#"));
    private SkillService mockedService = mock(SkillService.class);
    private SkillController sut = new SkillController(mockedService);
    private MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sut).build();

    @Test
    public void shouldReturnJsonEntryFromGivenLong() throws Exception {
        when(mockedService.getById(1L)).thenReturn(SKILL);
        doCallRealMethod().when(mockedService).doService(any());
        mockMvc.perform(get("http://localhost:8080/api/v1/skills/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(SKILL.getId().intValue())))
                .andExpect(jsonPath("$.name", is(SKILL.getName())));
    }

    @Test
    public void shouldReturnJsonAllEntries() throws Exception {
        when(mockedService.getAll()).thenReturn(SKILLS_LIST);
        doCallRealMethod().when(mockedService).doService(any());
        mockMvc.perform(get("http://localhost:8080/api/v1/skills"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(SKILLS_LIST.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(SKILLS_LIST.get(0).getName())))
                .andExpect(jsonPath("$[1].id", is(SKILLS_LIST.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(SKILLS_LIST.get(1).getName())));
    }

    @Test
    public void shouldInvokeCreateInServiceWithGivenJson() throws Exception {
        doCallRealMethod().when(mockedService).doService(any());
        mockMvc.perform(post("http://localhost:8080/api/v1/skills")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(SKILL)))
                .andExpect(status().isCreated());
        verify(mockedService, times(1)).create(SKILL);
    }

    @Test
    public void shouldInvokeUpdateInServiceWithGivenJson() throws Exception {
        doCallRealMethod().when(mockedService).doService(any());
        mockMvc.perform(put("http://localhost:8080/api/v1/skills")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(SKILL)))
                .andExpect(status().isNoContent());
        verify(mockedService, times(1)).update(SKILL);
    }

    @Test
    public void shouldInvokeDeleteInServiceWithGivenLong() throws Exception {
        doCallRealMethod().when(mockedService).doService(any());
        mockMvc.perform(delete("http://localhost:8080/api/v1/skills/1"))
                .andExpect(status().isNoContent());
        verify(mockedService, times(1)).delete(1L);
    }
}
