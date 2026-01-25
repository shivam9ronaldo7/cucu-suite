package com.cucu.report.plugin.model;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;

@Data
public class CucuScenario {

    private String featureFilePath;
    private String featureName;
    private String featureDescription;

    private LinkedList<String> scenarioTags;
    private String scenarioType = "Scenario";
    private String scenarioName;
    private String scenarioDescription;
    private LinkedList<CucuStep> steps;

    private Instant startTime;
    private Instant endTime;
    private Duration duration;

    private Status status;

    public String toString() {
        return "CucuScenario:\n" +
                "Feature File Path: " + featureFilePath + "\n" +
                "Feature Name: " + featureName + "\n" +
                "Feature Description: " + featureDescription + "\n" +
                "Scenario Tags: " + scenarioTags + "\n" +
                "Scenario Type: " + scenarioType + "\n" +
                "Scenario Name: " + scenarioName + "\n" +
                "Scenario Description: " + scenarioDescription + "\n" +
                "Steps: " + steps + "\n" +
                "Start Time: " + startTime + "\n" +
                "End Time: " + endTime + "\n" +
                "Duration: " + duration.getSeconds() + "\n" +
                "Status: " + status + "\n";
    }

}
