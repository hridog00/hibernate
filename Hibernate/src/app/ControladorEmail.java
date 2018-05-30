package app;

import java.util.ArrayList;

import app.EmailGenerator;
import models.*;

public class ControladorEmail {
	
	static ArrayList <String> emails = new ArrayList <String>();

	
	public static String generarEmail(Trabajadorbbdd t) {
		EmailGenerator eg = new EmailGenerator();
		String n= eg.generarNombreEmail(t.getNombre(), t.getApellido1(), t.getApellido2());
		String numero = eg.generarNumero(n, emails);
		emails.add(n);
		String email = eg.generarEmail(n, numero, t.getEmpresa());
		ModificarExcell m = new ModificarExcell();
		m.modificarExcel(t.getFila(),email,4);
		System.out.println(email);
		return email;
	}

}
