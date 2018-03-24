package com.java.controller.sexo;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.java.modelo.Sexo;
import com.java.service.SexoService;
import com.java.util.NegocioException;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class SexoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private SexoService sexoService;

	private List<Sexo> listarSexos;

	private Sexo sexoEdicao = new Sexo();

	private Sexo sexoSelecionada;

	private Sexo sexo;

	@PostConstruct
	public void init() throws SQLException {
		consultar();
		limpar();

	}

	public void consultar() throws SQLException {

		listarSexos = sexoService.listarTodos();

	}

	public void prepararNovoCadastro() {
		sexoEdicao = new Sexo();
	}
	
	public void salvar() throws SQLException {
		try {

			this.sexoService.salvar(sexoEdicao);

			FacesUtil.addSuccessMessage("Arquivo Salvo com Sucesso !!!!! ");

			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:sexoTable"));

			consultar();

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}
	}

	
	public void excluir() {
		try {
			sexoService.excluir(sexoSelecionada);
			this.listarSexos.remove(sexoSelecionada);
			FacesUtil.addSuccessMessage("Sexo " + sexoSelecionada.getDescricao() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void limpar() {
		this.sexo = new Sexo();
	}

	public List<Sexo> getListarSexos() {
		return listarSexos;
	}

	public void setListarSexos(List<Sexo> listarSexos) {
		this.listarSexos = listarSexos;
	}

	public Sexo getSexoEdicao() {
		return sexoEdicao;
	}

	public void setSexoEdicao(Sexo sexoEdicao) {
		this.sexoEdicao = sexoEdicao;
	}

	public Sexo getSexoSelecionada() {
		return sexoSelecionada;
	}

	public void setSexoSelecionada(Sexo sexoSelecionada) {
		this.sexoSelecionada = sexoSelecionada;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

}
