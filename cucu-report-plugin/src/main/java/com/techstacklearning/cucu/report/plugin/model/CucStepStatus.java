package com.techstacklearning.cucu.report.plugin.model;

import lombok.Getter;

@Getter
public enum CucStepStatus {
    PASS("Pass"),
    FAIL("Fail"),
    SKIP("Skip");

    private final String status;

    CucStepStatus(String status) {
        this.status = status;
    }

}
