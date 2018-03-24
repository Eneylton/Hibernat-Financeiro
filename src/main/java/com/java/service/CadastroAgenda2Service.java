package com.java.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.CadastroAgenda2DAO;
import com.java.modelo.CadastroAgenda2;
import com.java.modelo.Veiculo;
import com.java.util.NegocioException;

public class CadastroAgenda2Service implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroAgenda2DAO cadastroAgenda2DAO;

	public CadastroAgenda2 retornarCadastroAgenda2PorID(Long id) throws NegocioException {
		return cadastroAgenda2DAO.retornarCadastroAgenda2PorID(id);
	}

	public List<CadastroAgenda2> listarTodos() {

		return cadastroAgenda2DAO.listarTodos();
	}
	
	public List<Veiculo> buscarVeiculoPorInstrutor(Long idInstrutor) {
		return cadastroAgenda2DAO.buscarVeiculoPorInstrutor(idInstrutor);
	}

	public void salvar(CadastroAgenda2 cadastroAgenda2) throws NegocioException {

		if (cadastroAgenda2.getAluno().getId() == null) {
			throw new NegocioException("Descrição do Ação deve ser informada.");
		}

		this.cadastroAgenda2DAO.salvar(cadastroAgenda2);

	}
	
	public void editar(CadastroAgenda2 cadastroAgenda2) {
		this.cadastroAgenda2DAO.alterar(cadastroAgenda2);
	}

	public void excluir(CadastroAgenda2 cadastroAgenda2) throws NegocioException {

		if (cadastroAgenda2 == null) {
			throw new NegocioException("Ação deve ser selecionada.");
		} else if (cadastroAgenda2.getId() <= 0) {
			throw new NegocioException("Ação deve ser selecionada.");
		}

		this.cadastroAgenda2DAO.excluir(cadastroAgenda2);
	}

}
