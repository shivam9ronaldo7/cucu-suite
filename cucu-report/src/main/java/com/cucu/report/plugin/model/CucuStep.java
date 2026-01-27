package com.cucu.report.plugin.model;

import lombok.Data;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Data
public class CucuStep {

    private CucStepType stepType;
    private String stepText;

    private Instant startTime;
    private Instant endTime;
    private Duration duration;

    private String message;
    private String exception;

    private String docStringMediaType;
    private String docStringContent;
    private String docStringDelimiter;

    private List<List<String>> dataTableRows;

    private String attachmentBody;
    private String attachmentContentEncoding;
    private String attachmentMediaType;

    private CucStepStatus status;

}
