package org.mycode.service;

import org.apache.log4j.Logger;
import org.mycode.exceptions.RepoStorageException;
import org.mycode.model.Skill;
import org.mycode.repository.SkillRepository;
import org.mycode.repository.jdbc.JDBCSkillRepositoryImpl;
import org.mycode.service.visitors.ServiceVisitor;

import java.util.List;

public class SkillService extends Service {
    private static final Logger log = Logger.getLogger(SkillService.class);
    private SkillRepository currentRepo;
    public SkillService() throws RepoStorageException {
        this.currentRepo = new JDBCSkillRepositoryImpl();
    }
    public void create(Skill model) throws RuntimeException{
        currentRepo.create(model);
        log.debug("Service->Create");
    }
    public Skill getById(Long readID) throws RuntimeException{
        Skill skill = currentRepo.getById(readID);
        log.debug("Service->Read");
        return skill;
    }
    public void update(Skill updatedModel) throws RuntimeException{
        currentRepo.update(updatedModel);
        log.debug("Service->Update");
    }
    public void delete(Long deletedEntry) throws RuntimeException{
        currentRepo.delete(deletedEntry);
        log.debug("Service->Delete");
    }
    public List<Skill> getAll() throws RuntimeException{
        List<Skill> skills = currentRepo.getAll();
        log.debug("Service->Get all");
        return skills;
    }
    @Override
    public void doService(ServiceVisitor visitor) {
        visitor.visitSkillService(this);
    }
}
