package Bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
//import org.apache.commons.codec.digest.DigestUtils;

import org.apache.commons.codec.digest.DigestUtils;

import Dao.UsuarioDAO;

import Domain.Usuario;
import Util.FacesUtil;

@ManagedBean
@ViewScoped
public class UsuarioBean {

	private Usuario usuarioCadastro;
	private List<Usuario> listaUsuario;

	private String acao;
	private int codigo;
	// ----------------------------------------------------------------------------------------------------------

	public Usuario getUsuarioCadastro() {
		if (usuarioCadastro == null) {
			usuarioCadastro = new Usuario();
		}
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	// ----------------------------------------------------------------------------------------------------------

	// Novo
	public void novo() {
		usuarioCadastro = new Usuario();
	}

	// Salvar
	public void salvar() {
		try {
			FacesUtil.addMsgInfo("Usuário cadastrado com sucesso!");
			UsuarioDAO udao = new UsuarioDAO();

			// Sem Criptografia
			//usuarioCadastro.setSenha((usuarioCadastro.getSenha()));

			// Com Criptografia
			 usuarioCadastro.setSenha(DigestUtils.md5Hex(usuarioCadastro.getSenha()));
			// DigesUtils.md5Hex = Criptografia da senha.

			udao.salvar(usuarioCadastro);
			usuarioCadastro = new Usuario();

		} catch (RuntimeException ex) {
			FacesUtil.addMsgErro("Não é possível Cadastrar Usuário " + ex.getMessage());
			throw ex;
		}
	}

	// Pesquisa
	public void carregarPesquisa() {
		try {
			UsuarioDAO udao = new UsuarioDAO();
			listaUsuario = udao.listar();
		} catch (RuntimeException ex) {
			FacesUtil.addMsgErro("Erro" + ex.getMessage());
			throw ex;
		}
	}

	// Carregar Cadastro
	public void carregarCadastro() {
		try {
			acao = FacesUtil.getParam("funacao");
			String valor = FacesUtil.getParam("funcodigo");

			if (valor != null) {
				int cod = (int) Long.parseLong(valor);
				UsuarioDAO udao = new UsuarioDAO();
				usuarioCadastro = udao.buscarPorCodigo(cod);
			} else {
				usuarioCadastro = new Usuario();
			}

		} catch (RuntimeException ex) {
			FacesUtil.addMsgErro("Erro " + ex.getMessage());
			throw ex;
		}
	}

	// Excluir
	public void excluir() {
		try {
			UsuarioDAO udao = new UsuarioDAO();
			udao.excluir(usuarioCadastro);
			FacesUtil.addMsgInfo("Usuario excluído com sucesso!");
			usuarioCadastro = new Usuario();

		} catch (RuntimeException ex) {
			FacesUtil.addMsgErro("ERRO ao excluir Usuário " + ex.getMessage());
			throw ex;
		}
	}

	// Editar
	public void editar() {
		try {
			FacesUtil.addMsgInfo("Usuario editado com sucesso!");
			UsuarioDAO udao = new UsuarioDAO();

			// Sem Criptografia
			//usuarioCadastro.setSenha((usuarioCadastro.getSenha()));

			// Com Criptografia
			 usuarioCadastro.setSenha(DigestUtils.md5Hex(usuarioCadastro.getSenha()));
			// DigesUtils.md5Hex = Criptografia da senha.

			udao.editar(usuarioCadastro);
			usuarioCadastro = new Usuario();

		} catch (RuntimeException ex) {
			FacesUtil.addMsgErro("ERRO ao editar Usuário " + ex.getMessage());
			throw ex;
		}
	}

	/*/ Inserindo o usuÃ¡rio PADRÃƒO automaticamente
	public void inserirUsuario() {
		UsuarioDAO dao = new UsuarioDAO();

		if (dao.buscarPorCodigo(1) == null) {
			Usuario usuario = new Usuario();
			usuario.setCodigo(1);
			usuario.setNome("dvd");
			usuario.setCpf("Visitante");
			usuario.setSenha("");

			dao.salvar(usuario);
		}
	}
	// ----------------------------*/
}
