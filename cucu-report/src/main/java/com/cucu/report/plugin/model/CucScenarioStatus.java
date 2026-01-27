package com.cucu.report.plugin.model;

import lombok.Getter;

@Getter
public enum CucScenarioStatus {
    PASS("Pass"),
    FAIL("Fail");

    private final String status;

    CucScenarioStatus(String status) {
        this.status = status;
    }

}
