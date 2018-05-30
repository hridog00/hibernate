package app;

import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import models.Categorias;
import models.Empresas;
import models.Trabajadorbbdd;
import utils.HibernateUtil;

public class main {
	
	private static Session session;
	static ArrayList<Categorias> categorias = new ArrayList<>();
	static ArrayList<Empresas> empresas = new ArrayList<>();
	static ArrayList<Trabajadorbbdd> trabajadores = new ArrayList<>();
	


	public static void main(String[] args) {
		guardarEmpresas();
		guardarCategorias();
		guardarTrabajadores();
		comprobarDNI();	
		
		
	
		
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
	public static void guardarTrabajadores() {

		

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
		
	}

}
