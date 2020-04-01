package Dao;

import java.sql.Connection;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

//import Domain.Dvd;
import Util.HibernateUtil;
import Domain.Cartao;
import Domain.Categoria;
import Domain.Despesa;
//import Domain.Venda;
import Filter.DespesaFilter;
//import Filter.DespesaFilter2;
//import domain.DiaVencimento;
//import domain.GastoFixo;
import factory.ConexaoFactory;

public class DespesaDAO {

	// INSERT
	public void salvar(Despesa dvd) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			session.save(dvd);
			transaction.commit();

		} catch (RuntimeException ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw ex;

		} finally {
			session.close();
		}
	}
	// ----------------------------------------------------------------------------------------------------------

	// SELECT - Listar
	@SuppressWarnings("unchecked")
	public List<Despesa> listar() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Despesa> dvds = null;

		try {
			Query consulta = session.getNamedQuery("Despesa.listar");
			dvds = consulta.list();

		} catch (RuntimeException ex) {
			throw ex;

		} finally {
			session.close();
		}
		return dvds;
	}
	// -----------------------------------------------------------------------------------------------------------

	// SELECT - Buscar por codigo
	public Despesa buscarPorCodigo(int codigo) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Despesa dvd = null;

		try {
			Query consulta = session.getNamedQuery("Despesa.buscarPorCodigo");
			consulta.setInteger("codigo", codigo);
			dvd = (Despesa) consulta.uniqueResult();

		} catch (RuntimeException ex) { // Erro
			throw ex;

		} finally {
			session.close();
		}
		return dvd;
	}
	// ----------------------------------------------------------------------------------------------------------

	// UPDATE
	public void editar(Despesa dvd) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			session.update(dvd);
			transaction.commit();

		} catch (RuntimeException ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw ex;

		} finally {
			session.close();
		}
	}
	// -------------------------------------------------------------------------------------------------

	/*/ ---------------------------------*/
	// LISTAR por Status (N= inativo/ S= ativo)  "Não estou usando"
	public ArrayList<Despesa> listarPagoCancelamento() throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * ");
		sql.append(" FROM tbldesp ");
		sql.append("INNER JOIN tblcat ON tblcat.catid = tbldesp.tblcat_catid ");
		sql.append("WHERE  despag = 'N' and despcanc != 'S'  ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		// Resultado da Query
		ResultSet resultado = comando.executeQuery();

		// Criando um Array para os Resultados do código acima
		ArrayList<Despesa> itens = new ArrayList<Despesa>();

		// Listando os Categorias e Gastos
		while (resultado.next()) {
			Categoria c = new Categoria(); // Categoria
			c.setCodigo(resultado.getInt("tblcat.catid"));
			c.setDescricao(resultado.getString("tblcat.catdesc"));

			Despesa g = new Despesa(); // Gasto
			g.setCodigo(resultado.getLong("tbldesp.despid"));
			g.setDescricao(resultado.getString("tbldesp.despdesc"));
			g.setDataVencimento(resultado.getDate("tbldesp.despdatavenc"));
			g.setDataPagamento(resultado.getDate("tbldesp.despdatapag"));
			g.setValor(resultado.getDouble("tbldesp.despval"));
			g.setPago(resultado.getString("tbldesp.despag"));
			g.setCancelamento(resultado.getString("tbldesp.despcanc"));
			g.setFormaPagamento(resultado.getString("tbldesp.despformpag"));
			g.setObs(resultado.getString("tbldesp.despobs"));
			g.setCategoria(c); // Chave Estrangeira

			itens.add(g);
		}
		// Fim do While

		return itens;
	}/**/

	// ---------------------------------
	// Listar todas despesas cadastradas no sistema em ordem decrecente - ok 
	public ArrayList<Despesa> listarTudoDecrecente() throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * ");
		sql.append(" FROM tbldesp ");
		sql.append("INNER JOIN tblcat ON tblcat.catid = tbldesp.tblcat_catid  ");
		sql.append("INNER JOIN tblcartao ON tblcartao.cartaoid = tbldesp.tblcartao_cartaoid  ");
		sql.append("order by despid desc  ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		// Resultado da Query
		ResultSet resultado = comando.executeQuery();

		// Criando um Array para os Resultados do código acima
		ArrayList<Despesa> itens = new ArrayList<Despesa>();

		// Listando os Categorias e Gastos
		while (resultado.next()) {
			Categoria c = new Categoria(); // Categoria
			c.setCodigo(resultado.getInt("tblcat.catid"));
			c.setDescricao(resultado.getString("tblcat.catdesc"));
			
			Cartao ct = new Cartao(); // CARTÃO
			ct.setCodigo(resultado.getInt("tblcartao.cartaoid"));
			ct.setBandeira(resultado.getString("tblcartao.cartaoband")); /**/
			
			Despesa g = new Despesa(); // Gasto
			g.setCodigo(resultado.getLong("tbldesp.despid"));
			g.setDescricao(resultado.getString("tbldesp.despdesc"));
			g.setDataVencimento(resultado.getDate("tbldesp.despdatavenc"));
			g.setDataPagamento(resultado.getDate("tbldesp.despdatapag"));
			g.setValor(resultado.getDouble("tbldesp.despval"));
			g.setPago(resultado.getString("tbldesp.despag"));
			g.setCancelamento(resultado.getString("tbldesp.despcanc"));
			g.setFormaPagamento(resultado.getString("tbldesp.despformpag"));
			g.setObs(resultado.getString("tbldesp.despobs"));
			g.setParcela(resultado.getLong("tbldesp.desparcela"));
			g.setTipo(resultado.getString("tbldesp.desptipo"));
			g.setCategoria(c); // Chave Estrangeira
			g.setCartao(ct);  // Chave Estrangeira
			itens.add(g);
		}
		// Fim do While

		return itens;
	}
	
	// ---------------------------------
		// Listar despesas para pagar no mês atual - ok
	public ArrayList<Despesa> listarMesPagoCancelamento() throws SQLException {
		Calendar datahoje = Calendar.getInstance();
		int mesAtual = datahoje.get(Calendar.MONTH);
		// int mesAtual = 5;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * ");
		sql.append(" FROM tbldesp ");
		sql.append("INNER JOIN tblcat ON tblcat.catid = tbldesp.tblcat_catid ");
		sql.append("INNER JOIN tblcartao ON tblcartao.cartaoid = tbldesp.tblcartao_cartaoid  ");

		switch (mesAtual) {
		case 0:
			sql.append("WHERE Month(despdatapag) =  '01' and despag = 'N' and despcanc = 'N' and Year(despdatapag) = Year(now()) ");
			sql.append("ORDER BY despdatapag ASC ");
			break;
		case 1:
			sql.append("WHERE Month(despdatapag) =  '02' and despag = 'N' and despcanc = 'N' and Year(despdatapag) = Year(now()) ");
			sql.append("ORDER BY despdatapag ASC ");
			break;
		case 2:
			sql.append("WHERE Month(despdatapag) =  '03' and despag = 'N' and despcanc = 'N' and Year(despdatapag) = Year(now()) ");
			sql.append("ORDER BY despdatapag ASC ");
			break;
		case 3:
			sql.append("WHERE Month(despdatapag) =  '04' and despag = 'N' and despcanc = 'N' and Year(despdatapag) = Year(now()) ");
			sql.append("ORDER BY despdatapag ASC ");
			break;
		case 4:
			sql.append("WHERE Month(despdatapag) =  '05' and despag = 'N' and despcanc = 'N' and Year(despdatapag) = Year(now()) ");
			sql.append("ORDER BY despdatapag ASC ");
			break;
		case 5:
			sql.append("WHERE Month(despdatapag) =  '06' and despag = 'N' and despcanc = 'N' and Year(despdatapag) = Year(now()) ");
			sql.append("ORDER BY despdatapag ASC ");
			break;
		case 6:
			sql.append("WHERE Month(despdatapag) =  '07' and despag = 'N' and despcanc = 'N' and Year(despdatapag) = Year(now()) ");
			sql.append("ORDER BY despdatapag ASC ");
			break;
		case 7:
			sql.append("WHERE Month(despdatapag) =  '08' and despag = 'N' and despcanc = 'N' and Year(despdatapag) = Year(now()) ");
			sql.append("ORDER BY despdatapag ASC ");
			break;
		case 8:
			sql.append("WHERE Month(despdatapag) =  '09' and despag = 'N' and despcanc = 'N' and Year(despdatapag) = Year(now()) ");
			sql.append("ORDER BY despdatapag ASC ");
			break;
		case 9:
			sql.append("WHERE Month(despdatapag) =  '10' and despag = 'N' and despcanc = 'N' and Year(despdatapag) = Year(now()) ");
			sql.append("ORDER BY despdatapag ASC ");
			break;
		case 10:
			sql.append("WHERE Month(despdatapag) =  '11' and despag = 'N' and despcanc = 'N' and Year(despdatapag) = Year(now()) ");
			sql.append("ORDER BY despdatapag ASC ");
			break;
		default:
			sql.append("WHERE Month(despdatapag) =  '12' and despag = 'N' and despcanc = 'N' and Year(despdatapag) = Year(now()) ");
			sql.append("ORDER BY despdatapag ASC ");
		}

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		// Resultado da Query
		ResultSet resultado = comando.executeQuery();

		// Criando um Array para os Resultados do código acima
		ArrayList<Despesa> itens = new ArrayList<Despesa>();

		// Listando os Categorias e Gastos
		while (resultado.next()) {
			Categoria c = new Categoria(); // Categoria
			c.setCodigo(resultado.getInt("tblcat.catid"));
			c.setDescricao(resultado.getString("tblcat.catdesc"));
			
			Cartao ct = new Cartao();
			ct.setCodigo(resultado.getInt("tblcartao.cartaoid"));
			ct.setBandeira(resultado.getString("tblcartao.cartaoband"));

			Despesa g = new Despesa(); // Gasto
			g.setCodigo(resultado.getLong("tbldesp.despid"));
			g.setDescricao(resultado.getString("tbldesp.despdesc"));
			g.setDataVencimento(resultado.getDate("tbldesp.despdatavenc"));
			g.setDataPagamento(resultado.getDate("tbldesp.despdatapag"));
			g.setValor(resultado.getDouble("tbldesp.despval"));
			g.setPago(resultado.getString("tbldesp.despag"));
			g.setCancelamento(resultado.getString("tbldesp.despcanc"));
			g.setFormaPagamento(resultado.getString("tbldesp.despformpag"));
			g.setObs(resultado.getString("tbldesp.despobs"));
            g.setTipo(resultado.getString("tbldesp.desptipo"));
            g.setCartao(ct);
			g.setCategoria(c); // Chave Estrangeira
			itens.add(g);
		}
		
		return itens;
	}
	
	//-----------------------------------------------
	
	
		// Listar todas despesas cadastradas no Dia no sistema em ordem decrecente - ok  
		public ArrayList<Despesa> listarDia() throws SQLException {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append(" FROM tbldesp ");
			sql.append("INNER JOIN tblcat ON tblcat.catid = tbldesp.tblcat_catid ");
			sql.append("where despdatapag = now()  ");// condição dia atual
			sql.append("order by despid desc  ");

			Connection conexao = ConexaoFactory.conectar();

			PreparedStatement comando = conexao.prepareStatement(sql.toString());

			// Resultado da Query
			ResultSet resultado = comando.executeQuery();

			// Criando um Array para os Resultados do código acima
			ArrayList<Despesa> itens = new ArrayList<Despesa>();

			// Listando os Categorias e Gastos
			while (resultado.next()) {
				Categoria c = new Categoria(); // Categoria
				c.setCodigo(resultado.getInt("tblcat.catid"));
				c.setDescricao(resultado.getString("tblcat.catdesc"));

				Despesa g = new Despesa(); // Gasto
				g.setCodigo(resultado.getLong("tbldesp.despid"));
				g.setDescricao(resultado.getString("tbldesp.despdesc"));
				g.setDataVencimento(resultado.getDate("tbldesp.despdatavenc"));
				g.setDataPagamento(resultado.getDate("tbldesp.despdatapag"));
				g.setValor(resultado.getDouble("tbldesp.despval"));
				g.setPago(resultado.getString("tbldesp.despag"));
				g.setCancelamento(resultado.getString("tbldesp.despcanc"));
				g.setFormaPagamento(resultado.getString("tbldesp.despformpag"));
				g.setObs(resultado.getString("tbldesp.despobs"));
				g.setCategoria(c); // Chave Estrangeira

				itens.add(g);
			}
			// Fim do While

			return itens;
		}
		
		
		//-----------------------------------------------
		// Listar despesa paga no mês atual - ok
		public ArrayList<Despesa> listarMesPago() throws SQLException {
			Calendar datahoje = Calendar.getInstance();			
			int mesAtual = datahoje.get(Calendar.MONTH);
			// int mesAtual = 0;

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append(" FROM tbldesp ");
			sql.append("INNER JOIN tblcat ON tblcat.catid = tbldesp.tblcat_catid ");
			sql.append("INNER JOIN tblcartao ON tblcartao.cartaoid = tbldesp.tblcartao_cartaoid  ");

			switch (mesAtual) {
			case 0:
				sql.append("WHERE  Month(despdatapag) =  '01' and despag = 'S' and Year(despdatapag) = Year(now()) ");
				sql.append("ORDER BY despdatapag DESC ");
				break;
			case 1:
				sql.append("WHERE  Month(despdatapag) =  '02' and despag = 'S' and Year(despdatapag) = Year(now()) ");
				sql.append("ORDER BY despdatapag DESC ");
				break;
			case 2:
				sql.append("WHERE  Month(despdatapag) =  '03' and despag = 'S' and Year(despdatapag) = Year(now()) ");
				sql.append("ORDER BY despdatapag DESC ");
				break;
			case 3:
				sql.append("WHERE  Month(despdatapag) =  '04' and despag = 'S' and Year(despdatapag) = Year(now()) ");
				sql.append("ORDER BY despdatapag DESC ");
				break;
			case 4:
				sql.append("WHERE  Month(despdatapag) =  '05' and despag = 'S' and Year(despdatapag) = Year(now()) ");
				sql.append("ORDER BY despdatapag DESC ");
				break;
			case 5:
				sql.append("WHERE  Month(despdatapag) =  '06' and despag = 'S' and Year(despdatapag) = Year(now()) ");
				sql.append("ORDER BY despdatapag ASC ");
				break;
			case 6:
				sql.append("WHERE  Month(despdatapag) =  '07' and despag = 'S' and Year(despdatapag) = Year(now()) ");
				sql.append("ORDER BY despdatapag DESC ");
				break;
			case 7:
				sql.append("WHERE  Month(despdatapag) =  '08' and despag = 'S' and Year(despdatapag) = Year(now()) ");
				sql.append("ORDER BY despdatapag DESC ");
				break;
			case 8:
				sql.append("WHERE Month(despdatapag) =  '09' and despag = 'S' and Year(despdatapag) = Year(now()) ");
				sql.append("ORDER BY despdatapag DESC ");
				break;
			case 9:
				sql.append("WHERE  Month(despdatapag) =  '10' and despag = 'S' and Year(despdatapag) = Year(now()) ");
				sql.append("ORDER BY despdatapag DESC ");
				break;
			case 10:
				sql.append("WHERE  Month(despdatapag) =  '11' and despag = 'S' and Year(despdatapag) = Year(now()) ");
				sql.append("ORDER BY despdatapag DESC ");
				break;
			default:
				sql.append("WHERE  Month(despdatapag) =  '12' and despag = 'S' and Year(despdatapag) = Year(now()) ");
				sql.append("ORDER BY despdatapag DESC ");
			}

			Connection conexao = ConexaoFactory.conectar();

			PreparedStatement comando = conexao.prepareStatement(sql.toString());

			// Resultado da Query
			ResultSet resultado = comando.executeQuery();

			// Criando um Array para os Resultados do código acima
			ArrayList<Despesa> itens = new ArrayList<Despesa>();

			// Listando os Categorias e Gastos
			while (resultado.next()) {
				Categoria c = new Categoria(); // Categoria
				c.setCodigo(resultado.getInt("tblcat.catid"));
				c.setDescricao(resultado.getString("tblcat.catdesc"));
				
				Cartao ct = new Cartao();
				ct.setCodigo(resultado.getInt("tblcartao.cartaoid"));
				ct.setBandeira(resultado.getString("tblcartao.cartaoband"));

				Despesa g = new Despesa(); // Gasto
				g.setCodigo(resultado.getLong("tbldesp.despid"));
				g.setDescricao(resultado.getString("tbldesp.despdesc"));
				g.setDataVencimento(resultado.getDate("tbldesp.despdatavenc"));
				g.setDataPagamento(resultado.getDate("tbldesp.despdatapag"));
				g.setValor(resultado.getDouble("tbldesp.despval"));
				g.setPago(resultado.getString("tbldesp.despag"));
				g.setCancelamento(resultado.getString("tbldesp.despcanc"));
				g.setFormaPagamento(resultado.getString("tbldesp.despformpag"));
				g.setObs(resultado.getString("tbldesp.despobs"));
				g.setTipo(resultado.getString("tbldesp.desptipo"));
				g.setCartao(ct);
				g.setCategoria(c); // Chave Estrangeira

				itens.add(g);
			}
			// Fim do While

			return itens;
		}
		// ---------------------------------		
		
		//-----------------------------------------------
				// Listar despesa paga no mês anterior - ok
				public ArrayList<Despesa> listarMesAnt() throws SQLException {
					Calendar datahoje = Calendar.getInstance();
					int mesAtual = datahoje.get(Calendar.MONTH);
					// int mesAtual = 0;

					StringBuilder sql = new StringBuilder();
					sql.append("SELECT * ");
					sql.append(" FROM tbldesp ");
					sql.append("INNER JOIN tblcat ON tblcat.catid = tbldesp.tblcat_catid ");
					sql.append("INNER JOIN tblcartao ON tblcartao.cartaoid = tbldesp.tblcartao_cartaoid  ");

					switch (mesAtual) {
					case 0:
						sql.append("where Month(despdatapag) =  '12' and despag = 'S' and Year(despdatapag) = Year(now()) -1 ");
						sql.append("ORDER BY despdatapag ASC ");
						break;
					case 1:
						sql.append("WHERE  Month(despdatapag) =  '01' and despag = 'S' and Year(despdatapag) = Year(now()) ");
						sql.append("ORDER BY despdatapag ASC ");
						break;
					case 2:
						sql.append("WHERE  Month(despdatapag) =  '02' and despag = 'S' and Year(despdatapag) = Year(now()) ");
						sql.append("ORDER BY despdatapag ASC ");
						break;
					case 3:
						sql.append("WHERE  Month(despdatapag) =  '03' and despag = 'S' and Year(despdatapag) = Year(now()) ");
						sql.append("ORDER BY despdatapag ASC ");
						break;
					case 4:
						sql.append("WHERE  Month(despdatapag) =  '04' and despag = 'S' and Year(despdatapag) = Year(now()) ");
						sql.append("ORDER BY despdatapag ASC ");
						break;
					case 5:
						sql.append("WHERE  Month(despdatapag) =  '05' and despag = 'S' and Year(despdatapag) = Year(now()) ");
						sql.append("ORDER BY despdatapag ASC ");
						break;
					case 6:
						sql.append("WHERE  Month(despdatapag) =  '06' and despag = 'S' and Year(despdatapag) = Year(now()) ");
						sql.append("ORDER BY despdatapag ASC ");
						break;
					case 7:
						sql.append("WHERE  Month(despdatapag) =  '07' and despag = 'S' and Year(despdatapag) = Year(now()) ");
						sql.append("ORDER BY despdatapag ASC ");
						break;
					case 8:
						sql.append("WHERE Month(despdatapag) =  '08' and despag = 'S' and Year(despdatapag) = Year(now()) ");
						sql.append("ORDER BY despdatapag ASC ");
						break;
					case 9:
						sql.append("WHERE  Month(despdatapag) =  '09' and despag = 'S' and Year(despdatapag) = Year(now()) ");
						sql.append("ORDER BY despdatapag ASC ");
						break;
					case 10:
						sql.append("WHERE  Month(despdatapag) =  '10' and despag = 'S' and Year(despdatapag) = Year(now()) ");
						sql.append("ORDER BY despdatapag ASC ");
						break;
					default:
						sql.append("WHERE  Month(despdatapag) =  '11' and despag = 'S' and Year(despdatapag) = Year(now()) ");
						sql.append("ORDER BY despdatapag ASC ");
					}

					Connection conexao = ConexaoFactory.conectar();

					PreparedStatement comando = conexao.prepareStatement(sql.toString());

					// Resultado da Query
					ResultSet resultado = comando.executeQuery();

					// Criando um Array para os Resultados do código acima
					ArrayList<Despesa> itens = new ArrayList<Despesa>();

					// Listando os Categorias e Gastos
					while (resultado.next()) {
						Categoria c = new Categoria(); // Categoria
						c.setCodigo(resultado.getInt("tblcat.catid"));
						c.setDescricao(resultado.getString("tblcat.catdesc"));
						
						Cartao ct = new Cartao();
						ct.setCodigo(resultado.getInt("tblcartao.cartaoid"));
						ct.setBandeira(resultado.getString("tblcartao.cartaoband"));

						Despesa g = new Despesa(); // Gasto
						g.setCodigo(resultado.getLong("tbldesp.despid"));
						g.setDescricao(resultado.getString("tbldesp.despdesc"));
						g.setDataVencimento(resultado.getDate("tbldesp.despdatavenc"));
						g.setDataPagamento(resultado.getDate("tbldesp.despdatapag"));
						g.setValor(resultado.getDouble("tbldesp.despval"));
						g.setPago(resultado.getString("tbldesp.despag"));
						g.setCancelamento(resultado.getString("tbldesp.despcanc"));
						g.setFormaPagamento(resultado.getString("tbldesp.despformpag"));
						g.setObs(resultado.getString("tbldesp.despobs"));
						g.setTipo(resultado.getString("tbldesp.desptipo"));
						g.setCartao(ct);
						g.setCategoria(c); // Chave Estrangeira

						itens.add(g);
					}
					// Fim do While

					return itens;
				}
				// ---------------------------------------------------------------------------------------
				
				// Listar despesas para pagar no mês atual - ok
			public ArrayList<Despesa> listarMesPagoCancelamentoValorTotal() throws SQLException {
				Calendar datahoje = Calendar.getInstance();
				int mesAtual = datahoje.get(Calendar.MONTH);
				// int mesAtual = 5;

				StringBuilder sql = new StringBuilder();
				sql.append("SELECT sum(despval) ");
				sql.append(" FROM tbldesp ");
				sql.append("INNER JOIN tblcat ON tblcat.catid = tbldesp.tblcat_catid ");
				sql.append("INNER JOIN tblcartao ON tblcartao.cartaoid = tbldesp.tblcartao_cartaoid  ");

				switch (mesAtual) {
				case 0:
					sql.append("WHERE Month(despdatavenc) =  '01' and despag = 'N' and despcanc = 'N' and Year(despdatavenc) = Year(now()) ");
					sql.append("ORDER BY despdatavenc ASC ");
					break;
				case 1:
					sql.append("WHERE Month(despdatavenc) =  '02' and despag = 'N' and despcanc = 'N' and Year(despdatavenc) = Year(now()) ");
					sql.append("ORDER BY despdatavenc ASC ");
					break;
				case 2:
					sql.append("WHERE Month(despdatavenc) =  '03' and despag = 'N' and despcanc = 'N' and Year(despdatavenc) = Year(now()) ");
					sql.append("ORDER BY despdatavenc ASC ");
					break;
				case 3:
					sql.append("WHERE Month(despdatavenc) =  '04' and despag = 'N' and despcanc = 'N' and Year(despdatavenc) = Year(now()) ");
					sql.append("ORDER BY despdatavenc ASC ");
					break;
				case 4:
					sql.append("WHERE Month(despdatavenc) =  '05' and despag = 'N' and despcanc = 'N' and Year(despdatavenc) = Year(now()) ");
					sql.append("ORDER BY despdatavenc ASC ");
					break;
				case 5:
					sql.append("WHERE Month(despdatavenc) =  '06' and despag = 'N' and despcanc = 'N' and Year(despdatavenc) = Year(now()) ");
					sql.append("ORDER BY despdatavenc ASC ");
					break;
				case 6:
					sql.append("WHERE Month(despdatavenc) =  '07' and despag = 'N' and despcanc = 'N' and Year(despdatavenc) = Year(now()) ");
					sql.append("ORDER BY despdatavenc ASC ");
					break;
				case 7:
					sql.append("WHERE Month(despdatavenc) =  '08' and despag = 'N' and despcanc = 'N' and Year(despdatavenc) = Year(now()) ");
					sql.append("ORDER BY despdatavenc ASC ");					
					
				//	sql.append(" select sum(despvalor) from tbldesp ");
					break;
				case 8:
					sql.append("WHERE Month(despdatavenc) =  '09' and despag = 'N' and despcanc = 'N' and Year(despdatavenc) = Year(now()) ");
					sql.append("ORDER BY despdatavenc ASC ");
					break;
				case 9:
					sql.append("WHERE Month(despdatavenc) =  '10' and despag = 'N' and despcanc = 'N' and Year(despdatavenc) = Year(now()) ");
					sql.append("ORDER BY despdatavenc ASC ");
					break;
				case 10:
					sql.append("WHERE Month(despdatavenc) =  '11' and despag = 'N' and despcanc = 'N' and Year(despdatavenc) = Year(now()) ");
					sql.append("ORDER BY despdatavenc ASC ");
					break;
				default:
					sql.append("WHERE Month(despdatavenc) =  '12' and despag = 'N' and despcanc = 'N' and Year(despdatavenc) = Year(now()) ");
					sql.append("ORDER BY despdatavenc ASC ");
				}

				Connection conexao = ConexaoFactory.conectar();

				PreparedStatement comando = conexao.prepareStatement(sql.toString());

				// Resultado da Query
				ResultSet resultado = comando.executeQuery();

				// Criando um Array para os Resultados do código acima
				ArrayList<Despesa> itens = new ArrayList<Despesa>();

				// Listando os Categorias e Gastos
				while (resultado.next()) {
					Categoria c = new Categoria(); // Categoria
					c.setCodigo(resultado.getInt("tblcat.catid"));
					c.setDescricao(resultado.getString("tblcat.catdesc"));
					
					Cartao ct = new Cartao();
					ct.setCodigo(resultado.getInt("tblcartao.cartaoid"));
					ct.setBandeira(resultado.getString("tblcartao.cartaoband"));

					Despesa g = new Despesa(); // Gasto
					g.setCodigo(resultado.getLong("tbldesp.despid"));
					g.setDescricao(resultado.getString("tbldesp.despdesc"));
					g.setDataVencimento(resultado.getDate("tbldesp.despdatavenc"));
					g.setDataPagamento(resultado.getDate("tbldesp.despdatapag"));
					g.setValor(resultado.getDouble("tbldesp.despval"));
					g.setPago(resultado.getString("tbldesp.despag"));
					g.setCancelamento(resultado.getString("tbldesp.despcanc"));
					g.setFormaPagamento(resultado.getString("tbldesp.despformpag"));
					g.setObs(resultado.getString("tbldesp.despobs"));
					g.setTipo(resultado.getString("tbldesp.desptipo"));
					g.setCartao(ct);
					g.setCategoria(c); // Chave Estrangeira

					itens.add(g);
				}
				
				return itens;
			}
			//-------------------------------------------*/		
			
			// Despesa por PERÌODO -Filtro            
			@SuppressWarnings("unchecked")
			public List<Despesa> buscar(DespesaFilter filtro) {

				List<Despesa> vendas = null;

				// Concatenar o comando SQL
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT despesa FROM Despesa despesa ");
			//	sql.append("INNER JOIN tblcat ON tblcat.catid = tbldesp.tblcat_catid ");
				//sql.append("INNER JOIN tblcartao ON tblcartao.cartaoid = tbldesp.tblcartao_cartaoid  ");

				// Verificando se o usuÃ¡rio digitou a data inicial e final.
				if (filtro.getDataInicial() != null && filtro.getDataFinal() != null) {
					//sql.append("WHERE despesa.dataPagamento BETWEEN :dataInicial AND :dataFinal and(despesa.pago = 'S') ");
					sql.append("WHERE despesa.dataPagamento BETWEEN :dataInicial AND :dataFinal  "); 
					sql.append("and(despesa.pago = 'S' or despesa.pago = 'N')  ");
				}
				sql.append("ORDER BY despesa.dataPagamento ");

				Session sessao = HibernateUtil.getSessionFactory().openSession();

				try {
					Query consulta = sessao.createQuery(sql.toString());
					if(filtro.getDataInicial()!= null && filtro.getDataFinal() != null) {
					consulta.setDate("dataInicial", filtro.getDataInicial());
					consulta.setDate("dataFinal", filtro.getDataFinal());
					}
					vendas = consulta.list();
				} catch (RuntimeException ex) {
					throw ex;

				} finally {
					sessao.close();
				}
				return vendas;
			}
			// -------------------------------		
			
			// ---------------------------------
			// Listar todas despesas futura - ok 
			public ArrayList<Despesa> listarLancFuturo() throws SQLException {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT * ");
				sql.append(" FROM tbldesp ");
				sql.append("INNER JOIN tblcat ON tblcat.catid = tbldesp.tblcat_catid ");
				sql.append("INNER JOIN tblcartao ON tblcartao.cartaoid = tbldesp.tblcartao_cartaoid  ");
			//	sql.append("WHERE despag = 'N'  ");
				sql.append("WHERE despag = 'N' and MONTH(despdatapag) > MONTH(now()) ");
				sql.append("OR despag = 'N' and YEAR(despdatapag) > YEAR(now()) ");
				sql.append("ORDER BY despdatapag ASC  ");

				Connection conexao = ConexaoFactory.conectar();

				PreparedStatement comando = conexao.prepareStatement(sql.toString());

				// Resultado da Query
				ResultSet resultado = comando.executeQuery();

				// Criando um Array para os Resultados do código acima
				ArrayList<Despesa> itens = new ArrayList<Despesa>();

				// Listando os Categorias e Gastos
				while (resultado.next()) {
					Categoria c = new Categoria(); // Categoria
					c.setCodigo(resultado.getInt("tblcat.catid"));
					c.setDescricao(resultado.getString("tblcat.catdesc"));
					
					Cartao ct = new Cartao();
					ct.setCodigo(resultado.getInt("tblcartao.cartaoid"));
					ct.setBandeira(resultado.getString("tblcartao.cartaoband"));

					Despesa g = new Despesa(); // Gasto
					g.setCodigo(resultado.getLong("tbldesp.despid"));
					g.setDescricao(resultado.getString("tbldesp.despdesc"));
					g.setDataVencimento(resultado.getDate("tbldesp.despdatavenc"));
					g.setDataPagamento(resultado.getDate("tbldesp.despdatapag"));
					g.setValor(resultado.getDouble("tbldesp.despval"));
					g.setPago(resultado.getString("tbldesp.despag"));
					g.setCancelamento(resultado.getString("tbldesp.despcanc"));
					g.setFormaPagamento(resultado.getString("tbldesp.despformpag"));
					g.setObs(resultado.getString("tbldesp.despobs"));
					g.setTipo(resultado.getString("tbldesp.desptipo"));
					g.setCartao(ct);
					g.setCategoria(c); // Chave Estrangeira

					itens.add(g);
				}
				// Fim do While

				return itens;
			}
			//-----------------------------------------------------------------------------------
			
			
			
						
}
