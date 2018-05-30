package app;

import java.awt.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import models.Categorias;



public class leerExcel {
	
	XSSFWorkbook workbook;
	public leerExcel() throws IOException {
		String filename = "resources/SistemasInformacionII.xlsx";
		List sheetData = new List();
		 FileInputStream fis = null;
		 
		 fis = new FileInputStream(filename);
		 workbook = new XSSFWorkbook(fis);
	}
	
	
	
/*	public List<Trabajadorbbdd> getTrabajadores(){
		
		XSSFSheet sheet = workbook.getSheetAt(0);
		 int nfilas = sheet.getLastRowNum();
		 Iterator<Row> rows = sheet.rowIterator();
		 rows.next();
		 int cont = 0;
		 while (cont<nfilas) {
			 cont++;
			  Row row = (Row) rows.next();
			  Iterator<Cell> cells = row.cellIterator();
			  Cell cell;
			  List datos = new List();
			 
			  
			  Trabajadorbbdd t = new Trabajadorbbdd();
			  
			  t.setFila(row.getRowNum());

			  Cell c = row.getCell(0);
			  
			  if (c== null) {
				  t.setNifnie("");
			  }else {
				  
				  t.setNifnie(row.getCell(0).getStringCellValue());

			  }
			  c = row.getCell(1);
			  if (c== null) {
				  t.setApellido1("");
			  }else {
				  t.setApellido1(row.getCell(1).getStringCellValue());

			  }
			  c = row.getCell(2);
			  if (c== null) {
				  t.setApellido2("");
			  }else {
				  t.setApellido2(row.getCell(2).getStringCellValue());

			  }
			  c = row.getCell(3);
			  if (c== null) {
				  t.setNombre("");
			  }else {
				  t.setNombre(row.getCell(3).getStringCellValue());

			  }
			  c = row.getCell(5);
			  if (c== null) {
				  
			  }
				  t.setCategorias("");
			  }else {
				  t.setCategorias(row.getCell(5).getStringCellValue());

			  }
			  c = row.getCell(6);
			  if (c== null) {
				  t.setEmpresa("");
			  }else {
				  t.setEmpresa(row.getCell(6).getStringCellValue());

			  }
			  
			  c = row.getCell(8);
			  if (c== null) {
				  
			  }else {
				  t.setFechaAlta(row.getCell(8).getDateCellValue());

			  }
			  c = row.getCell(13);
			  if (c== null) {
				  t.setProrrateo(false);
			  }else {
				  if(row.getCell(13).getStringCellValue().equals("SI"))
					  t.setProrrateo(true);
				  else
					  t.setProrrateo(false);

			  }
			  c = row.getCell(14);
			  if (c== null) {
				  t.setCodigoCuenta("");
			  }else {
				  t.setCodigoCuenta(row.getCell(14).getStringCellValue());
			  }
			  c = row.getCell(15);
			  if (c== null) {
				  t.setPais("");
			  }else {
				  t.setPais(row.getCell(15).getStringCellValue());
			  }
			  
			  c = row.getCell(7);
			  
			  if (c== null) {
				 
			  }else {
				  if(!estaIncluida(row.getCell(7).getStringCellValue())) {
					  Empresa e = new Empresa();
					  e.setCif(row.getCell(7).getStringCellValue());
					  e.setNombre(row.getCell(6).getStringCellValue());
					  empresa.add(e);
				  }
				 
			  }
			  
			
			  //cells.next();//email

			  trabajadores.add(t); 
	}
		
	}*/

	public ArrayList <Categorias> getCategorias(){
		ArrayList <Categorias> categorias = new ArrayList();
		XSSFSheet sheet1 = workbook.getSheetAt(1);
		 int nfila1 = sheet1.getLastRowNum();
		 Iterator<Row> rows1 = sheet1.rowIterator();
		 rows1.next();
		 int cont = 0;
		 while (cont<14) {
			 cont++;
			  Row row = (Row) rows1.next();
			  Iterator<Cell> cells = row.cellIterator();
			  Cell cell;
			  List datos = new List();
			  Categorias cat  = new Categorias();
			 
			  Cell c = row.getCell(0);
			  
			 if(c==null) {
				 cat.setNombreCategoria("");
			 }else {
				 cat.setNombreCategoria(c.getStringCellValue());
			 }
			 c = row.getCell(1);
			 if(c==null) {
				 cat.setSalarioBaseCategoria(0.0);
			 }else {
				 cat.setSalarioBaseCategoria(c.getNumericCellValue());
			 }
			 c = row.getCell(2);
			 if(c==null) {
				 cat.setComplementoCategoria(0.0);
			 }else {
				 cat.setComplementoCategoria(c.getNumericCellValue());
			 }
			 c = row.getCell(3);
//			 if(c==null) {
//				 cat.setCodigoCotizacion(0);
//			 }else {
//				 cat.setCodigoCotizacion((int)c.getNumericCellValue());
//			 }
			  
			 categorias.add(cat);
		//	 System.out.println(cat.getNombreCategoria()+" "+cat.getSalarioBaseCategoria()+" "+cat.getComplementoCategoria()+" "+cat.getCodigoCotizacion());
		 }
		 
		 return categorias;
		
	}
	/*public static void leerArchivo() {
		String filename = "resources/SistemasInformacionII.xlsx";
		List sheetData = new List();
		 FileInputStream fis = null;
		 try {
			 fis = new FileInputStream(filename);
			 XSSFWorkbook workbook = new XSSFWorkbook(fis);
			 XSSFSheet sheet = workbook.getSheetAt(0);
			 int nfilas = sheet.getLastRowNum();
			 Iterator<Row> rows = sheet.rowIterator();
			 rows.next();
			 int cont = 0;
			 while (cont<nfilas) {
				 cont++;
				  Row row = (Row) rows.next();
				  Iterator<Cell> cells = row.cellIterator();
				  Cell cell;
				  List datos = new List();
				 
				  
				  Trabajador t = new Trabajador();
				  
				  t.setFila(row.getRowNum());

				  Cell c = row.getCell(0);
				  
				  if (c== null) {
					  t.setDNI("");
				  }else {
					  
					  t.setDNI(row.getCell(0).getStringCellValue());
					  t.getDNI();

				  }
				  c = row.getCell(1);
				  if (c== null) {
					  t.setApellido1("");
				  }else {
					  t.setApellido1(row.getCell(1).getStringCellValue());

				  }
				  c = row.getCell(2);
				  if (c== null) {
					  t.setApellido2("");
				  }else {
					  t.setApellido2(row.getCell(2).getStringCellValue());

				  }
				  c = row.getCell(3);
				  if (c== null) {
					  t.setNombre("");
				  }else {
					  t.setNombre(row.getCell(3).getStringCellValue());

				  }
				  c = row.getCell(5);
				  if (c== null) {
					  t.setCategorias("");
				  }else {
					  t.setCategorias(row.getCell(5).getStringCellValue());

				  }
				  c = row.getCell(6);
				  if (c== null) {
					  t.setEmpresa("");
				  }else {
					  t.setEmpresa(row.getCell(6).getStringCellValue());

				  }
				  
				  c = row.getCell(8);
				  if (c== null) {
					  
				  }else {
					  t.setFechaAlta(row.getCell(8).getDateCellValue());

				  }
				  c = row.getCell(13);
				  if (c== null) {
					  t.setProrrateo(false);
				  }else {
					  if(row.getCell(13).getStringCellValue().equals("SI"))
						  t.setProrrateo(true);
					  else
						  t.setProrrateo(false);

				  }
				  c = row.getCell(14);
				  if (c== null) {
					  t.setCodigoCuenta("");
				  }else {
					  t.setCodigoCuenta(row.getCell(14).getStringCellValue());
				  }
				  c = row.getCell(15);
				  if (c== null) {
					  t.setPais("");
				  }else {
					  t.setPais(row.getCell(15).getStringCellValue());
				  }
				  
				  c = row.getCell(7);
				  
				  if (c== null) {
					 
				  }else {
					  if(!estaIncluida(row.getCell(7).getStringCellValue())) {
						  Empresa e = new Empresa();
						  e.setCif(row.getCell(7).getStringCellValue());
						  e.setNombre(row.getCell(6).getStringCellValue());
						  empresa.add(e);
					  }
					 
				  }
				  
				
				  //cells.next();//email

				  trabajadores.add(t); 
				 
			 }
			 XSSFSheet sheet1 = workbook.getSheetAt(1);
			 int nfila1 = sheet1.getLastRowNum();
			 Iterator<Row> rows1 = sheet1.rowIterator();
			 rows1.next();
			 cont = 0;
			 while (cont<14) {
				 cont++;
				  Row row = (Row) rows1.next();
				  Iterator<Cell> cells = row.cellIterator();
				  Cell cell;
				  List datos = new List();
				  Categoria cat  = new Categoria();
				 
				  Cell c = row.getCell(0);
				  
				 if(c==null) {
					 cat.setNombreCategoria("");
				 }else {
					 cat.setNombreCategoria(c.getStringCellValue());
				 }
				 c = row.getCell(1);
				 if(c==null) {
					 cat.setSalarioBaseCategoria(0.0);
				 }else {
					 cat.setSalarioBaseCategoria(c.getNumericCellValue());
				 }
				 c = row.getCell(2);
				 if(c==null) {
					 cat.setComplementoCategoria(0.0);
				 }else {
					 cat.setComplementoCategoria(c.getNumericCellValue());
				 }
				 c = row.getCell(3);
				 if(c==null) {
					 cat.setCodigoCotizacion(0);
				 }else {
					 cat.setCodigoCotizacion((int)c.getNumericCellValue());
				 }
				  
				 categoria.add(cat);
			//	 System.out.println(cat.getNombreCategoria()+" "+cat.getSalarioBaseCategoria()+" "+cat.getComplementoCategoria()+" "+cat.getCodigoCotizacion());
			 }
			 
			 
			 
			 
		 } catch (IOException e) {
			 
	            e.printStackTrace();
	
	        }
		 

	}*/

}
