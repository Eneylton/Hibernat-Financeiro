package com.java.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.VeiculoDAO;
import com.java.modelo.Veiculo;
import com.java.util.NegocioException;

public class VeiculoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private VeiculoDAO veiculoDAO;

	public Veiculo retornarVeiculoPorID(Long id) throws NegocioException {
		return veiculoDAO.retornarVeiculoPorID(id);
	}

	public List<Veiculo> listarTodos() {

		return veiculoDAO.listarTodos();
	}
	
	public List<Veiculo> listarTodosPorEmpresa(Long id) {
		return veiculoDAO.listarTodosPorEmpresa(id);
	}

	public void salvar(Veiculo veiculo) throws NegocioException {

		if (veiculo.getMarca() == null || veiculo.getMarca().trim().equals("")) {
			throw new NegocioException("Descrição do Ação deve ser informada.");
		}

		this.veiculoDAO.salvar(veiculo);

	}
	
	
	public void alterar(Veiculo veiculo) throws NegocioException {

		if (veiculo.getMarca() == null || veiculo.getMarca().trim().equals("")) {
			throw new NegocioException("Descrição do Ação deve ser informada.");
		}

		this.veiculoDAO.alterar(veiculo);

	}

	public void excluir(Veiculo veiculo) throws NegocioException {

		if (veiculo == null) {
			throw new NegocioException("Ação deve ser selecionada.");
		} else if (veiculo.getId() <= 0) {
			throw new NegocioException("Ação deve ser selecionada.");
		}

		this.veiculoDAO.excluir(veiculo);
	}

}
