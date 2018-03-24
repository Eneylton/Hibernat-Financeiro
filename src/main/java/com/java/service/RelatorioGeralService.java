package com.java.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.RelatorioGeralDAO;
import com.java.modelo.Financeiro;

public class RelatorioGeralService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	
	private RelatorioGeralDAO relatorioGeralDAO;
	
	public List<Financeiro> listarPrevisao(Long id) {
		return relatorioGeralDAO.listarPrevisao(id);
		
	}
	
	
	public List<Financeiro> listarConsultaGeral(Long idEmp, boolean idDestino,boolean idStatus,boolean idTipo,Date dataInicio,Date dataFim) {
		return relatorioGeralDAO.listarConsultaGeral(idEmp, idDestino, idStatus, idTipo, dataInicio, dataFim);
		
	}
	
	public double totalReceita(Long idEmp, boolean idDestino,boolean idStatus,Date dataInicio,Date dataFim) {
		return relatorioGeralDAO.totalReceita(idEmp, idDestino, idStatus, dataInicio, dataFim);
	}
	
	
	public double totalDespesa(Long idEmp, boolean idDestino,boolean idStatus,Date dataInicio,Date dataFim) {
		return relatorioGeralDAO.totalDespesa(idEmp, idDestino, idStatus, dataInicio,dataFim);
	}


	public List<Financeiro> listarRelatorioGeral(Long id, boolean idDestino,boolean idStatus,boolean idTipo,Date dataInicio,Date dataFim) {
		return relatorioGeralDAO.listarRelatorioGeral(id, idDestino, idStatus, idTipo, dataInicio, dataFim);
	}
}
