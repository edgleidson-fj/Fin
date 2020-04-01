package Dao;

import java.util.List;

//import javax.servlet.jsp.tagext.TryCatchFinally;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Domain.Categoria;
import Util.HibernateUtil;

public class CategoriaDAO {

	// INSERT
	public void salvar(Categoria categoria) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;

		try {
			transacao = session.beginTransaction();
			session.save(categoria);
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
	public List<Categoria> listar() {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Categoria> categorias = null;

		try {
			Query consulta = sessao.getNamedQuery("Categoria.listar");
			categorias = consulta.list();

		} catch (RuntimeException ex) {
			throw ex;

		} finally {
			sessao.close();
		}
		return categorias;
	}
	// ---------------------------------------------------------------------------------------------------------------

	// SELECT - BUSCAR POR C�DIGO
	public Categoria buscarPorCodigo(int codigo) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Categoria categoria = null;

		try {
			Query consulta = session.getNamedQuery("Categoria.buscarPorCodigo");
			consulta.setInteger("codigo", codigo);
			categoria = (Categoria) consulta.uniqueResult();

		} catch (RuntimeException ex) {
			throw ex;

		} finally {
			session.close();
		}
		return categoria;
	}
	// -----------------------------------------------------------------------------------------------------------*/

	// DELETE - Normal
	public void excluir(Categoria categoria) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			session.delete(categoria);
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
	public void editar(Categoria categoria) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			Categoria categoriaEditar = buscarPorCodigo(categoria.getCodigo());
			categoriaEditar.setDescricao(categoria.getDescricao());

			session.update(categoriaEditar);
			transaction.commit();

		} catch (Exception ex) {
			if (categoria != null) {
				transaction.rollback();
			}
			// throw ex;

		} finally {
			session.close();
		}
	}
	// -------------------------------------------*/

}
