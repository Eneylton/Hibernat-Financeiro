package com.java.modelo;

import java.io.Serializable;

import javax.persistence.Transient;

public class Lancamento implements Serializable {
	private static final long serialVersionUID = 1L;

	private double total;
	private String mes;

	public Lancamento() {
	}
	

	@Transient
	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@Transient
	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

}