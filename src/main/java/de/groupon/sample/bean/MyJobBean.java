package de.groupon.sample.bean;

import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class MyJobBean {

    @Scheduled(fixedDelay = 1000)
    public void sayHello(){
        System.out.println("Hello");
    }

    @PostConstruct
    public void init(){
        System.out.println("Created");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("Destroyed");
    }

}
