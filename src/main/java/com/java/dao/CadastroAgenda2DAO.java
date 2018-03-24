package com.java.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.java.modelo.CadastroAgenda2;
import com.java.modelo.Veiculo;
import com.java.util.NegocioException;
import com.java.util.jpa.Transactional;

public class CadastroAgenda2DAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public CadastroAgenda2 retornarCadastroAgenda2PorID(Long id) {

		@SuppressWarnings("unchecked")
		List<CadastroAgenda2> listarCadastroAgendas = (List<CadastroAgenda2>) em
				.createQuery("from CadastroAgenda2 c " + "inner join fetch c.aluno " + "inner join fetch c.instrutor "
						+ "inner join fetch c.veiculo " + "where c.id = :paramId ")
				.setParameter("paramId", id).getResultList();

		if (listarCadastroAgendas != null) {
			return listarCadastroAgendas.get(0);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public List<CadastroAgenda2> listarTodos() {
		return em.createQuery("from CadastroAgenda2 ca order by ca.id desc ").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Veiculo> buscarVeiculoPorInstrutor(Long idInstrutor) {
		return em
				.createQuery("select v from Veiculo v " + "inner join fetch v.instrutor "
						+ "where instrutor_id = :paramInstrutor")
				.setParameter("paramInstrutor", idInstrutor).getResultList();
	}

	@Transactional
	public void salvar(CadastroAgenda2 cadastroAgenda2) {
		em.merge(cadastroAgenda2);
	}
	
	@Transactional
	public void alterar(CadastroAgenda2 cadastroAgenda2) {
		em.merge(cadastroAgenda2);
	}


	@Transactional
	public void excluir(CadastroAgenda2 cadastroAgenda2) throws NegocioException {
		CadastroAgenda2 excluir = em.find(CadastroAgenda2.class, cadastroAgenda2.getId());
		em.remove(excluir);
		em.flush();
	}

}
