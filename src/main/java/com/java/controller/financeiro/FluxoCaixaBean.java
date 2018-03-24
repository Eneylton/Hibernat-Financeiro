package com.java.controller.financeiro;

import java.io.Serializable;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.java.service.FluxoCaixaService;
import com.java.util.Session;

@Named
@ViewScoped
public class FluxoCaixaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FluxoCaixaService fluxoCaixaService;

	private double faturamentoDia;

	private double despesasEmpresa;

	private double areceberEmpresa;

	private double apagarEmpresa;

	private double movimentacaoBancaria;

	private double pendencia;

	private double pendenciaEmpresa;

	@PostConstruct
	public void init() throws SQLException {

		totalFaturamentoDiario();
		movimentacaoBancaria();
		pendenciaBancaria();
		pendenciaEmpresa();
		totalDespesas();
		totalReceber();
		totalPagar();

	}

	public void totalFaturamentoDiario() {
		Long idReceita = Session.retornaIdEmpresa();

		this.faturamentoDia = fluxoCaixaService.totalFaturamentoDiario(idReceita);

	}


	public void totalDespesas() {
		Long idReceita = Session.retornaIdEmpresa();

		this.despesasEmpresa = fluxoCaixaService.totalDespesaEmpresa(idReceita);

	}

	public void totalReceber() {
		Long idReceita = Session.retornaIdEmpresa();

		this.areceberEmpresa = fluxoCaixaService.totalReceber(idReceita);

	}

	public void totalPagar() {
		Long idReceita = Session.retornaIdEmpresa();

		this.apagarEmpresa = fluxoCaixaService.totalPagar(idReceita);

	}

	public void movimentacaoBancaria() {
		Long idReceita = Session.retornaIdEmpresa();

		this.movimentacaoBancaria = fluxoCaixaService.movimentacaoBancaria(idReceita);

	}

	public void pendenciaBancaria() {
		Long idReceita = Session.retornaIdEmpresa();

		this.pendencia = fluxoCaixaService.totalPendenciaBanco(idReceita);

	}

	public void pendenciaEmpresa() {
		Long idReceita = Session.retornaIdEmpresa();

		this.pendenciaEmpresa = fluxoCaixaService.totalPendenciaEmpresa(idReceita);

	}

	public double getFaturamentoDia() {
		return faturamentoDia;
	}

	public void setFaturamentoDia(double faturamentoDia) {
		this.faturamentoDia = faturamentoDia;
	}

	public double getDespesasEmpresa() {
		return despesasEmpresa;
	}

	public void setDespesasEmpresa(double despesasEmpresa) {
		this.despesasEmpresa = despesasEmpresa;
	}

	public double getAreceberEmpresa() {
		return areceberEmpresa;
	}

	public void setAreceberEmpresa(double areceberEmpresa) {
		this.areceberEmpresa = areceberEmpresa;
	}

	public double getApagarEmpresa() {
		return apagarEmpresa;
	}

	public void setApagarEmpresa(double apagarEmpresa) {
		this.apagarEmpresa = apagarEmpresa;
	}


	public double getMovimentacaoBancaria() {
		return movimentacaoBancaria;
	}

	public void setMovimentacaoBancaria(double movimentacaoBancaria) {
		this.movimentacaoBancaria = movimentacaoBancaria;
	}

	public double getPendencia() {
		return pendencia;
	}

	public void setPendencia(double pendencia) {
		this.pendencia = pendencia;
	}

	public double getPendenciaEmpresa() {
		return pendenciaEmpresa;
	}

	public void setPendenciaEmpresa(double pendenciaEmpresa) {
		this.pendenciaEmpresa = pendenciaEmpresa;
	}

}
