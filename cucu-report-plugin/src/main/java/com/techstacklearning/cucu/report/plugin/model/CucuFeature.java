package com.techstacklearning.cucu.report.plugin.model;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class CucuFeature {

    CucuTag featureTags;
    String featureFilePath;
    String featureName;
    String featureDescription;

    ArrayList<CucuScenario> scenarios;

    Instant startTime;
    Instant endTime;
    Duration duration;

}
