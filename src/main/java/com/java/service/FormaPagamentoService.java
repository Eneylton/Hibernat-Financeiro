package com.java.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.FormaPagamentoDAO;
import com.java.modelo.FormaPagamento;
import com.java.util.NegocioException;

public class FormaPagamentoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FormaPagamentoDAO formaPagamentoDAO;

	public FormaPagamento retornarFormaPagamentoPorID(Long id) throws NegocioException {
		return formaPagamentoDAO.retornarFormaPagamentoPorID(id);
	}

	public List<FormaPagamento> listarTodos() {

		return formaPagamentoDAO.listarTodos();
	}

	public void salvar(FormaPagamento formaPagamento) throws NegocioException {

		if (formaPagamento.getDescricao() == null || formaPagamento.getDescricao().trim().equals("")) {
			throw new NegocioException("Descrição do Ação deve ser informada.");
		}

		this.formaPagamentoDAO.salvar(formaPagamento);

	}

	public void excluir(FormaPagamento formaPagamento) throws NegocioException {

		if (formaPagamento == null) {
			throw new NegocioException("Ação deve ser selecionada.");
		} else if (formaPagamento.getId() <= 0) {
			throw new NegocioException("Ação deve ser selecionada.");
		}

		this.formaPagamentoDAO.excluir(formaPagamento);
	}

}
