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

        boolean inProcessingProperty = false;
        boolean inProcessingTestStep = false;

        for (int i = 2; i < testCaseSheet.getLastRowNum(); i++) {
            Row row = testCaseSheet.getRow(i);

            if ("Property".equals(VALUE_EXTRACTION.apply(row.getCell(0)))) {
                inProcessingProperty = true;
            } else if ("TestStep".equals(VALUE_EXTRACTION.apply(row.getCell(0)))) {
                inProcessingProperty = false;
                inProcessingTestStep = true;
            } else if ("END".equals(VALUE_EXTRACTION.apply(row.getCell(0)))) {
                break;
            } else {
                if (inProcessingProperty) {
//                    if (!VALUE_EXTRACTION.apply(row.getCell(1)).isBlank())
//                        template.putProperty(VALUE_EXTRACTION.apply(row.getCell(1)), VALUE_EXTRACTION.apply(row.getCell(3)));
                } else if (inProcessingTestStep) {
                    String type = VALUE_EXTRACTION.apply(row.getCell(1));
                    TestStep testStep = null;

                    switch (type) {
                        case "Input":
                            testStep = new InputElementTestStep(
                                    VALUE_EXTRACTION.apply(row.getCell(0)),
                                    VALUE_EXTRACTION.apply(row.getCell(2)),
                                    VALUE_EXTRACTION.apply(row.getCell(3)),
                                    0
                            );
                            break;
                        case "Click":
                            testStep = new ClickElementTestStep(
                                    VALUE_EXTRACTION.apply(row.getCell(0)),
                                    VALUE_EXTRACTION.apply(row.getCell(2)),
                                    0
                            );
                            break;
                        case "ClickAll":
                            testStep = new ClickAllElementTestStep(
                                    VALUE_EXTRACTION.apply(row.getCell(0)),
                                    VALUE_EXTRACTION.apply(row.getCell(2)),
                                    0
                            );
                            break;
                        case "InputSelect":
                            testStep = new InputSelectElementTestStep(
                                    VALUE_EXTRACTION.apply(row.getCell(0)),
                                    VALUE_EXTRACTION.apply(row.getCell(2)),
                                    VALUE_EXTRACTION.apply(row.getCell(3)),
                                    0
                            );
                            break;
                        case "TransferProperty":
                            testStep = new TransferPropertyTestStep(
                                    VALUE_EXTRACTION.apply(row.getCell(0)),
                                    VALUE_EXTRACTION.apply(row.getCell(3)),
                                    VALUE_EXTRACTION.apply(row.getCell(2)),
                                    0
                            );
                            break;
                        case "SetProperty":
                            testStep = new SetPropertyTestStep(
                                    VALUE_EXTRACTION.apply(row.getCell(0)),
                                    VALUE_EXTRACTION.apply(row.getCell(2)),
                                    VALUE_EXTRACTION.apply(row.getCell(3)),
                                    0
                            );
                            break;
                        case "SwitchFrame":
                            testStep = new SwitchFrameTestStep(
                                    VALUE_EXTRACTION.apply(row.getCell(0)),
                                    VALUE_EXTRACTION.apply(row.getCell(2)),
                                    0
                            );
                            break;
                        case "LoadPage":
                            testStep = new LoadPageTestStep(
                                    VALUE_EXTRACTION.apply(row.getCell(0)),
                                    VALUE_EXTRACTION.apply(row.getCell(3)),
                                    0
                            );
                            break;
                        case "Template":
                            testStep = new TemplateTestStep(
                                    VALUE_EXTRACTION.apply(row.getCell(0)),
                                    VALUE_EXTRACTION.apply(row.getCell(2)),
                                    VALUE_EXTRACTION.apply(row.getCell(3)),
                                    0
                            );
                            template.setNested(true);
                            break;
                    }

                    if (Objects.nonNull(testStep)) {
                        template.getTestSteps().add(testStep);
                    }
                }
            }
        }

        return template;
    }

}
