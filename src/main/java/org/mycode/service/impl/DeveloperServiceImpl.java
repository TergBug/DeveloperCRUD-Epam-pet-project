package org.mycode.service.impl;

import org.apache.log4j.Logger;
import org.mycode.assembler.DeveloperAssembler;
import org.mycode.dto.DeveloperDto;
import org.mycode.repository.DeveloperRepository;
import org.mycode.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("developerService")
public class DeveloperServiceImpl implements DeveloperService {
    private static final Logger log = Logger.getLogger(DeveloperServiceImpl.class);
    private DeveloperRepository currentRepo;
    private DeveloperAssembler developerAssembler;

    @Autowired
    public DeveloperServiceImpl(DeveloperRepository currentRepo, DeveloperAssembler developerAssembler) {
        this.currentRepo = currentRepo;
        this.developerAssembler = developerAssembler;
    }

    public void create(DeveloperDto model) {
        currentRepo.create(developerAssembler.assemble(model));
        log.debug("Service->Create");
    }

    public DeveloperDto getById(UUID readID) {
        DeveloperDto developer = developerAssembler.assemble(currentRepo.getById(readID));
        log.debug("Service->Read");
        return developer;
    }

    public void update(DeveloperDto updatedModel) {
        currentRepo.update(developerAssembler.assemble(updatedModel));
        log.debug("Service->Update");
    }

    public void delete(UUID deletedEntry) {
        currentRepo.delete(deletedEntry);
        log.debug("Service->Delete");
    }

    public List<DeveloperDto> getAll() {
        List<DeveloperDto> developers = currentRepo.getAll().stream().map(el -> developerAssembler.assemble(el))
                .collect(Collectors.toList());
        log.debug("Service->Get all");
        return developers;
    }
}
