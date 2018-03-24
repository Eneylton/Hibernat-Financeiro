package com.java.controller.mapa;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.java.modelo.Agenda;
import com.java.modelo.Aluno;
import com.java.modelo.Instrutor;
import com.java.modelo.Mapa;
import com.java.relatorio.RelatorioService;
import com.java.service.AlunoService;
import com.java.service.InstrutorService;
import com.java.service.MapaService;
import com.java.util.Session;
import com.java.util.jsf.FacesUtil;

import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class MapaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MapaService mapaService;

	@Inject
	private InstrutorService instrutorService;

	@Inject
	private AlunoService alunoService;
	
	@Inject
	private RelatorioService relatorioService;

	private Map<String, Instrutor> listarInstrutor = new HashMap<String, Instrutor>();

	private Map<String, Aluno> listarAluno = new HashMap<String, Aluno>();

	private List<Agenda> listarAgenda = new ArrayList<>();

	private Agenda agenda;

	private Agenda agendaSelecionada;

	private Instrutor instrutor;

	private Aluno aluno;

	private Mapa mapa = new Mapa();

	@PostConstruct
	public void inicializar() {

		try {
			limpar();
			filtrar();
			preencheListasSelect();

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage(ex.getMessage());
		}

	}

	public void pesquisarInstrutor() throws SQLException, ParseException {

		Long idInstrutor = this.mapa.getInstrutor().getId();

		if (idInstrutor == null) {
			filtrar();
		} else {
			listarAgenda = mapaService.listarTodosPorInstrutor(idInstrutor);
		}

	}

	public void pesquisarAlunos() throws SQLException, ParseException {

		Long idAluno = this.mapa.getAluno().getId();

		if (idAluno == null) {
			filtrar();
		} else {
			listarAgenda = mapaService.listarTodosPorAluno(idAluno);
		}

	}

	public void filtrar() throws SQLException, ParseException {

		listarAgenda = mapaService.listarTodos();

	}

	private void preencheListasSelect() {

		Long IdEmpresa = Session.retornaIdEmpresa();
		
		listarInstrutor = new TreeMap<String, Instrutor>();
		for (Instrutor instrutor : instrutorService.listarTodosEmpresa(IdEmpresa)) {
			listarInstrutor.put(instrutor.getNome(), instrutor);
		}

		Long idUsuario = Session.retornaIdUsuarioLogado();
		
		listarAluno = new TreeMap<String, Aluno>();
		for (Aluno aluno : alunoService.listarTodosPorUsuario(idUsuario)) {
			listarAluno.put(aluno.getNome(), aluno);
		}

	}

	public void imprirMapaInstrutor() throws JRException, SQLException, ParseException {
		Long idInstrutor = this.mapa.getInstrutor().getId();
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("idInstrutor", Integer.valueOf(idInstrutor.toString()));
	
		listarAgenda = mapaService.listarTodosPorInstrutor(idInstrutor);
		relatorioService.gerarMapaInstrutoresPDF(listarAgenda, (HashMap<String, Object>) parametros, "MapaInstrutor");

		limpar();
		filtrar();
		preencheListasSelect();
	}

	public void imprirMapaAluno() throws JRException, SQLException, ParseException {
		Long idAluno = this.mapa.getAluno().getId();
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("idAluno", Integer.valueOf(idAluno.toString()));
	
		listarAgenda = mapaService.listarTodosPorAluno(idAluno);
		relatorioService.gerarMapaAlunosPDF(listarAgenda, (HashMap<String, Object>) parametros, "MapaAlunos");
		limpar();
		filtrar();
		preencheListasSelect();

	}

	public void limpar() {
		this.agenda = new Agenda();
	}

	public List<Agenda> getListarAgenda() {
		return listarAgenda;
	}

	public void setListarAgenda(List<Agenda> listarAgenda) {
		this.listarAgenda = listarAgenda;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public Agenda getAgendaSelecionada() {
		return agendaSelecionada;
	}

	public void setAgendaSelecionada(Agenda agendaSelecionada) {
		this.agendaSelecionada = agendaSelecionada;
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

	public Mapa getMapa() {
		return mapa;
	}

	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Map<String, Aluno> getListarAluno() {
		return listarAluno;
	}

	public void setListarAluno(Map<String, Aluno> listarAluno) {
		this.listarAluno = listarAluno;
	}

}
