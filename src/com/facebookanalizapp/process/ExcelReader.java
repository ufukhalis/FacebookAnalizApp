
package com.facebookanalizapp.process;


import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Ufuk
 */
public class ExcelReader {
    private String path;


    public ExcelReader() {
    }
    
    public List<String> read(String path){
        List<String> data = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(new File(path));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()){
                        case Cell.CELL_TYPE_NUMERIC:
                            //System.out.print(cell.getNumericCellValue() + "\t");
                            //data.add(String.valueOf(cell.getNumericCellValue()));
                            break;
			case Cell.CELL_TYPE_STRING:
                            //System.out.print(cell.getStringCellValue() + "\t");
                            data.add(cell.getStringCellValue());
                            break;
                    }
                }
            }
            file.close();	
            return data;
        } catch (Exception e) {
            System.out.println("Error : Excel File cannot read by cause : " + e);
        } 
        
        return null;
    }
}
