package com.techstacklearning.cucu.report.plugin.model.enums;

import lombok.Getter;

@Getter
public enum CucuStepType {
    GIVEN("Given"),
    WHEN("When"),
    THEN("Then"),
    AND("And"),
    BUT("But"),
    BACKGROUND_GIVEN("Given"),
    BACKGROUND_WHEN("When"),
    BACKGROUND_THEN("Then"),
    BACKGROUND_AND("And"),
    BACKGROUND_BUT("But"),
    BEFORE_SCENARIO_HOOK("Hook"),
    AFTER_SCENARIO_HOOK("Hook"),
    BEFORE_STEP_HOOK("Hook"),
    AFTER_STEP_HOOK("Hook");

    private final String stepType;

    CucuStepType(String stepType) {
        this.stepType = stepType;
    }

}
