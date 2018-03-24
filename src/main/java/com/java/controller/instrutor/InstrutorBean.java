package com.java.controller.instrutor;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.java.modelo.Instrutor;
import com.java.service.EmpresaService;
import com.java.service.InstrutorService;
import com.java.util.NegocioException;
import com.java.util.Session;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class InstrutorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private InstrutorService instrutorService;
	
	@Inject
	private EmpresaService empresaService;

	private List<Instrutor> listarInstrutores;

	private Instrutor instrutorEdicao = new Instrutor();

	private Instrutor instrutorSelecionada;

	private Instrutor instrutor;

	@PostConstruct
	public void init() throws SQLException {
		consultar();
		limpar();

	}

	public void consultar() throws SQLException {

		listarInstrutores = instrutorService.listarTodos();

	}

	public void prepararNovoCadastro() {
		instrutorEdicao = new Instrutor();
	}

	public void salvar() throws SQLException {
		try {

			Long idEmpresa = Session.retornaIdEmpresa();
			
			instrutorEdicao.setEmpresa(empresaService.retornarEmpresaPorID(idEmpresa));
			
			this.instrutorService.salvar(instrutorEdicao);

			FacesUtil.addSuccessMessage("Arquivo Salvo com Sucesso !!!!! ");

			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:InstrutorTable"));

			consultar();

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}
	}

	public void excluir() {
		try {
			instrutorService.excluir(instrutorSelecionada);
			this.listarInstrutores.remove(instrutorSelecionada);
			FacesUtil.addSuccessMessage("Instrutor " + instrutorSelecionada.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void limpar() {
		this.instrutor = new Instrutor();
	}

	public List<Instrutor> getListarInstrutores() {
		return listarInstrutores;
	}

	public void setListarInstrutores(List<Instrutor> listarInstrutores) {
		this.listarInstrutores = listarInstrutores;
	}

	public Instrutor getInstrutorEdicao() {
		return instrutorEdicao;
	}

	public void setInstrutorEdicao(Instrutor instrutorEdicao) {
		this.instrutorEdicao = instrutorEdicao;
	}

	public Instrutor getInstrutorSelecionada() {
		return instrutorSelecionada;
	}

	public void setInstrutorSelecionada(Instrutor instrutorSelecionada) {
		this.instrutorSelecionada = instrutorSelecionada;
	}

	public Instrutor getInstrutor() {
		return instrutor;
	}

	public void setInstrutor(Instrutor instrutor) {
		this.instrutor = instrutor;
	}

}
