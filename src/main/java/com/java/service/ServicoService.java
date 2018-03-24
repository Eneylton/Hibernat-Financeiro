package com.java.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.ServicoDAO;
import com.java.modelo.Servico;
import com.java.util.NegocioException;

public class ServicoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ServicoDAO servicoDAO;

	public Servico retornarServicoPorID(Long id) throws NegocioException {
		return servicoDAO.retornarServicoPorID(id);
	}

	public List<Servico> listarTodos() {

		return servicoDAO.listarTodos();
	}
	
	public List<Servico> listarTodosPorEmpresa(Long id) {
		return servicoDAO.listarTodosPorEmpresa(id);
	}

	public void salvar(Servico servico) throws NegocioException {

		if (servico.getDescricao() == null || servico.getDescricao().trim().equals("")) {
			throw new NegocioException("Descrição do Ação deve ser informada.");
		}

		this.servicoDAO.salvar(servico);

	}

	public void excluir(Servico servico) throws NegocioException {

		if (servico == null) {
			throw new NegocioException("Ação deve ser selecionada.");
		} else if (servico.getId() <= 0) {
			throw new NegocioException("Ação deve ser selecionada.");
		}

		this.servicoDAO.excluir(servico);
	}

}
