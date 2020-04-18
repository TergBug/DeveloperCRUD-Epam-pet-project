package org.mycode.testutil;

import org.hibernate.SessionFactory;
import org.mycode.assembler.*;
import org.mycode.dto.*;
import org.mycode.model.*;
import org.mycode.model.enums.AccountStatus;
import org.mycode.model.enums.ProjectStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestedEntities {
    public static final int ENTITY_TO_CREATE = 4;
    public static final int ENTITY_TO_GET = 0;
    public static final int ENTITY_TO_UPDATE = 0;
    public static final int ENTITY_TO_DELETE = 3;
    private static final String HQL_QUERY_GET_ALL = "from ?";
    private static final String HQL_QUERY_GET_ALL_FETCHED = "select distinct t from ?1 t left join fetch t.?2";
    private List<SkillDto> skillDtos;
    private List<AccountDto> accountDtos;
    private List<DeveloperDto> developerDtos;
    private List<CustomerDto> customerDtos;
    private List<ProjectDto> projectDtos;

    private List<Skill> skills;
    private List<Account> accounts;
    private List<Developer> developers;
    private List<Customer> customers;
    private List<Project> projects;

    private SkillAssembler skillAssembler;
    private AccountAssembler accountAssembler;
    private DeveloperAssembler developerAssembler;
    private CustomerAssembler customerAssembler;
    private ProjectAssembler projectAssembler;
    private SessionFactory sessionFactory;

    @Autowired
    public TestedEntities(SkillAssembler skillAssembler, AccountAssembler accountAssembler,
                          DeveloperAssembler developerAssembler, CustomerAssembler customerAssembler,
                          ProjectAssembler projectAssembler, SessionFactory sessionFactory) {
        this.skillAssembler = skillAssembler;
        this.accountAssembler = accountAssembler;
        this.developerAssembler = developerAssembler;
        this.customerAssembler = customerAssembler;
        this.projectAssembler = projectAssembler;
        this.sessionFactory = sessionFactory;
    }

    public void init() {
        skills = sessionFactory.getCurrentSession().createQuery(HQL_QUERY_GET_ALL
                .replace("?", "Skill"), Skill.class).list();
        accounts = sessionFactory.getCurrentSession().createQuery(HQL_QUERY_GET_ALL
                .replace("?", "Account"), Account.class).list();
        developers = sessionFactory.getCurrentSession().createQuery(HQL_QUERY_GET_ALL_FETCHED
                .replace("?1", "Developer")
                .replace("?2", "skills"), Developer.class).list();
        customers = sessionFactory.getCurrentSession().createQuery(HQL_QUERY_GET_ALL_FETCHED
                .replace("?1", "Customer")
                .replace("?2", "projects"), Customer.class).list();
        projects = sessionFactory.getCurrentSession().createQuery(HQL_QUERY_GET_ALL_FETCHED
                .replace("?1", "Project")
                .replace("?2", "developers"), Project.class).list();

        skills.add(new Skill("HTML"));
        accounts.add(new Account("Lord", AccountStatus.ACTIVE));
        developers.add(new Developer("Lord", "Dog",
                Arrays.stream(new Skill[]{skills.get(1)}).collect(Collectors.toSet()), accounts.get(2)));
        customers.add(new Customer("Customer5",
                Arrays.stream(new Project[]{projects.get(2)}).collect(Collectors.toSet())));
        projects.add(new Project("Capton",
                Arrays.stream(new Developer[]{developers.get(0), developers.get(2)}).collect(Collectors.toSet()),
                ProjectStatus.DESIGN, customers.get(3)));

        skillDtos = skills.stream().map(skillAssembler::assemble).collect(Collectors.toList());
        accountDtos = accounts.stream().map(accountAssembler::assemble).collect(Collectors.toList());
        developerDtos = developers.stream().map(developerAssembler::assemble).collect(Collectors.toList());
        customerDtos = customers.stream().map(customerAssembler::assemble).collect(Collectors.toList());
        projectDtos = projects.stream().map(projectAssembler::assemble).collect(Collectors.toList());
    }

    public <T> T getEntity(Class<T> type, int forAction) {
        switch (type.getSimpleName().toLowerCase()) {
            case "skill":
                return (T) skills.get(forAction);
            case "account":
                return (T) accounts.get(forAction);
            case "developer":
                return (T) developers.get(forAction);
            case "customer":
                return (T) customers.get(forAction);
            case "project":
                return (T) projects.get(forAction);
            default:
                return null;
        }
    }

    public <T> T getDto(Class<T> type, int forAction) {
        switch (type.getSimpleName().toLowerCase()) {
            case "skilldto":
                return (T) skillDtos.get(forAction);
            case "accountdto":
                return (T) accountDtos.get(forAction);
            case "developerdto":
                return (T) developerDtos.get(forAction);
            case "customerdto":
                return (T) customerDtos.get(forAction);
            case "projectdto":
                return (T) projectDtos.get(forAction);
            default:
                return null;
        }
    }

    public <T> List<T> getEntityList(Class<T> type) {
        switch (type.getSimpleName().toLowerCase()) {
            case "skill":
                return (List<T>) skills.stream().filter(el -> skills.indexOf(el) != skills.size() - 1)
                        .collect(Collectors.toList());
            case "account":
                return (List<T>) accounts.stream().filter(el -> accounts.indexOf(el) != accounts.size() - 1)
                        .collect(Collectors.toList());
            case "developer":
                return (List<T>) developers.stream().filter(el -> developers.indexOf(el) != developers.size() - 1)
                        .collect(Collectors.toList());
            case "customer":
                return (List<T>) customers.stream().filter(el -> customers.indexOf(el) != customers.size() - 1)
                        .collect(Collectors.toList());
            case "project":
                return (List<T>) projects.stream().filter(el -> projects.indexOf(el) != projects.size() - 1)
                        .collect(Collectors.toList());
            default:
                return null;
        }
    }

    public <T> List<T> getDtoList(Class<T> type) {
        switch (type.getSimpleName().toLowerCase()) {
            case "skilldto":
                return (List<T>) skillDtos.stream().filter(el -> skillDtos.indexOf(el) != skillDtos.size() - 1)
                        .collect(Collectors.toList());
            case "accountdto":
                return (List<T>) accountDtos.stream().filter(el -> accountDtos.indexOf(el) != accountDtos.size() - 1)
                        .collect(Collectors.toList());
            case "developerdto":
                return (List<T>) developerDtos.stream().filter(el -> developerDtos.indexOf(el) != developerDtos.size() - 1)
                        .collect(Collectors.toList());
            case "customerdto":
                return (List<T>) customerDtos.stream().filter(el -> customerDtos.indexOf(el) != customerDtos.size() - 1)
                        .collect(Collectors.toList());
            case "projectdto":
                return (List<T>) projectDtos.stream().filter(el -> projectDtos.indexOf(el) != projectDtos.size() - 1)
                        .collect(Collectors.toList());
            default:
                return null;
        }
    }
}
