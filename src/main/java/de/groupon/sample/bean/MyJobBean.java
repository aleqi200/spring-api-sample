package de.groupon.sample.bean;

import org.springframework.scheduling.annotation.Scheduled;

public class MyJobBean {

    @Scheduled(fixedDelay = 1000)
    public void sayHello(){
        System.out.println("Hello");
    }

}
