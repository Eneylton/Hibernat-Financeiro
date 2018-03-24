package com.java.controller.estatistica;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.PieChartModel;

import com.java.modelo.EstatisticaTipo;
import com.java.service.EstatisticaService;
import com.java.util.Session;

@Named
@ViewScoped
public class EstatisticaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EstatisticaService estatisticaService;

	private List<Object[]> listarTipo = new ArrayList<>();

	private List<EstatisticaTipo> listarEstatistica = new ArrayList<>();

	private PieChartModel pieModelTipo;

	private EstatisticaTipo tipo;

	@PostConstruct
	public void init() throws SQLException {

		limpar();
		consultar();
		createPieModelTipo();

	}

	public void consultar() {

		Long idEmpresa = Session.retornaIdEmpresa();

		listarTipo = estatisticaService.graficoTipo(idEmpresa);

		for (Object[] tipo : listarTipo) {

			EstatisticaTipo esTipo = new EstatisticaTipo();

			String label = tipo[0].toString();
			String resultado = String.valueOf(tipo[1]);

			esTipo.setTipo(label);
			esTipo.setTotal(Integer.parseInt(resultado));
			
			listarEstatistica.add(esTipo);

		}
	}
	
	private void createPieModelTipo() throws SQLException {
		pieModelTipo = new PieChartModel();

		for (EstatisticaTipo tipo : listarEstatistica) {

			pieModelTipo.set(tipo.getTipo() + " Total (" + tipo.getTotal() + ")", tipo.getTotal());

		}

		pieModelTipo.setLegendPosition("nw");
		pieModelTipo.setTitle("Total de Lançamento");
	
		pieModelTipo.setFill(true);

		pieModelTipo.setShowDataLabels(true);
		pieModelTipo.setDiameter(150);
		pieModelTipo.setMouseoverHighlight(true);
		pieModelTipo.setShadow(true);
		pieModelTipo.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		pieModelTipo.setSeriesColors("BFF9CB,3FD5C0");

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

	public PieChartModel getPieModelTipo() {
		return pieModelTipo;
	}

	public void setPieModelTipo(PieChartModel pieModelTipo) {
		this.pieModelTipo = pieModelTipo;
	}

	public List<EstatisticaTipo> getListarEstatistica() {
		return listarEstatistica;
	}

	public void setListarEstatistica(List<EstatisticaTipo> listarEstatistica) {
		this.listarEstatistica = listarEstatistica;
	}

}
