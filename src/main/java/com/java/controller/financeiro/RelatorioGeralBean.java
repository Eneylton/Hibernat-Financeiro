package com.java.controller.financeiro;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.java.modelo.Financeiro;
import com.java.relatorio.RelatorioService;
import com.java.service.FinanceiroService;
import com.java.service.RelatorioGeralService;
import com.java.util.Session;

import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class RelatorioGeralBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private RelatorioGeralService relatorioGeralService;

	@Inject
	private RelatorioService relatorioService;
	
	@Inject
	private FinanceiroService financeiroService;

	private List<Financeiro> listarFinanceiros;

	private Financeiro relatorioGeralSelecionado;

	private Financeiro financeiro = new Financeiro();

	private Date dataInicio;

	private Date dataFim;

	private boolean idDestino;

	private boolean idStatus;

	private boolean idTipo;

	private double receita;

	private double despesa;

	private double total;

	@PostConstruct
	public void init() throws SQLException {

		totalEmpresaReceita();
		
		Long idEmpresa = Session.retornaIdEmpresa();

		listarFinanceiros = relatorioGeralService.listarPrevisao(idEmpresa);
	}

	public void consultaGeral() {

		Long idEmpresa = Session.retornaIdEmpresa();

		this.dataInicio = financeiro.getDataInicio();
		this.dataFim = financeiro.getDataFim();
		this.idDestino = financeiro.isDestino();
		this.idStatus = financeiro.isStatus();
		this.idTipo = financeiro.isTipo();
		this.receita = relatorioGeralService.totalReceita(idEmpresa, idDestino, idStatus, dataInicio, dataFim);
		this.despesa = relatorioGeralService.totalDespesa(idEmpresa, idDestino, idStatus, dataInicio, dataFim);

		this.total = receita - despesa;

		listarFinanceiros = relatorioGeralService.listarConsultaGeral(idEmpresa, idDestino, idStatus, idTipo,
				dataInicio, dataFim);

	}
	
	public void totalEmpresaReceita() {
		Long idReceita = Session.retornaIdEmpresa();

		double receita = financeiroService.totalReceitaEmpresa(idReceita);
		double despesa = financeiroService.totalDespesaEmpresa(idReceita);

		this.total = receita - despesa;

	}

	public void relatorioGeral() throws JRException {

		Long idEmpresa = Session.retornaIdEmpresa();

		this.dataInicio = financeiro.getDataInicio();
		this.dataFim = financeiro.getDataFim();
		this.idDestino = financeiro.isDestino();
		this.idStatus = financeiro.isStatus();
		this.idTipo = financeiro.isTipo();

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("idEmpresa", idEmpresa);
		parametros.put("dataInicio", this.dataInicio);
		parametros.put("dataFim", this.dataFim);
		parametros.put("idDestino", this.idDestino);
		parametros.put("idStatus", this.idStatus);
		parametros.put("idTipo", this.idTipo);

		listarFinanceiros = relatorioGeralService.listarRelatorioGeral(idEmpresa, idDestino, idStatus, idTipo,
				dataInicio, dataFim);
		relatorioService.gerarRelatorioGeral(listarFinanceiros, (HashMap<String, Object>) parametros, "RelatorioGeral");

		listarFinanceiros = relatorioGeralService.listarPrevisao(idEmpresa);
	}

	public List<Financeiro> getListarFinanceiros() {
		return listarFinanceiros;
	}

	public void setListarFinanceiros(List<Financeiro> listarFinanceiros) {
		this.listarFinanceiros = listarFinanceiros;
	}

	public Financeiro getRelatorioGeralSelecionado() {
		return relatorioGeralSelecionado;
	}

	public void setRelatorioGeralSelecionado(Financeiro relatorioGeralSelecionado) {
		this.relatorioGeralSelecionado = relatorioGeralSelecionado;
	}

	public Financeiro getFinanceiro() {
		return financeiro;
	}

	public void setFinanceiro(Financeiro financeiro) {
		this.financeiro = financeiro;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public double getReceita() {
		return receita;
	}

	public void setReceita(double receita) {
		this.receita = receita;
	}

	public double getDespesa() {
		return despesa;
	}

	public void setDespesa(double despesa) {
		this.despesa = despesa;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public boolean isIdDestino() {
		return idDestino;
	}

	public void setIdDestino(boolean idDestino) {
		this.idDestino = idDestino;
	}

	public boolean isIdStatus() {
		return idStatus;
	}

	public void setIdStatus(boolean idStatus) {
		this.idStatus = idStatus;
	}

	public boolean isIdTipo() {
		return idTipo;
	}

	public void setIdTipo(boolean idTipo) {
		this.idTipo = idTipo;
	}

}
