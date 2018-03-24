package com.java.modelo;

import java.io.Serializable;

public class CrossTabServicos implements Serializable {
	private static final long serialVersionUID = 1L;

	private String servicos;

	private double janeiro;

	private double fevereiro;

	private double marco;

	private double abril;

	private double maio;

	private double junho;

	private double julho;

	private double agosto;

	private double setembro;

	private double outubro;

	private double novembro;

	private double dezembro;

	private double total;

	public CrossTabServicos() {
	}

	public String getServicos() {
		return servicos;
	}

	public void setServicos(String servicos) {
		this.servicos = servicos;
	}

	public double getJaneiro() {
		return janeiro;
	}

	public void setJaneiro(double janeiro) {
		this.janeiro = janeiro;
	}

	public double getFevereiro() {
		return fevereiro;
	}

	public void setFevereiro(double fevereiro) {
		this.fevereiro = fevereiro;
	}

	public double getMarco() {
		return marco;
	}

	public void setMarco(double marco) {
		this.marco = marco;
	}

	public double getAbril() {
		return abril;
	}

	public void setAbril(double abril) {
		this.abril = abril;
	}

	public double getMaio() {
		return maio;
	}

	public void setMaio(double maio) {
		this.maio = maio;
	}

	public double getJunho() {
		return junho;
	}

	public void setJunho(double junho) {
		this.junho = junho;
	}

	public double getJulho() {
		return julho;
	}

	public void setJulho(double julho) {
		this.julho = julho;
	}

	public double getAgosto() {
		return agosto;
	}

	public void setAgosto(double agosto) {
		this.agosto = agosto;
	}

	public double getSetembro() {
		return setembro;
	}

	public void setSetembro(double setembro) {
		this.setembro = setembro;
	}

	public double getOutubro() {
		return outubro;
	}

	public void setOutubro(double outubro) {
		this.outubro = outubro;
	}

	public double getNovembro() {
		return novembro;
	}

	public void setNovembro(double novembro) {
		this.novembro = novembro;
	}

	public double getDezembro() {
		return dezembro;
	}

	public void setDezembro(double dezembro) {
		this.dezembro = dezembro;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}