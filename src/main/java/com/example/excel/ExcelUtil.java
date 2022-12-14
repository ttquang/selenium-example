package com.example.excel;

import com.example.test.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ExcelUtil {

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

    public List<Step> process(File file) throws IOException, InvalidFormatException {
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        List<Step> result = new ArrayList<>();

        for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
            Row row = sheet.getRow(i);
            String type = VALUE_EXTRACTION.apply(row.getCell(1));
            Step step = null;

            switch (type) {
                case "Input" :
                    step = new InputStep(VALUE_EXTRACTION.apply(row.getCell(0)),VALUE_EXTRACTION.apply(row.getCell(2)),VALUE_EXTRACTION.apply(row.getCell(3)));
                    break;
                case "Click" :
                    step = new ClickStep(VALUE_EXTRACTION.apply(row.getCell(0)),VALUE_EXTRACTION.apply(row.getCell(2)));
                    break;
                case "Delay" :
                    step = new DelayStep(VALUE_EXTRACTION.apply(row.getCell(0)));
                    break;
                case "InputSelect" :
                    step = new InputSelectStep(VALUE_EXTRACTION.apply(row.getCell(0)),VALUE_EXTRACTION.apply(row.getCell(2)),VALUE_EXTRACTION.apply(row.getCell(3)));
                    break;
                case "TransferProperty" :
                    step = new TransferPropertyStep(VALUE_EXTRACTION.apply(row.getCell(0)),VALUE_EXTRACTION.apply(row.getCell(3)),VALUE_EXTRACTION.apply(row.getCell(2)));
                    break;
                case "SetProperty" :
                    step = new SetPropertyStep(VALUE_EXTRACTION.apply(row.getCell(0)),VALUE_EXTRACTION.apply(row.getCell(2)),VALUE_EXTRACTION.apply(row.getCell(3)));
                    break;
                case "SwitchFrame" :
                    step = new SwitchFrameStep(VALUE_EXTRACTION.apply(row.getCell(0)),VALUE_EXTRACTION.apply(row.getCell(2)));
                    break;

            }

            result.add(step);
        }

        return result;
    }

}
