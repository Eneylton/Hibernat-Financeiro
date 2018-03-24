package com.java.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.EstatisticaDAO;

public class EstatisticaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EstatisticaDAO estatisticaDAO;

	public List<Object[]> graficoTipo(Long id) {
		return estatisticaDAO.graficoTipo(id);
	}
	
	public List<Object[]> graficoTipodiaReceita(Long id) {
		return estatisticaDAO.graficoTipodiaReceita(id);
	}
	
	public List<Object[]> graficoTipodiaDespesa(Long id) {
		return estatisticaDAO.graficoTipodiaDespesa(id);
	}
	
	public List<Object[]> graficoDia(Long id) {
		return estatisticaDAO.graficoDia(id);
	}
	
	public List<Object[]> graficoLancamentoMensalReceita(Long id) {
		return estatisticaDAO.graficoLancamentoMensalReceita(id);
	}

	public List<Object[]> graficoLancamentoMensalDespesa(Long id) {
		return estatisticaDAO.graficoLancamentoMensalDespesa(id);
	}
	
	public List<Object[]> graficoCategoria(Long id) {
		return estatisticaDAO.graficoCategoria(id);
	}
	
	public List<Object[]> graficoServicos(Long id) {
		return estatisticaDAO.graficoServicos(id);
	}
	
	public List<Object[]> graficoFrmPagamento(Long id) {
		return estatisticaDAO.graficoFrmPagamento(id);
	}
	
	public List<Object[]> graficoCivil(Long id) {
		return estatisticaDAO.graficoCivil(id);
	}
	
	public List<Object[]> graficoMasculino(Long id) {
		return estatisticaDAO.graficoMasculino(id);
	}
	
	public List<Object[]> graficoFeminino(Long id) {
		return estatisticaDAO.graficoFeminino(id);
	}
	
	public List<Object[]> graficoUsuario(Long id) {
		return estatisticaDAO.graficoUsuario(id);
	}
	
	public List<Object[]> crossTab(Long id) {
		return estatisticaDAO.crossTab(id);

	}
	
	public List<Object[]> crossTabServicos(Long id) {
		return estatisticaDAO.crossTabServicos(id);
	}
}
