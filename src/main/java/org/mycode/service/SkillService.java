package org.mycode.service;

import org.mycode.dto.SkillDto;

import java.util.List;
import java.util.UUID;

public interface SkillService {
    void create(SkillDto model);

    SkillDto getById(UUID readID);

    void update(SkillDto updatedModel);

    void delete(UUID deletedEntry);

    List<SkillDto> getAll();
}
