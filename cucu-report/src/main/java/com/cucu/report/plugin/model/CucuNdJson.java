package com.cucu.report.plugin.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Map;

@Getter
public class CucuNdJson {

    @Setter
    private Map<Object, Object> meta;

    @Setter
    private Map<Object, Object> testRunStarted;

    @Setter
    private Map<Object, Object> testRunFinished;

    private final ArrayList<Map<Object, Object>> testCaseList = new ArrayList<>();
    private final ArrayList<Map<Object, Object>> hookList = new ArrayList<>();
    private final ArrayList<Map<Object, Object>> testCaseStartedList = new ArrayList<>();
    private final ArrayList<Map<Object, Object>> testCaseFinishedList = new ArrayList<>();
    private final ArrayList<Map<Object, Object>> pickleList = new ArrayList<>();
    private final ArrayList<Map<Object, Object>> testStepStartedList = new ArrayList<>();
    private final ArrayList<Map<Object, Object>> testStepFinishedList = new ArrayList<>();
    private final ArrayList<Map<Object, Object>> attachmentList = new ArrayList<>();
    private final ArrayList<Map<Object, Object>> gherkinDocumentList = new ArrayList<>();

    public void setAttachment(Map<Object, Object> attachment) {
        this.attachmentList.add(attachment);
    }

    public void setTestStepFinished(Map<Object, Object> testStepFinished) {
        this.testStepFinishedList.add(testStepFinished);
    }

    public void setTestStepStarted(Map<Object, Object> testStepStarted) {
        this.testStepStartedList.add(testStepStarted);
    }

    public void setPickle(Map<Object, Object> pickle) {
        this.pickleList.add(pickle);
    }

    public void setTestCaseFinished(Map<Object, Object> testCaseFinished) {
        this.testCaseFinishedList.add(testCaseFinished);
    }

    public void setTestCaseStarted(Map<Object, Object> testCaseStarted) {
        this.testCaseStartedList.add(testCaseStarted);
    }

    public void setHook(Map<Object, Object> hook) {
        this.hookList.add(hook);
    }

    public void setTestCase(Map<Object, Object> testCase) {
        this.testCaseList.add(testCase);
    }

    public void setGherkinDocument(Map<Object, Object> gherkinDocument) {
        this.gherkinDocumentList.add(gherkinDocument);
    }

    public Map<Object, Object> getTestCaseStartedUsingTestCaseId(String testCaseId) {
        return testCaseStartedList.stream()
                .filter(testCaseStartedEle
                        -> String.valueOf(testCaseStartedEle.get("testCaseId")).equals(testCaseId))
                .findFirst().orElse(null);
    }

    public Map<Object, Object> getTestCaseFinishedUsingTestCaseStartedId(String testCaseStartedId) {
        return testCaseFinishedList.stream()
                .filter(testCaseFinishedEle
                        -> String.valueOf(testCaseFinishedEle.get("testCaseStartedId")).equals(testCaseStartedId))
                .findFirst().orElse(null);
    }

    public Map<Object, Object> getPickleUsingPickleId(String testCasePickleId) {
        return pickleList.stream()
                .filter(pickleEle
                        -> String.valueOf(pickleEle.get("id")).equals(testCasePickleId))
                .findFirst().orElse(null);
    }

    public Map<Object, Object> getGherkinDocumentUsingUri(String uri) {
        return gherkinDocumentList.stream()
                .filter(gherkinDocumentEle
                        -> String.valueOf(gherkinDocumentEle.get("uri")).equals(uri))
                .findFirst().orElse(null);
    }

    public Map<Object, Object> getTestStepStartedUsingTestStepId(String testStepId) {
        return testStepStartedList.stream()
                .filter(testStepStartedEle
                        -> String.valueOf(testStepStartedEle.get("testStepId")).equals(testStepId))
                .findFirst().orElse(null);
    }

    public Map<Object, Object> getTestStepFinishedUsingTestStepId(String testStepId) {
        return testStepFinishedList.stream()
                .filter(testStepFinishedEle
                        -> String.valueOf(testStepFinishedEle.get("testStepId")).equals(testStepId))
                .findFirst().orElse(null);
    }

    public Map<Object, Object> getHookUsingId(String hookId) {
        return hookList.stream()
                .filter(hookEle
                        -> String.valueOf(hookEle.get("id")).equals(hookId))
                .findFirst().orElse(null);
    }

    public Map<Object, Object> getAttachmentUsingTestStepId(String testStepId) {
        return attachmentList.stream()
                .filter(attachmentEle
                        -> String.valueOf(attachmentEle.get("testStepId")).equals(testStepId))
                .findFirst().orElse(null);
    }

}
