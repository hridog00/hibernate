package app;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import models.Categorias;
import models.Empresas;
import models.Nomina;
import models.Trabajadorbbdd;
import utils.HibernateUtil;

@SuppressWarnings("static-access")
public class main {
	
	private static Session session;
	static ArrayList<Categorias> categorias = new ArrayList<>();
	static ArrayList<Empresas> empresas = new ArrayList<>();
	static ArrayList<Trabajadorbbdd> trabajadores = new ArrayList<>();
	static ArrayList<Nomina> nominas = new ArrayList<>();


	public static void main(String[] args) {
		guardarEmpresas();
		guardarCategorias();
		leerTrabajadores();
		comprobarDNI();	
		generarEmails();
		generarIban();
		guardarTrabajador();
		try {
			generarNominas();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		guardarNominas();
		System.exit(0);
		
	}
	
	

	public static void guardarEmpresas() {
session = HibernateUtil.getSessionFactory().openSession();
		
		Transaction tx = session.beginTransaction();
	

		leerExcel archivo;
		try {
			archivo = new leerExcel();
			
			
			empresas = archivo.getEmpresas();
			for (int i = 0; i < empresas.size(); i++) {
				System.out.println(empresas.get(i).getNombre());
				Query query = session.createQuery("from Empresas where Cif=:cif");
				query.setParameter("cif", empresas.get(i).getCif());
				if(query.getResultList().isEmpty()) {
					session.save(empresas.get(i));

				}else {
					Empresas e = (Empresas) query.getResultList().get(0);
					empresas.get(i).setIdEmpresa(e.getIdEmpresa());
					session.merge(empresas.get(i));
				}

			}
			empresas = (ArrayList<Empresas>) session.createQuery("from Empresas").list();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tx.commit();
		session.close();
		
	}
	public static void guardarCategorias() {
		session = HibernateUtil.getSessionFactory().openSession();

		Transaction tx = session.beginTransaction();



		leerExcel archivo;
		try {
			archivo = new leerExcel();
			categorias = archivo.getCategorias();
			for (int i = 0; i < categorias.size(); i++) {
				System.out.println(categorias.get(i).getNombreCategoria());
				Query query = session.createQuery("from Categorias where nombreCategoria=:nombre");
				query.setParameter("nombre", categorias.get(i).getNombreCategoria());
				if(query.getResultList().isEmpty()) {
					session.save(categorias.get(i));
				}else {
					Categorias c = (Categorias) query.getResultList().get(0);
					categorias.get(i).setIdCategoria(c.getIdCategoria());
					session.merge(categorias.get(i));
				}

			}


			categorias = (ArrayList<Categorias>) session.createQuery("from Categorias").list();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tx.commit();
		session.close();
	}
	public static void guardarTrabajador() {
		session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		for (int i = 0; i < trabajadores.size(); i++) {
			if(trabajadores.get(i).getNifnie()!="") {

				Query query = session.createQuery("from Trabajadorbbdd where NIFNIE=:nie AND Nombre=:nombre AND FechaAlta=:fecha");
				query.setParameter("nombre", trabajadores.get(i).getNombre());
				query.setParameter("nie", trabajadores.get(i).getNifnie());
				query.setParameter("fecha", trabajadores.get(i).getFechaAlta());
				
				if(query.getResultList().isEmpty()) {
					System.out.println("ENTRO");
					session.save(trabajadores.get(i));

					Trabajadorbbdd t = (Trabajadorbbdd) query.getResultList().get(0);
					System.out.println(t.getIdTrabajador());
					trabajadores.get(i).setIdTrabajador(t.getIdTrabajador());
				}else {
					System.out.println(trabajadores.get(i).getApellido1());
					Trabajadorbbdd t = (Trabajadorbbdd) query.getResultList().get(0);
					trabajadores.get(i).setIdTrabajador(t.getIdTrabajador());
					t = trabajadores.get(i);
					session.merge(trabajadores.get(i));
					
				}
			}


		}
		tx.commit();
		session.close();
	}
	

	public static void leerTrabajadores() {

	

		leerExcel archivo;
		try {
			archivo = new leerExcel();
			trabajadores = archivo.getTrabajadores();
			
			 
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void comprobarDNI() {
		ControladorDni controladorDni = new ControladorDni();
		controladorDni.comprobar();
	}
	public static void generarIban() {
		ControladorIban  controladorIban = new ControladorIban();
		for (int i=0;i<trabajadores.size();i++) {
		String cc = trabajadores.get(i).getCodigoCuenta();
		
		trabajadores.get(i).setIban(controladorIban.comprobarIban(cc,  trabajadores.get(i).getPais(), trabajadores.get(i).getFila(), trabajadores.get(i)));
		System.out.println(trabajadores.get(i).getIban());
		}
	}
	public static void generarEmails() {
		ControladorEmail controladorEmail = new ControladorEmail();
		for (int i=0;i<trabajadores.size();i++) {
			trabajadores.get(i).setEmail(controladorEmail.generarEmail(trabajadores.get(i)));
		}
	}

	public static void generarNominas() throws ParseException {
		Date fecha = fechaNomina();
		for(int i=0;i<trabajadores.size();i++) {
			if(trabajadores.get(i).getNifnie()!="") {
	    	  // System.out.println("Trabajador :" + i);
	    	   if(fecha.after(trabajadores.get(i).getFechaAlta())){

	    		   NominaGenerator nomina = new NominaGenerator();
	    		   
	    		  nominas.add(nomina.generarNomina(trabajadores.get(i), fecha));

	    		   if((fecha.getMonth()==5)||(fecha.getMonth()==11)) {

	    			   if(!trabajadores.get(i).getProrrateo()) {
	    				   nominas.add(nomina.generarNominaExtra(trabajadores.get(i), fecha));
	    			   }	  
	    		   }
	    	   }
	    	     
			}    
	       }
	}
	public static Date fechaNomina() throws ParseException{
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce una fecha: MM/yyyy"); 
		String dateLeido = sc.nextLine();
		dateLeido = "01/" + dateLeido;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Date date = sdf.parse(dateLeido);

		
		return date;
		
	}
	public static void guardarNominas() {
		session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		for (int i = 0; i < nominas.size(); i++) {
			System.out.println(nominas.get(i).getTrabajadorbbdd().getIdTrabajador());
			Query query = session.createQuery("from Nomina where Mes=:Mes AND Anio=:Anio AND BrutoNomina=:BrutoNomina AND LiquidoNomina=:LiquidoNomina AND IdTrabajador=:IdTrabajador");
			query.setParameter("Mes", nominas.get(i).getMes());
			query.setParameter("Anio",  nominas.get(i).getAnio());
			query.setParameter("BrutoNomina", nominas.get(i).getBrutoNomina());
			query.setParameter("LiquidoNomina",  nominas.get(i).getLiquidoNomina());
			query.setParameter("IdTrabajador", nominas.get(i).getTrabajadorbbdd().getIdTrabajador());
			
			if(query.getResultList().isEmpty()) {
				session.save(nominas.get(i));

			}else {
					Nomina n = (Nomina) query.getResultList().get(0);
					nominas.get(i).setIdNomina(n.getIdNomina());
					session.merge(nominas.get(i));
				
				
			}

		}
		tx.commit();
		session.close();
	}
	
		
	
}
