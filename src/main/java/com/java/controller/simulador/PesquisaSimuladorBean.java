package com.java.controller.simulador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.java.modelo.Agenda2;
import com.java.service.Agenda2Service;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaSimuladorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Agenda2Service agendaService;

	private List<Agenda2> agendas = new ArrayList<>();

	private Agenda2 agenda;

	private Agenda2 agendaSelecionada;

	private String verdadeiro;

	private String falso;

	private int status;

	@PostConstruct
	public void inicializar() {

		try {
			agendas = agendaService.listarPrevisao();

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage(ex.getMessage());
		}

	}

	public List<Agenda2> getAgendas() {
		return agendas;
	}

	public void setAgendas(List<Agenda2> agendas) {
		this.agendas = agendas;
	}

	public Agenda2 getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda2 agenda) {
		this.agenda = agenda;
	}

	public Agenda2 getAgendaSelecionada() {
		return agendaSelecionada;
	}

	public void setAgendaSelecionada(Agenda2 agendaSelecionada) {
		this.agendaSelecionada = agendaSelecionada;
	}

	public String getVerdadeiro() {
		return verdadeiro;
	}

	public void setVerdadeiro(String verdadeiro) {
		this.verdadeiro = verdadeiro;
	}

	public String getFalso() {
		return falso;
	}

	public void setFalso(String falso) {
		this.falso = falso;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
