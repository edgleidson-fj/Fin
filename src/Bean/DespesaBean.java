package Bean;


//import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
//import java.time.Month;
//import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
//import java.text.DecimalFormat;
import java.util.List;

import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import Dao.CartaoDAO;
import Dao.CategoriaDAO;
import Dao.DespesaDAO;
import Domain.Cartao;
import Domain.Categoria;
import Domain.Despesa;
import Filter.DespesaFilter;
import Util.FacesUtil;

@ManagedBean
@ViewScoped
public class DespesaBean  {
	
	//private static final GregorianCalendar primeiro_DAY = new GregorianCalendar(1930, Calendar.JANUARY, 1, 0, 0, 0);

	
	

	private List<Categoria> listaCategoria;
	private List<Cartao> listaCartao;
	
	private Despesa dvdCadastro;
	private List<Despesa> listaDvd;
	private List<Despesa> listaDvdFiltrados;

	private String acao;
	private Integer codigo;
	
	private DespesaFilter filtro;
	// ---------------------------------------------------------------------------------------------------------

	public List<Categoria> getListaCategoria() {
		return listaCategoria;
	}

	public void setListaCategoria(List<Categoria> listaCategoria) {
		this.listaCategoria = listaCategoria;
	}
	
	


	public List<Cartao> getListaCartao() {
		return listaCartao;
	}

	public void setListaCartao(List<Cartao> listaCartao) {
		this.listaCartao = listaCartao;
	}

	public Despesa getDvdCadastro() {
		if(dvdCadastro == null) {
			dvdCadastro = new Despesa();
		}
		return dvdCadastro;
	}

	public void setDvdCadastro(Despesa dvdCadastro) {
		this.dvdCadastro = dvdCadastro;
	}

	public List<Despesa> getListaDvd() {
				return listaDvd;
	}

	public void setListaDvd(List<Despesa> listaDvd) {
		this.listaDvd = listaDvd;
	}

	public List<Despesa> getListaDvdFiltrados() {
		return listaDvdFiltrados;
	}

	public void setListaDvdFiltrados(List<Despesa> listaDvdFiltrados) {
		this.listaDvdFiltrados = listaDvdFiltrados;
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
	
	//---
	public void setFiltro(DespesaFilter filtro) {
		this.filtro = filtro;
	}

	// NecessÃ¡rio instaciar o filtro
	public DespesaFilter getFiltro() {
		if (filtro == null) {
			filtro = new DespesaFilter();
		}
		return filtro;
	}
	
	
	// ----------------------------------------------------------------------------------------------------------

	// Novo
	public void novo() {
		dvdCadastro = new Despesa();
	}

	// Salvar  LANÇAMENTO DO DIA
	public void salvar() {
		try {
			FacesUtil.addMsgInfo("Lançamento cadastrado com sucesso!");
			DespesaDAO dao = new DespesaDAO();
			CartaoDAO cdao = new CartaoDAO();
		    Cartao c = cdao.buscarPorCodigo(1);//Padrão
		      
			dvdCadastro.setPago("S");
			dvdCadastro.setCancelamento("N");  //Não
			dvdCadastro.setTipo("N"); //Normal
			if(dvdCadastro.getCartao()== null) {	
          	  dvdCadastro.setCartao(c); //1
		      }
			dao.salvar(dvdCadastro);
			dvdCadastro = new Despesa();

		} catch (RuntimeException ex) {
			FacesUtil.addMsgErro("ERRO: " + ex.getMessage());
			throw ex;
		}
	}
	
	
	// Salvar Despesa Futuro
		public void salvarFuturo() {
			try {
				FacesUtil.addMsgInfo("Lançamento cadastrado com sucesso!");
				DespesaDAO dao = new DespesaDAO();
				CartaoDAO cdao = new CartaoDAO();
				Cartao c = cdao.buscarPorCodigo(1);
					
				dvdCadastro.setCancelamento("N");  
				dvdCadastro.setPago("N");
				dvdCadastro.setCartao(c);
				dvdCadastro.setTipo("N"); //Normal
				dao.salvar(dvdCadastro);
				dvdCadastro = new Despesa();

			} catch (RuntimeException ex) {
				FacesUtil.addMsgErro("ERRO: " + ex.getMessage());
				throw ex;
			}
		}

	// Pesquisa
	public void carregarPesquisa() {
		try {
			DespesaDAO dao = new DespesaDAO();
			listaDvd = dao.listar();

		} catch (RuntimeException ex) {
			FacesUtil.addMsgErro("ERRO" + ex.getMessage());
			throw ex;
		}
	}
	
	// Carregar Cadastro
	public void carregarCadastro() {
		try {
			acao = FacesUtil.getParam("proacao");// Recebendo Paramentro para ocultar alguns botï¿½es
			String valor = FacesUtil.getParam("procodigo");
			//DespesaDAO dao = new DespesaDAO();
			
			if (valor != null) {
				int cod = (int) Long.parseLong(valor);
				DespesaDAO dao = new DespesaDAO();
				dvdCadastro = dao.buscarPorCodigo(cod);
			} else {
				dvdCadastro = new Despesa();
			}			
			CategoriaDAO cdao = new CategoriaDAO(); 
			listaCategoria = cdao.listar();	
			
			
			
			CartaoDAO ctdao = new CartaoDAO(); 
			listaCartao = ctdao.listar();
			
		} catch (RuntimeException ex) {
			FacesUtil.addMsgErro("ERRO" + ex.getMessage());
			throw ex;
		}
	}

	/*/ Excluir
	public void excluir() {
		try {
			DvdDAO dao = new DvdDAO();
			dao.excluir(dvdCadastro);			
			FacesUtil.addMsgInfo("Dvd excluÃ­do com sucesso!");
			dvdCadastro = new Dvd();
		} catch (RuntimeException ex) {
			FacesUtil.addMsgErro("ERRO: " + ex.getMessage());
			throw ex;
		}
	}*/

	// Editar
	public void editar() {
		try {
			FacesUtil.addMsgInfo("Lançamento corrigido com sucesso!");
			DespesaDAO dao = new DespesaDAO();
			dao.editar(dvdCadastro);			
		} catch (RuntimeException ex) {
			FacesUtil.addMsgErro("ERRO " + ex.getMessage());
			throw ex;
		}
	}
	
	// Editar
		public void pagamentoEfetuado() {
			try {
				FacesUtil.addMsgInfo("Pagamento efetuado com sucesso!");
				DespesaDAO dao = new DespesaDAO();
				
				Date dataHoje = new Date();
				dataHoje.getTime();			
				
				dvdCadastro.setDataPagamento(dataHoje); 
				dvdCadastro.setPago("S");
				dao.editar(dvdCadastro);			
			} catch (RuntimeException ex) {
				FacesUtil.addMsgErro("ERRO " + ex.getMessage());
				throw ex;
			}
		}
	
	// Cancelar
		public void cancelar() {
			try {
				FacesUtil.addMsgInfo("Lançamento cancelado com sucesso!");
				DespesaDAO dao = new DespesaDAO();
				dvdCadastro.setCancelamento("S");
				dvdCadastro.setPago("C");
				dvdCadastro.setFormaPagamento("CANCELADO");
				dvdCadastro.setObs("CANCELADO");
				dao.editar(dvdCadastro);			
			} catch (RuntimeException ex) {
				FacesUtil.addMsgErro("ERRO " + ex.getMessage());
				throw ex;
			}
		}
	
	public void carregarLista() {
		DespesaDAO dao = new DespesaDAO();
		//listaDvd = dao.listar();
		try {
		listaDvd = dao.listarTudoDecrecente();
		}catch(SQLException e) {
			//erro
		}
	}
	
	public void listarPorPagamentoCancelamento() {				 
		 DespesaDAO dao = new DespesaDAO();
				try {
			listaDvd = dao.listarPagoCancelamento();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void listarPorMesPagamentoCancelamento() {		 
		 DespesaDAO dao = new DespesaDAO();
				try {
			listaDvd = dao.listarMesPagoCancelamento();
			
			Calendar datahoje = Calendar.getInstance();
			Calendar anohoje = Calendar.getInstance();
			int proxAno = anohoje.get(Calendar.YEAR)+1;
			int mesAtual = datahoje.get(Calendar.MONTH);
			if(mesAtual == 11) {
			FacesUtil.addMsgInfo("Favor cadastrar as despesas fixa de "+ proxAno);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// ----------------------------	
	
	 public void listarPagoMesAnterior() {		 
		 DespesaDAO dao = new DespesaDAO();
				try {
				listaDvd = dao.listarMesAnt();					
		/*			Calendar datahoje = Calendar.getInstance();
					int mesAtual = datahoje.get(Calendar.MONTH);
					System.out.println(mesAtual);*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 // ----------------------------/
public void listarPagoMes() {		 
		 DespesaDAO dao = new DespesaDAO();
				try {
		//	listaDvd = dao.listarTudoMesAtual();
					listaDvd = dao.listarMesPago();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	// Carregar Cadastro
		public void carregarCadastro2() throws SQLException {
			try {
				acao = FacesUtil.getParam("proacao");// Recebendo Paramentro para ocultar alguns botï¿½es
				String valor = FacesUtil.getParam("procodigo");
				DespesaDAO dao = new DespesaDAO();
				
				if(acao == null) {/**/
				if (valor != null) {
					int cod = (int) Long.parseLong(valor);
					//DespesaDAO dao = new DespesaDAO();
					dvdCadastro = dao.buscarPorCodigo(cod);
				} else {
					dvdCadastro = new Despesa();
				}			
				//CategoriaDAO cdao = new CategoriaDAO(); //Listagem de Fabricante
				//listaCategoria = cdao.listar();
				}/**/
				CategoriaDAO cdao = new CategoriaDAO(); //Listagem de Fabricante
				listaCategoria = cdao.listar();
				listaDvd = dao.listarDia();
				/**/
				
				CartaoDAO ctdao = new CartaoDAO(); 
				listaCartao = ctdao.listar();
				
			
			} catch (RuntimeException ex) {
				FacesUtil.addMsgErro("ERRO" + ex.getMessage());
				throw ex;
			}
		}		
		
		// Buscar por Período - Filtrar
		public void buscar() {
			try {
				DespesaDAO dao = new DespesaDAO();
				listaDvdFiltrados = dao.buscar(filtro);
				
				/*for(Despesa desp : listaDvdFiltrados) {
					System.out.println(desp);
				}*/				
			} catch (RuntimeException ex) {
				FacesUtil.addMsgErro("Erro ao tentar buscar uma venda: " + ex.getMessage());
			}
		}
		// ----------------	 
		
		// Valor Total no DataTable
		public List<Despesa> getDespesas() {
	        return listaDvd;
	    }		
		public String getValorTotal() {
	        Double total = 0.00D;	 
	        for(Despesa despesa : getDespesas()) {
	            total += despesa.getValor();
	        }	 
	        return new DecimalFormat("###,###.##").format(total);
	    }	
		
		// Listar Despesas Futura
		public void carregarListaFutura() {
			DespesaDAO dao = new DespesaDAO();
			try {
			listaDvd = dao.listarLancFuturo();
			
			Calendar datahoje = Calendar.getInstance();
			Calendar anohoje = Calendar.getInstance();
			int proxAno = anohoje.get(Calendar.YEAR)+1;
			int mesAtual = datahoje.get(Calendar.MONTH);
			if(mesAtual == 11) {
			FacesUtil.addMsgInfo("Favor cadastrar as despesas fixa de "+ proxAno);
			}
			}catch(SQLException e) {
				//erro
			}
		}
		//----------------------
		
		// Réplicar Despesas
		public void replicar() {
			try {
				Long total = dvdCadastro.getParcela(); // Réplicar o número de parcelas informadas
				int x = 0; //Contador
				
				//Verificar possibilidade de criar a opção de dia e mês de pagamento				
				while(x<total) {
					DespesaDAO dao = new DespesaDAO();	
					CartaoDAO cdao = new CartaoDAO();
				      Cartao c = cdao.buscarPorCodigo(1); //Padrão
				      
					int contador = x + 1;					
					Calendar cal = Calendar.getInstance();
			        int diaSistema = cal.get(Calendar.DATE);
			        int mesSistema = cal.get(Calendar.MONTH) + 1;
			        int anoSistema = cal.get(Calendar.YEAR);					
					
			        GregorianCalendar hoje = new GregorianCalendar(anoSistema, mesSistema+contador, diaSistema, 0, 0, 0);
			        int numero=0;
			        hoje.add(Calendar.DATE, numero - 0);
			        hoje.add(Calendar.MONTH, numero - 1);
			        Date inicioPagamento = hoje.getTime();
			        Long numeroParcela = Long.valueOf(contador);
			      
			      dvdCadastro.setCancelamento("N");  //Não
			      dvdCadastro.setPago("N"); //Não
			      dvdCadastro.setDataPagamento(inicioPagamento); //Início da repetição
			      dvdCadastro.setParcela(numeroParcela); //Número da parcela			    
			      if(dvdCadastro.getCartao()== null) {	
                	  dvdCadastro.setCartao(c); //1
			      }		
					dao.salvar(dvdCadastro);
					x++;
				}								
				dvdCadastro = new Despesa();
			} catch (RuntimeException ex) {
				FacesUtil.addMsgErro("ERRO" + ex.getMessage());
				throw ex;
			}
		}
				// ----------------	 
}
