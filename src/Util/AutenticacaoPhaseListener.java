package Util;

import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;

import Bean.AutenticacaoBean;
import Domain.Usuario;

@SuppressWarnings("serial")
public class AutenticacaoPhaseListener implements PhaseListener {

	public void afterPhase(PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		UIViewRoot uiViewRoot = facesContext.getViewRoot();
		String paginaAtual = uiViewRoot.getViewId();

		// A pÃ¡gina de autenticaÃ§Ã£o deve ser a Ãºnica pÃ¡gina pÃºblica.
		boolean ehPaginaAutenticacao = paginaAtual.contains("principal2.xhtml");

		// As pÃ¡ginas que precisa de autenticaÃ§Ã£o dos usuÃ¡rios.
		if (!ehPaginaAutenticacao) {
			ExternalContext externalContext = facesContext.getExternalContext();

			// Mapa da sessÃ£o
			Map<String, Object> mapa = externalContext.getSessionMap();
			AutenticacaoBean autenticacaoBean = (AutenticacaoBean) mapa.get("autenticacaoBean");

			// Usuario
			Usuario usuario = autenticacaoBean.getUsuarioLogado();
			System.out.println("USUÁRIO: " + usuario);

			// Verificando CPF = null
			if (usuario.getCpf() == null) {
				FacesUtil.addMsgErro("USUÁRIO NÃO AUTENTICADO!");

				// ForÃ§ando a navegaÃ§Ã£o para apenas usuÃ¡rios autenticados, voltando para pÃ¡gina
				// de autenticacao.xhtml.
				Application application = facesContext.getApplication();
				NavigationHandler navigationHandler = application.getNavigationHandler();
				navigationHandler.handleNavigation(facesContext, null, "/pages/principal2.xhtml");
				// (FacesContext,Null, EndereÃ§o desejado)
			}
			/*/ Verificando UsuÃ¡rio Tipo = 1
			if (usuario.getTipo() == "1") {
				FacesUtil.addMsgErro("UsuÃ¡rio nÃ£o autenticado!");

				// ForÃ§ando a navegaÃ§Ã£o para apenas usuÃ¡rios autenticados, voltando para pÃ¡gina
				// de autenticacao.xhtml.
				Application application = facesContext.getApplication();
				NavigationHandler navigationHandler = application.getNavigationHandler();
				navigationHandler.handleNavigation(facesContext, null, "/pages/autenticacao.xhtml");
				// (FacesContext,Null, EndereÃ§o desejado)
			} */
		}
		System.out.println(paginaAtual);		
		//----------------------------------*/
		
		AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
		System.out.println("autenticacaoBean "+ autenticacaoBean);
		
		 paginaAtual = Faces.getViewId();
		System.out.println("Pagina atual "+ paginaAtual);		
	}

	public void beforePhase(PhaseEvent event) {

	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
