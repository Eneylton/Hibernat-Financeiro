package com.java.controller.estadoCivel;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.java.modelo.EstadoCivil;
import com.java.service.EstadoCivilService;
import com.java.util.NegocioException;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class EstadoCivilBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EstadoCivilService estadoCivilService;

	private List<EstadoCivil> listarEstadoCivils;

	private EstadoCivil estadoCivilEdicao = new EstadoCivil();

	private EstadoCivil estadoCivilSelecionada;

	private EstadoCivil estadoCivil;

	@PostConstruct
	public void init() throws SQLException {
		consultar();
		limpar();

	}

	public void consultar() throws SQLException {

		listarEstadoCivils = estadoCivilService.listarTodos();

	}

	public void prepararNovoCadastro() {
		estadoCivilEdicao = new EstadoCivil();
	}

	public void salvar() throws SQLException {
		try {

			this.estadoCivilService.salvar(estadoCivilEdicao);

			FacesUtil.addSuccessMessage("Arquivo Salvo com Sucesso !!!!! ");

			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:estadoCivilTable"));

			consultar();

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}
	}

	public void excluir() {
		try {
			estadoCivilService.excluir(estadoCivilSelecionada);
			this.listarEstadoCivils.remove(estadoCivilSelecionada);
			FacesUtil.addSuccessMessage(
					"EstadoCivil " + estadoCivilSelecionada.getDescricao() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void limpar() {
		this.estadoCivil = new EstadoCivil();
	}

	public List<EstadoCivil> getListarEstadoCivils() {
		return listarEstadoCivils;
	}

	public void setListarEstadoCivils(List<EstadoCivil> listarEstadoCivils) {
		this.listarEstadoCivils = listarEstadoCivils;
	}

	public EstadoCivil getEstadoCivilEdicao() {
		return estadoCivilEdicao;
	}

	public void setEstadoCivilEdicao(EstadoCivil estadoCivilEdicao) {
		this.estadoCivilEdicao = estadoCivilEdicao;
	}

	public EstadoCivil getEstadoCivilSelecionada() {
		return estadoCivilSelecionada;
	}

	public void setEstadoCivilSelecionada(EstadoCivil estadoCivilSelecionada) {
		this.estadoCivilSelecionada = estadoCivilSelecionada;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

}
