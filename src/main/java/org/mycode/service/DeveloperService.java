package org.mycode.service;

import org.apache.log4j.Logger;
import org.mycode.exceptions.RepoStorageException;
import org.mycode.model.Developer;
import org.mycode.repository.DeveloperRepository;
import org.mycode.repository.jdbc.JDBCDeveloperRepositoryImpl;
import org.mycode.service.visitors.ServiceVisitor;

import java.util.List;

public class DeveloperService extends Service{
    private static final Logger log = Logger.getLogger(DeveloperService.class);
    private DeveloperRepository currentRepo;
    public DeveloperService() throws RepoStorageException {
        this.currentRepo = new JDBCDeveloperRepositoryImpl();
    }
    public void create(Developer model) throws RuntimeException{
        currentRepo.create(model);
        log.debug("Service->Create");
    }
    public Developer getById(Long readID) throws RuntimeException{
        Developer developer = currentRepo.getById(readID);
        log.debug("Service->Read");
        return developer;
    }
    public void update(Developer updatedModel) throws RuntimeException{
        currentRepo.update(updatedModel);
        log.debug("Service->Update");
    }
    public void delete(Long deletedEntry) throws RuntimeException{
        currentRepo.delete(deletedEntry);
        log.debug("Service->Delete");
    }
    public List<Developer> getAll() throws RuntimeException{
        List<Developer> developers = currentRepo.getAll();
        log.debug("Service->Get all");
        return developers;
    }
    @Override
    public void doService(ServiceVisitor visitor) {
        visitor.visitDeveloperService(this);
    }
}
