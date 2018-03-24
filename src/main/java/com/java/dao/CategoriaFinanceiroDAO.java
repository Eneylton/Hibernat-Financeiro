package com.java.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.java.modelo.CategoriaFinanceiro;
import com.java.util.NegocioException;
import com.java.util.jpa.Transactional;

public class CategoriaFinanceiroDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public CategoriaFinanceiro retornarCategoriaFinanceiroPorID(Long id) {

		@SuppressWarnings("unchecked")
		List<CategoriaFinanceiro> listarCategoriaFinanceiros = (List<CategoriaFinanceiro>) em.createQuery("from CategoriaFinanceiro c " + "where c.id = :paramId ")
				.setParameter("paramId", id).getResultList();

		if (listarCategoriaFinanceiros != null) {
			return listarCategoriaFinanceiros.get(0);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public List<CategoriaFinanceiro> listarTodos() {
		return em.createQuery("from CategoriaFinanceiro c ").getResultList();
	}

	@Transactional
	public void salvar(CategoriaFinanceiro categoriaFinanceiro) {
		em.merge(categoriaFinanceiro);
	}

	@Transactional
	public void excluir(CategoriaFinanceiro categoriaFinanceiro) throws NegocioException {
		CategoriaFinanceiro excluir = em.find(CategoriaFinanceiro.class, categoriaFinanceiro.getId());
		em.remove(excluir);
		em.flush();
	}

}
