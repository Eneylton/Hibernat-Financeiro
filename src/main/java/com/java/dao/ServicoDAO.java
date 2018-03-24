package com.java.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.java.modelo.Servico;
import com.java.util.NegocioException;
import com.java.util.jpa.Transactional;

public class ServicoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public Servico retornarServicoPorID(Long id) {

		@SuppressWarnings("unchecked")
		List<Servico> listarServicos = (List<Servico>) em.createQuery("from Servico s " + "where s.id = :paramId ")
				.setParameter("paramId", id).getResultList();

		if (listarServicos != null) {
			return listarServicos.get(0);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public List<Servico> listarTodos() {
		return em.createQuery("from Servico s ").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Servico> listarTodosPorEmpresa(Long id) {
		return em.createQuery("select s from Servico s " + "inner join fetch s.empresa "
						+ "where empresa_id = :paramID ORDER BY s.id desc")
				.setParameter("paramID", id).getResultList();
	}

	@Transactional
	public void salvar(Servico servico) {
		em.merge(servico);
	}
	
	

	@Transactional
	public void excluir(Servico servico) throws NegocioException {
		Servico excluir = em.find(Servico.class, servico.getId());
		em.remove(excluir);
		em.flush();
	}

}
