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
        int elementIndex = 1;

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
                    Element element = null;

                    switch (type) {
                        case "Input":
                            element = new TextInputStep(
                                    String.valueOf(elementIndex),
                                    VALUE_EXTRACTION.apply(row.getCell(2)),
                                    VALUE_EXTRACTION.apply(row.getCell(3))
                            );
                            break;
                        case "Click":
                            element = new ClickStep(
                                    String.valueOf(elementIndex),
                                    VALUE_EXTRACTION.apply(row.getCell(2))
                            );
                            break;
                        case "InputSelect":
                            element = new SelectInputStep(
                                    String.valueOf(elementIndex),
                                    VALUE_EXTRACTION.apply(row.getCell(2)),
                                    VALUE_EXTRACTION.apply(row.getCell(3))
                            );
                            break;
//                        case "TransferProperty":
//                            element = new PropertyTransferStep(
//                                    String.valueOf(elementIndex),
//                                    VALUE_EXTRACTION.apply(row.getCell(3)),
//                                    VALUE_EXTRACTION.apply(row.getCell(2))
//                            );
//                            break;
//                        case "SetProperty":
//                            element = new SetPropertyTestStep(
//                                    String.valueOf(elementIndex),
//                                    VALUE_EXTRACTION.apply(row.getCell(2)),
//                                    VALUE_EXTRACTION.apply(row.getCell(3))
//                            );
//                            break;
//                        case "SwitchFrame":
//                            element = new SwitchFrameStep(
//                                    String.valueOf(elementIndex),
//                                    VALUE_EXTRACTION.apply(row.getCell(2))
//                            );
//                            break;
//                        case "LoadPage":
//                            element = new LoadPageTestStep(
//                                    String.valueOf(elementIndex),
//                                    VALUE_EXTRACTION.apply(row.getCell(3))
//                            );
//                            break;
                        case "Template":
                            element = new TemplateStep(
                                    String.valueOf(elementIndex),
                                    VALUE_EXTRACTION.apply(row.getCell(2)),
                                    VALUE_EXTRACTION.apply(row.getCell(3))
                            );
                            break;
                    }

                    if (Objects.nonNull(element)) {
                        template.getElements().add(element);
                        elementIndex++;
                    }
                }
            }
        }

        return template;
    }

}
