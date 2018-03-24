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

import com.java.modelo.EstatisticaCivil;
import com.java.service.EstatisticaService;
import com.java.util.Session;

@Named
@ViewScoped
public class EstatisticaCivilBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EstatisticaService estatisticaService;

	private List<Object[]> listarCivil = new ArrayList<>();

	private List<EstatisticaCivil> listarTotalCivil = new ArrayList<>();

	private DonutChartModel donutModel;

	private EstatisticaCivil civil;

	@PostConstruct
	public void init() throws SQLException {

		limpar();
		consultar();

	}

	public void consultar() throws SQLException {

		Long idEmpresa = Session.retornaIdEmpresa();

		listarCivil = estatisticaService.graficoCivil(idEmpresa);

		for (Object[] tipo : listarCivil) {

			EstatisticaCivil frmPag = new EstatisticaCivil();

			String label = tipo[0].toString();
			String resultado = String.valueOf(tipo[1]);

			frmPag.setCivil(label);
			frmPag.setTotal(Integer.parseInt(resultado));

			listarTotalCivil.add(frmPag);

			createDonutModels();

		}
	}

	private void createDonutModels() {
		donutModel = initDonutModel();
		donutModel.setLegendPosition("nw");
		donutModel = initDonutModel();
		donutModel.setTitle("Estado civil");
		donutModel.setLegendPosition("nw");
		donutModel.setSliceMargin(5);
		donutModel.setShowDataLabels(true);
		donutModel.setDataFormat("value");
		donutModel.setShadow(false);
		donutModel.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		donutModel.setSeriesColors("6d398b,444e99,2a71b0,0696bb,F4E500,fdc60b,f18e1c,e32322,c4037d,008e5b,8cbb26");
	}

	private DonutChartModel initDonutModel() {
		DonutChartModel model = new DonutChartModel();

		Map<String, Number> circle1 = new LinkedHashMap<String, Number>();
		for (Object[] cat : listarCivil) {

			String label = cat[0].toString();
			Number resultado = (Number) cat[1];

			circle1.put(label, resultado);

		}

		model.addCircle(circle1);

		return model;
	}

	public void limpar() {
		this.civil = new EstatisticaCivil();
	}

	public List<Object[]> getListarCivil() {
		return listarCivil;
	}

	public void setListarCivil(List<Object[]> listarCivil) {
		this.listarCivil = listarCivil;
	}

	public List<EstatisticaCivil> getListarTotalCivil() {
		return listarTotalCivil;
	}

	public void setListarTotalCivil(List<EstatisticaCivil> listarTotalCivil) {
		this.listarTotalCivil = listarTotalCivil;
	}

	public DonutChartModel getDonutModel() {
		return donutModel;
	}

	public void setDonutModel(DonutChartModel donutModel) {
		this.donutModel = donutModel;
	}

	public EstatisticaCivil getCivil() {
		return civil;
	}

	public void setCivil(EstatisticaCivil civil) {
		this.civil = civil;
	}

}
