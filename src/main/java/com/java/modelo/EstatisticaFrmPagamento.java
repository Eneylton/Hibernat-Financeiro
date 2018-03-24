package com.java.modelo;

import java.io.Serializable;

import javax.persistence.Transient;


public class EstatisticaFrmPagamento implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private int total;
	private String pagamento;
	
	public EstatisticaFrmPagamento() {
	}
	

	@Transient
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}


	@Transient
	public String getPagamento() {
		return pagamento;
	}


	public void setPagamento(String pagamento) {
		this.pagamento = pagamento;
	}


	
}