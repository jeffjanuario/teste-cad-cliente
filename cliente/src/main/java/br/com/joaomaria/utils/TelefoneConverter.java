package br.com.joaomaria.utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("telefoneConverter")
public class TelefoneConverter implements Converter {

	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (value == null) {
			return null;
		}
		String telefone = value.toString();

		if (value != null && !value.equals(""))
			telefone = telefone.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(" ", "").replaceAll("\\-", "");

		
		try {
			String codigoArea = telefone.substring(0, 2);
			String cincoPrimeiros = telefone.substring(2, 7);
			String resto = telefone.substring(7);
			return "(" + codigoArea + ") " + cincoPrimeiros + "-" + resto;
		} catch (IndexOutOfBoundsException e) {
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Problemas na conversão do telefone.",
					"Ele deve ser informado com código do país, de área e o número. Ex: (99) 99999-9999");

			throw new ConverterException(facesMessage);
		}
	}

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}

		String telefone = value;
		if (value != null && !value.equals(""))
			telefone = value.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(" ", "").replaceAll("\\-", "");

		return telefone;
	}
}
