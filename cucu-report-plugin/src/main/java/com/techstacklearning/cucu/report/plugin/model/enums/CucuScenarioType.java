package com.techstacklearning.cucu.report.plugin.model.enums;

import lombok.Getter;

@Getter
public enum CucuScenarioType {
    SCENARIO("Scenario"),
    SCENARIO_OUTLINE("Scenario Outline");

    private final String type;

    CucuScenarioType(String type) {
        this.type = type;
    }

}
