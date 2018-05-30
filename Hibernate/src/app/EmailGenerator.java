package app;

import java.text.Normalizer;
import java.util.ArrayList;

public class EmailGenerator {

	public String generarNombreEmail(String nombre, String ap1, String ap2){
		String email = nombre.substring(0, 3)+ ap1.substring(0, 2).toLowerCase();
		if(ap2.length()!=0) {
			email = email +ap2.substring(0, 2).toLowerCase();
		}
		
		email = Normalizer.normalize(email, Normalizer.Form.NFD);
	    email = email.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	   
		return email;
	}
	public String generarNumero(String email, ArrayList<String> creados) {
		int rep = 0;
		for(int i=0;i<creados.size();i++) {
			if(creados.get(i).equals(email))
				rep++;
		}
		String resultado = String.valueOf(rep);
		if(resultado.length()==1) {
			resultado = "0"+resultado;
		}
		return resultado;
	}

	public String generarEmail(String email, String numero, String empresa) {

		
		
		return email + numero + "@" + empresa.toLowerCase() + ".es";
	}

}
