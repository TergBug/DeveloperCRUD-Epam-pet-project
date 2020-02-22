package org.mycode.configs;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.mycode.controller")
public class ControllerConfig {
    @Bean
    public Gson getJson(){
        return new Gson();
    }
}
