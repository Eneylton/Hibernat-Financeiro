package com.java.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.java.modelo.Turma;
import com.java.util.NegocioException;
import com.java.util.jpa.Transactional;

public class TurmaDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public Turma retornarTurmaPorID(Long id) {

		@SuppressWarnings("unchecked")
		List<Turma> requerentes = (List<Turma>) em
				.createQuery("from Turma t " + "inner join fetch t.professor " + "inner join fetch t.alunos "
						+ "inner join fetch t.empresa " + "where t.id = :paramId ")
				.setParameter("paramId", id).getResultList();

		if (requerentes != null) {
			return requerentes.get(0);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public List<Turma> listarTodos(Long id) {
		return em.createQuery("from Turma t WHERE empresa_id = :paramID ").setParameter("paramID", id).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Turma> buscarTodos() {
		return em.createNamedQuery("Turma.buscarTodos").getResultList();
	}

	public Turma buscarTurmaComAlunos(Long id) {
		return em.createNamedQuery("Turma.buscarTurmaComAlunos", Turma.class).setParameter("paramID", id)
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Turma> listarPresencaoAluno(Long id) {
		return em.createNativeQuery("SELECT * from turma as t WHERE t.id = :paramID").setParameter("paramID", id)
				.getResultList();
	}

	@Transactional
	public void salvar(Turma turma) {
		em.merge(turma);
	}

	@Transactional
	public void excluir(Turma turma) throws NegocioException {
		Turma excluir = em.find(Turma.class, turma.getId());
		em.remove(excluir);
		em.flush();
	}

}
