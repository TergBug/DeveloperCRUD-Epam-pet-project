package org.mycode.service;

import org.mycode.dto.DeveloperDto;

import java.util.List;
import java.util.UUID;

public interface DeveloperService {
    void create(DeveloperDto model);

    DeveloperDto getById(UUID readID);

    void update(DeveloperDto updatedModel);

    void delete(UUID deletedEntry);

    List<DeveloperDto> getAll();
}
