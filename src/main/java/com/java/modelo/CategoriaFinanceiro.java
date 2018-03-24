package com.java.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the categoria_financeiro database table.
 * 
 */
@Entity
@Table(name="categoria_financeiro")
@NamedQuery(name="CategoriaFinanceiro.findAll", query="SELECT c FROM CategoriaFinanceiro c")
public class CategoriaFinanceiro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String descricao;

	public CategoriaFinanceiro() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}