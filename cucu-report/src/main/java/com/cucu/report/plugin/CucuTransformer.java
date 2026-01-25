package com.cucu.report.plugin;

import com.cucu.report.plugin.model.CucuNdJson;
import com.cucu.report.plugin.model.CucuScenario;
import com.cucu.report.plugin.model.CucuStep;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.maven.plugin.logging.Log;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

@Component
public class CucuTransformer {

    private final Log mavenLog;

    private final ObjectMapper objectMapper;

    private CucuNdJson cucuNdJson;

    private final LinkedList<CucuScenario> cucuScenarioList = new LinkedList<>();

    public CucuTransformer(Log mavenLog, ObjectMapper objectMapper) {
        this.mavenLog = mavenLog;
        this.objectMapper = objectMapper;
    }

    void transform(File[] ndjsonArr) throws IOException {
        String[] names = Arrays.stream(ndjsonArr).map(File::getName).toArray(String[]::new);
        mavenLog.info("Found " + ndjsonArr.length + " .ndjson file(s): " + Arrays.toString(names));
        for (File file : ndjsonArr) {
            if (!file.exists() || !file.isFile()) {
                mavenLog.warn("Skipping missing or invalid file: " + file.getAbsolutePath());
                continue;
            }
            cucuNdJson = transformNdJsonToCucuNdJson(file);
            for (Map<Object, Object> testCase : cucuNdJson.getTestCaseList()) {
                cucuScenarioList.add(transformTestCaseToCucuScenario(testCase));
            }
        }
    }

    CucuNdJson transformNdJsonToCucuNdJson(File ndJson) throws IOException {
        mavenLog.info("Transforming ndjson file: " + ndJson.getName() + " to cucu ndjson format.");
        BufferedReader reader = new BufferedReader(new FileReader(ndJson));
        String line;
        CucuNdJson cucuNdJson = new CucuNdJson();
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            Map<String, Map<Object, Object>> map = objectMapper.readValue(line, new TypeReference<>() {
            });
            Optional.ofNullable(map.get("attachment")).ifPresent(cucuNdJson::setAttachment);
            Optional.ofNullable(map.get("hook")).ifPresent(cucuNdJson::setHook);
            Optional.ofNullable(map.get("meta")).ifPresent(cucuNdJson::setMeta);
            Optional.ofNullable(map.get("pickle")).ifPresent(cucuNdJson::setPickle);
            Optional.ofNullable(map.get("testCase")).ifPresent(cucuNdJson::setTestCase);
            Optional.ofNullable(map.get("testCaseFinished")).ifPresent(cucuNdJson::setTestCaseFinished);
            Optional.ofNullable(map.get("testCaseStarted")).ifPresent(cucuNdJson::setTestCaseStarted);
            Optional.ofNullable(map.get("testRunFinished")).ifPresent(cucuNdJson::setTestRunFinished);
            Optional.ofNullable(map.get("testRunStarted")).ifPresent(cucuNdJson::setTestRunStarted);
            Optional.ofNullable(map.get("testStepFinished")).ifPresent(cucuNdJson::setTestStepFinished);
            Optional.ofNullable(map.get("testStepStarted")).ifPresent(cucuNdJson::setTestStepStarted);
            Optional.ofNullable(map.get("gherkinDocument")).ifPresent(cucuNdJson::setGherkinDocument);
        }
        mavenLog.info("Transformed ndjson file: " + ndJson.getName() + " to cucu ndjson format.");
        return cucuNdJson;
    }

    @SuppressWarnings("unchecked")
    CucuScenario transformTestCaseToCucuScenario(Map<Object, Object> testCase) {
        mavenLog.info("Transforming Cucu NdJson Test Case to Cucu Scenario.");

        CucuScenario cucuScenario = new CucuScenario();

        String testCaseId = (String) testCase.get("id");
        String testCasePickleId = (String) testCase.get("pickleId");
        LinkedList<Map<Object, Object>> testSteps = (LinkedList<Map<Object, Object>>) testCase.get("testSteps");

        // Parsing testCaseStarted and testCaseFinished to get scenario start and end time
        Map<Object, Object> testCaseStarted = cucuNdJson.getTestCaseStartedUsingTestCaseId(testCaseId);
        String testCaseStartedId = (String) testCaseStarted.get("id");
        Instant cucuScenarioStartTime = Instant.ofEpochSecond(
                ((Integer) ((Map<Object, Object>) testCaseStarted.get("timestamp")).get("seconds")).longValue(),
                ((Integer) ((Map<Object, Object>) testCaseStarted.get("timestamp")).get("nanos")).longValue()
        );
        cucuScenario.setStartTime(cucuScenarioStartTime);

        Map<Object, Object> testCaseFinished = cucuNdJson.getTestCaseFinishedUsingTestCaseStartedId(testCaseStartedId);
        Instant cucuScenarioEndTime = Instant.ofEpochSecond(
                ((Integer) ((Map<Object, Object>) testCaseFinished.get("timestamp")).get("seconds")).longValue(),
                ((Integer) ((Map<Object, Object>) testCaseFinished.get("timestamp")).get("nanos")).longValue()
        );
        cucuScenario.setEndTime(cucuScenarioEndTime);

        cucuScenario.setDuration(Duration.between(cucuScenarioStartTime, cucuScenarioEndTime));

        // Parsing pickle to get scenario tags, feature file path
        Map<Object, Object> pickle = cucuNdJson.getPickleUsingPickleId(testCasePickleId);
        LinkedList<String> tags = pickle.get("tags") != null ?
                ((LinkedList<Map<Object, Object>>) pickle.get("tags")).stream()
                        .map(tagMap -> tagMap.get("name").toString())
                        .collect(LinkedList::new, LinkedList::add, LinkedList::addAll)
                : null;
        cucuScenario.setScenarioTags(tags);
        cucuScenario.setFeatureFilePath(pickle.get("uri").toString());

        // Parsing gherkinDocument to get feature name and description
        Map<Object, Object> feature = (Map<Object, Object>) cucuNdJson
                .getGherkinDocumentUsingUri(pickle.get("uri").toString()).get("feature");
        String featureName = feature.get("name").toString();
        String featureDescription = feature.get("description").toString();
        cucuScenario.setFeatureName(featureName);
        cucuScenario.setFeatureDescription(featureDescription);

        for (Map<Object, Object> testStep : testSteps) {
            CucuStep cucuStep = new CucuStep();
            String id = (String) testStep.get("id");
            Instant cucuStepStartTime = Instant.ofEpochSecond(
                    ((Integer) ((Map<Object, Object>) cucuNdJson.getTestStepStartedUsingTestStepId(id)
                            .get("timestamp")).get("seconds")).longValue(),
                    ((Integer) ((Map<Object, Object>) cucuNdJson.getTestStepStartedUsingTestStepId(id)
                            .get("timestamp")).get("nanos")).longValue()
            );
            Instant cucuStepEndTime = Instant.ofEpochSecond(
                    ((Integer) ((Map<Object, Object>) cucuNdJson.getTestStepFinishedUsingTestStepId(id)
                            .get("timestamp")).get("seconds")).longValue(),
                    ((Integer) ((Map<Object, Object>) cucuNdJson.getTestStepFinishedUsingTestStepId(id)
                            .get("timestamp")).get("nanos")).longValue()
            );
            cucuStep.setStartTime(cucuStepStartTime);
            cucuStep.setEndTime(cucuStepEndTime);
            cucuStep.setDuration(Duration.between(cucuStepStartTime, cucuStepEndTime));
            if (testStep.containsKey("hookId")) {
                String hookId = (String) testStep.get("hookId");
                Map<Object, Object> hook = cucuNdJson.getHookUsingId(hookId);
                String hookType = hook.get("type").toString();
                cucuStep.setStepType(hookType);
            } else if (testStep.containsKey("pickleStepId")) {
                Map<Object, Object> attachment = cucuNdJson.getAttachmentUsingTestStepId(id);
                cucuStep.setAttachmentContentEncoding(attachment.get("contentEncoding").toString());
                cucuStep.setAttachmentMediaType(attachment.get("mediaType").toString());
                cucuStep.setAttachmentBody(attachment.get("body").toString());

                String pickleStepId = (String) testStep.get("pickleStepId");

                Map<Object, Object> pickleStep = ((LinkedList<Map<Object, Object>>) pickle.get("steps")).stream()
                        .filter(step -> step.get("id").toString().equals(pickleStepId))
                        .findFirst().orElse(null);
                String[] astNodeIds = (String[]) pickleStep.get("astNodeIds");
                if(astNodeIds.length>1) {
                    cucuScenario.setScenarioType("Scenario Outline");
                }
                feature.get("children")
            } else {
                mavenLog.warn("Unknown test step type in test case id: " + testCaseId);
            }
        }


        mavenLog.info("Transformed Cucu Scenario: \n" + cucuScenario);
        return cucuScenario;
    }

}
