package app;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.itextpdf.text.DocumentException;

import models.Categorias;
import models.Empresas;
import models.Nomina;
import models.Trabajadorbbdd;

public class NominaGenerator extends main {
	public Nomina generarNomina(Trabajadorbbdd t, Date fecha) throws ParseException {

		
		System.out.println("Apel1:" + t.getApellido1()+ "Apel2: " + t.getApellido2()+ "Nombre:" +t.getNombre() );
		Categorias c = new Categorias();
		double prorrateo = 0;

		double salarioBase = getSalarioBase(t)/14;
		
		double complemento = getComplemento(t)/14;
		int antiguedad = getAntiguedad(t, fecha);
	
		if(t.getProrrateo()) {
			prorrateo = getProrrateo(salarioBase, complemento, antiguedad, fecha, t);
		}
		
		double tDevengo = salarioBase+complemento+antiguedad+prorrateo;
		
		double totalCalcular = 0;
		if(t.getProrrateo()){
			totalCalcular = tDevengo;
		}else {
		
			totalCalcular = tDevengo + getProrrateo(salarioBase, complemento, antiguedad, fecha, t);
		}
		
		double ss = totalCalcular * 0.047;
		double desempleo = totalCalcular * 0.016;
		double formacion = totalCalcular * 0.001;
		double irpf = tDevengo * getIRPF(t.getFechaAlta(),fecha, c.findCategoria(t.getCategorias(), categorias));
		
		double tDeduccion = ss+desempleo+formacion+irpf;
		
		double liquidoPercibir = tDevengo - tDeduccion;
	//	System.out.println("Salario Base: "+salarioBase+"\n"+"Complemento: "+complemento+"\n"+"Antiguedad: "+antiguedad+"\nProrrateo: "+prorrateo+"\n");
	//	System.out.println("Total Devengo: " + tDevengo + "SS: " + ss + "Desempleo: " + desempleo + "Formacion: " + formacion + "IRPF: " + irpf + "Total Deduccion: "+tDeduccion);
		
		//Coste empresario
		double empresarioBase = totalCalcular;
		double contingenciasComunes = totalCalcular * 0.236;
		double desempleoEmp =  totalCalcular * 0.067;
		double formacionEmp =  totalCalcular * 0.006;
		double accidentes =  totalCalcular * 0.01;
		double impFOGASA =  totalCalcular * 0.002;
		
		double totalEmpresario =  contingenciasComunes + desempleoEmp + formacionEmp + accidentes + impFOGASA;
		double costeTrabajor=  tDevengo + totalEmpresario;
		
		//damos valores
		Nomina nomina = new Nomina();
		
		nomina.setMes(fecha.getMonth());
		nomina.setAnio(fecha.getYear());
		nomina.setNumeroTrienios((int)(getMesesTrabajados(t.getFechaAlta(), fecha)-1)/36);
		nomina.setImporteTrienios(antiguedad);
		nomina.setImporteSalarioMes(salarioBase);
		nomina.setImporteComplementoMes(complemento);
		nomina.setValorProrrateo(prorrateo);
		nomina.setBrutoAnual(c.findCategoria(t.getCategorias(), categorias).getSalarioBaseCategoria());
		nomina.setIrpf(getIRPF(t.getFechaAlta(),fecha, c.findCategoria(t.getCategorias(), categorias)));
		nomina.setImporteIrpf(irpf);
		nomina.setBaseEmpresario(empresarioBase);
		nomina.setSeguridadSocialEmpresario(0.236);
		nomina.setImporteSeguridadSocialEmpresario(contingenciasComunes);
		nomina.setDesempleoEmpresario(0.067);
		nomina.setImporteDesempleoEmpresario(desempleoEmp);
		nomina.setFormacionEmpresario(0.006);
		nomina.setImporteFormacionEmpresario(formacionEmp);
		nomina.setFogasaempresario(0.002);
		nomina.setImporteFogasaempresario(impFOGASA);
		nomina.setSeguridadSocialTrabajador(0.047);
		nomina.setImporteSeguridadSocialTrabajador(ss);
		nomina.setDesempleoTrabajador(0.016);
		nomina.setImporteDesempleoTrabajador(desempleo);
		nomina.setFormacionTrabajador(0.001);
		nomina.setImporteFormacionTrabajador(formacion);
		nomina.setBrutoNomina(tDevengo);
		nomina.setLiquidoNomina(liquidoPercibir);
		nomina.setCosteTotalEmpresario(totalEmpresario);
	
		nomina.setAccidentesTrabajoEmpresario(0.01);
		nomina.setImporteAccidentesTrabajoEmpresario(accidentes);
		nomina.setValorParaIrpf(totalCalcular);		
		nomina.settDeducciones(tDeduccion);
		nomina.setCosteTotalTrabajador(costeTrabajor);
		nomina.setTrabajadorbbdd(t);
		
		Empresas e = new Empresas();
		e  = e.findEmpresa(t.getEmpresa(), empresas);

		PdfGenerator pdf = new PdfGenerator();
		
		try {
			pdf.generarPdf(e, t, fecha, nomina,false);
		} catch (DocumentException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		
		return nomina;
		
		//donde llamamamos al resto 
	}
	
	public void generarNominaExtra(Trabajadorbbdd t, Date fecha) throws ParseException {
		Categorias c = new Categorias();
		double prorrateo = 0;

		double salarioBase = getSalarioBase(t)/14;
		double complemento = getComplemento(t)/14;
		int antiguedad = getAntiguedad(t, fecha);
		
		int mesesTrabajados = getMesesTrabajados(t.getFechaAlta(), fecha);
		
		
		double tDevengo = salarioBase+complemento+antiguedad;
		if(mesesTrabajados<6) {
			tDevengo = (tDevengo/6)*mesesTrabajados;
		}
		
		double totalCalcular = 0;
		
		double ss = totalCalcular * 0.047;
		double desempleo = totalCalcular * 0.016;
		double formacion = totalCalcular * 0.001;
		double irpf =  tDevengo * getIRPF(t.getFechaAlta(),fecha, c.findCategoria(t.getCategorias(), categorias));
		
		double tDeduccion = ss+desempleo+formacion+irpf;
		
		double liquidoPercibir = tDevengo - tDeduccion;
	//	System.out.println("Salario Base: "+salarioBase+"\n"+"Complemento: "+complemento+"\n"+"Antiguedad: "+antiguedad+"\nProrrateo: "+prorrateo+"\n");
	//	System.out.println("Total Devengo: " + tDevengo + "SS: " + ss + "Desempleo: " + desempleo + "Formacion: " + formacion + "IRPF: " + irpf + "Total Deduccion: "+tDeduccion);
		
		//Coste empresario
		double empresarioBase = totalCalcular;
		double contingenciasComunes = totalCalcular * 0.236;
		double desempleoEmp =  totalCalcular * 0.067;
		double formacionEmp =  totalCalcular * 0.006;
		double accidentes =  totalCalcular * 0.01;
		double impFOGASA =  totalCalcular * 0.002;
		
		double totalEmpresario =  contingenciasComunes + desempleo + formacionEmp + accidentes + impFOGASA;
		double costeTrabajor=  tDevengo + empresarioBase;
		
Nomina nomina = new Nomina();
		


nomina.setMes(fecha.getMonth());
nomina.setAnio(fecha.getYear());
nomina.setNumeroTrienios((int)(getMesesTrabajados(t.getFechaAlta(), fecha)-1)/36);
nomina.setImporteTrienios(antiguedad);
nomina.setImporteSalarioMes(salarioBase);
nomina.setImporteComplementoMes(complemento);
nomina.setValorProrrateo(prorrateo);
nomina.setBrutoAnual(c.findCategoria(t.getCategorias(), categorias).getSalarioBaseCategoria());
nomina.setIrpf(getIRPF(t.getFechaAlta(),fecha, c.findCategoria(t.getCategorias(), categorias)));
nomina.setImporteIrpf(irpf);
nomina.setBaseEmpresario(empresarioBase);
nomina.setSeguridadSocialEmpresario(0.236);
nomina.setImporteSeguridadSocialEmpresario(contingenciasComunes);
nomina.setDesempleoEmpresario(0.067);
nomina.setImporteDesempleoEmpresario(desempleoEmp);
nomina.setFormacionEmpresario(0.006);
nomina.setImporteFormacionEmpresario(formacionEmp);
nomina.setFogasaempresario(0.002);
nomina.setImporteFogasaempresario(impFOGASA);
nomina.setSeguridadSocialTrabajador(0.047);
nomina.setImporteSeguridadSocialTrabajador(ss);
nomina.setDesempleoTrabajador(0.016);
nomina.setImporteDesempleoTrabajador(desempleo);
nomina.setFormacionTrabajador(0.001);
nomina.setImporteFormacionTrabajador(formacion);
nomina.setBrutoNomina(tDevengo);
nomina.setLiquidoNomina(liquidoPercibir);
nomina.setCosteTotalEmpresario(totalEmpresario);

nomina.setAccidentesTrabajoEmpresario(0.01);
nomina.setImporteAccidentesTrabajoEmpresario(accidentes);
nomina.setValorParaIrpf(totalCalcular);		
nomina.settDeducciones(tDeduccion);
nomina.setCosteTotalTrabajador(costeTrabajor);
nomina.setTrabajadorbbdd(t);


		PdfGenerator pdf = new PdfGenerator();
		Empresas e = new Empresas();
		e  = e.findEmpresa(t.getEmpresa(), empresas);
		try {
			pdf.generarPdf(e, t, fecha, nomina,true);
		} catch (DocumentException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
		
	}
	
	public int getAntiguedad(Trabajadorbbdd t, Date fecha) throws ParseException {
		int antiguedad = 0;
		int mesesTrabajados = getMesesTrabajados(t.getFechaAlta(), fecha)-1;
		int trieniosCompletos = mesesTrabajados/36;
		antiguedad= asignarTrienio(trieniosCompletos);
	
		return antiguedad;
		
	}
	
	public double getProrrateo(double sb, double comp, int antiguedad, Date fecha, Trabajadorbbdd t) throws ParseException {
		if (cambioTrienio(fecha, t.getFechaAlta())) {
			antiguedad = getAntiguedad(t, fecha) +1;
		}
		double res =(sb+comp+antiguedad)/6;
		return res;
	}
	
	public double getSalarioBase(Trabajadorbbdd t) {
		for(int i=0;i<categorias.size();i++) {
			if(categorias.get(i).getNombreCategoria().equals(t.getCategorias())){
				return categorias.get(i).getSalarioBaseCategoria();
			}
		}
		return 0;
	}
	
	public double getComplemento(Trabajadorbbdd t) {
		for(int i=0;i<categorias.size();i++) {
			if(categorias.get(i).getNombreCategoria().equals(t.getCategorias())){
				return categorias.get(i).getComplementoCategoria();
			}
		}
		return 0;
	}
	
	public int getMesesTrabajados(Date date, Date date2) throws ParseException {
		Calendar inicio = Calendar.getInstance();
		  inicio.setTime(date);
		
        Calendar fin = Calendar.getInstance();
		  fin.setTime(date2);
			

			
        int difA = fin.get(Calendar.YEAR) - inicio.get(Calendar.YEAR);
        int difM =  difA * 12 + fin.get(Calendar.MONTH) - inicio.get(Calendar.MONTH);
       return difM;
      
	}
	
	public double getIRPF(Date date2, Date fecha, Categorias c) throws ParseException {
		int brutoAnual = 0;
		String dateLeido = "01/01/"+fecha.toString().substring(fecha.toString().length()-4,fecha.toString().length() ) ;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Date date = sdf.parse(dateLeido);

		int mesesTrabajados = getMesesTrabajados(date2, date)-1;
		int trieniosCompletos = mesesTrabajados/36;
		int importeBruto = asignarTrienio(trieniosCompletos);
		int mesesSinTrienio = mesesTrabajados%36;
		int mesesRestantes = 36 - mesesSinTrienio;
		System.out.println("Meses Restantes "+mesesRestantes);
		if(mesesRestantes<6) {
			System.out.println("Entro en el 1�");
			brutoAnual = mesesRestantes*importeBruto+(14-mesesRestantes)*asignarTrienio(trieniosCompletos+1);
		}else if((6<=mesesRestantes)&&(mesesRestantes<=11)){
		System.out.println("Entro en el 2�");

			brutoAnual = (mesesRestantes+1)*importeBruto+(14-mesesRestantes-1)*asignarTrienio(trieniosCompletos+1);

		}else if(mesesRestantes==12){
			System.out.println("Entro en el 3�");

			brutoAnual = (mesesRestantes+2)*importeBruto+(14-mesesRestantes-2)*asignarTrienio(trieniosCompletos+1);

		}else {
			System.out.println("Entro en el 4�");

			brutoAnual =importeBruto*14;
		}
		System.out.println("Bruto anueal: "+brutoAnual);
		double total = c.getSalarioBaseCategoria()+c.getComplementoCategoria() +brutoAnual;
		System.out.println("Total: "+total);

		double irpf = getRetencion(total);
		System.out.println("retencion: "+irpf);

		
		return irpf;
		
		
		
		
	}
	public int  asignarTrienio(int nTrienios){
		int importeTrienio = 0;
		switch (nTrienios) {
		case 1:
			importeTrienio = 15;
			break;
		case 2:
			importeTrienio = 25;
			break;
		case 3:
			importeTrienio = 45;
			break;
		case 4:
			importeTrienio = 60;
			break;
		case 5:
			importeTrienio = 70;
			break;
		case 6:
			importeTrienio = 83;
			break;
		case 7:
			
			importeTrienio = 90;
			break;
		case 8:
			importeTrienio = 100;
			break;
		case 9:
			importeTrienio = 112;
			break;
		case 10:
			importeTrienio = 125;
			break;
		case 11: 
			importeTrienio = 140;
			break;
		case 12:
			importeTrienio = 160;
			break;
		case 13:
			importeTrienio = 170;
			break;
		case 14:
			importeTrienio = 182;
			break;
		case 15:
			importeTrienio = 190;
			break;
		case 16:
			importeTrienio = 202;
			break;
		case 17:
			importeTrienio = 215;
			break;
		case 18:
			importeTrienio = 230;
			break;
		default:
			break;
		}
		
		return importeTrienio;
		
	}
	public double getRetencion(double salario){
		System.out.println(salario);
		double retencion = 0.0;
		if(salario >= 60000){
			retencion = 26.22;
		}else if(salario >= 59000){
			retencion = 25.92;
		}else if(salario >= 58000){
			retencion = 25.62;
		}else if(salario >= 57000){
			retencion = 25.32;
		}else if(salario >= 56000){
			retencion = 25.02;
		}else if(salario >= 55000){
			retencion = 24.72;
		}else if(salario >= 54000){
			retencion = 24.42;
		}else if(salario >= 53000){
			retencion = 24.12;
		}else if(salario >= 52000){
			retencion = 23.82;
		}else if(salario >= 51000){
			retencion = 23.52;
		}else if(salario >= 50000){
			retencion = 23.22;
		}else if(salario >= 49000){
			retencion = 22.92;
		}else if(salario >= 48000){
			retencion = 22.62;
		}else if(salario >= 47000){
			retencion = 22.32;
		}else if(salario >= 46000){
			retencion = 22.02;
		}else if(salario >= 45000){
			retencion = 21.72;
		}else if(salario >= 44000){
			retencion = 21.42;
		}else if(salario >= 43000){
			retencion = 21.11;
		}else if(salario >= 42000){
			retencion = 20.80;
		}else if(salario >= 41000){
			retencion = 20.49;
		}else if(salario >= 41000){
			retencion = 20.49;
		}else if(salario >= 40000){
			retencion = 20.18;
		}else if(salario >= 39000){
			retencion = 19.87;
		}else if(salario >= 38000){
			retencion = 19.56;
		}else if(salario >= 37000){
			retencion = 19.25;
		}else if(salario >= 36000){
			retencion = 18.94;
		}else if(salario >= 35000){
			retencion = 18.63;
		}else if(salario >= 34000){
			retencion = 18.32;
		}else if(salario >= 33000){
			retencion = 18.01;
		}else if(salario >= 32000){
			retencion = 17.68;
		}else if(salario >= 31000){
			retencion = 17.38;
		}else if(salario >= 30000){
			retencion = 16.96;
		}else if(salario >= 29000){
			retencion = 15.55;
		}else if(salario >= 28000){
			retencion = 16.12;
		}else if(salario >= 27000){
			retencion = 15.66;
		}else if(salario >= 26000){
			retencion = 15.17;
		}else if(salario >= 25000){
			retencion = 14.63;
		}else if(salario >= 24000){
			retencion = 14.05;
		}
		else if(salario >= 23000){
			retencion = 13.49;
		}else if(salario >= 22000){
			retencion = 13.06;
		}else if(salario >= 21000){
			retencion = 12.60;
		}else if(salario >= 20000){
			retencion = 12.08;
		}
		else if(salario >= 19000){
			retencion = 11.51;
		}
		else if(salario >= 18000){
			retencion = 10.88;
		}else if(salario >= 17000){
			retencion = 10.18;
		}else if(salario >= 16000){
			retencion = 9.39;
		}else if(salario >= 15000){
			retencion = 8.31;
		}else if(salario >= 14000){
			retencion = 6.12;
		}else if(salario >= 13000){
			retencion = 3.30;
		}else if(salario >= 12000){
			retencion = 0.0;
		}
		
		return retencion/100;
	}
	
	public boolean cambioTrienio(Date fecha, Date altaTrabajador) throws ParseException {
		int mesesTrabajados = getMesesTrabajados(altaTrabajador, fecha)-1;
		int trieniosCompletos = mesesTrabajados/36;
		int importeBruto = asignarTrienio(trieniosCompletos);
		int mesesSinTrienio = mesesTrabajados%36;
		int mesesRestantes = 36 - mesesSinTrienio;
		
		if(mesesRestantes<(6-(fecha.getMonth()+1))) {
			return true;
		}
		return false;
	}
	

	
	
}
