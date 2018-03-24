package com.java.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.java.modelo.FormaPagamento;
import com.java.util.NegocioException;
import com.java.util.jpa.Transactional;

public class FormaPagamentoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public FormaPagamento retornarFormaPagamentoPorID(Long id) {

		@SuppressWarnings("unchecked")
		List<FormaPagamento> listarFormaPagamentos = (List<FormaPagamento>) em.createQuery("from FormaPagamento a " + "where a.id = :paramId ")
				.setParameter("paramId", id).getResultList();

		if (listarFormaPagamentos != null) {
			return listarFormaPagamentos.get(0);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public List<FormaPagamento> listarTodos() {
		return em.createQuery("from FormaPagamento s ").getResultList();
	}

	@Transactional
	public void salvar(FormaPagamento formaPagamento) {
		em.merge(formaPagamento);
	}

	@Transactional
	public void excluir(FormaPagamento formaPagamento) throws NegocioException {
		FormaPagamento excluir = em.find(FormaPagamento.class, formaPagamento.getId());
		em.remove(excluir);
		em.flush();
	}

}
