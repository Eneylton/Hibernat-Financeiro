package com.java.controller.estatistica;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.DonutChartModel;
import org.primefaces.model.chart.LegendPlacement;

import com.java.modelo.EstatisticaFrmPagamento;
import com.java.service.EstatisticaService;
import com.java.util.Session;

@Named
@ViewScoped
public class EstatisticaFrmPagamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EstatisticaService estatisticaService;

	private List<Object[]> listarFrmPagamento = new ArrayList<>();

	private List<EstatisticaFrmPagamento> listarTotalPagamento = new ArrayList<>();

	private DonutChartModel donutModel;

	private EstatisticaFrmPagamento frmPagamento;

	@PostConstruct
	public void init() throws SQLException {

		limpar();
		consultar();

	}

	public void consultar() throws SQLException {

		Long idEmpresa = Session.retornaIdEmpresa();

		listarFrmPagamento = estatisticaService.graficoFrmPagamento(idEmpresa);

		for (Object[] tipo : listarFrmPagamento ) {

			EstatisticaFrmPagamento frmPag = new EstatisticaFrmPagamento();

			String label = tipo[0].toString();
			String resultado = String.valueOf(tipo[1]);

			frmPag.setPagamento(label);
			frmPag.setTotal(Integer.parseInt(resultado));

			listarTotalPagamento.add(frmPag);

			createDonutModels();

		}
	}

	private void createDonutModels() {
		donutModel = initDonutModel();
		donutModel.setLegendPosition("nw");

		donutModel = initDonutModel();
		donutModel.setTitle("Formas de Pagamento");
		donutModel.setLegendPosition("nw");
		donutModel.setSliceMargin(5);
		donutModel.setShowDataLabels(true);
		donutModel.setDataFormat("value");
		donutModel.setShadow(false);
		donutModel.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		donutModel.setSeriesColors("F4E500,fdc60b,f18e1c,e32322,c4037d,6d398b,444e99,2a71b0,0696bb,008e5b,8cbb26");
	}

	private DonutChartModel initDonutModel() {
		DonutChartModel model = new DonutChartModel();

		Map<String, Number> circle1 = new LinkedHashMap<String, Number>();
		for (Object[] cat : listarFrmPagamento) {

			String label = cat[0].toString();
			Number resultado = (Number) cat[1];

			circle1.put(label, resultado);

		}

		model.addCircle(circle1);

		return model;
	}

	public void limpar() {
		this.frmPagamento = new EstatisticaFrmPagamento();
	}

	public List<Object[]> getListarFrmPagamento() {
		return listarFrmPagamento;
	}

	public void setListarFrmPagamento(List<Object[]> listarFrmPagamento) {
		this.listarFrmPagamento = listarFrmPagamento;
	}

	public List<EstatisticaFrmPagamento> getListarTotalPagamento() {
		return listarTotalPagamento;
	}

	public void setListarTotalPagamento(List<EstatisticaFrmPagamento> listarTotalPagamento) {
		this.listarTotalPagamento = listarTotalPagamento;
	}

	public DonutChartModel getDonutModel() {
		return donutModel;
	}

	public void setDonutModel(DonutChartModel donutModel) {
		this.donutModel = donutModel;
	}

	public EstatisticaFrmPagamento getFrmPagamento() {
		return frmPagamento;
	}

	public void setFrmPagamento(EstatisticaFrmPagamento frmPagamento) {
		this.frmPagamento = frmPagamento;
	}

}
