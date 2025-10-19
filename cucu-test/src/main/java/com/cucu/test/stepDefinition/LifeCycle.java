package com.cucu.test.stepDefinition;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LifeCycle {

    @Value("${env}")
    private String environment;

    @PostConstruct
    public void postConstruct() throws Exception {
        if (environment.equalsIgnoreCase("local")) {
            System.out.println("Local post construct");
        }
    }

    @PreDestroy
    public void preDestroy() throws Exception {
        if (environment.equalsIgnoreCase("local")) {
            System.out.println("Local pre destroy");
        }
    }

}
