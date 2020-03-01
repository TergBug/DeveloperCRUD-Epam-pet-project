package org.mycode.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"org.mycode.service", "org.mycode.repository"})
public class TestConfig {
}
