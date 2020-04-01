package Bean;

//import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import Dao.CategoriaDAO;
import Domain.Categoria;
import Util.FacesUtil;

@ManagedBean
@ViewScoped
public class CategoriaBean {

	private Categoria categoriaCadastro;
	private List<Categoria> listaCategoria;
	private List<Categoria> listaCategoriaFiltrados;
	
	private String acao;
	private Integer codigo;
	// --------------------------------------------------------------------------------------------------------

	public Categoria getCategoriaCadastro() {
		if (categoriaCadastro == null) {
			categoriaCadastro = new Categoria();
		}
		return categoriaCadastro;
	}

	public void setCategoriaCadastro(Categoria categoriaCadastro) {
		this.categoriaCadastro = categoriaCadastro;
	}

	public List<Categoria> getListaCategoria() {
		return listaCategoria;
	}

	public void setListaCategoria(List<Categoria> listaCategoria) {
		this.listaCategoria = listaCategoria;
	}

	public List<Categoria> getListaCategoriaFiltrados() {
		return listaCategoriaFiltrados;
	}

	public void setListaCategoriaFiltrados(List<Categoria> listaCategoriaFiltrados) {
		this.listaCategoriaFiltrados = listaCategoriaFiltrados;
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
		categoriaCadastro = new Categoria();
	}

	// Salvar
	public void salvar() {
		try {
			FacesUtil.addMsgInfo("Categoria cadastrada com sucesso");
			CategoriaDAO cdao = new CategoriaDAO();
			cdao.salvar(categoriaCadastro);
			categoriaCadastro = new Categoria();

		} catch (RuntimeException ex) {
			FacesUtil.addMsgErro("ERRO: " + ex.getMessage());
			throw ex;
		}
	}

	// Pesquisa
	public void carregarPesquisa() {
		try {						
			CategoriaDAO cdao = new CategoriaDAO();
			listaCategoria = cdao.listar();

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
			CategoriaDAO cdao = new CategoriaDAO();		
			
			if (valor != null) {					
				int cod =(int) Long.parseLong(valor);
				//CategoriaDAO cdao = new CategoriaDAO();
				categoriaCadastro = cdao.buscarPorCodigo(cod);
				}
				//CategoriaDAO dao = new CategoriaDAO();
				listaCategoria = cdao.listar();
				
		} catch (RuntimeException ex) {
			FacesUtil.addMsgErro("ERRO" + ex.getMessage());
			throw ex;
		}
	}
	
	// Excluir
		public void excluir() {
			try {
				CategoriaDAO cdao = new CategoriaDAO();
				cdao.excluir(categoriaCadastro);
				FacesUtil.addMsgInfo("Categoria excluído com sucesso");
				categoriaCadastro = new Categoria();
				
			} catch (RuntimeException ex) {
				FacesUtil.addMsgErro("ERRO: " + ex.getMessage());
				throw ex;
			}
		}
		
		//Editar
		public void editar(){
			try {
				FacesUtil.addMsgInfo("Categoria editado com sucesso");
				CategoriaDAO cdao = new CategoriaDAO();
				cdao.editar(categoriaCadastro);
				categoriaCadastro = new Categoria();
				
			} catch (RuntimeException ex) {
				FacesUtil.addMsgErro("ERRO "+ ex.getMessage());
				throw ex;
			}
		}

	// ----------------------------
		
		
		
}
