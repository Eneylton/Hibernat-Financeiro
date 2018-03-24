package com.java.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.ProfessorDAO;
import com.java.modelo.Professor;
import com.java.util.NegocioException;

public class ProfessorService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProfessorDAO professorDAO;

	public Professor retornarProfessorPorID(Long id) throws NegocioException {
		return professorDAO.retornarProfessorPorID(id);
	}

	public List<Professor> listarTodos() {

		return professorDAO.listarTodos();
	}
	
	public List<Professor> listarTodosPorEmpresa(Long id) {
		return professorDAO.listarTodosPorEmpresa(id);
	}

	public void salvar(Professor professor) throws NegocioException {

		if (professor.getNome() == null || professor.getNome().trim().equals("")) {
			throw new NegocioException("Descrição do Ação deve ser informada.");
		}

		this.professorDAO.salvar(professor);

	}

	public void excluir(Professor professor) throws NegocioException {

		if (professor == null) {
			throw new NegocioException("Ação deve ser selecionada.");
		} else if (professor.getId() <= 0) {
			throw new NegocioException("Ação deve ser selecionada.");
		}

		this.professorDAO.excluir(professor);
	}

}
