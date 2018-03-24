package com.java.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.AgendaDAO;
import com.java.modelo.Agenda;
import com.java.util.NegocioException;

public class AgendaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AgendaDAO agendaDAO;

	public Agenda retornarAgendaPorID(Long id) throws NegocioException {
		return agendaDAO.retornarAgendaPorID(id);
	}

	public List<Agenda> listarTodos() {

		return agendaDAO.listarTodos();
	}

	public List<Agenda> listarTodosIDagenda(Long id) {
		return agendaDAO.listarTodosIDagenda(id);
	}

	public List<Agenda> listarPrevisao() {
		return agendaDAO.listarPrevisao();
	}
	
	public int listarTotalAlunos(Long id) {
		return agendaDAO.listarTodosIDagenda(id).size();
	}
	
	public List<Agenda> listarTodosPorEmpresa(Long id) {
		return agendaDAO.listarTodosPorEmpresa(id);
	}

	public void salvar(Agenda agenda) throws NegocioException {
		
			this.agendaDAO.salvar(agenda);
			
		

	}

	public void excluir(Agenda agenda) throws NegocioException {

		if (agenda == null) {
			throw new NegocioException("Ação deve ser selecionada.");
		} else if (agenda.getId() <= 0) {
			throw new NegocioException("Ação deve ser selecionada.");
		}

		this.agendaDAO.excluir(agenda);
	}

}
