package Bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.codec.digest.DigestUtils;

//import org.apache.commons.codec.digest.DigestUtils;

import Dao.UsuarioDAO;
import Domain.Usuario;
import Util.FacesUtil;

@ManagedBean
@SessionScoped
public class AutenticacaoBean {
	
	private Usuario usuarioLogado;
	// -----------------------------------------------------------------------------------------------
	public Usuario getUsuarioLogado() {
		if(usuarioLogado == null) {
			usuarioLogado = new Usuario();
		}
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
		// -----------------------------------------------------------------------------------------------

	public String autenticar() {
		try {
			UsuarioDAO usudao = new UsuarioDAO();
			
			// Sem criptografia
			//usuarioLogado = usudao.autenticar(usuarioLogado.getCpf(), usuarioLogado.getSenha());
			
			// Com criptografia
			usuarioLogado = usudao.autenticar(usuarioLogado.getCpf(), DigestUtils.md5Hex(usuarioLogado.getSenha()));
			// DigesUtils.md5Hex = Criptografia da senha.
			
			if (usuarioLogado == null) {
				FacesUtil.addMsgErro("Login ou senha errado!");
				return null;
				
			} else {
				FacesUtil.addMsgInfo("Login executado com sucesso!");
				System.out.println("Entrou");
				return "/pages/despLista.xhtml?faces-redirect=true";				
			}

		} catch (RuntimeException ex) {
			FacesUtil.addMsgErro("Erro " + ex.getMessage());
			return null;
		}
	}
		
	// Sair do Login voltando para tela de autenticação
	public String sair( ){
		usuarioLogado = null;
		System.out.println("Sair");
		return "/pages/autenticacao.xhtml?faces-redirect=true"; 
		// Método similar ao "outcome" das páinas "xhtml".
	}
	// -------------
}
