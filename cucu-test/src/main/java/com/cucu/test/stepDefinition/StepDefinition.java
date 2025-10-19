package com.cucu.test.stepDefinition;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cucu.test.exceptions.UnImplementedCallException;
import com.cucu.test.exceptions.UnmatchedCaseException;
import io.cucumber.java.DocStringType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.cucu.test.placeholder.Placeholder;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import com.cucu.test.utility.DataWorld;
import com.cucu.test.utility.ReportUtil;
import com.cucu.test.utility.WebDriverUtil;

import java.util.*;

public class StepDefinition {

	@Autowired
	private WebDriverUtil webDriverUtil;

	@Autowired
	private ReportUtil reportUtil;

    @Autowired
    private DataWorld dataWorld;

    @Autowired
    private ObjectMapper objectMapper;

    @DocStringType(contentType = "json-placeholder-data")
    public List<Placeholder> jsonPlaceholderData(String docString) throws JsonProcessingException {
        return objectMapper.readValue(docString, new TypeReference<>() {});
    }

	@Given("open {string}")
	public void open(String string) throws UnImplementedCallException, UnmatchedCaseException {
		webDriverUtil.openUrl(string);
		reportUtil.reportLog("Web page opened");
		reportUtil.attachScreenshotToReport();
	}
	@Then("I verify the {string} is loaded")
	public void i_verify_the_is_loaded(String string) {
		webDriverUtil.visibilityOfElement(By.xpath(string));
		reportUtil.reportLog("Element searched");
		reportUtil.attachScreenshotToReport();
	}

    @Given("I want to write a step with precondition")
    public void i_want_to_write_a_step_with_precondition() {
        reportUtil.reportLog("I want to write a step with precondition");
    }

    @Given("some other precondition")
    public void some_other_precondition() {
        reportUtil.reportLog("some other precondition");
    }

    @When("I complete action")
    public void i_complete_action() {
        reportUtil.reportLog("I complete action");
    }

    @When("some other action")
    public void some_other_action() {
        reportUtil.reportLog("some other action");
    }

    @When("yet another action")
    public void yet_another_action() {
        reportUtil.reportLog("yet another action");
    }

    @Then("I validate the outcomes")
    public void i_validate_the_outcomes() {
        reportUtil.reportLog("I validate the outcomes");
    }

    @Then("check more outcomes")
    public void check_more_outcomes() {
        reportUtil.reportLog("check more outcomes");
    }

    @Given("I want to write a step with name1")
    public void i_want_to_write_a_step_with_name1() {
        reportUtil.reportLog("I want to write a step with name1");
    }

    @When("I check for the {int} in step")
    public void i_check_for_the_in_step(Integer int1) {
        reportUtil.reportLog("I check for the {int} in step");
    }

    @Then("I verify the success in step")
    public void i_verify_the_success_in_step() {
        reportUtil.reportLog("I verify the success in step");
    }

    @Given("user adds below placeholder")
    public void user_adds_below_placeholder(List<Placeholder> placeholderList)
            throws UnmatchedCaseException, JsonProcessingException {
        Map<String, String> placeholderMap = new LinkedHashMap<>();
        for (Placeholder placeholder: placeholderList) {
            String data = switch (placeholder.getType().toLowerCase()) {
                case "random", "timestamp" -> placeholder.generateValue();
                case "replace" -> dataWorld.replaceTextWithTestData(placeholder.generateValue());
                default -> throw new UnmatchedCaseException(String
                        .format("%s case for placeholder is not yet implemented", placeholder.getType()));
            };
            dataWorld.setTestData(placeholder.getKey(), data);
            placeholderMap.put(placeholder.getKey(), data);
        }
        reportUtil.attachJson(objectMapper.writeValueAsString(placeholderMap), "Placeholder Data");
    }

}
