package de.groupon.sample.config.context;

import de.groupon.sample.bean.MyJobBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulingConfig {

    @Bean
    public MyJobBean jobBean(){
        return new MyJobBean();
    }
}
