package com.java.controller.estatistica;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.java.modelo.Lancamento;
import com.java.service.EstatisticaService;
import com.java.util.Session;

@Named
@ViewScoped
public class EstatisticaLancamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EstatisticaService estatisticaService;

	private List<Object[]> listarLancamento = new ArrayList<>();

	private List<Lancamento> listarTotalLancamento = new ArrayList<>();

	private List<Object[]> listarDespesa = new ArrayList<>();

	private List<Lancamento> listarTotaldespesa = new ArrayList<>();

	private BarChartModel graficoBarra;

	private Lancamento lancamento;

	@PostConstruct
	public void init() throws SQLException {
		totalLancamento();
		limpar();
	}

	public void totalLancamento() throws SQLException {

		Long idEmpresa = Session.retornaIdEmpresa();

		listarLancamento = estatisticaService.graficoLancamentoMensalReceita(idEmpresa);
		listarDespesa    = estatisticaService.graficoLancamentoMensalDespesa(idEmpresa);

		for (Object[] lancamento : listarLancamento) {

			Lancamento lancar = new Lancamento();

			String label = lancamento[0].toString();
			String resultado = String.valueOf(lancamento[1]);

			lancar.setMes(label);
			lancar.setTotal(Double.parseDouble(resultado));

			listarTotalLancamento.add(lancar);

			createAnimatedModels();

		}
		
		for (Object[] desp : listarDespesa) {

			Lancamento lancar = new Lancamento();

			String label = desp[0].toString();
			String resultado = String.valueOf(desp[1]);

			lancar.setMes(label);
			lancar.setTotal(Double.parseDouble(resultado));

			listarTotaldespesa.add(lancar);

			createAnimatedModels();

		}


	}

	private void createAnimatedModels() throws SQLException {

		graficoBarra = initBarModel();
		graficoBarra.setAnimate(true);
		graficoBarra.setShowPointLabels(true);
		graficoBarra.setLegendPosition("ne");

		Axis yAxis = graficoBarra.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(30000);
		yAxis.setTickFormat("R$ %'.2f");

		Axis xAxis = graficoBarra.getAxis(AxisType.X);
		xAxis.setMin(1);
		xAxis.setMax(31);
		xAxis.setTickFormat("R$ %'.2f");
		xAxis.setTickCount(31);
		xAxis.setTickAngle(-50);
		/*graficoBarra.setStacked(true);*/
		graficoBarra.setExtender("chartExtender");
		graficoBarra.setSeriesColors("607d8b,bdfff0");

	}

	private BarChartModel initBarModel() throws SQLException {
		BarChartModel model = new BarChartModel();
		ChartSeries receita = new ChartSeries();
		ChartSeries despesa = new ChartSeries();

		for (Lancamento lancamento : listarTotalLancamento) {
			receita.setLabel("Receitas");
			receita.set(lancamento.getMes(), lancamento.getTotal());

		}
		
		for (Lancamento lancamento : listarTotaldespesa) {
			despesa.setLabel("Despesas");
			despesa.set(lancamento.getMes(), lancamento.getTotal());

		}


		model.addSeries(despesa);
		model.addSeries(receita);

		return model;

	}

	public void limpar() {
		this.lancamento = new Lancamento();
	}

	public List<Object[]> getListarLancamento() {
		return listarLancamento;
	}

	public void setListarLancamento(List<Object[]> listarLancamento) {
		this.listarLancamento = listarLancamento;
	}

	public BarChartModel getGraficoBarra() {
		return graficoBarra;
	}

	public void setGraficoBarra(BarChartModel graficoBarra) {
		this.graficoBarra = graficoBarra;
	}

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

	public List<Lancamento> getListarToalLancamento() {
		return listarTotalLancamento;
	}

	public void setListarToalLancamento(List<Lancamento> listarToalLancamento) {
		this.listarTotalLancamento = listarToalLancamento;
	}

	public List<Lancamento> getListarTotalLancamento() {
		return listarTotalLancamento;
	}

	public void setListarTotalLancamento(List<Lancamento> listarTotalLancamento) {
		this.listarTotalLancamento = listarTotalLancamento;
	}

	public List<Object[]> getListarDespesa() {
		return listarDespesa;
	}

	public void setListarDespesa(List<Object[]> listarDespesa) {
		this.listarDespesa = listarDespesa;
	}

	public List<Lancamento> getListarTotaldespesa() {
		return listarTotaldespesa;
	}

	public void setListarTotaldespesa(List<Lancamento> listarTotaldespesa) {
		this.listarTotaldespesa = listarTotaldespesa;
	}

}
