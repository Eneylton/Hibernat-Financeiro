package com.java.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.java.modelo.Veiculo;
import com.java.util.NegocioException;
import com.java.util.jpa.Transactional;

public class VeiculoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public Veiculo retornarVeiculoPorID(Long id) {

		@SuppressWarnings("unchecked")
		List<Veiculo> listarVeiculos = (List<Veiculo>) em
				.createQuery("from Veiculo v " + "inner join fetch v.instrutor " + "where v.id = :paramId ")
				.setParameter("paramId", id).getResultList();

		if (listarVeiculos != null) {
			return listarVeiculos.get(0);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public List<Veiculo> listarTodos() {
		return em.createQuery("from Veiculo v ").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Veiculo> listarTodosPorEmpresa(Long id) {
		return em.createQuery("select v from Veiculo v " + "inner join fetch v.empresa "
						+ "where empresa_id = :paramID ORDER BY v.id desc")
				.setParameter("paramID", id).getResultList();
	}

	@Transactional
	public void salvar(Veiculo veiculo) {
		em.merge(veiculo);
	}

	@Transactional
	public void alterar(Veiculo veiculo) {
		em.merge(veiculo);
	}

	@Transactional
	public void excluir(Veiculo veiculo) throws NegocioException {
		Veiculo excluir = em.find(Veiculo.class, veiculo.getId());
		em.remove(excluir);
		em.flush();
	}

}
