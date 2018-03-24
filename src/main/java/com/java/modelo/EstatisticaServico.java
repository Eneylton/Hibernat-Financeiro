package com.java.modelo;

import java.io.Serializable;

import javax.persistence.Transient;


public class EstatisticaServico implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private int total;
	private String servico;
	
	public EstatisticaServico() {
	}
	

	@Transient
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}


	@Transient
	public String getServico() {
		return servico;
	}


	public void setServico(String servico) {
		this.servico = servico;
	}

	
	
 	



}