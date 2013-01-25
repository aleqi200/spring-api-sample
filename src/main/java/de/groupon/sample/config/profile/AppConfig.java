package de.groupon.sample.config.profile;

import de.groupon.sample.config.context.ApplicationConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
@Import(ApplicationConfig.class)
public class AppConfig {

}
