package com.java.controller.categoriaFinanceiro;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.java.modelo.CategoriaFinanceiro;
import com.java.service.CategoriaFinanceiroService;
import com.java.util.NegocioException;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CategoriaFinanceiroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CategoriaFinanceiroService categoriaFinanceiroService;

	private List<CategoriaFinanceiro> listarCategoriaFinanceiros;

	private CategoriaFinanceiro categoriaFinanceiroEdicao = new CategoriaFinanceiro();

	private CategoriaFinanceiro categoriaFinanceiroSelecionada;

	private CategoriaFinanceiro categoriaFinanceiro;

	@PostConstruct
	public void init() throws SQLException {
		consultar();
		limpar();

	}

	public void consultar() throws SQLException {

		listarCategoriaFinanceiros = categoriaFinanceiroService.listarTodos();

	}

	public void prepararNovoCadastro() {
		categoriaFinanceiroEdicao = new CategoriaFinanceiro();
	}

	public void salvar() throws SQLException {
		try {

			this.categoriaFinanceiroService.salvar(categoriaFinanceiroEdicao);

			FacesUtil.addSuccessMessage("Arquivo Salvo com Sucesso !!!!! ");

			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:categoriaFinanceiroTable"));

			consultar();

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}
	}

	public void excluir() {
		try {
			categoriaFinanceiroService.excluir(categoriaFinanceiroSelecionada);
			this.listarCategoriaFinanceiros.remove(categoriaFinanceiroSelecionada);
			FacesUtil.addSuccessMessage(
					"CategoriaFinanceiro " + categoriaFinanceiroSelecionada.getDescricao() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void limpar() {
		this.categoriaFinanceiro = new CategoriaFinanceiro();
	}

	public List<CategoriaFinanceiro> getListarCategoriaFinanceiros() {
		return listarCategoriaFinanceiros;
	}

	public void setListarCategoriaFinanceiros(List<CategoriaFinanceiro> listarCategoriaFinanceiros) {
		this.listarCategoriaFinanceiros = listarCategoriaFinanceiros;
	}

	public CategoriaFinanceiro getCategoriaFinanceiroEdicao() {
		return categoriaFinanceiroEdicao;
	}

	public void setCategoriaFinanceiroEdicao(CategoriaFinanceiro categoriaFinanceiroEdicao) {
		this.categoriaFinanceiroEdicao = categoriaFinanceiroEdicao;
	}

	public CategoriaFinanceiro getCategoriaFinanceiroSelecionada() {
		return categoriaFinanceiroSelecionada;
	}

	public void setCategoriaFinanceiroSelecionada(CategoriaFinanceiro categoriaFinanceiroSelecionada) {
		this.categoriaFinanceiroSelecionada = categoriaFinanceiroSelecionada;
	}

	public CategoriaFinanceiro getCategoriaFinanceiro() {
		return categoriaFinanceiro;
	}

	public void setCategoriaFinanceiro(CategoriaFinanceiro categoriaFinanceiro) {
		this.categoriaFinanceiro = categoriaFinanceiro;
	}

}
