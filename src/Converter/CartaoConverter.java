package Converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import Dao.CartaoDAO;
import Domain.Cartao;

@FacesConverter("cartaoConverter")
public class CartaoConverter implements Converter {

	//@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String valor) {
		try {
			int codigo = Integer.parseInt(valor);
			
			CartaoDAO cdao = new CartaoDAO();
			Cartao cat= cdao.buscarPorCodigo(codigo);
			return cat;
			
		} catch (RuntimeException ex) {
			return null;
		}

	}
	// ----

	//@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object objeto) {
		try {
			Cartao cat = (Cartao) objeto;
			Long codigo = (long) cat.getCodigo();
			return codigo.toString();
			
		} catch (RuntimeException ex) {
			return null;
		}
	}

}
