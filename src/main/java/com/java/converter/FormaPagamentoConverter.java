package com.java.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.java.dao.FormaPagamentoDAO;
import com.java.modelo.FormaPagamento;
import com.java.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = FormaPagamento.class)
public class FormaPagamentoConverter implements Converter {

	private FormaPagamentoDAO formaPagamentoDAO;

	public FormaPagamentoConverter() {
		this.formaPagamentoDAO = CDIServiceLocator.getBean(FormaPagamentoDAO.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		FormaPagamento retorno = null;

		try {
			if (value != null) {
				retorno = this.formaPagamentoDAO.retornarFormaPagamentoPorID(new Long(value));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((FormaPagamento) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());
			return retorno;
		}
		return "";
	}

}