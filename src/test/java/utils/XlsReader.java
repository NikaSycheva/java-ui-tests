package utils;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class XlsReader {
    private final File xlsxFile;
    private XSSFSheet sheet;//страница
    private XSSFWorkbook book;//книга
    private String sheetName;//на какую страницу хотим попадать вначале

    public XlsReader(File xlsxFile) throws IOException {
        this.xlsxFile = xlsxFile;
        try {
            FileInputStream fs = new FileInputStream(xlsxFile);
            book = new XSSFWorkbook(fs);
            sheet = book.getSheetAt(0);
        } catch (IOException e) {
            throw new IOException("Wrong file");
        }
    }

    public XlsReader(File xlsxFile, String sheetName) throws IOException {
        this.xlsxFile = xlsxFile;
        try {
            FileInputStream fs = new FileInputStream(xlsxFile);
            book = new XSSFWorkbook(fs);
            sheet = book.getSheet(sheetName);
        } catch (IOException e) {
            throw new IOException("Wrong file");
        }
    }


    //метод преобразования ячейки в строчку, тк в каждой ячейке разный тип данных
    private String cellToString(XSSFCell cell) throws Exception {
        CellType type = cell.getCellType();
        Object result = switch (type) {
            case NUMERIC -> cell.getNumericCellValue();
            case STRING -> cell.getStringCellValue();
            case FORMULA -> cell.getCellFormula();
            case BLANK -> "";
            default -> throw new Exception("Ошибка чтения ячейки");
        };
        return result.toString();
    }

    //получаем кол-во столбцов(получаем первую строчку и кол-во ячеек в ней)
    private int xlsxCountColumn() {
        return sheet.getRow(0).getLastCellNum();
    }

    //получаем кол-во строк без нулевой
    private int xlsxCountRow() {
        return sheet.getLastRowNum() + 1;
    }

    private String[][] deletNulls(String[][] data){
        return Arrays.stream(data)
                .filter(row -> Arrays.stream(row).anyMatch(Objects::nonNull))
                .toArray(String[][] :: new);

    }

    //читать все значения из таблицы
    public String[][] getSheetData() throws Exception {
        int numberOfColumn = xlsxCountColumn();
        int numberOfRows = xlsxCountRow();
        String[][] data = new String[numberOfRows - 1][numberOfColumn];
        for (int i = 1; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumn; j++) {
                XSSFRow row = sheet.getRow(i);
                XSSFCell cell = row.getCell(j);
                if(cell == null){
                    break;
                }
                String value = cellToString(cell);
                data[i - 1][j] = value;
            }
        }
        data = deletNulls(data);
        return data;
    }

    //читать все значения из таблицы из указанного листа
    public String[][] getSheetData(String sheetName) throws Exception {
        int numberOfColumn = xlsxCountColumn();
        int numberOfRows = xlsxCountRow();
        String[][] data = new String[numberOfRows - 1][numberOfColumn];
        for (int i = 1; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumn; j++) {
                XSSFRow row = book.getSheet(sheetName).getRow(i);
                XSSFCell cell = row.getCell(j);
                String value = cellToString(cell);
                data[i - 1][j] = value;
            }
        }
        data = deletNulls(data);
        return data;
    }
}