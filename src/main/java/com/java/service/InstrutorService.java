package com.java.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.InstrutorDAO;
import com.java.modelo.Instrutor;
import com.java.util.NegocioException;

public class InstrutorService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private InstrutorDAO instrutorDAO;

	public Instrutor retornarInstrutorPorID(Long id) throws NegocioException {
		return instrutorDAO.retornarInstrutorPorID(id);
	}

	public List<Instrutor> listarTodos() {

		return instrutorDAO.listarTodos();
	}
	
	public List<Instrutor> listarTodosEmpresa(Long id) {
		return instrutorDAO.listarTodosEmpresa(id);
	}

	public void salvar(Instrutor instrutor) throws NegocioException {

		if (instrutor.getNome() == null || instrutor.getNome().trim().equals("")) {
			throw new NegocioException("Descrição do Ação deve ser informada.");
		}

		this.instrutorDAO.salvar(instrutor);

	}

	public void excluir(Instrutor instrutor) throws NegocioException {

		if (instrutor == null) {
			throw new NegocioException("Ação deve ser selecionada.");
		} else if (instrutor.getId() <= 0) {
			throw new NegocioException("Ação deve ser selecionada.");
		}

		this.instrutorDAO.excluir(instrutor);
	}

}
