package com.java.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.FinanceiroDAO;
import com.java.modelo.Financeiro;
import com.java.util.NegocioException;

public class FinanceiroService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FinanceiroDAO financeiroDAO;

	public Financeiro retornarFinanceiroPorID(Long id) throws NegocioException {
		return financeiroDAO.retornarFinanceiroPorID(id);
	}

	public List<Financeiro> listarTodos() {

		return financeiroDAO.listarTodos();
	}

	public List<Financeiro> listarTodosPorAluno(Long id) {
		return financeiroDAO.listarTodosPorAluno(id);
	}

	public List<Financeiro> listarParcelaFinanceiro(Long id) {
		return financeiroDAO.listarParcelaFinanceiro(id);
	}

	public List<Financeiro> listarPrevisao(Long id) {
		return financeiroDAO.listarPrevisao(id);
	}
	
	public List<Financeiro> reciboLancamento(Long id) {
		return financeiroDAO.reciboLancamento(id);
	}

	public double total(Long id) {
		return financeiroDAO.total(id);
	}

	public double totalReceitaEmpresa(Long id) {
		return financeiroDAO.totalReceitaEmpresa(id);
	}

	public double totalDespesaEmpresa(Long id) {
		return financeiroDAO.totalDespesaEmpresa(id);
	}

	public double totalReceitaBanco(Long id) {
		return financeiroDAO.totalReceitaBanco(id);
	}
	
	public List<Financeiro> reciboFaturamento(Long id) {
		return financeiroDAO.reciboFaturamento(id);
	}

	public double totalDespesaBanco(Long id) {
		return financeiroDAO.totalDespesaBanco(id);
	}

	public void salvar(Financeiro financeiro) throws NegocioException {

		this.financeiroDAO.salvar(financeiro);

	}

	public void editar(Financeiro financeiro) throws NegocioException {

		this.financeiroDAO.alterar(financeiro);

	}

	public void excluir(Financeiro financeiro) throws NegocioException {

		if (financeiro == null) {
			throw new NegocioException("Ação deve ser selecionada.");
		} else if (financeiro.getId() <= 0) {
			throw new NegocioException("Ação deve ser selecionada.");
		}

		this.financeiroDAO.excluir(financeiro);
	}

}
