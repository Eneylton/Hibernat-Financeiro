package com.java.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.java.modelo.Professor;
import com.java.util.NegocioException;
import com.java.util.jpa.Transactional;

public class ProfessorDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public Professor retornarProfessorPorID(Long id) {

		@SuppressWarnings("unchecked")
		List<Professor> listarProfessors = (List<Professor>) em.createQuery("from Professor a " + "where a.id = :paramId ")
				.setParameter("paramId", id).getResultList();

		if (listarProfessors != null) {
			return listarProfessors.get(0);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public List<Professor> listarTodos() {
		return em.createQuery("from Professor s ").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Professor> listarTodosPorEmpresa(Long id) {
		return em.createQuery("select f from Professor f " + "inner join fetch f.empresa "
						+ "where empresa_id = :paramID ORDER BY f.id desc")
				.setParameter("paramID", id).getResultList();
	}

	@Transactional
	public void salvar(Professor professor) {
		em.merge(professor);
	}

	@Transactional
	public void excluir(Professor professor) throws NegocioException {
		Professor excluir = em.find(Professor.class, professor.getId());
		em.remove(excluir);
		em.flush();
	}

}
