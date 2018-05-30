package app;

import java.io.IOException;
import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;

import models.Categorias;
import utils.HibernateUtil;

public class main {
	
	private static Session session;

	public static void main(String[] args) {
		
		session = HibernateUtil.getSessionFactory().openSession();
		
		Transaction tx = session.beginTransaction();
		
		
		ArrayList<Categorias> categorias = new ArrayList<>();
		leerExcel archivo;
		try {
			archivo = new leerExcel();
			categorias = archivo.getCategorias();
			for (int i = 0; i < categorias.size(); i++) {
				System.out.println(categorias.get(i).getNombreCategoria());
				session.save(categorias.get(i));

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tx.commit();
		session.close();
	}

}
