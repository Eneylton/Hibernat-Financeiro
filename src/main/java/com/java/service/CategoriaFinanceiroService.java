package com.java.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.CategoriaFinanceiroDAO;
import com.java.modelo.CategoriaFinanceiro;
import com.java.util.NegocioException;

public class CategoriaFinanceiroService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CategoriaFinanceiroDAO categoriaFinanceiroDAO;

	public CategoriaFinanceiro retornarCategoriaFinanceiroPorID(Long id) throws NegocioException {
		return categoriaFinanceiroDAO.retornarCategoriaFinanceiroPorID(id);
	}

	public List<CategoriaFinanceiro> listarTodos() {

		return categoriaFinanceiroDAO.listarTodos();
	}

	public void salvar(CategoriaFinanceiro categoriaFinanceiro) throws NegocioException {

		if (categoriaFinanceiro.getDescricao() == null || categoriaFinanceiro.getDescricao().trim().equals("")) {
			throw new NegocioException("Descrição do Ação deve ser informada.");
		}

		this.categoriaFinanceiroDAO.salvar(categoriaFinanceiro);

	}

	public void excluir(CategoriaFinanceiro categoriaFinanceiro) throws NegocioException {

		if (categoriaFinanceiro == null) {
			throw new NegocioException("Ação deve ser selecionada.");
		} else if (categoriaFinanceiro.getId() <= 0) {
			throw new NegocioException("Ação deve ser selecionada.");
		}

		this.categoriaFinanceiroDAO.excluir(categoriaFinanceiro);
	}

}
