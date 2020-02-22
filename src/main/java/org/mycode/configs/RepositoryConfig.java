package org.mycode.configs;

import org.mycode.repository.AccountRepository;
import org.mycode.repository.DeveloperRepository;
import org.mycode.repository.SkillRepository;
import org.mycode.repository.jdbc.JDBCAccountRepositoryImpl;
import org.mycode.repository.jdbc.JDBCDeveloperRepositoryImpl;
import org.mycode.repository.jdbc.JDBCSkillRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
    @Bean(name = "skillRepository")
    public SkillRepository skillRepository(){
        return new JDBCSkillRepositoryImpl();
    }
    @Bean(name = "accountRepository")
    public AccountRepository accountRepository(){
        return new JDBCAccountRepositoryImpl();
    }
    @Bean(name = "developerRepository")
    public DeveloperRepository developerRepository(){
        return new JDBCDeveloperRepositoryImpl();
    }
}
