package com.techstacklearning.cucu.report.plugin.model;

import lombok.Getter;

@Getter
public enum CucStepType {
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

    CucStepType(String stepType) {
        this.stepType = stepType;
    }

}
