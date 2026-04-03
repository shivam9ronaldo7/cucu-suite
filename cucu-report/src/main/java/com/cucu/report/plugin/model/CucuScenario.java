package com.cucu.report.plugin.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class CucuScenario {

    private UUID id = UUID.randomUUID();

    private String featureFilePath;
    private String featureName;
    private String featureDescription;

    private List<String> scenarioTags;
    private String scenarioType = CucScenarioType.SCENARIO.getType();
    private String scenarioName;
    private String scenarioDescription;
    private List<CucuStep> steps = new ArrayList<>();
    private List<String> exampleRowsHeaders;
    private List<String> exampleRowsValues;

    private String startTime;
    private String endTime;
    private String durationInSeconds;

    private String status = CucScenarioStatus.FAIL.getStatus();

}
