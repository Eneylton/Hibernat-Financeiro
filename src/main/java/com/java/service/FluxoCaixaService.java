package com.java.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.java.dao.FluxoCaixaDAO;

public class FluxoCaixaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FluxoCaixaDAO fluxoCaixaDAO;

	public double totalFaturamentoDiario(Long id) {
		return fluxoCaixaDAO.totalFaturamentoDiario(id);
	}


	public double totalDespesaEmpresa(Long id) {
		return fluxoCaixaDAO.totalDespesaEmpresa(id);
	}

	public double totalReceber(Long id) {
		return fluxoCaixaDAO.totalReceber(id);
	}

	public double totalPagar(Long id) {
		return fluxoCaixaDAO.totalPagar(id);
	}

	public double movimentacaoBancaria(Long id) {
		return fluxoCaixaDAO.movimentacaoBancaria(id);

	}

	public double totalPendenciaBanco(Long id) {
		return fluxoCaixaDAO.totalPendenciaBanco(id);

	}
	
	public double totalPendenciaEmpresa(Long id) {
		
		return fluxoCaixaDAO.totalPendenciaEmpresa(id);
	}
}
