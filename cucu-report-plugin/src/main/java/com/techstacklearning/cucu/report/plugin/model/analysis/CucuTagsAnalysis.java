package com.techstacklearning.cucu.report.plugin.model.analysis;

import java.time.Instant;

public class CucuTagsAnalysis {
    String tagName;
    Instant startTime;
    Instant endTime;
    long durationInSeconds;
    long passedScenarios;
    long failedScenarios;
    long totalScenarios;
}
