package com.cucu.report.plugin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CucuBeanInitializer {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
