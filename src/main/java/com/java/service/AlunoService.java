package com.java.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.AlunoDAO;
import com.java.modelo.Aluno;
import com.java.util.NegocioException;

public class AlunoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AlunoDAO alunoDAO;

	public Aluno retornarAlunoPorID(Long id) throws NegocioException {
		return alunoDAO.retornarAlunoPorID(id);
	}

	public List<Aluno> listarTodos() {

		return alunoDAO.listarTodos();
	}

	public List<Aluno> listarTodosPorUsuario(Long idUsuario) {
		return alunoDAO.listarTodosPorUsuario(idUsuario);
	}

	public List<Aluno> listarTodosPorEmpresa(Long idEmpresa) {
		return alunoDAO.listarTodosPorEmpresa(idEmpresa);
	}
	
	public List<Aluno> listarTodosPorEmpresaUsuario(Long idEmpresa,Long idUsuario) {
		return alunoDAO.listarTodosPorEmpresaUsuario(idEmpresa, idUsuario);
	}
	
	public List<Aluno> listarTodosAlunosEmpresa(Long idEmpresa) {
		return alunoDAO.listarTodosAlunosEmpresa(idEmpresa);
	}

	public void salvar(Aluno aluno) throws NegocioException {

		if (aluno.getNome() == null || aluno.getNome().trim().equals("")) {
			throw new NegocioException("Descrição do Ação deve ser informada.");
		}

		this.alunoDAO.salvar(aluno);

	}

	public void excluir(Aluno aluno) throws NegocioException {

		if (aluno == null) {
			throw new NegocioException("Ação deve ser selecionada.");
		} else if (aluno.getId() <= 0) {
			throw new NegocioException("Ação deve ser selecionada.");
		}

		this.alunoDAO.excluir(aluno);
	}

}
