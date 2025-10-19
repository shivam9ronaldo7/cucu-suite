package com.cucu.test.utility;

import com.cucu.test.exceptions.UnImplementedCallException;
import com.cucu.test.exceptions.UnmatchedCaseException;
import io.cucumber.spring.ScenarioScope;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.time.Duration;
import java.util.Objects;

@Component
@ScenarioScope
public class WebDriverUtil {

    @Getter
    private WebDriver webDriver;

    private WebDriverWait webDriverWait;

    @Value("${pollingTimeInSec}")
    private int pollingTimeInSec;

    @Value("${durationTimeInSec}")
    private long durationTimeInSec;

    @Value("${browser}")
    private String browser;

    @Value("${webdriverBinariesPath}")
    private String webdriverBinariesPath;

    @Value("#{new Boolean('${headless}')}")
    private boolean headless;

    private void startBrowser() throws UnmatchedCaseException, UnImplementedCallException {
        if(Objects.isNull(webDriver)) {
            startBrowser(browser);
            initializeWebDriverWait();
        }
    }

    private void startBrowser(String browserType) throws UnmatchedCaseException, UnImplementedCallException {
        if (browserType.equalsIgnoreCase("chrome")) {
            startChromeDriver();
        } else {
            throw new UnmatchedCaseException(String.format("%s is invalid/unimplemented browser case.", browserType));
        }
        webDriver.manage().window().maximize();
    }

    private void startChromeDriver() throws UnImplementedCallException {
        String osName = System.getProperty("os.name");
        if(osName.toLowerCase().contains("win")) {
            System.setProperty("webdriver.chrome.driver", Path.of(webdriverBinariesPath, "chromedriver.exe").toString());
        } else if(osName.toLowerCase().contains("nix") ||
                osName.toLowerCase().contains("nux") ||
                osName.toLowerCase().contains("aix")) {
            System.setProperty("webdriver.chrome.driver", Path.of(webdriverBinariesPath, "chromedriver").toString());
        } else {
            throw new UnImplementedCallException(String.format("Support for %s OS not yet implemented", osName));
        }
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(headless);
        this.webDriver = new ChromeDriver(options);
    }

    private void initializeWebDriverWait() {
        webDriverWait = (WebDriverWait) new WebDriverWait(webDriver, Duration.ofSeconds(durationTimeInSec))
                .pollingEvery(Duration.ofSeconds(pollingTimeInSec));
    }

    public void closeWebDriver() {
        webDriver.quit();
        webDriver = null;
    }

    public void openUrl(String url) throws UnImplementedCallException, UnmatchedCaseException {
        if(Objects.isNull(webDriver)) {
            startBrowser();
        }
        webDriver.get(url);
    }

    public WebElement visibilityOfElement(By by) {
        return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public String getCurrentUrl() {
        return webDriver.getCurrentUrl();
    }

}
