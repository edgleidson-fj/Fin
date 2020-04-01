package Bean;

//import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import Dao.CartaoDAO;
import Domain.Cartao;
import Util.FacesUtil;

@ManagedBean
@ViewScoped
public class CartaoBean {

	private Cartao cartaoCadastro;
	private List<Cartao> listaCartao;
	private List<Cartao> listaCartaoFiltrados;
	
	private String acao;
	private Integer codigo;
	// --------------------------------------------------------------------------------------------------------

	public Cartao getCartaoCadastro() {
		if (cartaoCadastro == null) {
			cartaoCadastro = new Cartao();
		}
		return cartaoCadastro;
	}

	public void setCartaoCadastro(Cartao cartaoCadastro) {
		this.cartaoCadastro = cartaoCadastro;
	}

	public List<Cartao> getListaCartao() {
		return listaCartao;
	}

	public void setListaCartao(List<Cartao> listaCartao) {
		this.listaCartao = listaCartao;
	}

	public List<Cartao> getListaCartaoFiltrados() {
		return listaCartaoFiltrados;
	}

	public void setListaCartaoFiltrados(List<Cartao> listaCartaoFiltrados) {
		this.listaCartaoFiltrados = listaCartaoFiltrados;
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
	// --------------------------------------------------------------------------------------------------------

	// Novo
	public void novo() {
		cartaoCadastro = new Cartao();
	}

	// Salvar
	public void salvar() {
		try {
			FacesUtil.addMsgInfo("Cartao cadastrada com sucesso");
			CartaoDAO cdao = new CartaoDAO();
			cdao.salvar(cartaoCadastro);
			cartaoCadastro = new Cartao();

		} catch (RuntimeException ex) {
			FacesUtil.addMsgErro("ERRO: " + ex.getMessage());
			throw ex;
		}
	}

	// Pesquisa
	public void carregarPesquisa() {
		try {						
			CartaoDAO cdao = new CartaoDAO();
			listaCartao = cdao.listar();

		} catch (RuntimeException ex) {
			FacesUtil.addMsgErro("ERRO" + ex.getMessage());
			throw ex;
		}
	}
	// --------------------------------------------------------------------------------------------------------

	// Carregar Cadastro    
	public void carregarCadastro() {
		try {
			acao = FacesUtil.getParam("catacao");//Recebendo Paramentro para ocultar alguns botï¿½es			
			String valor = FacesUtil.getParam("catcodigo");
			CartaoDAO cdao = new CartaoDAO();		
			
			if (valor != null) {					
				int cod =(int) Long.parseLong(valor);
				//CategoriaDAO cdao = new CategoriaDAO();
				cartaoCadastro = cdao.buscarPorCodigo(cod);
				}
				//CategoriaDAO dao = new CategoriaDAO();
				listaCartao = cdao.listar();
				
		} catch (RuntimeException ex) {
			FacesUtil.addMsgErro("ERRO" + ex.getMessage());
			throw ex;
		}
	}
	
	// Excluir
		public void excluir() {
			try {
				CartaoDAO cdao = new CartaoDAO();
				cdao.excluir(cartaoCadastro);
				FacesUtil.addMsgInfo("Cartao excluído com sucesso");
				cartaoCadastro = new Cartao();
				
			} catch (RuntimeException ex) {
				FacesUtil.addMsgErro("ERRO: " + ex.getMessage());
				throw ex;
			}
		}
		
		//Editar
		public void editar(){
			try {
				FacesUtil.addMsgInfo("Cartao editado com sucesso");
				CartaoDAO cdao = new CartaoDAO();
				cdao.editar(cartaoCadastro);
				cartaoCadastro = new Cartao();
				
			} catch (RuntimeException ex) {
				FacesUtil.addMsgErro("ERRO "+ ex.getMessage());
				throw ex;
			}
		}

	// ----------------------------
		
		
		
}
