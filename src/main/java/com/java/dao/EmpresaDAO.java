package com.java.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.java.modelo.Empresa;
import com.java.util.NegocioException;
import com.java.util.jpa.Transactional;

public class EmpresaDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public Empresa retornarEmpresaPorID(Long id) {

		@SuppressWarnings("unchecked")
		List<Empresa> listarEmpresas = (List<Empresa>) em.createQuery("from Empresa e " + "where e.id = :paramId ")
				.setParameter("paramId", id).getResultList();

		if (listarEmpresas != null) {
			return listarEmpresas.get(0);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public List<Empresa> listarTodos() {
		return em.createNativeQuery("select * from empresa ", Empresa.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Empresa> listarTodosEmpresa(Long id) {
		return em
				.createNativeQuery("select * from empresa as emp inner join usuario as us "
						+ "ON (us.empresa_id = emp.id) Where us.id = :paramID", Empresa.class)
				.setParameter("paramID", id).getResultList();
	}

	@Transactional
	public void salvar(Empresa empresa) {
		em.merge(empresa);
	}

	@Transactional
	public void excluir(Empresa empresa) throws NegocioException {
		Empresa excluir = em.find(Empresa.class, empresa.getId());
		em.remove(excluir);
		em.flush();
	}

}
