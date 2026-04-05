package com.techstacklearning.cucu.report.plugin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techstacklearning.cucu.report.plugin.model.transformer.*;
import com.techstacklearning.cucu.report.plugin.model.enums.CucuScenarioStatus;
import com.techstacklearning.cucu.report.plugin.model.enums.CucuScenarioType;
import com.techstacklearning.cucu.report.plugin.model.enums.CucuStepStatus;
import com.techstacklearning.cucu.report.plugin.model.enums.CucuStepType;
import org.apache.maven.plugin.logging.Log;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class CucuTransformer {

    private final Log mavenLog;

    private final ObjectMapper objectMapper;

    private CucuNdJson cucuNdJson;

    private final ArrayList<CucuScenario> cucuScenarioList = new ArrayList<>();

    public CucuTransformer(Log mavenLog, ObjectMapper objectMapper) {
        this.mavenLog = mavenLog;
        this.objectMapper = objectMapper;
    }

    ArrayList<CucuScenario> transform(File[] ndjsonArr) throws IOException {
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
        mavenLog.info("Transformation complete. Total Cucu Scenarios transformed: "
                + cucuScenarioList.size());
//        mavenLog.info("Cucu Scenarios: \n"
//                + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(cucuScenarioList));
        return cucuScenarioList;
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

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        CucuScenario cucuScenario = new CucuScenario();

        String testCaseId = (String) testCase.get("id");
        mavenLog.debug("Processing started for test case id: " + testCaseId);
        String testCasePickleId = (String) testCase.get("pickleId");
        mavenLog.debug("Pickle id for test case: " + testCasePickleId);
        ArrayList<Map<Object, Object>> testSteps = (ArrayList<Map<Object, Object>>) testCase.get("testSteps");
        Map<Object, Object> pickle = cucuNdJson.getPickleUsingPickleId(testCasePickleId);
        List<String> pickleAstNodeIds = (List<String>) pickle.get("astNodeIds");
        String gherkinDocumentFeatureChildrenScenarioId = pickleAstNodeIds.get(0);

        mavenLog.debug("Feature uri of test case: " + pickle.get("uri").toString());
        Map<Object, Object> gherkinDocumentFeature = (Map<Object, Object>) cucuNdJson
                .getGherkinDocumentUsingUri(pickle.get("uri").toString()).get("feature");
        List<Map<Object, Object>> gherkinDocumentFeatureChildren
                = (List<Map<Object, Object>>) gherkinDocumentFeature.get("children");

        List<Map<Object, Object>> gherkinDocumentFeatureChildrenScenario = new ArrayList<>();

        // Finding background from gherkinDocument's feature children then creating new map
        if (gherkinDocumentFeatureChildren.get(0).containsKey("background")) {
            Map<Object, Object> gherkinDocumentFeatureChildrenBackground
                    = (Map<Object, Object>) gherkinDocumentFeatureChildren.get(0).get("background");
            String gherkinDocumentFeatureChildrenBackgroundId = gherkinDocumentFeatureChildrenBackground.get("id").toString();
            mavenLog.debug("Background id for test case in gherkin document: "
                    + gherkinDocumentFeatureChildrenBackgroundId);
            gherkinDocumentFeatureChildrenScenario.add(gherkinDocumentFeatureChildrenBackground);
        }

        // Finding scenario from gherkinDocument's feature children then creating new map
        for (Map<Object, Object> featureChild : gherkinDocumentFeatureChildren) {
            if (featureChild.containsKey("scenario")
                    && ((Map<Object, Object>) featureChild.get("scenario")).get("id").toString()
                    .equals(gherkinDocumentFeatureChildrenScenarioId)) {
                Map<Object, Object> scenario = (Map<Object, Object>) featureChild.get("scenario");
                mavenLog.debug("Scenario id in gherkin document for test case: "
                        + gherkinDocumentFeatureChildrenScenarioId);
                gherkinDocumentFeatureChildrenScenario.add(scenario);

                // Setting scenario type, name, description from gherkinDocument's scenario
                cucuScenario.setScenarioType(switch (scenario.get("keyword").toString()) {
                    case "Scenario Outline" -> CucuScenarioType.SCENARIO_OUTLINE.getType();
                    case "Scenario" -> CucuScenarioType.SCENARIO.getType();
                    default -> throw new RuntimeException("Unknown scenario keyword: " + scenario.get("keyword"));
                });
                cucuScenario.setScenarioName(scenario.get("name").toString());
                cucuScenario.setScenarioDescription(scenario.get("description").toString());

                if (cucuScenario.getScenarioType().equals(CucuScenarioType.SCENARIO_OUTLINE.getType())) {
                    // Setting example values for scenario outline
                    mavenLog.debug(gherkinDocumentFeatureChildrenScenarioId + " is a Scenario Outline.");
                    Map<Object, Object> example = ((List<Map<Object, Object>>) scenario.get("examples")).get(0);
                    List<String> headers = new ArrayList<>();
                    for (Map<Object, Object> cell : (List<Map<Object, Object>>) ((Map<Object, Object>) example
                            .get("tableHeader")).get("cells")) {
                        headers.add(cell.get("value").toString());
                    }
                    cucuScenario.setExampleRowsHeaders(headers);
                    List<Map<Object, Object>> tableBodyRows
                            = (List<Map<Object, Object>>) example.get("tableBody");
                    String exampleId = pickleAstNodeIds.get(1);
                    mavenLog.debug("Example id for scenario outline: " + exampleId);
                    Map<Object, Object> tableBodyRow = null;
                    for (Map<Object, Object> row : tableBodyRows) {
                        String rowId = row.get("id").toString();
                        mavenLog.debug("Example Row ID: " + rowId + ", Pickle Example ID: " + exampleId);
                        if (rowId.equals(exampleId)) {
                            tableBodyRow = row;
                            break;
                        }
                    }
                    List<String> rows = new ArrayList<>();
                    if (Objects.isNull(tableBodyRow)) {
                        throw new RuntimeException("Example row not found for scenario: "
                                + cucuScenario.getScenarioName());
                    } else {
                        for (Map<Object, Object> cell : (List<Map<Object, Object>>) tableBodyRow.get("cells")) {
                            rows.add(cell.get("value").toString());
                        }
                    }
                    cucuScenario.setExampleRowsValues(rows);
                }
                mavenLog.debug(cucuScenario.getScenarioType()
                        + " data pulled from gherkin document with id: "
                        + gherkinDocumentFeatureChildrenScenarioId);
                break;
            }
        }

        // Setting feature file path, name, description
        cucuScenario.setFeatureFilePath(pickle.get("uri").toString());
        cucuScenario.setFeatureName(gherkinDocumentFeature.get("name").toString());
        cucuScenario.setFeatureDescription(gherkinDocumentFeature.get("description").toString());

        // Setting scenario tags if present
        if (pickle.get("tags") != null) {
            cucuScenario.setScenarioTags(((ArrayList<Map<Object, Object>>) pickle.get("tags")).stream()
                    .map(tagMap -> tagMap.get("name").toString())
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll));
        }

        // Setting start time, end time and duration of scenario
        Map<Object, Object> testCaseStarted = cucuNdJson.getTestCaseStartedUsingTestCaseId(testCaseId);
        String testCaseStartedId = (String) testCaseStarted.get("id");
        mavenLog.debug("Test case started id: " + testCaseStartedId);
        Map<Object, Object> testCaseFinished = cucuNdJson.getTestCaseFinishedUsingTestCaseStartedId(testCaseStartedId);
        Instant startInstant = Instant.ofEpochSecond(
                ((Integer) ((Map<Object, Object>) testCaseStarted.get("timestamp")).get("seconds")).longValue(),
                ((Integer) ((Map<Object, Object>) testCaseStarted.get("timestamp")).get("nanos")).longValue()
        );
        Instant endInstant = Instant.ofEpochSecond(
                ((Integer) ((Map<Object, Object>) testCaseFinished.get("timestamp")).get("seconds")).longValue(),
                ((Integer) ((Map<Object, Object>) testCaseFinished.get("timestamp")).get("nanos")).longValue()
        );
        cucuScenario.setStartTime(startInstant);
        cucuScenario.setEndTime(endInstant);
        cucuScenario.setDurationInSeconds(Duration.between(startInstant, endInstant).getSeconds());

        // Processing each test step
        for (Map<Object, Object> testStep : testSteps) {
            CucuStep cucuStep = new CucuStep();
            String id = (String) testStep.get("id");
            mavenLog.debug("Processing for test step id: " + id);

            Map<Object, Object> testStepFinished = cucuNdJson.getTestStepFinishedUsingTestStepId(id);
            Map<Object, Object> testStepResult = (Map<Object, Object>) testStepFinished.get("testStepResult");
            String testStepResultStatus = testStepResult.get("status").toString();
            Map<Object, Object> attachment = cucuNdJson.getAttachmentUsingTestStepId(id);

            // Setting start time, end time, duration, status, message, exception and attachment of step
            Instant stepStartInstant = Instant.ofEpochSecond(
                    ((Integer) ((Map<Object, Object>) cucuNdJson.getTestStepStartedUsingTestStepId(id)
                            .get("timestamp")).get("seconds")).longValue(),
                    ((Integer) ((Map<Object, Object>) cucuNdJson.getTestStepStartedUsingTestStepId(id)
                            .get("timestamp")).get("nanos")).longValue()
            );
            Instant stepEndInstant = Instant.ofEpochSecond(
                    ((Integer) ((Map<Object, Object>) testStepFinished.get("timestamp")).get("seconds")).longValue(),
                    ((Integer) ((Map<Object, Object>) testStepFinished.get("timestamp")).get("nanos")).longValue()
            );
            cucuStep.setStartTime(stepStartInstant.atZone(ZoneId.systemDefault()).format(formatter));
            cucuStep.setEndTime(stepEndInstant.atZone(ZoneId.systemDefault()).format(formatter));
            cucuStep.setDurationInSeconds(String.format("%s seconds",
                    Duration.between(stepStartInstant, stepEndInstant).getSeconds()));
            cucuStep.setStatus(switch (testStepResultStatus) {
                case "PASSED" -> CucuStepStatus.PASS.getStatus();
                case "FAILED" -> CucuStepStatus.FAIL.getStatus();
                default -> CucuStepStatus.SKIP.getStatus();
            });
            cucuScenario.setStatus(testStepResultStatus.equals("PASSED") ?
                    CucuScenarioStatus.PASS.getStatus() : CucuScenarioStatus.FAIL.getStatus());
            if (!Objects.isNull(testStepResult.get("message"))) {
                cucuStep.setMessage(testStepResult.get("message").toString());
            }
            if (!Objects.isNull(testStepResult.get("exception"))) {
                cucuStep.setException(testStepResult.get("exception").toString());
            }
            if (!Objects.isNull(attachment)) {
                cucuStep.setAttachmentContentEncoding(attachment.get("contentEncoding").toString());
                cucuStep.setAttachmentMediaType(attachment.get("mediaType").toString());
                cucuStep.setAttachmentBody(attachment.get("body").toString());
            }
            if (testStep.containsKey("hookId")) {
                String hookId = (String) testStep.get("hookId");
                mavenLog.debug("Test step is hook with id: " + hookId);
                Map<Object, Object> hook = cucuNdJson.getHookUsingId(hookId);
                String hookType = hook.get("type").toString();
                cucuStep.setStepType(switch (hook.get("type").toString()) {
                    case "BEFORE_TEST_CASE" -> CucuStepType.BEFORE_SCENARIO_HOOK;
                    case "AFTER_TEST_CASE" -> CucuStepType.AFTER_SCENARIO_HOOK;
                    case "BEFORE_TEST_STEP" -> CucuStepType.BEFORE_STEP_HOOK;
                    case "AFTER_TEST_STEP" -> CucuStepType.AFTER_STEP_HOOK;
                    default -> throw new RuntimeException("Unknown hook type: " + hookType);
                });
            } else if (testStep.containsKey("pickleStepId")) {
                String pickleStepId = (String) testStep.get("pickleStepId");
                mavenLog.debug("Step is pickle step with id: " + pickleStepId);
                Map<Object, Object> pickleStep = ((ArrayList<Map<Object, Object>>) pickle.get("steps")).stream()
                        .filter(step -> step.get("id").toString().equals(pickleStepId))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Pickle step not found for id: " + pickleStepId));
                String pickleStepAstNodeId = ((ArrayList<String>) pickleStep.get("astNodeIds")).get(0);
                mavenLog.debug("Pickle step ast node id: " + pickleStepAstNodeId);
                gherkinFor:
                for (Map<Object, Object> gherkinDocumentFeatureChildrenScenarioChildren :
                        gherkinDocumentFeatureChildrenScenario) {
                    List<Map<Object, Object>> steps
                            = (List<Map<Object, Object>>) gherkinDocumentFeatureChildrenScenarioChildren.get("steps");
                    for (Map<Object, Object> step : steps) {
                        if (step.get("id").toString().equals(pickleStepAstNodeId)) {
                            mavenLog.debug("Pickle step ast node id and" +
                                    "gherkin document scenario step matched");
                            String stepKeyword = step.get("keyword").toString();
                            cucuStep.setStepText(step.get("text").toString());
                            cucuStep.setStepType(switch (stepKeyword.trim()) {
                                case "Given" -> CucuStepType.GIVEN;
                                case "When" -> CucuStepType.WHEN;
                                case "Then" -> CucuStepType.THEN;
                                case "And" -> CucuStepType.AND;
                                case "But" -> CucuStepType.BUT;
                                default -> throw new RuntimeException("Unknown step keyword: " + stepKeyword);
                            });
                            if (!Objects.isNull(step.get("docString"))) {
                                Map<Object, Object> docString
                                        = (Map<Object, Object>) step.get("docString");
                                cucuStep.setDocStringContent(docString.get("content").toString());
                                cucuStep.setDocStringDelimiter(docString.get("delimiter").toString());
                                if (!Objects.isNull(docString.get("mediaType"))) {
                                    cucuStep.setDocStringMediaType(docString.get("mediaType").toString());
                                }
                            }
                            if (!Objects.isNull(step.get("dataTable"))) {
                                List<Map<Object, Object>> dataTableRows
                                        = (List<Map<Object, Object>>) ((Map<Object, Object>) step.get("dataTable"))
                                        .get("rows");
                                List<List<String>> rows = new ArrayList<>();
                                for (Map<Object, Object> row : dataTableRows) {
                                    List<String> rowCells = new ArrayList<>();
                                    List<Map<Object, Object>> dataTableCells
                                            = (List<Map<Object, Object>>) row.get("cells");
                                    for (Map<Object, Object> cell : dataTableCells) {
                                        rowCells.add(cell.get("value").toString());
                                    }
                                    rows.add(rowCells);
                                }
                                cucuStep.setDataTableRows(rows);
                            }
                            cucuScenario.getSteps().add(cucuStep);
                            break gherkinFor;
                        }
                    }
                }
            } else {
                throw new RuntimeException("Unknown test step type in test step id: " + id);
            }
            mavenLog.debug("Processing done for test step id: " + id);
        }

        mavenLog.debug("Processing done for test case id: " + testCaseId);
        mavenLog.info("Transformed Cucu Scenario: " + cucuScenario.getScenarioName());
        return cucuScenario;
    }
}