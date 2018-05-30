package app;

import java.awt.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.SAXException;

import app.IbanGenerator;
import models.Trabajadorbbdd;
import app.XMLCuentas;

public class ControladorIban {
	static ArrayList <Trabajadorbbdd> trabajadores = new ArrayList<Trabajadorbbdd>();
	static int XMLCreadoCuentas = 0;
	
	public static void comprobarIban(String cc, String pais, int fila, Trabajadorbbdd t) {
		
		IbanGenerator generator = new IbanGenerator();
		String iban = "";
		Boolean correcto = true;
		if(!generator.comprobarC1(cc.substring(8, 9),"00"+ cc.substring(0,8))) {
			String  c1 = String.valueOf(generator.generarCCC("00"+cc.substring(0,8)));
			cc = cc.substring(0,8)+c1+cc.substring(9);

			correcto = false;
			
		}
		if(!generator.comprobarC1(cc.substring(9, 10), cc.substring(10))) {
			String  c2 = String.valueOf(generator.generarCCC(cc.substring(10)));
			cc = cc.substring(0,9)+c2+cc.substring(10);

			correcto = false;

			 
		}
		iban = generator.generarIban(cc, pais);
		t.setIban(iban);
		if(!correcto) {
			modificarExcel(fila, cc, 14);
			generarErrorCuentas(String.valueOf(fila), t.getNombre(), t.getApellido1(), t.getApellido2(), t.getCategoria(), t.getEmpresa(), t.getCodigoCuenta(), iban);
		}
		
		
		modificarExcel(fila, iban, 16);
		
		
	}
	
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
	
	public static void generarErrorCuentas(String id,String nombre, String ap1, String ap2, String cat, String empresa, String oldCuenta, String ibanNuevo) {
		XMLCuentas xml = new XMLCuentas();
		if (XMLCreadoCuentas==0) {
			try {
				xml.generar();
			} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
				e.printStackTrace();
			}
			XMLCreadoCuentas++;
		}
			try {
				xml.add(id, nombre, ap1, ap2, cat, empresa, oldCuenta, ibanNuevo);
			} catch (SAXException | IOException | ParserConfigurationException | TransformerException e) {
				e.printStackTrace();
			}
		
			
	}
	
	

}
