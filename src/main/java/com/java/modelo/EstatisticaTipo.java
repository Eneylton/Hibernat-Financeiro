package com.java.modelo;

import java.io.Serializable;

import javax.persistence.Transient;


public class EstatisticaTipo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private int total;
	private String tipo;
	
	public EstatisticaTipo() {
	}
	

	@Transient
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}


	@Transient
	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}