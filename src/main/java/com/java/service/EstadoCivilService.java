package com.java.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.EstadoCivilDAO;
import com.java.modelo.EstadoCivil;
import com.java.util.NegocioException;

public class EstadoCivilService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EstadoCivilDAO estadoCivilDAO;

	public EstadoCivil retornarEstadoCivilPorID(Long id) throws NegocioException {
		return estadoCivilDAO.retornarEstadoCivilPorID(id);
	}

	public List<EstadoCivil> listarTodos() {

		return estadoCivilDAO.listarTodos();
	}

	public void salvar(EstadoCivil estadoCivil) throws NegocioException {

		if (estadoCivil.getDescricao() == null || estadoCivil.getDescricao().trim().equals("")) {
			throw new NegocioException("Descrição do Ação deve ser informada.");
		}

		this.estadoCivilDAO.salvar(estadoCivil);

	}

	public void excluir(EstadoCivil estadoCivil) throws NegocioException {

		if (estadoCivil == null) {
			throw new NegocioException("Ação deve ser selecionada.");
		} else if (estadoCivil.getId() <= 0) {
			throw new NegocioException("Ação deve ser selecionada.");
		}

		this.estadoCivilDAO.excluir(estadoCivil);
	}

}
