package org.mycode;

import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.SessionFactory;
import org.junit.runner.RunWith;
import org.mycode.assembler.*;
import org.mycode.configs.CommonConfig;
import org.mycode.testutil.TestedEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = CommonConfig.class)
@TestPropertySource(value = "classpath:testConfig.properties")
@EnableTransactionManagement
public abstract class BaseIT {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected SessionFactory sessionFactory;
    @Autowired
    protected TestedEntities util;

    @Configuration
    @EnableWebMvc
    @ComponentScan("org.mycode")
    protected static class TestContextConfiguration {
        @Autowired
        private DataSource dataSource;

        @Bean
        public SpringLiquibase liquibase() {
            SpringLiquibase liquibase = new SpringLiquibase();
            liquibase.setChangeLog("classpath:liquibase/changelog.xml");
            liquibase.setDataSource(dataSource);
            return liquibase;
        }

        @Bean
        public MockMvc mockMvc(WebApplicationContext webApplicationContext) {
            return MockMvcBuilders
                    .webAppContextSetup(webApplicationContext)
                    .apply(SecurityMockMvcConfigurers.springSecurity())
                    .alwaysDo(print())
                    .build();
        }

        @Bean
        @Transactional(readOnly = true)
        public TestedEntities testedEntities(SkillAssembler skillAssembler, AccountAssembler accountAssembler,
                                             DeveloperAssembler developerAssembler, CustomerAssembler customerAssembler,
                                             ProjectAssembler projectAssembler, SessionFactory sessionFactory) {
            TestedEntities testedEntities = new TestedEntities(skillAssembler, accountAssembler, developerAssembler,
                    customerAssembler, projectAssembler, sessionFactory);
            testedEntities.init();
            return testedEntities;
        }
    }
}
