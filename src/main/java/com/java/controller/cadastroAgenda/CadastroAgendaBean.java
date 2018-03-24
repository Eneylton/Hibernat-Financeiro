package com.java.controller.cadastroAgenda;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.java.modelo.CadastroAgenda;
import com.java.modelo.Instrutor;
import com.java.modelo.Veiculo;
import com.java.service.AlunoService;
import com.java.service.CadastroAgendaService;
import com.java.service.InstrutorService;
import com.java.util.NegocioException;
import com.java.util.Session;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroAgendaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroAgendaService cadastroAgendaService;

	@Inject
	private AlunoService alunoService;

	@Inject
	private InstrutorService instrutorService;

	private Map<String, Instrutor> listarInstrutor = new HashMap<String, Instrutor>();

	private List<CadastroAgenda> listarAlunosAgenda;

	private List<Veiculo> listarVeiculos;

	private CadastroAgenda cadastroAgendaSelecionada;

	private CadastroAgenda cadastroAgenda;

	private CadastroAgenda agenda;

	@PostConstruct
	public void init() throws SQLException, NumberFormatException, NegocioException {
		try {
			preencheListasSelect();
			limpar();

			String idAluno = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("aluno");

			if (idAluno != null) {

				this.cadastroAgenda.setAluno(alunoService.retornarAlunoPorID(Long.parseLong(idAluno)));
				
				listarAlunosAgenda = cadastroAgendaService.listarAgendamentoPorEmpresa(Long.parseLong(idAluno));
				

			} else {

				System.out.println("Parametro null " + idAluno);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}

	}

	

	private void preencheListasSelect() {

		Long IdEmpresa = Session.retornaIdEmpresa();

		listarInstrutor = new TreeMap<String, Instrutor>();
		for (Instrutor instrutor : instrutorService.listarTodosEmpresa(IdEmpresa)) {
			listarInstrutor.put(instrutor.getNome(), instrutor);
		}

	}

	public void buscarVeiculo() {

		Long idInstrutor = this.cadastroAgenda.getInstrutor().getId();

		listarVeiculos = cadastroAgendaService.buscarVeiculoPorInstrutor(idInstrutor);
	}

	public void salvar() throws SQLException {
		try {

			this.cadastroAgendaService.salvar(cadastroAgenda);

			FacesUtil.addSuccessMessage("Arquivo Salvo com Sucesso !!!!! ");

			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:cadastroAgendaTable"));

			Long idAluno = cadastroAgenda.getAluno().getId();
			
			listarAlunosAgenda = cadastroAgendaService.listarAgendamentoPorEmpresa(idAluno);

		
		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}
	}

	public void limpar() {
		this.cadastroAgenda = new CadastroAgenda();
	}

	public Map<String, Instrutor> getListarInstrutor() {
		return listarInstrutor;
	}

	public void setListarInstrutor(Map<String, Instrutor> listarInstrutor) {
		this.listarInstrutor = listarInstrutor;
	}

	public List<Veiculo> getListarVeiculos() {
		return listarVeiculos;
	}

	public void setListarVeiculos(List<Veiculo> listarVeiculos) {
		this.listarVeiculos = listarVeiculos;
	}

	public CadastroAgenda getCadastroAgendaSelecionada() {
		return cadastroAgendaSelecionada;
	}

	public void setCadastroAgendaSelecionada(CadastroAgenda cadastroAgendaSelecionada) {
		this.cadastroAgendaSelecionada = cadastroAgendaSelecionada;
	}

	public CadastroAgenda getCadastroAgenda() {
		return cadastroAgenda;
	}

	public void setCadastroAgenda(CadastroAgenda cadastroAgenda) {
		this.cadastroAgenda = cadastroAgenda;
	}

	public CadastroAgenda getAgenda() {
		return agenda;
	}

	public void setAgenda(CadastroAgenda agenda) {
		this.agenda = agenda;
	}

	public List<CadastroAgenda> getListarAlunosAgenda() {
		return listarAlunosAgenda;
	}

	public void setListarAlunosAgenda(List<CadastroAgenda> listarAlunosAgenda) {
		this.listarAlunosAgenda = listarAlunosAgenda;
	}

}
