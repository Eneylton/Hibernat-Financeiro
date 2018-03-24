package com.java.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.TurmaDAO;
import com.java.modelo.Turma;
import com.java.util.NegocioException;

public class TurmaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TurmaDAO turmaDAO;

	public Turma retornarTurmaPorID(Long id) throws NegocioException {
		return turmaDAO.retornarTurmaPorID(id);
	}

	public List<Turma> listarTodos(Long id) {

		return turmaDAO.listarTodos(id);
	}

	public Turma buscarTurmaComAlunos(Long id) {
		return turmaDAO.buscarTurmaComAlunos(id);
	}
	
	public List<Turma> listarPresencaoAluno(Long id) {
		return turmaDAO.listarPresencaoAluno(id);
	}
	
	
	
	public void salvar(Turma turma) throws NegocioException {

		if (turma.getDescricao() == null || turma.getDescricao().trim().equals("")) {
			throw new NegocioException("Descrição do Ação deve ser informada.");
		}

		this.turmaDAO.salvar(turma);

	}

	public void excluir(Turma turma) throws NegocioException {

		if (turma == null) {
			throw new NegocioException("Ação deve ser selecionada.");
		} else if (turma.getId() <= 0) {
			throw new NegocioException("Ação deve ser selecionada.");
		}

		this.turmaDAO.excluir(turma);
	}

}
