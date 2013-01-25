package de.groupon.sample.config.context;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "de.groupon.sample", excludeFilters = {
        @ComponentScan.Filter(Configuration.class)
})
@Import({
        SpringMVCConfig.class,
        RedisConfig.class
})
public class ApplicationConfig {
}
