package com.quangtt.webtest;

import com.quangtt.webtest.api.ExcelImport;
import com.quangtt.webtest.api.WebDriverFactory;
import com.quangtt.webtest.core.exception.StepRuntimeException;
import com.quangtt.webtest.core.model.ExecutionEnvironment;
import com.quangtt.webtest.core.model.TestSuite;
import com.quangtt.webtest.execution.DefaultExecutionEnvironment;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Application {
    public static void main(String[] args) {
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "D:\\testTool\\webdriver\\chromedriver.exe");
        WebDriver webDriver = null;
        try {
            ExcelImport excelImport = new ExcelImport();
            File file = new File("D:\\testTool\\testsuite\\TestSuite3.xlsx");
            TestSuite testSuite = excelImport.processTestSuit(file);

            webDriver = WebDriverFactory.get();
            Map<String, String> properties = new HashMap<>();
            properties.put("sec_admin_acc","RLOS1");
            properties.put("sec_admin_pass","1");
            properties.put("sec_admin_maker","SECURITY ADMIN MAKER");
            properties.put("sec_admin_checker","SECURITY ADMIN CHECKER");
            properties.put("username","AUTO.TEST.BO");
            properties.put("role","100");
            properties.put("branch","CN 1 - TP HCM - PGD DAKAO");
            properties.put("prefer_lang","English");
            properties.put("module","MPCHF");
            ExecutionEnvironment environment = new DefaultExecutionEnvironment(webDriver, properties);
            testSuite.runWith(environment);
        } catch (StepRuntimeException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            webDriver.quit();
        }

    }
}
