package com.quangtt.webtest.api;

import com.quangtt.webtest.core.model.*;
import com.quangtt.webtest.template.service.TemplateUtils;
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

public class ExcelImport {

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

    public TestSuite processTestSuit(File file) throws IOException, InvalidFormatException {
        Workbook workbook = new XSSFWorkbook(file);
        Iterator<Sheet> iterator = workbook.iterator();
        TestSuite testSuite = constructTestSuite(iterator.next());

        while (iterator.hasNext()) {
            TestCase testCase = constructTestCase(iterator.next());
            testSuite.addTestCase(testCase);
        }

        return testSuite;
    }

    public TestCase constructTestCase(Sheet testCaseSheet) {
        TemplateUtils utils = new TemplateUtils();
        utils.loadTemplate();
        utils.compileTemplate();
        utils.printTemplate();

        TestCase testCase = new TestCase(testCaseSheet.getSheetName());

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
                    if (!VALUE_EXTRACTION.apply(row.getCell(1)).isBlank())
                        testCase.putProperty(VALUE_EXTRACTION.apply(row.getCell(1)), VALUE_EXTRACTION.apply(row.getCell(3)));
                } else if (inProcessingTestStep) {
                    String type = VALUE_EXTRACTION.apply(row.getCell(1));
                    Step testStep = null;

                    switch (type) {
                        case "Input":
                            testStep = new TextInputStep(
                                    VALUE_EXTRACTION.apply(row.getCell(0)),
                                    VALUE_EXTRACTION.apply(row.getCell(2)),
                                    VALUE_EXTRACTION.apply(row.getCell(3))
                            );
                            break;
                        case "Click":
                            testStep = new ClickStep(
                                    VALUE_EXTRACTION.apply(row.getCell(0)),
                                    VALUE_EXTRACTION.apply(row.getCell(2))
                            );
                            break;
                        case "ClickAll":
                            testStep = new ClickAllElementTestStep(
                                    VALUE_EXTRACTION.apply(row.getCell(0)),
                                    VALUE_EXTRACTION.apply(row.getCell(2))
                            );
                            break;
                        case "InputSelect":
                            testStep = new SelectStep(
                                    VALUE_EXTRACTION.apply(row.getCell(0)),
                                    VALUE_EXTRACTION.apply(row.getCell(2)),
                                    VALUE_EXTRACTION.apply(row.getCell(3))
                            );
                            break;
                        case "TransferProperty":
                            testStep = new PropertyTransferDOMValueStep(
                                    VALUE_EXTRACTION.apply(row.getCell(0)),
                                    VALUE_EXTRACTION.apply(row.getCell(3)),
                                    VALUE_EXTRACTION.apply(row.getCell(2))
                            );
                            break;
                        case "SwitchFrame":
                            testStep = new SwitchToFrameByXpathStep(
                                    VALUE_EXTRACTION.apply(row.getCell(0)),
                                    VALUE_EXTRACTION.apply(row.getCell(2))
                            );
                            break;
                        case "LoadPage":
                            testStep = new NavigationToUrlStep(
                                    VALUE_EXTRACTION.apply(row.getCell(0)),
                                    VALUE_EXTRACTION.apply(row.getCell(3))
                            );
                            break;
                        default:
                            List<String> parameters = Arrays.asList(VALUE_EXTRACTION.apply(row.getCell(2)).split(","));
                            List<Step> testSteps = utils.process(
                                    VALUE_EXTRACTION.apply(row.getCell(0)),
                                    VALUE_EXTRACTION.apply(row.getCell(1)),
                                    parameters
                            );
                            testSteps.forEach(testCase::addStep);
                            break;
                    }

                    if (Objects.nonNull(testStep)) {
                        testCase.addStep(testStep);
                    }

                }
            }

        }

        return testCase;
    }

    public TestSuite constructTestSuite(Sheet testSuiteSheet) {
        Row nameRow = testSuiteSheet.getRow(0);
        TestSuite testSuite = new TestSuite(VALUE_EXTRACTION.apply(nameRow.getCell(1)));

        boolean inProcessingProperty = false;
        for (int i = 1; i < testSuiteSheet.getLastRowNum(); i++) {
            Row row = testSuiteSheet.getRow(i);
            if (inProcessingProperty) {
                if (!VALUE_EXTRACTION.apply(row.getCell(1)).isBlank())
                    testSuite.putProperty(VALUE_EXTRACTION.apply(row.getCell(1)), VALUE_EXTRACTION.apply(row.getCell(2)));
            } else {
                if ("Property".equals(VALUE_EXTRACTION.apply(row.getCell(0)))) {
                    inProcessingProperty = true;
                } else if ("END".equals(VALUE_EXTRACTION.apply(row.getCell(0)))) {
                    break;
                }
            }
        }

        return testSuite;
    }
}
