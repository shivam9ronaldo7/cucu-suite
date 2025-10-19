package com.cucu.test.utility;

import io.cucumber.spring.ScenarioScope;
import org.apache.commons.text.CaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@ScenarioScope
public class DataWorld {

    @Autowired
    ReportUtil reportUtil;

    private final Map<String, String> testData = new HashMap<>();

    public String getTestData(String key) {
        return Objects.requireNonNull(this.testData.get(key), String.format("Data with key %s is not present", key));
    }

    public void setTestData(String key, String value) {
        this.testData.put(key, value);
    }

    public String replaceTextWithTestData(String text) {
        for (Map.Entry<String, String> entry: testData.entrySet()) {
            text = text.replaceAll(String.format(":%s:", entry.getKey()), entry.getValue());
        }
        return text;
    }

    public String generateCamelCaseKey(String text) {
        return CaseUtils.toCamelCase(text, false, ' ');
    }

}
