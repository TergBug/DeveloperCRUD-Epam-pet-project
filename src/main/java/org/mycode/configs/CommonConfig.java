package org.mycode.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RepositoryConfig.class, ServiceConfig.class, WebConfig.class})
public class CommonConfig {
}
