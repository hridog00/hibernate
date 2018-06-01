package app;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import models.Empresas;
import models.Nomina;
import models.Trabajadorbbdd;



public class PdfGenerator {
	public void generarPdf(Empresas empresa , Trabajadorbbdd trabajador, Date fecha, Nomina n, Boolean extra) throws DocumentException, MalformedURLException, IOException {
		String nombre = "";
		String mes = getMonthName(fecha);
		if(extra){
			 nombre = "resources/"+ trabajador.getNifnie() + "_" + trabajador.getApellido1() + trabajador.getApellido2() + trabajador.getNombre() +"_"+mes+""+fecha.toString().substring(fecha.toString().length()-4,fecha.toString().length() )+ "_Extra" + ".pdf";

		}else{
			 nombre = "resources/"+ trabajador.getNifnie() + "_" + trabajador.getApellido1() + trabajador.getApellido2() + trabajador.getNombre() + "_" +mes+""+fecha.toString().substring(fecha.toString().length()-4,fecha.toString().length() )+  ".pdf";

		}
		
		File PDFNewFile = new File(nombre);
		Document document = new Document();
		PdfWriter.getInstance(document,new FileOutputStream(PDFNewFile));
		document.open();
		Paragraph empty = new Paragraph("");
		PdfPTable tableDatosEmpresa = new PdfPTable(2);

		Paragraph nom = new Paragraph(empresa.getNombre());
		Paragraph cif = new Paragraph(empresa.getCif());
		//Paragraph dir2 = new Paragraph(empresa.getCodigoPostal());

		PdfPCell cell1 = new PdfPCell();
		cell1.addElement(new Paragraph(empresa.getNombre()));
		cell1.addElement(new Paragraph("CIF: " + empresa.getCif()));
		cell1.addElement(empty);
		cell1.addElement(empty);
		cell1.setPadding(1);
		
		
		PdfPCell cellCabeceraDcha = new PdfPCell();
		cellCabeceraDcha.addElement(new Paragraph("IBAN: "+ trabajador.getIban()));
		
		
		cellCabeceraDcha.addElement(new Paragraph("Categoria: "+ trabajador.getCategorias()));
		
		
		cellCabeceraDcha.addElement(new Paragraph("Bruto anual: "+ n.getBrutoAnual()));
		
		
		cellCabeceraDcha.addElement(new Paragraph("Fecha de Alta: "+ trabajador.getFechaAlta()));
		cellCabeceraDcha.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		
		tableDatosEmpresa.addCell(cell1); 
		tableDatosEmpresa.addCell(cellCabeceraDcha);
		tableDatosEmpresa.setSpacingAfter(10f);
		document.add(tableDatosEmpresa);
		 PdfPTable tableDatosTrabajador= new PdfPTable(2);

		 Paragraph destinatario = new Paragraph("Destinatario: ");
		 Paragraph nomTrabajador = new Paragraph(trabajador.getNombre()+""+trabajador.getApellido1()+" "+trabajador.getApellido2());
		 nomTrabajador.setAlignment(Element.ALIGN_RIGHT);
		 Paragraph niftrab = new Paragraph("DNI: " +trabajador.getNifnie());
		 niftrab.setAlignment(Element.ALIGN_RIGHT);
		 Paragraph dir1trab = new Paragraph("Avenida de la facultad");
		 dir1trab.setAlignment(Element.ALIGN_RIGHT);
		 Paragraph dir2trab = new Paragraph("24001 León");
		 dir2trab.setAlignment(Element.ALIGN_RIGHT);

		 
		 Image img = Image.getInstance("resources/bisnes.jpg");
		 img.setAbsolutePosition(100f,100f);
		 PdfPCell cellImagen = new PdfPCell(img,true);
		 cellImagen.setBorder(Rectangle.OUT_BOTTOM);
		 cellImagen.setPadding(50);
		 cellImagen.setPaddingTop(10);
		 cellImagen.setPaddingBottom(10);

		 
		 PdfPCell celltrabajador = new PdfPCell();
		 celltrabajador.addElement(destinatario);
		 celltrabajador.addElement(nomTrabajador);
		 celltrabajador.addElement(niftrab);
		 celltrabajador.addElement(empty);
		 celltrabajador.addElement(dir1trab);
		 celltrabajador.addElement(dir2trab);
		 celltrabajador.addElement(empty);

		 celltrabajador.setIndent(10);
		 celltrabajador.setPadding(10);

		 tableDatosTrabajador.addCell(cellImagen);
		 tableDatosTrabajador.addCell(celltrabajador);
		 tableDatosTrabajador.setSpacingAfter(10f);
		 document.add(tableDatosTrabajador);


		 PdfPTable tablaFecha = new PdfPTable(1);

		 Font fontTit = new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC,
		GrayColor.BLACK);

		 Paragraph fechLit = new Paragraph("Nomina "+mes+" "+fecha.toString().substring(fecha.toString().length()-4,fecha.toString().length() ) ,fontTit);
		 fechLit.setAlignment(Element.ALIGN_CENTER);
		 PdfPCell cellfecha2 = new PdfPCell();
		 cellfecha2.addElement(fechLit);
		// cellfecha2.setBorder(Rectangle.OUT_BOTTOM);
		 cellfecha2.setPadding(10);
		 tablaFecha.addCell(cellfecha2);

		 PdfPCell cellfecha3 = new PdfPCell(new Paragraph(""));
		 //cellfecha3.setBorder(Rectangle.OUT_BOTTOM);
		 tablaFecha.addCell(cellfecha3);


		 tablaFecha.setSpacingAfter(10f);
		 document.add(tablaFecha);
		 
		 
		 
		 PdfPTable tablaNomina = new PdfPTable(5);
		 
	     PdfPCell cell;
	     
	     cell = new PdfPCell(new Phrase(""));
	     cell.setVerticalAlignment(Element.ALIGN_CENTER);
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     
	     
	     cell = new PdfPCell(new Phrase("Cantidad"));
	     cell.setVerticalAlignment(Element.ALIGN_CENTER);
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     
	     
	     cell = new PdfPCell(new Phrase("Imp. Unit."));
	     cell.setBorder(0);
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase("Dev."));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase("Deduccion"));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase("Salario Base"));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase("30 dias"));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(String.format("%.2f", (n.getImporteSalarioMes()))));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase("Prorrata"));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase("30 dias"));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(String.format("%.2f",n.getValorProrrateo())));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase("Complemento"));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase("30 dias"));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(String.format("%.2f",n.getImporteComplementoMes())));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase("Antiguedad"));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(n.getNumeroTrienios()+" Trienios"));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""+n.getImporteTrienios()));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
     
	     cell = new PdfPCell(new Phrase("Contigencias Generales"));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase("4.7%"));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase("de " + String.format("%.2f", n.getValorParaIrpf())));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""+ String.format("%.2f", n.getImporteSeguridadSocialTrabajador())));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     
	     
	     cell = new PdfPCell(new Phrase("Desempleo"));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase("1.6%"));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase("de " + String.format("%.2f", n.getValorParaIrpf())));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""+ String.format("%.2f", n.getImporteDesempleoTrabajador())));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     
	     
	     cell = new PdfPCell(new Phrase("Cuota Formacion"));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase("0.1%"));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase("de " +String.format("%.2f", n.getValorParaIrpf())));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""+ String.format("%.2f", n.getImporteFormacionTrabajador())));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     
	     
	     cell = new PdfPCell(new Phrase("IRPF"));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(String.format("%.2f", (n.getIrpf()*100))+"%"));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase("de " + String.format("%.2f",n.getBrutoNomina())));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""));
	     cell.setBorder(0);

	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""+ String.format("%.2f", n.getImporteIrpf())));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     
	     
	     
	     cell = new PdfPCell(new Phrase("Total Deducciones"));
	     cell.setBorder(0);
	     cell.setColspan(3);
	     
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase("" + String.format("%.2f", n.gettDeducciones())));
	     cell.setBorder(0);
	     
	     tablaNomina.addCell(cell);
	     

	     
	     cell = new PdfPCell(new Phrase("Total Devengos"));
	     cell.setBorder(0);

	     cell.setColspan(3);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase("" + String.format("%.2f", n.getBrutoNomina())));
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase(""));
	     
	     cell.setBorder(0);
	     tablaNomina.addCell(cell);
	     
	    
	     cell = new PdfPCell(new Phrase("Liquido a percibir    "));
	     cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	     cell.setColspan(4);
	     cell.setBorder(0);
	     cell.setRowspan(3);
	     tablaNomina.addCell(cell);
	     cell = new PdfPCell(new Phrase("" + String.format("%.2f", n.getLiquidoNomina())));
	     cell.setBorder(0);
//	     cell.setRowspan(3);
	     tablaNomina.addCell(cell);
	     
		 document.add(tablaNomina);
		 
		 
		 document.add(new Phrase(" "));
		 document.add(new Phrase(" "));
		 
		 PdfPTable tablaEmp = new PdfPTable(2);
		 tablaEmp.getDefaultCell().setBorder(0);
		 
	     
	     PdfPCell cellEmp = new PdfPCell(new Phrase("Calculo Empresario: BASE"));
	     cellEmp.setBorder(0);
	     tablaEmp.addCell(cellEmp);
	     cellEmp = new PdfPCell(new Phrase("" + String.format("%.2f", n.getBaseEmpresario()) ));
	     cellEmp.setBorder(0);
	     cellEmp.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	     tablaEmp.addCell(cellEmp);
	     
	     cellEmp = new PdfPCell(new Phrase("Contingencias comunes 23,60%"));
	     cellEmp.setBorder(0);
	     tablaEmp.addCell(cellEmp);
	     cellEmp = new PdfPCell(new Phrase("" + String.format("%.2f", n.getImporteSeguridadSocialEmpresario())));
	     cellEmp.setBorder(0);
	     cellEmp.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	     tablaEmp.addCell(cellEmp);
	     
	     cellEmp = new PdfPCell(new Phrase("Desempleo 6.7%"));
	     cellEmp.setBorder(0);
	     tablaEmp.addCell(cellEmp);
	     cellEmp = new PdfPCell(new Phrase("" + String.format("%.2f", n.getImporteDesempleoEmpresario())));
	     cellEmp.setBorder(0);
	     cellEmp.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	     tablaEmp.addCell(cellEmp);
	     
	     cellEmp = new PdfPCell(new Phrase("Formacion 0.6%"));
	     cellEmp.setBorder(0);
	     tablaEmp.addCell(cellEmp);
	     cellEmp = new PdfPCell(new Phrase("" + String.format("%.2f", n.getImporteFormacionEmpresario())));
	     cellEmp.setBorder(0);
	     cellEmp.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	     tablaEmp.addCell(cellEmp);
	     
	     cellEmp = new PdfPCell(new Phrase("Accidentes de trabajo 1.0%"));
	     cellEmp.setBorder(0);
	     tablaEmp.addCell(cellEmp);
	     cellEmp = new PdfPCell(new Phrase("" + String.format("%.2f", n.getImporteAccidentesTrabajoEmpresario())));
	     cellEmp.setBorder(0);
	     cellEmp.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	 
	     tablaEmp.addCell(cellEmp);
	     
	     cellEmp = new PdfPCell(new Phrase("FOGASA 0.2%"));
	     cellEmp.setBorder(0);
	     tablaEmp.addCell(cellEmp);
	     cellEmp = new PdfPCell(new Phrase("" + String.format("%.2f", n.getImporteFogasaempresario())));
	     cellEmp.setBorder(0);
	     cellEmp.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	     tablaEmp.addCell(cellEmp);
	     
	     cellEmp = new PdfPCell(new Phrase("TOTAL empresario"));
	     cellEmp.setBorder(0);
	     tablaEmp.addCell(cellEmp);
	     cellEmp = new PdfPCell(new Phrase("" + String.format("%.2f", n.getCosteTotalEmpresario())));
	     cellEmp.setBorder(0);
	     cellEmp.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	     tablaEmp.addCell(cellEmp);
	     
	     cellEmp = new PdfPCell(new Phrase("Coste total trabajador"));
	     cellEmp.setBorder(0);
	     tablaEmp.addCell(cellEmp);
	     cellEmp = new PdfPCell(new Phrase("" + String.format("%.2f", n.getCosteTotalTrabajador())));
	     cellEmp.setBorder(0);
	     cellEmp.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	     tablaEmp.addCell(cellEmp);
	          
	     
		 document.add(tablaEmp);
		 
		 
		 
		 
		 document.close();
	}
	
	public String getMonthName(Date date){
		String name = null;
		switch (date.getMonth()+1){
		case 1:
			name = "Enero";
			break;
		case 2:
			name = "Febrero";
			break;
		case 3:
			name = "Marzo";
			break;
		case 4:
			name = "Abril";
			break;
		case 5:
			name = "Mayo";
			break;
		case 6:
			name = "Junio";
			break;
		case 7:
			name = "Julio";
			break;
		case 8:
			name = "Agosto";
			break;
		case 9:
			name = "Septiembre";
			break;
		case 10:
			name = "Octubre";
			break;
		case 11:
			name = "Noviembre";
			break;
		case 12:
			name = "Diciembre";
			break;
		}
		return name;
}
}
