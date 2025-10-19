package com.cucu.test.stepDefinition;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.springframework.beans.factory.annotation.Autowired;
import com.cucu.test.utility.ReportUtil;
import com.cucu.test.utility.WebDriverUtil;

import java.util.Objects;

public class Hooks {

    @Autowired
    private WebDriverUtil webDriverUtil;

    @Autowired
    private ReportUtil reportUtil;

    @AfterStep
    public void afterStepHook(Scenario scenario) {
        if (scenario.isFailed() && !Objects.isNull(webDriverUtil.getWebDriver())) {
            reportUtil.attachScreenshotToReport();
        }
    }

    @Before
    public void beforeHook(Scenario scenario) {
        reportUtil.setScenario(scenario);
    }

    @After
    public void afterHook() {
        if (!Objects.isNull(webDriverUtil.getWebDriver())) {
            webDriverUtil.closeWebDriver();
        }
    }

}
