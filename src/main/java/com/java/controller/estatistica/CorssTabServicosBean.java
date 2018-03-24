package com.java.controller.estatistica;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.java.modelo.CrossTab;
import com.java.modelo.CrossTabServicos;
import com.java.service.EstatisticaService;
import com.java.util.NegocioException;
import com.java.util.Session;

@Named
@ViewScoped
public class CorssTabServicosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EstatisticaService estatisticaService;

	private List<Object[]> listarCrossTabServicos = new ArrayList<>();

	private List<CrossTabServicos> listarTotalCrossTabServicos = new ArrayList<>();

	private CrossTab cross;

	@PostConstruct
	public void init() throws SQLException, NumberFormatException, NegocioException {

		consultar();
		limpar();

	}

	public void consultar() throws NumberFormatException, NegocioException {

		Long idEmpresa = Session.retornaIdEmpresa();

		listarCrossTabServicos = estatisticaService.crossTabServicos(idEmpresa);

		for (Object[] cross : listarCrossTabServicos) {

			CrossTabServicos crossTab = new CrossTabServicos();

			String idServicos = cross[1].toString();
			String janeiro = String.valueOf(cross[2]);
			String fevereiro = String.valueOf(cross[3]);
			String marco = String.valueOf(cross[4]);
			String abril = String.valueOf(cross[5]);
			String maio = String.valueOf(cross[6]);
			String junho = String.valueOf(cross[7]);
			String julho = String.valueOf(cross[8]);
			String agosto = String.valueOf(cross[9]);
			String setembro = String.valueOf(cross[10]);
			String outubro = String.valueOf(cross[11]);
			String novembro = String.valueOf(cross[12]);
			String dezembro = String.valueOf(cross[13]);
			String total = String.valueOf(cross[14]);

			crossTab.setServicos(idServicos);
			crossTab.setJaneiro(Double.parseDouble(janeiro));
			crossTab.setFevereiro(Double.parseDouble(fevereiro));
			crossTab.setMarco(Double.parseDouble(marco));
			crossTab.setAbril(Double.parseDouble(abril));
			crossTab.setMaio(Double.parseDouble(maio));
			crossTab.setJunho(Double.parseDouble(junho));
			crossTab.setJulho(Double.parseDouble(julho));
			crossTab.setAgosto(Double.parseDouble(agosto));
			crossTab.setSetembro(Double.parseDouble(setembro));
			crossTab.setOutubro(Double.parseDouble(outubro));
			crossTab.setNovembro(Double.parseDouble(novembro));
			crossTab.setDezembro(Double.parseDouble(dezembro));
			crossTab.setTotal(Double.parseDouble(total));

			listarTotalCrossTabServicos.add(crossTab);

		}

	}

	public void limpar() {
		this.cross = new CrossTab();
	}

	public List<Object[]> getListarCrossTabServicos() {
		return listarCrossTabServicos;
	}

	public void setListarCrossTabServicos(List<Object[]> listarCrossTabServicos) {
		this.listarCrossTabServicos = listarCrossTabServicos;
	}

	public List<CrossTabServicos> getListarTotalCrossTabServicos() {
		return listarTotalCrossTabServicos;
	}

	public void setListarTotalCrossTabServicos(List<CrossTabServicos> listarTotalCrossTabServicos) {
		this.listarTotalCrossTabServicos = listarTotalCrossTabServicos;
	}

	public CrossTab getCross() {
		return cross;
	}

	public void setCross(CrossTab cross) {
		this.cross = cross;
	}

}
