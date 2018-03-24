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

import com.java.modelo.EstatisticaCategoria;
import com.java.service.EstatisticaService;
import com.java.util.Session;

@Named
@ViewScoped
public class EstatisticaCategoriaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EstatisticaService estatisticaService;

	private List<Object[]> listarCategoria = new ArrayList<>();

	private List<EstatisticaCategoria> listarTotalCategoria = new ArrayList<>();

	private PieChartModel pieModelTipo;

	private EstatisticaCategoria categoria;

	@PostConstruct
	public void init() throws SQLException {

		limpar();
		consultar();
		

	}

	public void consultar() throws SQLException {

		Long idEmpresa = Session.retornaIdEmpresa();

		listarCategoria = estatisticaService.graficoCategoria(idEmpresa);

		for (Object[] tipo : listarCategoria) {

			EstatisticaCategoria categoria = new EstatisticaCategoria();

			String label = tipo[0].toString();
			String resultado = String.valueOf(tipo[1]);

			categoria.setCategoria(label);
			categoria.setTotal(Integer.parseInt(resultado));

			listarTotalCategoria.add(categoria);
			
			createPieModelTipo();

		}
	}

	private void createPieModelTipo() throws SQLException {
		pieModelTipo = new PieChartModel();

		for (EstatisticaCategoria cat : listarTotalCategoria) {

			pieModelTipo.set(cat.getCategoria() + " Total (" + cat.getTotal() + ")", cat.getTotal());

		}

		pieModelTipo.setLegendPosition("nw");
		pieModelTipo.setTitle("Categorias");

		pieModelTipo.setFill(true);

		pieModelTipo.setShowDataLabels(true);
		pieModelTipo.setDiameter(220);
		pieModelTipo.setMouseoverHighlight(true);
		pieModelTipo.setShadow(true);
		pieModelTipo.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		pieModelTipo.setSeriesColors("F4E500,fdc60b,f18e1c,e32322,c4037d,6d398b,444e99,2a71b0,0696bb,008e5b,8cbb26");

	}

	public void limpar() {
		this.categoria = new EstatisticaCategoria();
	}

	public List<Object[]> getListarCategoria() {
		return listarCategoria;
	}

	public void setListarCategoria(List<Object[]> listarCategoria) {
		this.listarCategoria = listarCategoria;
	}

	public List<EstatisticaCategoria> getListarTotalCategoria() {
		return listarTotalCategoria;
	}

	public void setListarTotalCategoria(List<EstatisticaCategoria> listarTotalCategoria) {
		this.listarTotalCategoria = listarTotalCategoria;
	}

	public PieChartModel getPieModelTipo() {
		return pieModelTipo;
	}

	public void setPieModelTipo(PieChartModel pieModelTipo) {
		this.pieModelTipo = pieModelTipo;
	}

	public EstatisticaCategoria getCategoria() {
		return categoria;
	}

	public void setCategoria(EstatisticaCategoria categoria) {
		this.categoria = categoria;
	}

}
