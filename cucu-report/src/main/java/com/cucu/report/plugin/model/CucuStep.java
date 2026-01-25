package com.cucu.report.plugin.model;

import lombok.Data;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@Data
public class CucuStep {

    private String stepType;
    private String stepText;

    private Instant startTime;
    private Instant endTime;
    private Duration duration;

    private String attachmentBody;
    private String attachmentContentEncoding;
    private String attachmentMediaType;

    private Status status;

    public String toString() {
        return "CucuStep:\n" +
                "Step Type: " + stepType + "\n" +
                "Step Text: " + stepText + "\n" +
                "Start Time: " + startTime + "\n" +
                "End Time: " + endTime + "\n" +
                "Duration: " + duration + "\n" +
                "Status: " + status + "\n";
    }

}
