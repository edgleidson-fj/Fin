package Util;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

public class FacesUtil {

	// INFO
	public static void addMsgInfo(String mensagem) {
		FacesMessage facesMensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem);
		FacesContext facesContext = FacesContext.getCurrentInstance();

		// Para o GROWL funcionar, quando for mudar de página utilizando(?Faces-Redirect = true)
		ExternalContext externalContext = facesContext.getExternalContext();
		Flash flash = externalContext.getFlash();
		flash.setKeepMessages(true);
		facesContext.addMessage(null, facesMensagem);
	}
	// ------------------------------------------------------------------------------------------------------------

	// ERRO
	public static void addMsgErro(String mensagem) {
		FacesMessage facesMensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, mensagem);
		FacesContext facesContext = FacesContext.getCurrentInstance();

		// Para o GROWL funcionar, quando for mudar de página utilizando(?Faces-Redirect = true)
		ExternalContext externalContext = facesContext.getExternalContext();
		Flash flash = externalContext.getFlash();
		flash.setKeepMessages(true);
		facesContext.addMessage(null, facesMensagem);
	}
	// ------------------------------------------------------------------------------------------------------------

	// <p:Param />
	public static String getParam(String nome) {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		ExternalContext externalContext = facesContext.getExternalContext();

		Map<String, String> parametros = externalContext.getRequestParameterMap();

		String valor = parametros.get(nome);

		return valor;
	}
	// ------------------
}
