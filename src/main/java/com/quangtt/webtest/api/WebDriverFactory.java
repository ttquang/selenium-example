package com.quangtt.webtest.api;

import com.quangtt.webtest.execution.WebDriverProxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
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
        options.addArguments("--remote-allow-origins=*");
//        options.addArguments("--headless");

        WebDriver webDriver = new ChromeDriver(chromeDriverService, options);
        webDriver.manage().timeouts().pageLoadTimeout(Duration.of(30, ChronoUnit.MINUTES));
        return new WebDriverProxy(webDriver);
    }
    public static WebDriver getEdge() {
        DriverService.Builder<EdgeDriverService, EdgeDriverService.Builder> serviceBuilder = new EdgeDriverService.Builder();
        EdgeDriverService edgeDriverService = serviceBuilder.build();
        try {
            edgeDriverService.sendOutputTo(new FileOutputStream("edgedriver_log.txt", true));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        EdgeOptions options = new EdgeOptions();

        WebDriver webDriver = new EdgeDriver(edgeDriverService, options);
        webDriver.manage().timeouts().pageLoadTimeout(Duration.of(30, ChronoUnit.MINUTES));
        return new WebDriverProxy(webDriver);
    }

}
