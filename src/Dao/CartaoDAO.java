package Dao;

import java.util.List;

//import javax.servlet.jsp.tagext.TryCatchFinally;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Domain.Cartao;
import Util.HibernateUtil;

public class CartaoDAO {

	// INSERT
	public void salvar(Cartao cartao) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;

		try {
			transacao = session.beginTransaction();
			session.save(cartao);
			transacao.commit();

		} catch (RuntimeException ex) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw ex;

		} finally {
			session.close();
		}
	}
	// -----------------------------------------------------------------------------------------------------------

	// SELECT - Lista
	@SuppressWarnings("unchecked")
	public List<Cartao> listar() {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Cartao> cartoes = null;

		try {
			Query consulta = sessao.getNamedQuery("Cartao.listar");
			cartoes = consulta.list();

		} catch (RuntimeException ex) {
			throw ex;

		} finally {
			sessao.close();
		}
		return cartoes;
	}
	// ---------------------------------------------------------------------------------------------------------------

	// SELECT - BUSCAR POR C�DIGO
	public Cartao buscarPorCodigo(int codigo) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Cartao cartao = null;

		try {
			Query consulta = session.getNamedQuery("Cartao.buscarPorCodigo");
			consulta.setInteger("codigo", codigo);
			cartao = (Cartao) consulta.uniqueResult();

		} catch (RuntimeException ex) {
			throw ex;

		} finally {
			session.close();
		}
		return cartao;
	}
	// -----------------------------------------------------------------------------------------------------------*/

	// DELETE - Normal
	public void excluir(Cartao cartao) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			session.delete(cartao);
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

	// ---------------------------------------------------------------------------------------------------------*/
	// UPDATE
	public void editar(Cartao cartao) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			Cartao cartaoEditar = buscarPorCodigo(cartao.getCodigo());
			cartaoEditar.setBandeira(cartao.getBandeira());

			session.update(cartaoEditar);
			transaction.commit();

		} catch (Exception ex) {
			if (cartao != null) {
				transaction.rollback();
			}
			// throw ex;

		} finally {
			session.close();
		}
	}
	// -------------------------------------------*/

}
