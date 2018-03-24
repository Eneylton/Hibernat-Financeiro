package com.java.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.java.dao.CategoriaFinanceiroDAO;
import com.java.modelo.CategoriaFinanceiro;
import com.java.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = CategoriaFinanceiro.class)
public class CategoriaFinanceiroConverter implements Converter {

	private CategoriaFinanceiroDAO categoriaFinanceiroDAO;

	public CategoriaFinanceiroConverter() {
		this.categoriaFinanceiroDAO = CDIServiceLocator.getBean(CategoriaFinanceiroDAO.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		CategoriaFinanceiro retorno = null;

		try {
			if (value != null) {
				retorno = this.categoriaFinanceiroDAO.retornarCategoriaFinanceiroPorID(new Long(value));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((CategoriaFinanceiro) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());
			return retorno;
		}
		return "";
	}

}