package com.cucu.report.plugin.model;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;

public class CucuFeature {

    CucuTag featureTags;
    String featureFilePath;
    String featureName;
    String featureDescription;

    LinkedList<CucuScenario> scenarios;

    Instant startTime;
    Instant endTime;
    Duration duration;

}
