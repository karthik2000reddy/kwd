package keys;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtlis {
    static XSSFWorkbook workbook;
    static XSSFSheet sheet;

    public static void setexcelfile(String path, String sheetName) throws IOException {
        FileInputStream f = new FileInputStream(path);
        workbook = new XSSFWorkbook(f);  // Pass FileInputStream to XSSFWorkbook to load the file
        sheet = workbook.getSheet(sheetName);
        f.close();  // Close the input stream after loading the workbook
    }

    public static String getcellData(int row, int column) {
        XSSFCell cell = sheet.getRow(row).getCell(column);
        String celldata = cell.getStringCellValue();
        return celldata;  // Return celldata directly
    }
}
