package com.java.controller.servico;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.java.modelo.Servico;
import com.java.service.EmpresaService;
import com.java.service.ServicoService;
import com.java.util.NegocioException;
import com.java.util.Session;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ServicoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ServicoService servicoService;

	@Inject
	private EmpresaService empresaService;
	
	private List<Servico> listarServicos;

	private Servico servicoEdicao = new Servico();

	private Servico servicoSelecionada;

	private Servico servico;

	@PostConstruct
	public void init() throws SQLException {
		consultar();
		limpar();

	}

	public void consultar() throws SQLException {

		Long idEmpresa = Session.retornaIdEmpresa();
		
		listarServicos = servicoService.listarTodosPorEmpresa(idEmpresa);

	}

	public void prepararNovoCadastro() {
		servicoEdicao = new Servico();
	}
	
	public void salvar() throws SQLException {
		try {
			
			Long idEmpresa = Session.retornaIdEmpresa();
			
			servicoEdicao.setEmpresa(empresaService.retornarEmpresaPorID(idEmpresa));

			this.servicoService.salvar(servicoEdicao);

			FacesUtil.addSuccessMessage("Serviço Salvo com Sucesso !!!!! ");

			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:servicoTable"));

			consultar();

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}
	}

	
	public void excluir() {
		try {
			servicoService.excluir(servicoSelecionada);
			this.listarServicos.remove(servicoSelecionada);
			FacesUtil.addSuccessMessage("Servico " + servicoSelecionada.getDescricao() + " excluído com sucesso.");
			
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:servicoTable"));
			
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void limpar() {
		this.servico = new Servico();
	}

	public List<Servico> getListarServicos() {
		return listarServicos;
	}

	public void setListarServicos(List<Servico> listarServicos) {
		this.listarServicos = listarServicos;
	}

	public Servico getServicoEdicao() {
		return servicoEdicao;
	}

	public void setServicoEdicao(Servico servicoEdicao) {
		this.servicoEdicao = servicoEdicao;
	}

	public Servico getServicoSelecionada() {
		return servicoSelecionada;
	}

	public void setServicoSelecionada(Servico servicoSelecionada) {
		this.servicoSelecionada = servicoSelecionada;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	

}
