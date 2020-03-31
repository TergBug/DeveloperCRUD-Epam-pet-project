package org.mycode.repository.hibernate;

import org.hibernate.SessionFactory;
import org.mycode.exceptions.NoSuchEntryException;
import org.mycode.model.Skill;
import org.mycode.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@EnableTransactionManagement
public class SkillRepositoryImpl implements SkillRepository {
    private SessionFactory sessionFactory;

    @Autowired
    public SkillRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void create(Skill model) {
        sessionFactory.getCurrentSession().save(model);
    }

    @Override
    @Transactional(readOnly = true)
    public Skill getById(UUID readID) {
        Skill readSkill = sessionFactory.getCurrentSession().get(Skill.class, readID);
        if (readSkill == null) {
            throw new NoSuchEntryException("Entity for read not found");
        }
        return readSkill;
    }

    @Override
    @Transactional
    public void update(Skill updatedModel) {
        Skill updatedSkill = sessionFactory.getCurrentSession().get(Skill.class, updatedModel.getId());
        ;
        if (updatedSkill == null) {
            throw new NoSuchEntryException("Entity for update not found");
        }
        sessionFactory.getCurrentSession().detach(updatedSkill);
        sessionFactory.getCurrentSession().update(updatedModel);
    }

    @Override
    @Transactional
    public void delete(UUID deletedEntryId) {
        Skill deletedSkill = sessionFactory.getCurrentSession().get(Skill.class, deletedEntryId);
        if (deletedSkill == null) {
            throw new NoSuchEntryException("Entity for delete not found");
        }
        sessionFactory.getCurrentSession().delete(deletedSkill);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Skill> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from Skill", Skill.class).list();
    }
}
