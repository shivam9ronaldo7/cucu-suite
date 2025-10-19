package com.cucu.test.utility;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Utilities {

    public String pdfReader(String filePath) throws InvalidPasswordException, IOException {
        String pdfFileInText = null;
        try (PDDocument document = PDDocument
                .load(new File(filePath))) {
            document.getClass();
            if (!document.isEncrypted()) {
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                PDFTextStripper tStripper = new PDFTextStripper();
                pdfFileInText = tStripper.getText(document);
            }
        }
        return pdfFileInText;
    }
}
