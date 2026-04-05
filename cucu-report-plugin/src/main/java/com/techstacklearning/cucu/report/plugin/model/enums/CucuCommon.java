package com.techstacklearning.cucu.report.plugin.model.enums;

import lombok.Getter;

@Getter
public enum CucuCommon {
    FEATURE("Feature"),
    EXAMPLE("Example");

    private final String status;

    CucuCommon(String status) {
        this.status = status;
    }

}
