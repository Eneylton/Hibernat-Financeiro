package com.java.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.java.modelo.EstadoCivil;
import com.java.util.NegocioException;
import com.java.util.jpa.Transactional;

public class EstadoCivilDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public EstadoCivil retornarEstadoCivilPorID(Long id) {

		@SuppressWarnings("unchecked")
		List<EstadoCivil> listarEstadoCivils = (List<EstadoCivil>) em
				.createQuery("from EstadoCivil e " + "where e.id = :paramId ").setParameter("paramId", id)
				.getResultList();

		if (listarEstadoCivils != null) {
			return listarEstadoCivils.get(0);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public List<EstadoCivil> listarTodos() {
		return em.createQuery("from EstadoCivil e ").getResultList();
	}

	@Transactional
	public void salvar(EstadoCivil estadoCivil) {
		em.merge(estadoCivil);
	}

	@Transactional
	public void excluir(EstadoCivil estadoCivil) throws NegocioException {
		EstadoCivil excluir = em.find(EstadoCivil.class, estadoCivil.getId());
		em.remove(excluir);
		em.flush();
	}

}
