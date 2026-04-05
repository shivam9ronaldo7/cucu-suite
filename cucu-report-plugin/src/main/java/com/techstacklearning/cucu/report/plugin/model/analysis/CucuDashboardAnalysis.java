package com.techstacklearning.cucu.report.plugin.model.analysis;

public class CucuDashboardAnalysis {
    String suiteStartTime;
    String suiteEndTime;
    long totalPassedScenarios;
    long totalFailedScenarios;
    long totalScenarios;
    long totalDurationInSeconds;
    public record featureAnalysis(String featureName, long passedScenarios,
                                  long failedScenarios, long durationInSeconds, long totalScenarios) {};
    public record tagAnalysis(String tagName, long passedScenarios,
                              long failedScenarios, long durationInSeconds, long totalScenarios) {};
}
