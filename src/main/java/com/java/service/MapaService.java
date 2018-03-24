package com.java.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.MapaDAO;
import com.java.modelo.Agenda;

public class MapaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MapaDAO mapaDAO;
	

	public List<Agenda> listarTodos() {
        return mapaDAO.listarTodos();
	}
	
	public List<Agenda> listarTodosPorInstrutor(Long idInstrutor ) {
        return mapaDAO.listarTodosPorInstrutor(idInstrutor);
	}
	
	public List<Agenda> listarTodosPorAluno(Long idALuno) {
		return mapaDAO.listarTodosPorAluno(idALuno);
	}

}
