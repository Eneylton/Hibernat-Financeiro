package com.java.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.CadastroAgendaDAO;
import com.java.modelo.Agenda;
import com.java.modelo.CadastroAgenda;
import com.java.modelo.Veiculo;
import com.java.util.NegocioException;

public class CadastroAgendaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroAgendaDAO cadastroAgendaDAO;

	public CadastroAgenda retornarCadastroAgendaPorID(Long id) throws NegocioException {
		return cadastroAgendaDAO.retornarCadastroAgendaPorID(id);
	}

	public List<CadastroAgenda> listarTodos() {

		return cadastroAgendaDAO.listarTodos();
	}
	
	public List<Veiculo> buscarVeiculoPorInstrutor(Long idInstrutor) {
		return cadastroAgendaDAO.buscarVeiculoPorInstrutor(idInstrutor);
	}
	
	public List<Agenda> listarPrevisao(Long id) {
		return cadastroAgendaDAO.listarPrevisao(id);
	}
	
	public List<CadastroAgenda> listarAgendamentoPorEmpresa(Long id) {
		return cadastroAgendaDAO.listarAgendamentoPorEmpresa(id);
	}

	public void salvar(CadastroAgenda cadastroAgenda) throws NegocioException {

		if (cadastroAgenda.getAluno().getId() == null) {
			throw new NegocioException("Descrição do Ação deve ser informada.");
		}

		this.cadastroAgendaDAO.salvar(cadastroAgenda);

	}

	public void excluir(CadastroAgenda cadastroAgenda) throws NegocioException {

		if (cadastroAgenda == null) {
			throw new NegocioException("Ação deve ser selecionada.");
		} else if (cadastroAgenda.getId() <= 0) {
			throw new NegocioException("Ação deve ser selecionada.");
		}

		this.cadastroAgendaDAO.excluir(cadastroAgenda);
	}

}
