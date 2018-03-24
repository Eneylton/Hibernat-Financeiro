package com.java.modelo;

import java.io.Serializable;

import javax.persistence.Transient;


public class EstatisticaCivil implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private int total;
	private String civil;
	
	public EstatisticaCivil() {
	}
	

	@Transient
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}


	@Transient
	public String getCivil() {
		return civil;
	}


	public void setCivil(String civil) {
		this.civil = civil;
	}


	

	
}