package models;
// Generated 30-may-2018 16:39:10 by Hibernate Tools 5.3.0.Beta2


import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import practica1.Categoria;

/**
 * Categorias generated by hbm2java
 */

@Entity
@Table(name="Categorias")
public class Categorias implements java.io.Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer idCategoria;
	@Column
	private String nombreCategoria;
	@Column
	private double salarioBaseCategoria;
	@Column
	private double complementoCategoria;

	

	public Categorias() {
	}



	public Categorias(String nombreCategoria, double salarioBaseCategoria, double complementoCategoria) {
		this.nombreCategoria = nombreCategoria;
		this.salarioBaseCategoria = salarioBaseCategoria;
		this.complementoCategoria = complementoCategoria;
		
	}

	public Integer getIdCategoria() {
		return this.idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombreCategoria() {
		return this.nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public double getSalarioBaseCategoria() {
		return this.salarioBaseCategoria;
	}

	public void setSalarioBaseCategoria(double salarioBaseCategoria) {
		this.salarioBaseCategoria = salarioBaseCategoria;
	}

	public double getComplementoCategoria() {
		return this.complementoCategoria;
	}

	public void setComplementoCategoria(double complementoCategoria) {
		this.complementoCategoria = complementoCategoria;
	}
	public Categorias findCategoria(String nombre, ArrayList<Categorias> lista) {
		Categorias c = new Categorias();
		for(int i=0;i<lista.size();i++) {
			if(lista.get(i).getNombreCategoria().equals(nombre)) {
				return lista.get(i);
			}
		}
		return c;
	}

}
