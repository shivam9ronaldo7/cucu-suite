package com.cucu.report.plugin.model;

import lombok.Getter;

@Getter
public enum CucCommon {
    FEATURE("Feature"),
    EXAMPLE("Example");

    private final String status;

    CucCommon(String status) {
        this.status = status;
    }

}
