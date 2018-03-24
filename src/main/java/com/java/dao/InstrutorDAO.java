package com.java.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.java.modelo.Instrutor;
import com.java.util.NegocioException;
import com.java.util.jpa.Transactional;

public class InstrutorDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public Instrutor retornarInstrutorPorID(Long id) {

		@SuppressWarnings("unchecked")
		List<Instrutor> listarInstrutores = (List<Instrutor>) em.createQuery("from Instrutor i where i.id = :paramId ")
				.setParameter("paramId", id).getResultList();

		if (listarInstrutores != null) {
			return listarInstrutores.get(0);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public List<Instrutor> listarTodos() {
		return em.createQuery("from Instrutor i ").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Instrutor> listarTodosEmpresa(Long id) {
		return em
				.createQuery("select it from Instrutor it " 
		                + "inner join fetch it.empresa "
						+ "where empresa_id = :paramID ORDER BY it.id desc")
				.setParameter("paramID", id).getResultList();
	}


	@Transactional
	public void salvar(Instrutor instrutor) {
		em.merge(instrutor);
	}

	@Transactional
	public void alterar(Instrutor instrutor) {
		em.merge(instrutor);
	}

	@Transactional
	public void excluir(Instrutor instrutor) throws NegocioException {
		Instrutor excluir = em.find(Instrutor.class, instrutor.getId());
		em.remove(excluir);
		em.flush();
	}

}
