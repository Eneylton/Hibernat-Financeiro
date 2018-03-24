package com.java.modelo;

import java.io.Serializable;

import javax.persistence.Transient;

public class EstatisticaSexo implements Serializable {
	private static final long serialVersionUID = 1L;

	private int total;
	private String sexo;

	public EstatisticaSexo() {
	}

	@Transient
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Transient
	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	
	

}