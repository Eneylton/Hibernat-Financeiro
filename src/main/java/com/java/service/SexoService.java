package com.java.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.SexoDAO;
import com.java.modelo.Sexo;
import com.java.util.NegocioException;

public class SexoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private SexoDAO sexoDAO;

	public Sexo retornarSexoPorID(Long id) throws NegocioException {
		return sexoDAO.retornarSexoPorID(id);
	}

	public List<Sexo> listarTodos() {

		return sexoDAO.listarTodos();
	}

	public void salvar(Sexo sexo) throws NegocioException {

		if (sexo.getDescricao() == null || sexo.getDescricao().trim().equals("")) {
			throw new NegocioException("Descrição do Ação deve ser informada.");
		}

		this.sexoDAO.salvar(sexo);

	}

	public void excluir(Sexo sexo) throws NegocioException {

		if (sexo == null) {
			throw new NegocioException("Ação deve ser selecionada.");
		} else if (sexo.getId() <= 0) {
			throw new NegocioException("Ação deve ser selecionada.");
		}

		this.sexoDAO.excluir(sexo);
	}

}
