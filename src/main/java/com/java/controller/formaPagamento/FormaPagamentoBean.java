package com.java.controller.formaPagamento;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.java.modelo.FormaPagamento;
import com.java.service.FormaPagamentoService;
import com.java.util.NegocioException;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class FormaPagamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FormaPagamentoService formaPagamentoService;

	private List<FormaPagamento> listarFormaPagamentos;

	private FormaPagamento formaPagamentoEdicao = new FormaPagamento();

	private FormaPagamento formaPagamentoSelecionada;

	private FormaPagamento formaPagamento;

	@PostConstruct
	public void init() throws SQLException {
		consultar();
		limpar();

	}

	public void consultar() throws SQLException {

		listarFormaPagamentos = formaPagamentoService.listarTodos();

	}

	public void prepararNovoCadastro() {
		formaPagamentoEdicao = new FormaPagamento();
	}

	public void salvar() throws SQLException {
		try {

			this.formaPagamentoService.salvar(formaPagamentoEdicao);

			FacesUtil.addSuccessMessage("Arquivo Salvo com Sucesso !!!!! ");

			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:formaPagamentoTable"));

			consultar();

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}
	}

	public void excluir() {
		try {
			formaPagamentoService.excluir(formaPagamentoSelecionada);
			this.listarFormaPagamentos.remove(formaPagamentoSelecionada);
			FacesUtil.addSuccessMessage(
					"FormaPagamento " + formaPagamentoSelecionada.getDescricao() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void limpar() {
		this.formaPagamento = new FormaPagamento();

	}

	public List<FormaPagamento> getListarFormaPagamentos() {
		return listarFormaPagamentos;
	}

	public void setListarFormaPagamentos(List<FormaPagamento> listarFormaPagamentos) {
		this.listarFormaPagamentos = listarFormaPagamentos;
	}

	public FormaPagamento getFormaPagamentoEdicao() {
		return formaPagamentoEdicao;
	}

	public void setFormaPagamentoEdicao(FormaPagamento formaPagamentoEdicao) {
		this.formaPagamentoEdicao = formaPagamentoEdicao;
	}

	public FormaPagamento getFormaPagamentoSelecionada() {
		return formaPagamentoSelecionada;
	}

	public void setFormaPagamentoSelecionada(FormaPagamento formaPagamentoSelecionada) {
		this.formaPagamentoSelecionada = formaPagamentoSelecionada;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

}
