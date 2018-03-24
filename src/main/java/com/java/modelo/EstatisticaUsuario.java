package com.java.modelo;

import java.io.Serializable;

import javax.persistence.Transient;

public class EstatisticaUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	private int total;
	private String valor;
	private String usuario;

	public EstatisticaUsuario() {
	}

	
	@Transient
	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Transient
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Transient
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}