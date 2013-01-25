package de.groupon.sample.config.context;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan(basePackages = "de.groupon.api", excludeFilters = {@ComponentScan.Filter(Configuration.class)})
@Import({
        SpringMVCConfig.class
})
public class ApplicationConfig {
}
