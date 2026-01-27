package com.cucu.report.plugin.model;

import lombok.Getter;

@Getter
public enum CucScenarioType {
    SCENARIO("Scenario"),
    SCENARIO_OUTLINE("Scenario Outline");

    private final String type;

    CucScenarioType(String type) {
        this.type = type;
    }

}
