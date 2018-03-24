package com.java.controller.empresa;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.java.modelo.Empresa;
import com.java.service.EmpresaService;
import com.java.util.FileUploadCopiarArquivo;
import com.java.util.NegocioException;
import com.java.util.Session;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class EmpresaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EmpresaService empresaService;

	private List<Empresa> listarEmpresas;

	private UploadedFile arquivoUP;

	private Empresa empresaEdicao = new Empresa();

	private Empresa empresaSelecionada;

	private Empresa empresa;

	@PostConstruct
	public void init() throws SQLException {
		consultar();
		limpar();

	}

	public void consultar() throws SQLException {
		
		Long idUsuario = Session.retornaIdUsuarioLogado();

		listarEmpresas = empresaService.listarTodosEmpresa(idUsuario);

	}

	public void prepararNovoCadastro() {
		empresaEdicao = new Empresa();
	}

	public void salvar() throws SQLException {
		try {

			String nomeArquivo = "";
			if (arquivoUP != null) {
				FileUploadCopiarArquivo.iniciarCopia(arquivoUP);
				nomeArquivo = arquivoUP.getFileName();
				empresaEdicao.setLogo(nomeArquivo);
			} else {
				empresaEdicao.setLogo("");
			}

			this.empresaService.salvar(empresaEdicao);

			FacesUtil.addSuccessMessage("Arquivo Salvo com Sucesso !!!!! ");

			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:empresaTable"));

			consultar();

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}
	}

	public void excluir() {
		try {
			empresaService.excluir(empresaSelecionada);
			this.listarEmpresas.remove(empresaSelecionada);
			FacesUtil.addSuccessMessage("Empresa " + empresaSelecionada.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		this.setArquivoUP(event.getFile());
	}

	public void limpar() {
		this.empresa = new Empresa();
	}

	public List<Empresa> getListarEmpresas() {
		return listarEmpresas;
	}

	public void setListarEmpresas(List<Empresa> listarEmpresas) {
		this.listarEmpresas = listarEmpresas;
	}

	public Empresa getEmpresaEdicao() {
		return empresaEdicao;
	}

	public void setEmpresaEdicao(Empresa empresaEdicao) {
		this.empresaEdicao = empresaEdicao;
	}

	public Empresa getEmpresaSelecionada() {
		return empresaSelecionada;
	}

	public void setEmpresaSelecionada(Empresa empresaSelecionada) {
		this.empresaSelecionada = empresaSelecionada;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public UploadedFile getArquivoUP() {
		return arquivoUP;
	}

	public void setArquivoUP(UploadedFile arquivoUP) {
		this.arquivoUP = arquivoUP;
	}

}
