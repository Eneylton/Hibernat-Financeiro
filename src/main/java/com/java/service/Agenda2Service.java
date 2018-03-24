package com.java.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.Agenda2DAO;
import com.java.modelo.Agenda2;
import com.java.util.NegocioException;

public class Agenda2Service implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Agenda2DAO agenda2DAO;

	public Agenda2 retornarAgenda2PorID(Long id) throws NegocioException {
		return agenda2DAO.retornarAgenda2PorID(id);
	}

	public List<Agenda2> listarTodos() {

		return agenda2DAO.listarTodos();
	}
	
	public List<Agenda2> listarPrevisao() {
		return agenda2DAO.listarPrevisao();
	}
	
	public List<Agenda2> listarTodosIDAluno(Long id) {
		return agenda2DAO.listarTodosIDAluno(id);
	}
	
	public List<Agenda2> listarTodosPorEmpresa(Long id) {
		return agenda2DAO.listarTodosPorEmpresa(id);
	}

	public void salvar(Agenda2 agenda) throws NegocioException {

		this.agenda2DAO.salvar(agenda);

	}

	public void excluir(Agenda2 agenda) throws NegocioException {

		if (agenda == null) {
			throw new NegocioException("Ação deve ser selecionada.");
		} else if (agenda.getId() <= 0) {
			throw new NegocioException("Ação deve ser selecionada.");
		}

		this.agenda2DAO.excluir(agenda);
	}

}
