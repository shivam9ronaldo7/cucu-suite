package com.techstacklearning.cucu.report.plugin.model.enums;

import lombok.Getter;

@Getter
public enum CucuScenarioStatus {
    PASS("Pass"),
    FAIL("Fail");

    private final String status;

    CucuScenarioStatus(String status) {
        this.status = status;
    }

}
