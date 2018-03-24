package com.java.modelo;

import java.io.Serializable;

/**
 * The persistent class for the sexo database table.
 * 
 */

public class Mapa implements Serializable {
	private static final long serialVersionUID = 1L;

	private Instrutor instrutor;

	private Aluno aluno;

	public Instrutor getInstrutor() {
		return instrutor;
	}

	public void setInstrutor(Instrutor instrutor) {
		this.instrutor = instrutor;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

}