package org.mycode.controller.ut;

import com.google.gson.Gson;
import org.junit.Test;
import org.mycode.controller.SkillController;
import org.mycode.dto.SkillDto;
import org.mycode.service.impl.SkillServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SkillControllerTest {
    private static final UUID ID_FOR_GET = UUID.fromString("77351156-9d20-405e-a0c5-24e17b4b0ad0");
    private static final UUID ID_FOR_LIST = UUID.fromString("e893a876-a583-42d4-99c1-a99ef99723db");
    private static final SkillDto SKILL = new SkillDto(ID_FOR_GET, "Java");
    private static final List<SkillDto> SKILLS_LIST = Arrays.asList(SKILL, new SkillDto(ID_FOR_LIST, "C#"));
    private SkillServiceImpl mockedService = mock(SkillServiceImpl.class);
    private SkillController sut = new SkillController(mockedService);
    private MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sut).build();

    @Test
    public void shouldReturnJsonEntryFromGivenLong() throws Exception {
        when(mockedService.getById(ID_FOR_GET)).thenReturn(SKILL);
        mockMvc.perform(get("http://localhost:8080/api/v1/skills/77351156-9d20-405e-a0c5-24e17b4b0ad0"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(SKILL.getId().toString())))
                .andExpect(jsonPath("$.name", is(SKILL.getName())));
    }

    @Test
    public void shouldReturnJsonAllEntries() throws Exception {
        when(mockedService.getAll()).thenReturn(SKILLS_LIST);
        mockMvc.perform(get("http://localhost:8080/api/v1/skills"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(SKILLS_LIST.get(0).getId().toString())))
                .andExpect(jsonPath("$[0].name", is(SKILLS_LIST.get(0).getName())))
                .andExpect(jsonPath("$[1].id", is(SKILLS_LIST.get(1).getId().toString())))
                .andExpect(jsonPath("$[1].name", is(SKILLS_LIST.get(1).getName())));
    }

    @Test
    public void shouldInvokeCreateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(post("http://localhost:8080/api/v1/skills")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(SKILL)))
                .andExpect(status().isCreated());
        verify(mockedService, times(1)).create(SKILL);
    }

    @Test
    public void shouldInvokeUpdateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(put("http://localhost:8080/api/v1/skills")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(SKILL)))
                .andExpect(status().isNoContent());
        verify(mockedService, times(1)).update(SKILL);
    }

    @Test
    public void shouldInvokeDeleteInServiceWithGivenLong() throws Exception {
        mockMvc.perform(delete("http://localhost:8080/api/v1/skills/77351156-9d20-405e-a0c5-24e17b4b0ad0"))
                .andExpect(status().isNoContent());
        verify(mockedService, times(1)).delete(ID_FOR_GET);
    }
}
