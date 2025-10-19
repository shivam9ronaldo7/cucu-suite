package com.cucu.test.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Scenario;
import io.cucumber.spring.ScenarioScope;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@ScenarioScope
@Log4j2
public class ReportUtil {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebDriverUtil webDriverUtil;

    @Setter
    private Scenario scenario;

    public void info(String message) {
        log.info("[{}] - {}", scenario.getName(), message);
    }

    public void error(String message) {
        log.error("[{}] - {}", scenario.getName(), message);
    }

    public void reportLog(String message) {
        info(message);
        scenario.log(message);
    }

    public void reportBigLog(String message) {
        info(message);
        scenario.log(String.format("<textarea style='width:50%%; height:400px;'>%s</textarea>", message));
    }

    public void attachText(String data, String name) {
        info(data);
        scenario.attach(data, "text/plain", name);
    }

    public void attachJson(String data, String name) throws JsonProcessingException {
        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
        data = objectMapper.writer(prettyPrinter).writeValueAsString(objectMapper.readTree(data));
        info(data);
        scenario.attach(data, "text/plain", name);
    }

    public void attachScreenshotToReport() {
        scenario.attach(((TakesScreenshot) webDriverUtil.getWebDriver()).getScreenshotAs(OutputType.BYTES),
                "image/png", String.format("URL: %s", webDriverUtil.getCurrentUrl()));
    }

    public void attachTable(String header, List<String> list, String name) {
        info(list.toString());
        StringBuilder tableHeadRowHtml = new StringBuilder();
        for (String column : header.split(" -> ")) {
            tableHeadRowHtml.append(String.format("<th style='border: 1px solid black; border-collapse: collapse;'>%s</td>\n", column));
        }
        StringBuilder tableBodyRowHtml = new StringBuilder();
        for (String row : list) {
            StringBuilder columnHtml = new StringBuilder();
            for (String column : row.split(" -> ")) {
                columnHtml.append(String.format("<td style='border: 1px solid black; border-collapse: collapse;'>%s</td>\n", column));
            }
            tableBodyRowHtml.append(String.format("<tr style='border: 1px solid black; border-collapse: collapse;'>%s</td>\n", columnHtml));
        }
        String tableHtml = String.format(
                "<div>" +
                        "<table style='border: 1px solid black; border-collapse: collapse;'>" +
                        "<thead>" + "%s" + "</thead>" +
                        "<tbody>" + "%s" + "</tbody>" +
                        "</table>" +
                        "</div>",
                tableHeadRowHtml.toString(), tableBodyRowHtml.toString()
        );
        if (Objects.isNull(System.getenv("JENKINS_HOME"))) {
            scenario.attach(tableHtml, "text/x.cucumber.log+plain", name);
        } else {
            scenario.attach(tableHtml, "text/html", name);
        }
    }

}
