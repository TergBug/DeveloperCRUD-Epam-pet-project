package org.mycode.service;

import org.mycode.dto.ProjectDto;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    void create(ProjectDto model);

    ProjectDto getById(UUID readID);

    void update(ProjectDto updatedModel);

    void delete(UUID deletedEntry);

    List<ProjectDto> getAll();
}
