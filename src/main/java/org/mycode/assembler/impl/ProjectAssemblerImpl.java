package org.mycode.assembler.impl;

import org.mycode.assembler.ProjectAssembler;
import org.mycode.dto.ProjectDto;
import org.mycode.exceptions.NoSuchEntryException;
import org.mycode.model.Developer;
import org.mycode.model.Project;
import org.mycode.model.enums.ProjectStatus;
import org.mycode.repository.CustomerRepository;
import org.mycode.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProjectAssemblerImpl implements ProjectAssembler {
    private DeveloperRepository developerRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public void setDeveloperRepository(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Project assemble(ProjectDto projectDto) {
        try {
            return new Project(projectDto.getId(), projectDto.getName(),
                    getDevelopersByAccountNames(projectDto.getDevelopersAccounts()),
                    ProjectStatus.valueOf(projectDto.getStatus()), customerRepository.getById(projectDto.getCustomerId()));
        } catch (NoSuchEntryException e) {
            return new Project(projectDto.getId(), projectDto.getName(),
                    getDevelopersByAccountNames(projectDto.getDevelopersAccounts()),
                    ProjectStatus.valueOf(projectDto.getStatus()), null);
        }
    }

    @Override
    public ProjectDto assemble(Project project) {
        return new ProjectDto(project.getId(), project.getName(),
                project.getDevelopers().stream().map(el -> el.getAccount().getName()).collect(Collectors.toSet()),
                project.getCustomer().getId(), project.getStatus().name());
    }

    private Set<Developer> getDevelopersByAccountNames(Set<String> accountNames) {
        Set<Developer> developers = new HashSet<>(developerRepository.getAll());
        developers.removeIf(el -> !accountNames.contains(el.getAccount().getName()));
        return developers;
    }
}
