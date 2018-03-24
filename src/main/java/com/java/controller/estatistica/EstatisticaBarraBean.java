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
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import com.java.modelo.EstatisticaTipo;
import com.java.modelo.EstatisticaTipoDia;
import com.java.service.EstatisticaService;
import com.java.util.Session;

@Named
@ViewScoped
public class EstatisticaBarraBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EstatisticaService estatisticaService;

	private List<Object[]> listarTipo = new ArrayList<>();

	private List<EstatisticaTipoDia> listarEstatistica = new ArrayList<>();

	private List<Object[]> listarTipoDespesa = new ArrayList<>();

	private List<EstatisticaTipoDia> listarEstatisticaDespesa = new ArrayList<>();
	
	private List<Object[]> listarDia = new ArrayList<>();

	private List<EstatisticaTipoDia> listarDiaAtual = new ArrayList<>();

	private LineChartModel graficoLinha;

	private EstatisticaTipo tipo;

	@PostConstruct
	public void init() throws SQLException {

		limpar();
		consultar();
		createAnimatedModels();

	}

	public void consultar() {

		Long idEmpresa = Session.retornaIdEmpresa();

		listarTipo = estatisticaService.graficoTipodiaReceita(idEmpresa);
		listarTipoDespesa = estatisticaService.graficoTipodiaDespesa(idEmpresa);
		listarDia = estatisticaService.graficoDia(idEmpresa);

		for (Object[] tipo : listarTipo) {

			EstatisticaTipoDia esTipo = new EstatisticaTipoDia();

			String label = tipo[0].toString();
			String resultado = String.valueOf(tipo[1]);

			esTipo.setDia(label);
			esTipo.setTotal(Integer.parseInt(resultado));

			listarEstatistica.add(esTipo);

		}
		
		for (Object[] tipoDespesa : listarTipoDespesa) {

			EstatisticaTipoDia esTipo = new EstatisticaTipoDia();

			String label = tipoDespesa[0].toString();
			String resultado = String.valueOf(tipoDespesa[1]);

			esTipo.setDia(label);
			esTipo.setTotal(Integer.parseInt(resultado));

			listarEstatisticaDespesa.add(esTipo);

		}
		
		
		for (Object[] dia : listarDia) {

			EstatisticaTipoDia esTipo = new EstatisticaTipoDia();

			String label = dia[0].toString();

			esTipo.setDia(label);
			listarDiaAtual.add(esTipo);

		}
		
	}

	private LineChartModel initLinearModel() throws SQLException {

		LineChartModel model = new LineChartModel();
		model.setShowPointLabels(true);

		BarChartSeries series1 = new BarChartSeries();
		LineChartSeries series2 = new LineChartSeries();

		for (EstatisticaTipoDia tipo : listarEstatistica) {

			series1.setLabel("Receita");

			series1.set(tipo.getDia(), tipo.getTotal());
			

		}
		
		for (EstatisticaTipoDia tipoDespesa : listarEstatisticaDespesa) {

			series2.setLabel("Despesa");

			series2.set(tipoDespesa.getDia(), tipoDespesa.getTotal());
			
			
			

		}

		model.addSeries(series1);
		model.addSeries(series2);
		return model;

	}

	private void createAnimatedModels() throws SQLException {

		graficoLinha = initLinearModel();

		graficoLinha.setAnimate(true);
		graficoLinha.setLegendPosition("nw");
		graficoLinha.setTitle("Lançamentos");
		graficoLinha.setShowPointLabels(true);
		graficoLinha.setLegendPosition("ne");
		graficoLinha.setZoom(true);
		graficoLinha.setMouseoverHighlight(false);
		graficoLinha.setShowDatatip(false);
		graficoLinha.setShowPointLabels(true);
		
		
		

		Axis yAxis = graficoLinha.getAxis(AxisType.Y);

		yAxis.setMin(0);
		yAxis.setMax(15);
	
		yAxis.setTickFormat("%d");
		
		for (EstatisticaTipoDia dia : listarDiaAtual) {
		

		Axis xAxis = graficoLinha.getAxis(AxisType.X);
		xAxis.setMin(1);
		xAxis.setMax(dia.getDia());
		xAxis.setTickCount(Integer.parseInt(dia.getDia()));
	
		xAxis.setTickFormat("%d");
		graficoLinha.setSeriesColors("c8e6ff,FF0000");
		
		}
	}

	public void limpar() {
		this.tipo = new EstatisticaTipo();
	}

	public List<Object[]> getListarTipo() {
		return listarTipo;
	}

	public void setListarTipo(List<Object[]> listarTipo) {
		this.listarTipo = listarTipo;
	}

	public EstatisticaTipo getTipo() {
		return tipo;
	}

	public void setTipo(EstatisticaTipo tipo) {
		this.tipo = tipo;
	}

	public List<EstatisticaTipoDia> getListarEstatistica() {
		return listarEstatistica;
	}

	public void setListarEstatistica(List<EstatisticaTipoDia> listarEstatistica) {
		this.listarEstatistica = listarEstatistica;
	}

	public LineChartModel getGraficoLinha() {
		return graficoLinha;
	}

	public void setGraficoLinha(LineChartModel graficoLinha) {
		this.graficoLinha = graficoLinha;
	}

	public List<Object[]> getListarTipoDespesa() {
		return listarTipoDespesa;
	}

	public void setListarTipoDespesa(List<Object[]> listarTipoDespesa) {
		this.listarTipoDespesa = listarTipoDespesa;
	}

	public List<EstatisticaTipoDia> getListarEstatisticaDespesa() {
		return listarEstatisticaDespesa;
	}

	public void setListarEstatisticaDespesa(List<EstatisticaTipoDia> listarEstatisticaDespesa) {
		this.listarEstatisticaDespesa = listarEstatisticaDespesa;
	}

	public List<Object[]> getListarDia() {
		return listarDia;
	}

	public void setListarDia(List<Object[]> listarDia) {
		this.listarDia = listarDia;
	}

	public List<EstatisticaTipoDia> getListarDiaAtual() {
		return listarDiaAtual;
	}

	public void setListarDiaAtual(List<EstatisticaTipoDia> listarDiaAtual) {
		this.listarDiaAtual = listarDiaAtual;
	}

	
}
