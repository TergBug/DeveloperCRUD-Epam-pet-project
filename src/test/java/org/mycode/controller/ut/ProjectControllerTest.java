package org.mycode.controller.ut;

import com.google.gson.Gson;
import org.junit.Test;
import org.mycode.controller.ProjectController;
import org.mycode.dto.ProjectDto;
import org.mycode.service.impl.ProjectServiceImpl;
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

public class ProjectControllerTest {
    private static final UUID ID_FOR_GET = UUID.fromString("e07748c4-88ba-4c69-a994-8bad8890b2b8");
    private static final UUID ID_FOR_LIST = UUID.fromString("958c58da-dfae-4fcc-85bd-8d4a7454cfb4");
    private static final UUID CUSTOMER_ID_FOR_GET = UUID.fromString("2936f525-5b05-47e2-8c08-f681c9ab1139");
    private static final UUID CUSTOMER_ID_FOR_LIST = UUID.fromString("3025faf1-dc64-40ae-840b-58d5f711eeb6");
    private static final ProjectDto PROJECT = new ProjectDto(ID_FOR_GET, "Dinot",
            Arrays.stream(new String[]{"LiXiao", "Ford"}).collect(Collectors.toSet()),
            CUSTOMER_ID_FOR_GET, "DESIGN");
    private static final List<ProjectDto> PROJECTS_LIST = Arrays.asList(PROJECT,
            new ProjectDto(ID_FOR_LIST, "Pejh", Arrays.stream(new String[]{"Geek"}).collect(Collectors.toSet()),
                    CUSTOMER_ID_FOR_LIST, "TESTING"));
    private ProjectServiceImpl mockedService = mock(ProjectServiceImpl.class);
    private ProjectController sut = new ProjectController(mockedService);
    private MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sut).build();

    @Test
    public void shouldReturnJsonEntryFromGivenLong() throws Exception {
        when(mockedService.getById(ID_FOR_GET)).thenReturn(PROJECT);
        mockMvc.perform(get("http://localhost:8080/api/v1/projects/e07748c4-88ba-4c69-a994-8bad8890b2b8"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(PROJECT.getId().toString())))
                .andExpect(jsonPath("$.name", is(PROJECT.getName())))
                .andExpect(jsonPath("$.developersAccounts", hasSize(2)))
                .andExpect(jsonPath("$.developersAccounts[0]", is(PROJECT.getDevelopersAccounts()
                        .toArray(new String[0])[0])))
                .andExpect(jsonPath("$.developersAccounts[1]", is(PROJECT.getDevelopersAccounts()
                        .toArray(new String[0])[1])))
                .andExpect(jsonPath("$.customerId", is(PROJECT.getCustomerId().toString())))
                .andExpect(jsonPath("$.status", is(PROJECT.getStatus())));
    }

    @Test
    public void shouldReturnJsonAllEntries() throws Exception {
        when(mockedService.getAll()).thenReturn(PROJECTS_LIST);
        mockMvc.perform(get("http://localhost:8080/api/v1/projects"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(PROJECTS_LIST.get(0).getId().toString())))
                .andExpect(jsonPath("$[0].name", is(PROJECTS_LIST.get(0).getName())))
                .andExpect(jsonPath("$[0].developersAccounts", hasSize(2)))
                .andExpect(jsonPath("$[0].developersAccounts[0]", is(PROJECTS_LIST.get(0)
                        .getDevelopersAccounts().toArray(new String[0])[0])))
                .andExpect(jsonPath("$[0].developersAccounts[1]", is(PROJECTS_LIST.get(0)
                        .getDevelopersAccounts().toArray(new String[0])[1])))
                .andExpect(jsonPath("$[0].customerId", is(PROJECTS_LIST.get(0).getCustomerId().toString())))
                .andExpect(jsonPath("$[0].status", is(PROJECTS_LIST.get(0).getStatus())))

                .andExpect(jsonPath("$[1].id", is(PROJECTS_LIST.get(1).getId().toString())))
                .andExpect(jsonPath("$[1].name", is(PROJECTS_LIST.get(1).getName())))
                .andExpect(jsonPath("$[1].developersAccounts", hasSize(1)))
                .andExpect(jsonPath("$[1].developersAccounts[0]", is(PROJECTS_LIST.get(1)
                        .getDevelopersAccounts().toArray(new String[0])[0])))
                .andExpect(jsonPath("$[1].customerId", is(PROJECTS_LIST.get(1).getCustomerId().toString())))
                .andExpect(jsonPath("$[1].status", is(PROJECTS_LIST.get(1).getStatus())));
    }

    @Test
    public void shouldInvokeCreateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(post("http://localhost:8080/api/v1/projects")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(PROJECT)))
                .andExpect(status().isCreated());
        verify(mockedService, times(1)).create(PROJECT);
    }

    @Test
    public void shouldInvokeUpdateInServiceWithGivenJson() throws Exception {
        mockMvc.perform(put("http://localhost:8080/api/v1/projects")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(PROJECT)))
                .andExpect(status().isNoContent());
        verify(mockedService, times(1)).update(PROJECT);
    }

    @Test
    public void shouldInvokeDeleteInServiceWithGivenLong() throws Exception {
        mockMvc.perform(delete("http://localhost:8080/api/v1/projects/e07748c4-88ba-4c69-a994-8bad8890b2b8"))
                .andExpect(status().isNoContent());
        verify(mockedService, times(1)).delete(ID_FOR_GET);
    }
}
