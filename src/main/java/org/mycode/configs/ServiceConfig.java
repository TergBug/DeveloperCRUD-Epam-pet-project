package org.mycode.configs;

import org.mycode.repository.AccountRepository;
import org.mycode.repository.DeveloperRepository;
import org.mycode.repository.SkillRepository;
import org.mycode.service.AccountService;
import org.mycode.service.DeveloperService;
import org.mycode.service.Serviceable;
import org.mycode.service.SkillService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    @Bean(name = "skillService")
    public Serviceable skillService(SkillRepository skillRepository){
        return new SkillService(skillRepository);
    }
    @Bean(name = "accountService")
    public Serviceable accountService(AccountRepository accountRepository){
        return new AccountService(accountRepository);
    }
    @Bean(name = "developerService")
    public Serviceable developerService(DeveloperRepository developerRepository){
        return new DeveloperService(developerRepository);
    }
}
