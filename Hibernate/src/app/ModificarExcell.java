package app;

import java.awt.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ModificarExcell {
	
	public static void  modificarExcel(int fila, String DNI, int columna) {
		String filename = "resources/SistemasInformacionII.xlsx";
		List sheetData = new List();
		 FileInputStream fis = null;
		 XSSFWorkbook workbook;
		 
		 try {
			 fis = new FileInputStream(filename);
			  workbook = new XSSFWorkbook(fis);
			 XSSFSheet sheet = workbook.getSheetAt(0);
			 XSSFRow col = sheet.getRow(fila);
			 Cell cell = col.getCell(columna);
			 if(cell==null) {
				 cell = col.createCell(columna);
			 }
			 cell.setCellValue(DNI);
			 FileOutputStream fileOut = new FileOutputStream("resources/SistemasInformacionII.xlsx");
			 workbook.write(fileOut);
			 fileOut.close();
			 
			 
		 } catch (IOException e) {
			 	
	            e.printStackTrace();
	
	        }
		 
	}

}
