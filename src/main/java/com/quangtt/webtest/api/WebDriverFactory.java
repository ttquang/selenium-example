package com.quangtt.webtest.api;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.service.DriverService;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class WebDriverFactory {
    public static WebDriver get() {
        DriverService.Builder<ChromeDriverService, ChromeDriverService.Builder> serviceBuilder = new ChromeDriverService.Builder();
        ChromeDriverService chromeDriverService = serviceBuilder.build();
        try {
            chromeDriverService.sendOutputTo(new FileOutputStream("chromedriver_log.txt", true));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ChromeOptions options = new ChromeOptions();
        options.setLogLevel(ChromeDriverLogLevel.INFO);

        WebDriver webDriver = new ChromeDriver(chromeDriverService, options);
        webDriver.manage().timeouts().pageLoadTimeout(Duration.of(30, ChronoUnit.MINUTES));
        return webDriver;
    }
}
