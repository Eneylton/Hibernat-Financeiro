package com.java.controller.veiculo;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.java.modelo.Instrutor;
import com.java.modelo.Veiculo;
import com.java.service.EmpresaService;
import com.java.service.InstrutorService;
import com.java.service.VeiculoService;
import com.java.util.FileUploadCopiarArquivo;
import com.java.util.NegocioException;
import com.java.util.Session;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class VeiculoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private VeiculoService veiculoService;
	
	@Inject
	private EmpresaService empresaService;
	
	@Inject
	private InstrutorService instrutorService;
	

	private Map<String, Instrutor> listarInstrutor = new HashMap<String, Instrutor>();
	
	private Instrutor instrutor;

	private UploadedFile arquivoUP;

	private List<Veiculo> listarVeiculos;

	private Veiculo veiculoEdicao = new Veiculo();

	private Veiculo veiculoSelecionada;

	private Veiculo veiculo;

	@PostConstruct
	public void init() throws SQLException {
		consultar();
		 preencheListasSelect();
		limpar();

	}

	public void consultar() throws SQLException {

		Long idEmpresa = Session.retornaIdEmpresa();
		
		listarVeiculos = veiculoService.listarTodosPorEmpresa(idEmpresa);

	}

	public void prepararNovoCadastro() {
		veiculoEdicao = new Veiculo();
	}
	
	
	private void preencheListasSelect() {

		Long IdEmpresa = Session.retornaIdEmpresa();
		
		listarInstrutor = new TreeMap<String, Instrutor>();
		for (Instrutor instrutor : instrutorService.listarTodosEmpresa(IdEmpresa)) {
			listarInstrutor.put(instrutor.getNome(), instrutor);
		}
		
		
	}

	public void salvar() throws SQLException {
		try {

			String nomeArquivo = "";
			if (arquivoUP != null) {
				FileUploadCopiarArquivo.iniciarCopia(arquivoUP);
				nomeArquivo = arquivoUP.getFileName();
				veiculoEdicao.setFoto(nomeArquivo);
			} else {
				veiculoEdicao.setFoto("");
			}

			if (veiculoEdicao.getId() == null) {
				
				Long idEmpresa = Session.retornaIdEmpresa(); 
						
			    veiculoEdicao.setEmpresa(empresaService.retornarEmpresaPorID(idEmpresa));

				veiculoService.salvar(veiculoEdicao);
				consultar();

				FacesUtil.addSuccessMessage("Arquivo Cadastrado com sucesso!");

				RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:veiculoTable"));

			} else {

				veiculoService.alterar(veiculoEdicao);
				consultar();

				FacesUtil.addSuccessMessage("Arquivo Altrarado com sucesso!");

				RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:veiculoTable"));

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}
	}

	public void excluir() {
		try {
			veiculoService.excluir(veiculoSelecionada);
			this.listarVeiculos.remove(veiculoSelecionada);
			FacesUtil.addSuccessMessage("Veiculo " + veiculoSelecionada.getMarca() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		this.setArquivoUP(event.getFile());
	}

	public void limpar() {
		this.veiculo = new Veiculo();
	}

	public List<Veiculo> getListarVeiculos() {
		return listarVeiculos;
	}

	public void setListarVeiculos(List<Veiculo> listarVeiculos) {
		this.listarVeiculos = listarVeiculos;
	}

	public Veiculo getVeiculoEdicao() {
		return veiculoEdicao;
	}

	public void setVeiculoEdicao(Veiculo veiculoEdicao) {
		this.veiculoEdicao = veiculoEdicao;
	}

	public Veiculo getVeiculoSelecionada() {
		return veiculoSelecionada;
	}

	public void setVeiculoSelecionada(Veiculo veiculoSelecionada) {
		this.veiculoSelecionada = veiculoSelecionada;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public UploadedFile getArquivoUP() {
		return arquivoUP;
	}

	public void setArquivoUP(UploadedFile arquivoUP) {
		this.arquivoUP = arquivoUP;
	}

	public Map<String, Instrutor> getListarInstrutor() {
		return listarInstrutor;
	}

	public void setListarInstrutor(Map<String, Instrutor> listarInstrutor) {
		this.listarInstrutor = listarInstrutor;
	}

	public Instrutor getInstrutor() {
		return instrutor;
	}

	public void setInstrutor(Instrutor instrutor) {
		this.instrutor = instrutor;
	}

	
}
