package app;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import app.XMLController;

public class ControladorDni {
	
	static ArrayList <String> analizados = new ArrayList <String>();
	
	static int XMLCreadoCuentas = 0;
	static int XMLCreadoDni = 0;
	
	public static void generarErrorDni(String id,String nombre, String ap1, String ap2, String cat, String empresa) {
		XMLController xml = new XMLController();
		if (XMLCreadoCuentas==0) {
			try {
				xml.generar();
			} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
				e.printStackTrace();
			}
			XMLCreadoCuentas++;
		}
			try {
				xml.add(id, nombre, ap1, ap2, cat, empresa);
			} catch (SAXException | IOException | ParserConfigurationException | TransformerException e) {
				e.printStackTrace();
			}
		
	}

	
	public static boolean estaVacio(String DNI) {
		if(DNI.length()==0) {
			return true;
		}
		return false;
	}
	public static boolean estaDuplicado(String DNI) {
		
		
		for(int i=0;i<analizados.size();i++) {
			
			if(analizados.get(i)==DNI) {
				
				return true;
			}
			
		}
		return false;
	}
	public static boolean comprobarDNI(String DNI) {
		if(esExtranjero(DNI)) {
			DNI = transformarNIE(DNI);	
		}
		
		String numero = DNI.substring(0, DNI.length() - 1);
		int n = Integer.parseInt(numero);
		int nLetra = n %23;
		String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
		char letra = letras.charAt(nLetra);
		if(letra == DNI.charAt(DNI.length()-1)){
			return true;
		}else {
			return false;
		}
		
	}
	public static boolean esExtranjero(String DNI) {
		if((DNI.charAt(0)=='X')|| (DNI.charAt(0)=='Y')|| (DNI.charAt(0)=='Z')){
			return true;
		}
		return false;
	}
	public static String transformarNIE(String DNI) {
		String resultado = "";
		if(DNI.charAt(0)=='X') {
			resultado = "0"+DNI.substring(1);
			
		}
		if(DNI.charAt(0)=='Y') {
			resultado = "1"+DNI.substring(1);
			
		}
		if(DNI.charAt(0)=='Z') {
			resultado = "2"+DNI.substring(1);
			
		}
		
		return resultado;
	}
	
	public static String arreglarDNI(String dni) {
		String dniOriginal= dni;
		if(esExtranjero(dni)) {
			dni = transformarNIE(dni);
		}
		String dniFinal = dni;
		String numero = dni.substring(0, dni.length() - 1);
		int n = Integer.parseInt(numero);
		int nLetra = n %23;
		String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
		char letra = letras.charAt(nLetra);
		dniFinal = dniOriginal.substring(0, dni.length() - 1) + ""+letra;
		
		return dniFinal;
		
	}
	

}
