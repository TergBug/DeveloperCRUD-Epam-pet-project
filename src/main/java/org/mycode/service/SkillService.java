package org.mycode.service;

import org.apache.log4j.Logger;
import org.mycode.exceptions.RepoStorageException;
import org.mycode.model.Skill;
import org.mycode.repository.SkillRepository;
import org.mycode.repository.jdbc.JDBCSkillRepositoryImpl;

import java.util.List;

public class SkillService {
    private static final Logger log = Logger.getLogger(SkillService.class);
    private SkillRepository currentRepo;
    public SkillService() throws RepoStorageException {
        this.currentRepo = new JDBCSkillRepositoryImpl();
    }
    public void create(Skill model) throws Exception{
        currentRepo.create(model);
        log.debug("Service->Create");
    }
    public Skill getById(Long readID) throws Exception{
        Skill skill = currentRepo.getById(readID);
        log.debug("Service->Read");
        return skill;
    }
    public void update(Skill updatedModel) throws Exception{
        currentRepo.update(updatedModel);
        log.debug("Service->Update");
    }
    public void delete(Long deletedEntry) throws Exception{
        currentRepo.delete(deletedEntry);
        log.debug("Service->Delete");
    }
    public List<Skill> getAll() throws Exception{
        List<Skill> skills = currentRepo.getAll();
        log.debug("Service->Get all");
        return skills;
    }
}
