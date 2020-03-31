package org.mycode.controller.ut;

import com.google.gson.Gson;
import org.junit.Test;
import org.mycode.controller.DeveloperController;
import org.mycode.dto.DeveloperDto;
import org.mycode.service.impl.DeveloperServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DeveloperControllerTest {
    private static final UUID ID_FOR_GET = UUID.fromString("673c347a-ba46-4c5f-9f13-6d1a1aa29b81");
    private static final UUID ID_FOR_LIST = UUID.fromString("9a4f7c3f-fe35-4b45-82f1-128b65cb3a92");
    private static final DeveloperDto DEVELOPER = new DeveloperDto(ID_FOR_GET, "Lord", "Dog",
            Arrays.stream(new String[]{"Java"}).collect(Collectors.toSet()), "Frog");

    private static final List<DeveloperDto> DEVELOPERS_LIST = Arrays.asList(DEVELOPER,
            new DeveloperDto(ID_FOR_LIST, "Gon", "Pink",
                    Arrays.stream(new String[]{"Java", "C#"}).collect(Collectors.toSet()), "Doh"));

    private DeveloperServiceImpl mockedService = mock(DeveloperServiceImpl.class);
    private DeveloperController sut = new DeveloperController(mockedService);
    private MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sut).build();

    @Test
    public void shouldReturnJsonEntryFromGivenLong() throws Exception {
        when(mockedService.getById(ID_FOR_GET)).thenReturn(DEVELOPER);
        mockMvc.perform(get("http://localhost:8080/api/v1/developers/673c347a-ba46-4c5f-9f13-6d1a1aa29b81"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(DEVELOPER.getId().toString())))
                .andExpect(jsonPath("$.firstName", is(DEVELOPER.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(DEVELOPER.getLastName())))
                .andExpect(jsonPath("$.skills", hasSize(1)))
                .andExpect(jsonPath("$.skills[0]", is(DEVELOPER.getSkills()
                        .toArray(new String[1])[0])))
                .andExpect(jsonPath("$.account", is(DEVELOPER.getAccount())));
    }

    @Test
    public void shouldReturnJsonAllEntries() throws Exception {
        when(mockedService.getAll()).thenReturn(DEVELOPERS_LIST);
        mockMvc.perform(get("http://localhost:8080/api/v1/developers"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].id", is(DEVELOPERS_LIST.get(0).getId().toString())))
                .andExpect(jsonPath("$[0].firstName", is(DEVELOPERS_LIST.get(0).getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(DEVELOPERS_LIST.get(0).getLastName())))
                .andExpect(jsonPath("$[0].skills", hasSize(1)))
                .andExpect(jsonPath("$[0].skills[0]", is(DEVELOPERS_LIST.get(0).getSkills()
                        .toArray(new String[1])[0])))
                .andExpect(jsonPath("$[0].account", is(DEVELOPERS_LIST.get(0).getAccount())))

                .andExpect(jsonPath("$[1].id", is(DEVELOPERS_LIST.get(1).getId().toString())))
                .andExpect(jsonPath("$[1].firstName", is(DEVELOPERS_LIST.get(1).getFirstName())))
                .andExpect(jsonPath("$[1].lastName", is(DEVELOPERS_LIST.get(1).getLastName())))
                .andExpect(jsonPath("$[1].skills", hasSize(2)))
                .andExpect(jsonPath("$[1].skills[0]", is(DEVELOPERS_LIST.get(1).getSkills()
                        .toArray(new String[2])[0])))
                .andExpect(jsonPath("$[1].skills[1]", is(DEVELOPERS_LIST.get(1).getSkills()
                        .toArray(new String[2])[1])))
                .andExpect(jsonPath("$[1].account", is(DEVELOPERS_LIST.get(1).getAccount())));
    }

    @Test
    public void shouldInvokeCreateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(post("http://localhost:8080/api/v1/developers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(DEVELOPER)))
                .andExpect(status().isCreated());
        verify(mockedService, times(1)).create(DEVELOPER);
    }

    @Test
    public void shouldInvokeUpdateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(put("http://localhost:8080/api/v1/developers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(DEVELOPER)))
                .andExpect(status().isNoContent());
        verify(mockedService, times(1)).update(DEVELOPER);
    }

    @Test
    public void shouldInvokeDeleteInServiceWithGivenLong() throws Exception {
        mockMvc.perform(delete("http://localhost:8080/api/v1/developers/673c347a-ba46-4c5f-9f13-6d1a1aa29b81"))
                .andExpect(status().isNoContent());
        verify(mockedService, times(1)).delete(ID_FOR_GET);
    }
}
