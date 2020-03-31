package org.mycode.repository.hibernate;

import org.hibernate.SessionFactory;
import org.mycode.configs.DatabaseConfig;
import org.mycode.exceptions.NoSuchEntryException;
import org.mycode.model.Developer;
import org.mycode.model.Skill;
import org.mycode.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@EnableTransactionManagement
public class DeveloperRepositoryImpl implements DeveloperRepository {
    private static final String HQL_QUERY_GET_BY_ID = "select distinct d from Developer d left join fetch d.skills " +
            "where d.id=unhex(replace('?', '-', ''))";
    private static final String HQL_QUERY_GET_ALL = "select distinct d from Developer d left join fetch d.skills";
    private SessionFactory sessionFactory;

    @Autowired
    public DeveloperRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        DeveloperRepository developerRepository = applicationContext.getBean(DeveloperRepository.class);
        AccountRepository accountRepository = applicationContext.getBean(AccountRepository.class);
        ProjectRepository projectRepository = applicationContext.getBean(ProjectRepository.class);
        SkillRepository skillRepository = applicationContext.getBean(SkillRepository.class);
        CustomerRepository customerRepository = applicationContext.getBean(CustomerRepository.class);

        skillRepository.create(new Skill(UUID.fromString("ad34f1da-43cd-4742-948c-f2e59a975839"), "Java"));

//        customerRepository.create(new Customer("Cu8", Arrays.stream(
//                new Project []{new Project("Pr1", Arrays.stream(
//                        new Developer[]{developerRepository.getById(UUID.fromString("8F0B7DBA-89F5-42FD-9907-E9C2F6809C1C"))})
//                        .collect(Collectors.toSet()), ProjectStatus.IMPLEMENTATION, null)})
//                .collect(Collectors.toSet())));

        //Developer developer = developerRepository.getById(UUID.fromString("9a4f7c3f-fe35-4b45-82f1-128b65cb3a92"));
        //System.out.println(developer.getSkills());
        //System.out.println(developer.getAccount());
//        Account account = accountRepository.getById(UUID.fromString("e86937cd-1535-4c0e-9026-e6ae27adc992"));
//        account.setStatus(AccountStatus.ACTIVE);
//        developer.setAccount(account);
        //developerRepository.update(developer);
//        developerRepository.create(new Developer("P", "Empo", Arrays.stream(
//                new Skill []{skillRepository.getById(UUID.fromString("77351156-9d20-405e-a0c5-24e17b4b0ad0")),})
//                .collect(Collectors.toSet()),
//                accountRepository.getById(UUID.fromString("77845a88-c171-4cd0-a73a-3045bdbe4f30"))));

        //System.out.println("\n"+developerRepository.getById(UUID.fromString("9a4f7c3f-fe35-4b45-82f1-128b65cb3a92")).getAccount());
    }

    @Override
    @Transactional
    public void create(Developer model) {
        sessionFactory.getCurrentSession().save(model);
    }

    @Override
    @Transactional(readOnly = true)
    public Developer getById(UUID readID) {
        List<Developer> readDevelopers = sessionFactory.getCurrentSession()
                .createQuery(HQL_QUERY_GET_BY_ID.replace("?", readID.toString()), Developer.class)
                .getResultList();
        if (readDevelopers == null || readDevelopers.size() != 1) {
            throw new NoSuchEntryException("Entity for read not found");
        }
        return readDevelopers.get(0);
    }

    @Override
    @Transactional
    public void update(Developer updatedModel) {
        Developer updatedDeveloper = sessionFactory.getCurrentSession().get(Developer.class, updatedModel.getId());
        if (updatedDeveloper == null) {
            throw new NoSuchEntryException("Entity for update not found");
        }
        sessionFactory.getCurrentSession().detach(updatedDeveloper);
        sessionFactory.getCurrentSession().update(updatedModel);
    }

    @Override
    @Transactional
    public void delete(UUID deletedEntryId) {
        Developer deletedDeveloper = sessionFactory.getCurrentSession().get(Developer.class, deletedEntryId);
        if (deletedDeveloper == null) {
            throw new NoSuchEntryException("Entity for delete not found");
        }
        sessionFactory.getCurrentSession().delete(deletedDeveloper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Developer> getAll() {
        return sessionFactory.getCurrentSession()
                .createQuery(HQL_QUERY_GET_ALL, Developer.class).list();
    }
}
