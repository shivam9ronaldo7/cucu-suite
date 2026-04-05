package com.techstacklearning.cucu.report.plugin.model.enums;

import lombok.Getter;

@Getter
public enum CucuStepStatus {
    PASS("Pass"),
    FAIL("Fail"),
    SKIP("Skip");

    private final String status;

    CucuStepStatus(String status) {
        this.status = status;
    }

}
