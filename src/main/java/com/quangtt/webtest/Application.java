package com.quangtt.webtest;

import com.quangtt.webtest.api.ExcelImport;
import com.quangtt.webtest.api.WebDriverFactory;
import com.quangtt.webtest.core.exception.StepRuntimeException;
import com.quangtt.webtest.core.model.TestRunner;
import com.quangtt.webtest.core.model.TestSuite;
import com.quangtt.webtest.execution.DefaultExecutionEnvironment;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.edge.EdgeDriverService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Application {
    public static void main(String[] args) {
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "D:/webdriver/chromedriver.exe");
        System.setProperty(EdgeDriverService.EDGE_DRIVER_EXE_PROPERTY, "D:/webdriver/msedgedriver.exe");
        WebDriver webDriver = null;
        try {
            ExcelImport excelImport = new ExcelImport();
            File file = new File("TestSuite3.1.xlsx");
            TestSuite testSuite = excelImport.processTestSuit(file);

            webDriver = WebDriverFactory.get();
            Map<String, String> properties = new HashMap<>();
            properties.put("url","http://localhost/rlos/Login.do");
            properties.put("sec_admin_acc","RLOS1");
            properties.put("sec_admin_pass","1");
            properties.put("sec_admin_maker","SECURITY ADMIN MAKER");
            properties.put("sec_admin_checker","SECURITY ADMIN CHECKER");
            properties.put("username","AUTO.TEST.BO");
            properties.put("role","100");
            properties.put("branch","90236");
            properties.put("prefer_lang","English");
            properties.put("module","MPCHF");
            TestRunner environment = new DefaultExecutionEnvironment(webDriver, properties);
            testSuite.runWith(environment);
        } catch (StepRuntimeException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            webDriver.quit();
        }

//        try {
//            TemplateUtils utils = new TemplateUtils();
//            utils.loadTemplate();
//            utils.compileTemplate();
//            utils.printTemplate();
//
//            List<Step> testSteps = utils.process(
//                    "group.1",
//                    "LoginAs",
//                    List.of("param1","param2","param3")
//            );
//
//            testSteps.forEach(step -> System.out.println(step.toString()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}
