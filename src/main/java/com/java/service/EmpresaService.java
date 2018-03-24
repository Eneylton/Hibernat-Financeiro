package com.java.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.EmpresaDAO;
import com.java.modelo.Empresa;
import com.java.util.NegocioException;

public class EmpresaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EmpresaDAO empresaDAO;

	public Empresa retornarEmpresaPorID(Long id) throws NegocioException {
		return empresaDAO.retornarEmpresaPorID(id);
	}

	public List<Empresa> listarTodos() {

		return empresaDAO.listarTodos();
	}

	public List<Empresa> listarTodosEmpresa(Long id) {
		return empresaDAO.listarTodosEmpresa(id);
	}

	public void salvar(Empresa empresa) throws NegocioException {

		if (empresa.getRazao() == null || empresa.getRazao().trim().equals("")) {
			throw new NegocioException("Descrição do Ação deve ser informada.");
		}

		this.empresaDAO.salvar(empresa);

	}

	public void excluir(Empresa empresa) throws NegocioException {

		if (empresa == null) {
			throw new NegocioException("Ação deve ser selecionada.");
		} else if (empresa.getId() <= 0) {
			throw new NegocioException("Ação deve ser selecionada.");
		}

		this.empresaDAO.excluir(empresa);
	}

}
