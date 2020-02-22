package org.mycode.service;

import org.apache.log4j.Logger;
import org.mycode.model.Skill;
import org.mycode.repository.SkillRepository;
import org.mycode.service.visitors.ServiceVisitor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService implements Serviceable {
    private static final Logger log = Logger.getLogger(SkillService.class);
    private SkillRepository currentRepo;
    public SkillService(SkillRepository currentRepo) {
        this.currentRepo = currentRepo;
    }
    public void doStuff(){
        System.out.println(currentRepo);
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
