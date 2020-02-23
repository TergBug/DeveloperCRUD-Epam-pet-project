package org.mycode.configs;

import org.mycode.annotations.TimerBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RepositoryConfig.class, ServiceConfig.class, WebConfig.class})
public class CommonConfig {
    @Bean
    public TimerBeanPostProcessor timerBeanPostProcessor(){
        return new TimerBeanPostProcessor();
    }
}
