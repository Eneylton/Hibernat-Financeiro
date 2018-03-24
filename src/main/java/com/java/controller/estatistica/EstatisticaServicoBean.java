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

import com.java.modelo.EstatisticaServico;
import com.java.service.EstatisticaService;
import com.java.util.Session;

@Named
@ViewScoped
public class EstatisticaServicoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EstatisticaService estatisticaService;

	private List<Object[]> listarServico = new ArrayList<>();

	private List<EstatisticaServico> listarTotalservico = new ArrayList<>();

	private PieChartModel pieModelTipo;

	private EstatisticaServico servico;

	@PostConstruct
	public void init() throws SQLException {

		limpar();
		consultar();

	}

	public void consultar() throws SQLException {

		Long idEmpresa = Session.retornaIdEmpresa();

		listarServico = estatisticaService.graficoServicos(idEmpresa);

		for (Object[] tipo : listarServico) {

			EstatisticaServico servico = new EstatisticaServico();

			String label = tipo[0].toString();
			String resultado = String.valueOf(tipo[1]);

			servico.setServico(label);
			servico.setTotal(Integer.parseInt(resultado));

			listarTotalservico.add(servico);

			createPieModelTipo();

		}
	}

	private void createPieModelTipo() throws SQLException {
		pieModelTipo = new PieChartModel();

		for (EstatisticaServico serv : listarTotalservico) {

			pieModelTipo.set(serv.getServico() + " Total (" + serv.getTotal() + ")", serv.getTotal());

		}

		pieModelTipo.setLegendPosition("ne");
		pieModelTipo.setTitle("Serviços");

		pieModelTipo.setFill(true);

		pieModelTipo.setShowDataLabels(true);
		pieModelTipo.setDiameter(200);
		pieModelTipo.setMouseoverHighlight(true);
		pieModelTipo.setShadow(true);
		pieModelTipo.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		pieModelTipo.setSeriesColors("444e99,2a71b0,0696bb,008e5b,8cbb26,F4E500,fdc60b,f18e1c,e32322,c4037d,6d398b");

	}

	public void limpar() {
		this.servico = new EstatisticaServico();
	}

	public List<Object[]> getListarServico() {
		return listarServico;
	}

	public void setListarServico(List<Object[]> listarServico) {
		this.listarServico = listarServico;
	}

	public List<EstatisticaServico> getListarTotalservico() {
		return listarTotalservico;
	}

	public void setListarTotalservico(List<EstatisticaServico> listarTotalservico) {
		this.listarTotalservico = listarTotalservico;
	}

	public PieChartModel getPieModelTipo() {
		return pieModelTipo;
	}

	public void setPieModelTipo(PieChartModel pieModelTipo) {
		this.pieModelTipo = pieModelTipo;
	}

	public EstatisticaServico getServico() {
		return servico;
	}

	public void setServico(EstatisticaServico servico) {
		this.servico = servico;
	}

}
