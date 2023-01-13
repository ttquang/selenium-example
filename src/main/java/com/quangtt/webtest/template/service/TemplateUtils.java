package com.quangtt.webtest.template.service;

import com.quangtt.webtest.template.model.*;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateUtils {

    static Pattern PARAMETER_PATTERN = Pattern.compile("\\$\\{([\\d]+)}");
    private Map<String, Template> templates;

    public void loadTemplate() {
        try {
            TemplateImport templateImport = new TemplateImport();
            File file = new File("Template.xlsx");
            templates = templateImport.importFromExcel(file);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    public void printTemplate() {
        try {
            List<Template> immutableTemplates = List.copyOf(templates.values());
            for (Template template : immutableTemplates) {
                System.out.println("Template : " + template.getName() + "=============================================");
                for (TestStep step : template.getTestSteps()) {
                    System.out.println(step.toString());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    public void compileTemplate() {
        try {
            List<Template> immutableTemplates = List.copyOf(templates.values());
            boolean inProcess = false;
            do {
                inProcess = false;
                for (Template template : immutableTemplates) {
                    List<TestStep> baseSteps = List.copyOf(template.getTestSteps());
                    for (TestStep step : baseSteps) {
                        if (step instanceof TemplateTestStep) {
                            inProcess = true;
                            int stepIndex = template.getTestSteps().indexOf(step);
                            TemplateTestStep templateStep = (TemplateTestStep) step;
                            String templateName = templateStep.getSelector();
                            List<String> parameters = Arrays.asList(templateStep.getValue().split(","));
                            List<TestStep> steps = process(templateName, parameters);
                            template.getTestSteps().addAll(stepIndex + 1, steps);
                            template.getTestSteps().remove(step);
                        }
                    }
                }
            } while (inProcess);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    public List<TestStep> process(String templateName, List<String> parameters) {
        List<TestStep> steps = new ArrayList<>();
        try {
            Template template = templates.get(templateName);
            for (TestStep step : template.getTestSteps()) {
                steps.add((TestStep) step.clone());
            }
            processParameter(steps, parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return steps;
    }

    private String processParameter(String s, List<String> parameters) {
        String result = s;
        Set<Integer> placeHolders = new HashSet<>();
        Matcher m = PARAMETER_PATTERN.matcher(result);
        while (m.find()) {
            placeHolders.add(Integer.valueOf(m.group(1)) - 1);
            result = result.replace(m.group(), "PLACEHOLDER" + (Integer.valueOf(m.group(1)) - 1));
            System.out.println(result);
            m = PARAMETER_PATTERN.matcher(result);
        }

        for (Integer index : placeHolders) {
            result = result.replace("PLACEHOLDER" + index, parameters.get(index));
        }

        return result;
    }

    private List<TestStep> processParameter(List<TestStep> steps, List<String> parameters) {
        for (TestStep step : steps) {
            if (step instanceof ElementAware) {
                ElementAware elementAware = (ElementAware) step;
                String selector = elementAware.getSelector();
                elementAware.setSelector(processParameter(selector, parameters));
            }

            if (step instanceof InputAware) {
                InputAware inputAware = (InputAware) step;
                String value = inputAware.getValue();
                inputAware.setValue(processParameter(value, parameters));
            }
        }
        return steps;
    }

}
