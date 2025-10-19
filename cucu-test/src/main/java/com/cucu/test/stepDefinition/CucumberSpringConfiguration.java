package com.cucu.test.stepDefinition;

import io.cucumber.spring.CucumberContextConfiguration;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.DataProvider;

@CucumberContextConfiguration
@ContextConfiguration(classes = {CucumberScenarioBeanInitializer.class})
public class CucumberSpringConfiguration extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

}
