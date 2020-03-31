package org.mycode.service.impl;

import org.apache.log4j.Logger;
import org.mycode.assembler.SkillAssembler;
import org.mycode.dto.SkillDto;
import org.mycode.repository.SkillRepository;
import org.mycode.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("skillService")
public class SkillServiceImpl implements SkillService {
    private static final Logger log = Logger.getLogger(SkillServiceImpl.class);
    private SkillRepository currentRepo;
    private SkillAssembler skillAssembler;

    @Autowired
    public SkillServiceImpl(SkillRepository currentRepo, SkillAssembler skillAssembler) {
        this.currentRepo = currentRepo;
        this.skillAssembler = skillAssembler;
    }

    public void create(SkillDto model) {
        currentRepo.create(skillAssembler.assemble(model));
        log.debug("Service->Create");
    }

    public SkillDto getById(UUID readID) {
        SkillDto skill = skillAssembler.assemble(currentRepo.getById(readID));
        log.debug("Service->Read");
        return skill;
    }

    public void update(SkillDto updatedModel) {
        currentRepo.update(skillAssembler.assemble(updatedModel));
        log.debug("Service->Update");
    }

    public void delete(UUID deletedEntry) {
        currentRepo.delete(deletedEntry);
        log.debug("Service->Delete");
    }

    public List<SkillDto> getAll() {
        List<SkillDto> skills = currentRepo.getAll().stream().map(el -> skillAssembler.assemble(el))
                .collect(Collectors.toList());
        log.debug("Service->Get all");
        return skills;
    }
}
