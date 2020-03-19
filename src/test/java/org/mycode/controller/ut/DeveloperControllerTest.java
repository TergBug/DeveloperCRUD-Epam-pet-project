package org.mycode.controller.ut;

import com.google.gson.Gson;
import org.junit.Test;
import org.mycode.controller.DeveloperController;
import org.mycode.model.Account;
import org.mycode.model.AccountStatus;
import org.mycode.model.Developer;
import org.mycode.model.Skill;
import org.mycode.service.DeveloperService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DeveloperControllerTest {
    private static final Developer DEVELOPER = new Developer(1L, "Lord", "Dog",
            Arrays.stream(new Skill[]{new Skill(1L, "Java")}).collect(Collectors.toSet()),
            new Account(1L, "Frog", AccountStatus.ACTIVE));

    private static final List<Developer> DEVELOPERS_LIST = Arrays.asList(DEVELOPER,
            new Developer(2L, "Gon", "Pink",
                    Arrays.stream(new Skill[]{new Skill(1L, "Java"), new Skill(2L, "C#")})
                            .collect(Collectors.toSet()),
                    new Account(2L, "Doh", AccountStatus.DELETED)));

    private DeveloperService mockedService = mock(DeveloperService.class);
    private DeveloperController sut = new DeveloperController(mockedService);
    private MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sut).build();

    @Test
    public void shouldReturnJsonEntryFromGivenLong() throws Exception {
        when(mockedService.getById(1L)).thenReturn(DEVELOPER);
        doCallRealMethod().when(mockedService).doService(any());
        mockMvc.perform(get("http://localhost:8080/api/v1/developers/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(DEVELOPER.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(DEVELOPER.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(DEVELOPER.getLastName())))
                .andExpect(jsonPath("$.skills", hasSize(1)))
                .andExpect(jsonPath("$.skills[0].id", is(DEVELOPER.getSkills()
                        .toArray(new Skill[1])[0].getId().intValue())))
                .andExpect(jsonPath("$.skills[0].name", is(DEVELOPER.getSkills()
                        .toArray(new Skill[1])[0].getName())))
                .andExpect(jsonPath("$.account.id", is(DEVELOPER.getAccount().getId().intValue())))
                .andExpect(jsonPath("$.account.name", is(DEVELOPER.getAccount().getName())))
                .andExpect(jsonPath("$.account.status", is(DEVELOPER.getAccount().getStatus().toString())));
    }

    @Test
    public void shouldReturnJsonAllEntries() throws Exception {
        when(mockedService.getAll()).thenReturn(DEVELOPERS_LIST);
        doCallRealMethod().when(mockedService).doService(any());
        mockMvc.perform(get("http://localhost:8080/api/v1/developers"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].id", is(DEVELOPERS_LIST.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].firstName", is(DEVELOPERS_LIST.get(0).getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(DEVELOPERS_LIST.get(0).getLastName())))
                .andExpect(jsonPath("$[0].skills", hasSize(1)))
                .andExpect(jsonPath("$[0].skills[0].id", is(DEVELOPERS_LIST.get(0).getSkills()
                        .toArray(new Skill[1])[0].getId().intValue())))
                .andExpect(jsonPath("$[0].skills[0].name", is(DEVELOPERS_LIST.get(0).getSkills()
                        .toArray(new Skill[1])[0].getName())))
                .andExpect(jsonPath("$[0].account.id", is(DEVELOPERS_LIST.get(0).getAccount()
                        .getId().intValue())))
                .andExpect(jsonPath("$[0].account.name", is(DEVELOPERS_LIST.get(0).getAccount().getName())))
                .andExpect(jsonPath("$[0].account.status", is(DEVELOPERS_LIST.get(0).getAccount()
                        .getStatus().toString())))

                .andExpect(jsonPath("$[1].id", is(DEVELOPERS_LIST.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].firstName", is(DEVELOPERS_LIST.get(1).getFirstName())))
                .andExpect(jsonPath("$[1].lastName", is(DEVELOPERS_LIST.get(1).getLastName())))
                .andExpect(jsonPath("$[1].skills", hasSize(2)))
                .andExpect(jsonPath("$[1].skills[0].id", is(DEVELOPERS_LIST.get(1).getSkills()
                        .toArray(new Skill[2])[0].getId().intValue())))
                .andExpect(jsonPath("$[1].skills[0].name", is(DEVELOPERS_LIST.get(1).getSkills()
                        .toArray(new Skill[2])[0].getName())))
                .andExpect(jsonPath("$[1].skills[1].id", is(DEVELOPERS_LIST.get(1).getSkills()
                        .toArray(new Skill[2])[1].getId().intValue())))
                .andExpect(jsonPath("$[1].skills[1].name", is(DEVELOPERS_LIST.get(1).getSkills()
                        .toArray(new Skill[2])[1].getName())))
                .andExpect(jsonPath("$[1].account.id", is(DEVELOPERS_LIST.get(1).getAccount()
                        .getId().intValue())))
                .andExpect(jsonPath("$[1].account.name", is(DEVELOPERS_LIST.get(1).getAccount().getName())))
                .andExpect(jsonPath("$[1].account.status", is(DEVELOPERS_LIST.get(1).getAccount()
                        .getStatus().toString())));
    }

    @Test
    public void shouldInvokeCreateInServiceWithGivenJson() throws Exception {
        doCallRealMethod().when(mockedService).doService(any());
        mockMvc.perform(post("http://localhost:8080/api/v1/developers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(DEVELOPER)))
                .andExpect(status().isCreated());
        verify(mockedService, times(1)).create(DEVELOPER);
    }

    @Test
    public void shouldInvokeUpdateInServiceWithGivenJson() throws Exception {
        doCallRealMethod().when(mockedService).doService(any());
        mockMvc.perform(put("http://localhost:8080/api/v1/developers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(DEVELOPER)))
                .andExpect(status().isNoContent());
        verify(mockedService, times(1)).update(DEVELOPER);
    }

    @Test
    public void shouldInvokeDeleteInServiceWithGivenLong() throws Exception {
        doCallRealMethod().when(mockedService).doService(any());
        mockMvc.perform(delete("http://localhost:8080/api/v1/developers/1"))
                .andExpect(status().isNoContent());
        verify(mockedService, times(1)).delete(1L);
    }
}
