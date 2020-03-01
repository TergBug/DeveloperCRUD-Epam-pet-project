package org.mycode.service;

import org.apache.log4j.Logger;
import org.mycode.model.Developer;
import org.mycode.repository.DeveloperRepository;
import org.mycode.service.visitors.ServiceVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("developerService")
public class DeveloperService implements Serviceable {
    private static final Logger log = Logger.getLogger(DeveloperService.class);
    private DeveloperRepository currentRepo;

    @Autowired
    public DeveloperService(DeveloperRepository currentRepo) {
        this.currentRepo = currentRepo;
    }

    public void create(Developer model) {
        currentRepo.create(model);
        log.debug("Service->Create");
    }

    public Developer getById(Long readID) {
        Developer developer = currentRepo.getById(readID);
        log.debug("Service->Read");
        return developer;
    }

    public void update(Developer updatedModel) {
        currentRepo.update(updatedModel);
        log.debug("Service->Update");
    }

    public void delete(Long deletedEntry) {
        currentRepo.delete(deletedEntry);
        log.debug("Service->Delete");
    }

    public List<Developer> getAll() {
        List<Developer> developers = currentRepo.getAll();
        log.debug("Service->Get all");
        return developers;
    }

    @Override
    public void doService(ServiceVisitor visitor) {
        visitor.visitDeveloperService(this);
    }
}
