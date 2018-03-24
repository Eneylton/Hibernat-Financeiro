package com.java.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.java.modelo.Sexo;
import com.java.util.NegocioException;
import com.java.util.jpa.Transactional;

public class SexoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public Sexo retornarSexoPorID(Long id) {

		@SuppressWarnings("unchecked")
		List<Sexo> listarSexos = (List<Sexo>) em.createQuery("from Sexo a " + "where a.id = :paramId ")
				.setParameter("paramId", id).getResultList();

		if (listarSexos != null) {
			return listarSexos.get(0);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public List<Sexo> listarTodos() {
		return em.createQuery("from Sexo s ").getResultList();
	}

	@Transactional
	public void salvar(Sexo sexo) {
		em.merge(sexo);
	}

	@Transactional
	public void excluir(Sexo sexo) throws NegocioException {
		Sexo excluir = em.find(Sexo.class, sexo.getId());
		em.remove(excluir);
		em.flush();
	}

}
