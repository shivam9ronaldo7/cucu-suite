package com.techstacklearning.cucu.report.plugin.model.transformer;

import com.techstacklearning.cucu.report.plugin.model.enums.CucuScenarioStatus;
import com.techstacklearning.cucu.report.plugin.model.enums.CucuScenarioType;
import lombok.Data;

import java.time.Instant;
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
    private String scenarioType = CucuScenarioType.SCENARIO.getType();
    private String scenarioName;
    private String scenarioDescription;
    private List<CucuStep> steps = new ArrayList<>();
    private List<String> exampleRowsHeaders;
    private List<String> exampleRowsValues;

    private Instant startTime;
    private Instant endTime;
    private long durationInSeconds;

    private String status = CucuScenarioStatus.FAIL.getStatus();

}
