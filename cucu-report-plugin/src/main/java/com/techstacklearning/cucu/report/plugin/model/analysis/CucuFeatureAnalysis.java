package com.techstacklearning.cucu.report.plugin.model.analysis;

import com.techstacklearning.cucu.report.plugin.model.enums.CucuScenarioStatus;

import java.time.Instant;

public class CucuFeatureAnalysis {
    String filePath;
    String featureName;
    String featureDescription;
    Instant startTime;
    Instant endTime;
    long totalScenarios;
    long passedScenarios;
    long failedScenarios;
    long durationInSeconds;
    public record scenarioAnalysis(String scenarioName, CucuScenarioStatus cucuScenarioStatus, long duration) {};
}
