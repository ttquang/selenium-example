package com.quangtt.webtest.template.service;

import com.quangtt.webtest.template.model.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class TemplateImport {

    public Function<Cell, String> VALUE_EXTRACTION = cell -> {
        if (Objects.isNull(cell)) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case FORMULA:
                return cell.getStringCellValue();
            default:
                return "";
        }
    };

    public Map<String, Template> importFromExcel(File file) throws IOException, InvalidFormatException {
        Map<String, Template> result = new HashMap<>();
        Workbook workbook = new XSSFWorkbook(file);
        Iterator<Sheet> iterator = workbook.iterator();

        while (iterator.hasNext()) {
            Template template = constructTemplate(iterator.next());
            result.put(template.getName(), template);
        }

        return result;
    }

    public Template constructTemplate(Sheet testCaseSheet) {
        Template template = new Template(testCaseSheet.getSheetName());

        int elementIndex = 1;
        Element element = null;

        for (int i = 4; i < testCaseSheet.getLastRowNum(); i++) {
            Row row = testCaseSheet.getRow(i);

            if ("END".equals(VALUE_EXTRACTION.apply(row.getCell(0)))) {
                template.getElements().add(element);
                break;
            }

            String type = VALUE_EXTRACTION.apply(row.getCell(1));

            if (type.isBlank()) {
                element.setProperty(VALUE_EXTRACTION.apply(row.getCell(2)), VALUE_EXTRACTION.apply(row.getCell(3)));
            } else {
                if (Objects.nonNull(element)) {
                    template.getElements().add(element);
                    elementIndex++;
                }

                switch (type) {
                    case "WebElement":
                        element = new Element(String.valueOf(elementIndex), type);
                        element.setProperty(VALUE_EXTRACTION.apply(row.getCell(2)), VALUE_EXTRACTION.apply(row.getCell(3)));
                        break;
                    case "Navigation":
                        element = new Element(String.valueOf(elementIndex), type);
                        element.setProperty(VALUE_EXTRACTION.apply(row.getCell(2)), VALUE_EXTRACTION.apply(row.getCell(3)));
                        break;
                    case "Property":
                        element = new Element(String.valueOf(elementIndex), type);
                        element.setProperty(VALUE_EXTRACTION.apply(row.getCell(2)), VALUE_EXTRACTION.apply(row.getCell(3)));
                        break;
                    default:
                        element = new Element(String.valueOf(elementIndex), "Template");
                        element.setProperty("TEMPLATE_NAME", type);
                        element.setProperty(VALUE_EXTRACTION.apply(row.getCell(2)), VALUE_EXTRACTION.apply(row.getCell(3)));
                        break;
                }
            }
        }

        return template;
    }

}
