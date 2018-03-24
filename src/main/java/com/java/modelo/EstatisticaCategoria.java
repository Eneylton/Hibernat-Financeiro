package com.java.modelo;

import java.io.Serializable;

import javax.persistence.Transient;


public class EstatisticaCategoria implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private int total;
	private String categoria;
	
	public EstatisticaCategoria() {
	}
	

	@Transient
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Transient
 	public String getCategoria() {
		return categoria;
	}


	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}



}