package com.cucu.test.runner;

import io.cucumber.testng.*;
import org.testng.annotations.DataProvider;

@CucumberOptions(
		features = {
				"src/test/resources/features"
		},
		glue = {
				"stepDefination"
		},
		tags = "@test",
//		monochrome = true,
		plugin = {
				"pretty",
				"html:target/cucumber/report.html",
				"json:target/cucumber/report.json",
				"message:target/cucumber/report.ndjson",
		})
public class TestNGRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

}
