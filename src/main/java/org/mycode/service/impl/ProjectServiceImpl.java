package org.mycode.service.impl;

import org.apache.log4j.Logger;
import org.mycode.assembler.ProjectAssembler;
import org.mycode.dto.ProjectDto;
import org.mycode.repository.ProjectRepository;
import org.mycode.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
    private static final Logger log = Logger.getLogger(ProjectServiceImpl.class);
    private ProjectRepository currentRepo;
    private ProjectAssembler projectAssembler;

    @Autowired
    public ProjectServiceImpl(ProjectRepository currentRepo, ProjectAssembler projectAssembler) {
        this.currentRepo = currentRepo;
        this.projectAssembler = projectAssembler;
    }

    public void create(ProjectDto model) {
        currentRepo.create(projectAssembler.assemble(model));
        log.debug("Service->Create");
    }

    public ProjectDto getById(UUID readID) {
        ProjectDto project = projectAssembler.assemble(currentRepo.getById(readID));
        log.debug("Service->Read");
        return project;
    }

    public void update(ProjectDto updatedModel) {
        currentRepo.update(projectAssembler.assemble(updatedModel));
        log.debug("Service->Update");
    }

    public void delete(UUID deletedEntry) {
        currentRepo.delete(deletedEntry);
        log.debug("Service->Delete");
    }

    public List<ProjectDto> getAll() {
        List<ProjectDto> projects = currentRepo.getAll().stream().map(el -> projectAssembler.assemble(el))
                .collect(Collectors.toList());
        log.debug("Service->Get all");
        return projects;
    }
}
