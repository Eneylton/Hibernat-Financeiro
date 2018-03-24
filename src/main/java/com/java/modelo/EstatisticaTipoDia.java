package com.java.modelo;

import java.io.Serializable;

import javax.persistence.Transient;

public class EstatisticaTipoDia implements Serializable {
	private static final long serialVersionUID = 1L;

	private int total;
	private String dia;

	public EstatisticaTipoDia() {
	}

	@Transient
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Transient
	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

}