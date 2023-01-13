package com.quangtt.webtest.api;

import com.quangtt.webtest.core.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateUtil {
    static Pattern TEMPLATE_PATTERN = Pattern.compile("(.+)\\((.*)\\)");
    Map<String, List<TestStep>> templateMap = new HashMap<>();

    public void constructTemplate() {
        TestStep step1 = new ClickElementTestStep(
                "",
                "//select[@name='$1']/following-sibling::div/a",
                0);
        TestStep step2 = new ClickElementTestStep(
                "",
                "//select[@name='$1']/following-sibling::div//ul/li[count(//select[@name='$1']/option[@value='$2']/preceding::option) + 1]",
                100);
        templateMap.put("template1", List.of(step1, step2));
    }

    public void constructTemplateLogin() {
        TestStep step1 = new InputElementTestStep("", "//*[@id = 'userName']", "$1", 0);
        TestStep step2 = new InputElementTestStep("", "//*[@id = 'password']", "$2", 0);
        TestStep step3 = new ClickElementTestStep("", "//*[@id = 'btn_signin']", 0);
        templateMap.put("templateLogin", List.of(step1, step2, step3));
    }

    public List<TestStep> get(String s) {
        List<TestStep> result = new ArrayList<>();

        try {
            Matcher m = TEMPLATE_PATTERN.matcher(s);
            if (m.find()) {
                String template = m.group(1);
                List<TestStep> templateTestSteps = templateMap.get(template);
                String[] values = m.group(2).split(",");

                for (TestStep templateTestStep : templateTestSteps) {
                    TestStep testStep = (TestStep) templateTestStep.clone();
                    if (testStep instanceof ElementAware) {
                        ElementAware elementAware = (ElementAware) testStep;
                        for (int i = 0; i < values.length; i++) {
                            elementAware.setSelector(elementAware.getSelector().replace("$" + (i + 1), values[i]));
                        }
                    }

                    if (testStep instanceof InputAware) {
                        InputAware inputAware = (InputAware) testStep;
                        for (int i = 0; i < values.length; i++) {
                            inputAware.setValue(inputAware.getValue().replace("$" + (i + 1), values[i]));
                        }
                    }

                    result.add(testStep);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
