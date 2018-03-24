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

import com.java.modelo.EstatisticaSexo;
import com.java.modelo.EstatisticaTipoDia;
import com.java.service.EstatisticaService;
import com.java.util.Session;

@Named
@ViewScoped
public class EstatisticaSexoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EstatisticaService estatisticaService;

	private List<Object[]> listarMasculino = new ArrayList<>();

	private List<EstatisticaSexo> listarTotalMasculino = new ArrayList<>();

	private List<Object[]> listarFeminino = new ArrayList<>();

	private List<EstatisticaSexo> listarTotalFeminino = new ArrayList<>();

	private List<Object[]> listarDia = new ArrayList<>();

	private List<EstatisticaTipoDia> listarDiaAtual = new ArrayList<>();

	private BarChartModel graficoBarra;

	private EstatisticaSexo sexo;

	@PostConstruct
	public void init() throws SQLException {

		limpar();
		consultar();
		
		createAnimatedModels();

	}

	public void consultar() throws SQLException {

		Long idEmpresa = Session.retornaIdEmpresa();

		listarMasculino = estatisticaService.graficoMasculino(idEmpresa);
	
		listarFeminino = estatisticaService.graficoFeminino(idEmpresa);
	
		listarDia = estatisticaService.graficoDia(idEmpresa);

		for (Object[] masculino : listarMasculino) {

			EstatisticaSexo masc = new EstatisticaSexo();

			String label = masculino[0].toString();
			String resultado = String.valueOf(masculino[1]);

			masc.setSexo(label);
			masc.setTotal(Integer.parseInt(resultado));

			listarTotalMasculino.add(masc);

		}

		for (Object[] femnino : listarFeminino) {

			EstatisticaSexo femnin = new EstatisticaSexo();

			String label = femnino[0].toString();
			String resultado = String.valueOf(femnino[1]);

			femnin.setSexo(label);
			femnin.setTotal(Integer.parseInt(resultado));

			listarTotalFeminino.add(femnin);

		}

		for (Object[] dia : listarDia) {

			EstatisticaTipoDia esTipo = new EstatisticaTipoDia();

			String label = dia[0].toString();

			esTipo.setDia(label);
			listarDiaAtual.add(esTipo);

		}
		
		

	}

	private void createAnimatedModels() throws SQLException {

		graficoBarra = initBarModel();
		graficoBarra.setAnimate(true);
		graficoBarra.setShowPointLabels(true);
		graficoBarra.setLegendPosition("ne");
		graficoBarra.setZoom(true);
		graficoBarra.setMouseoverHighlight(false);
		graficoBarra.setShowDatatip(false);
		graficoBarra.setShowPointLabels(true);
		

		Axis yAxis = graficoBarra.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(15);
		yAxis.setTickFormat("%d");

		Axis xAxis = graficoBarra.getAxis(AxisType.X);
		xAxis.setMin(1);
		xAxis.setMax(31);
		xAxis.setTickCount(31);
		xAxis.setTickAngle(-30);
		xAxis.setTickFormat("%d");
		graficoBarra.setTitle("Quantidade de Alunos Matriculados por Sexo");
		graficoBarra.setStacked(true); 
		graficoBarra.setExtender("chartExtender");
		graficoBarra.setSeriesColors("ffeb3b,00bcd4");

	}

	private BarChartModel initBarModel() throws SQLException {
		BarChartModel model = new BarChartModel();
		ChartSeries masculino = new ChartSeries();
		ChartSeries femnino = new ChartSeries();

		for (EstatisticaSexo sexoM : listarTotalMasculino) {
			masculino.setLabel("Masculino");
			masculino.set(sexoM.getSexo(), sexoM.getTotal());

		}

		for (EstatisticaSexo sexoF : listarTotalFeminino) {
			femnino.setLabel("Feminino");
			femnino.set(sexoF.getSexo(), sexoF.getTotal());

		}

		model.addSeries(masculino);
		model.addSeries(femnino);

		return model;

	}

	public void limpar() {
		this.sexo = new EstatisticaSexo();
	}

	public List<Object[]> getListarMasculino() {
		return listarMasculino;
	}

	public void setListarMasculino(List<Object[]> listarMasculino) {
		this.listarMasculino = listarMasculino;
	}

	public List<EstatisticaSexo> getListarTotalMasculino() {
		return listarTotalMasculino;
	}

	public void setListarTotalMasculino(List<EstatisticaSexo> listarTotalMasculino) {
		this.listarTotalMasculino = listarTotalMasculino;
	}

	public List<Object[]> getListarFeminino() {
		return listarFeminino;
	}

	public void setListarFeminino(List<Object[]> listarFeminino) {
		this.listarFeminino = listarFeminino;
	}

	public List<EstatisticaSexo> getListarTotalFeminino() {
		return listarTotalFeminino;
	}

	public void setListarTotalFeminino(List<EstatisticaSexo> listarTotalFeminino) {
		this.listarTotalFeminino = listarTotalFeminino;
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

	public EstatisticaSexo getSexo() {
		return sexo;
	}

	public void setSexo(EstatisticaSexo sexo) {
		this.sexo = sexo;
	}

	public BarChartModel getGraficoBarra() {
		return graficoBarra;
	}

	public void setGraficoBarra(BarChartModel graficoBarra) {
		this.graficoBarra = graficoBarra;
	}

}
