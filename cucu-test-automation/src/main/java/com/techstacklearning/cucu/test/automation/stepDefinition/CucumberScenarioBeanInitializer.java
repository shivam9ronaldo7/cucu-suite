package com.techstacklearning.cucu.test.automation.stepDefinition;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

@Component
@ComponentScan(basePackages = {"annotations", "com/techstacklearning/cucu/test/automation/utility", "com/techstacklearning/cucu/test/automation/stepDefinition"})
@PropertySource(value = {"classpath:application.properties", "classpath:application-${env}.properties"})
public class CucumberScenarioBeanInitializer {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Yaml yaml() {
        return new Yaml();
    }

}
