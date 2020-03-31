package org.mycode.assembler.impl;

import org.mycode.assembler.DeveloperAssembler;
import org.mycode.dto.DeveloperDto;
import org.mycode.exceptions.NotUniqueEntryException;
import org.mycode.model.Account;
import org.mycode.model.Developer;
import org.mycode.model.Skill;
import org.mycode.repository.AccountRepository;
import org.mycode.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DeveloperAssemblerImpl implements DeveloperAssembler {
    private SkillRepository skillRepository;
    private AccountRepository accountRepository;

    @Autowired
    public void setSkillRepository(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public DeveloperDto assemble(Developer developerEntity) {
        return new DeveloperDto(developerEntity.getId(),
                developerEntity.getFirstName(),
                developerEntity.getLastName(),
                developerEntity.getSkills().stream().map(Skill::getName).collect(Collectors.toSet()),
                developerEntity.getAccount() == null ? "null" : developerEntity.getAccount().getName());
    }

    @Override
    public Developer assemble(DeveloperDto developer) {
        return new Developer(developer.getId(),
                developer.getFirstName(),
                developer.getLastName(),
                getSkillsByNames(developer.getSkills()),
                getAccountByName(developer.getAccount()));
    }

    private Set<Skill> getSkillsByNames(Set<String> names) {
        Set<Skill> skills = new HashSet<>(skillRepository.getAll());
        skills.removeIf(el -> !names.contains(el.getName()));
        return skills;
    }

    private Account getAccountByName(String name) {
        List<Account> accounts = accountRepository.getAll();
        Account resultAccount = null;
        for (Account account : accounts) {
            if (account.getName().equals(name)) {
                if (resultAccount != null) {
                    throw new NotUniqueEntryException("Not unique name of skill");
                }
                resultAccount = account;
            }
        }
        return resultAccount;
    }
}
