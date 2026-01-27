package com.cucu.report.plugin.model;

import lombok.Data;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class CucuScenario {

    private String featureFilePath;
    private String featureName;
    private String featureDescription;

    private List<String> scenarioTags;
    private CucScenarioType scenarioType = CucScenarioType.SCENARIO;
    private String scenarioName;
    private String scenarioDescription;
    private List<CucuStep> steps = new ArrayList<>();
    private List<String> exampleRowsHeaders;
    private List<String> exampleRowsValues;

    private Instant startTime;
    private Instant endTime;
    private Duration duration;

    private CucScenarioStatus status;

}
