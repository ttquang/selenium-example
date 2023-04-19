package com.quangtt.webtest.template.service;

import com.quangtt.webtest.core.model.Step;
import com.quangtt.webtest.template.model.*;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TemplateUtils {

    public static Pattern PARAMETER_PATTERN = Pattern.compile("\\$\\{([\\d\\w]+)}");
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
                for (Element step : template.getElements()) {
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
            boolean inProcess;
            do {
                inProcess = false;
                for (Template template : immutableTemplates) {
                    List<Element> baseSteps = List.copyOf(template.getElements());
                    for (Element step : baseSteps) {
                        if (step.isTemplate()) {
                            inProcess = true;
                            int stepIndex = template.getElements().indexOf(step);
                            Element templateStep = step;
                            String name = templateStep.getName();
                            String templateName = templateStep.getParameters().get("TEMPLATE_NAME");
                            List<Element> elements = processElements(name, templateName, templateStep.getParameters());
                            template.getElements().addAll(stepIndex + 1, elements);
                            template.getElements().remove(step);
                        }
                    }
                }
            } while (inProcess);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    public List<Element> processElements(String name, String templateName, Map<String,String> parameters) {
        List<Element> elements = new ArrayList<>();
        try {
            Template template = templates.get(templateName);
            elements = template.getElements().stream().map(Element::clone).collect(Collectors.toList());
            for (int i = 0; i < elements.size(); i++) {
                Element element = elements.get(i);
                element.setName(name + "." + (i + 1));
                element.processParameter(parameters);
//                if (element instanceof TemplateXpathAware) {
//                    ((TemplateXpathAware) element).processSelector(parameters);
//                }
//                if (element instanceof TemplateInputAware) {
//                    ((TemplateInputAware) element).processValue(parameters);
//                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return elements;
    }

    public static String processParameter(String s, Map<String,String> parameters) {
        String result = s;
        Set<String> placeHolders = new HashSet<>();
        Matcher m = PARAMETER_PATTERN.matcher(result);
        while (m.find()) {
            placeHolders.add(m.group(1));
            result = result.replace(m.group(), "PLACEHOLDER" + m.group(1));
            System.out.println(result);
            m = PARAMETER_PATTERN.matcher(result);
        }

        for (String index : placeHolders) {
            result = result.replace("PLACEHOLDER" + index, parameters.get(index));
        }

        return result;
    }

    public static Map<String,String> processParameter(Map<String,String> parameters, Map<String,String> inputParameters) {
        Set<String> placeHolders = new HashSet<>();
        for (String key: parameters.keySet()) {
            String rawValue = parameters.get(key);
            Matcher m = PARAMETER_PATTERN.matcher(rawValue);
            while (m.find()) {
                placeHolders.add(m.group(1));
                rawValue = rawValue.replace(m.group(), "PLACEHOLDER" + m.group(1));
                System.out.println(rawValue);
                m = PARAMETER_PATTERN.matcher(rawValue);
            }

            for (String index : placeHolders) {
                rawValue = rawValue.replace("PLACEHOLDER" + index, inputParameters.get(index));
            }
            parameters.put(key,rawValue);
        }

        return parameters;
    }

    public List<Step> process(String group, String templateName, Map<String,String> parameters) {
        List<Step> steps = new ArrayList<>();
        try {
            Template template = templates.get(templateName);
            for (Element element : template.getElements()) {
                try {
                    Step step = element.generateStep(group, parameters);
                    steps.add(step);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

//            steps = template.getElements().stream().map(element -> element.generateStep(group, parameters)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return steps;
    }

}
