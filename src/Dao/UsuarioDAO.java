package Dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Domain.Usuario;
import Util.HibernateUtil;

public class UsuarioDAO {

	// INSERT
	public void salvar(Usuario usuario) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try { 
			transaction = session.beginTransaction();
			session.save(usuario);
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
	// -----------------------------------------------------------------------------------------------------------

	// SELECT - Lista
	@SuppressWarnings("unchecked")
	public List<Usuario> listar() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Usuario> usuarios = null;

		try { 
			Query consulta = session.getNamedQuery("Usuario.listar");
			usuarios = consulta.list();

		} catch (RuntimeException ex) { 
			throw ex;

		} finally { 
			session.close();
		}
		return usuarios;
	}
	// ------------------------------------------------------------------------------------------------------------

	// SELECT - BUSCAR POR C�DIGO
	public Usuario buscarPorCodigo(int codigo) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Usuario usuario = null;

		try { 
			Query consulta = session.getNamedQuery("Usuario.buscarPorCodigo");
			consulta.setInteger("codigo", codigo);
			usuario = (Usuario) consulta.uniqueResult();

		} catch (RuntimeException ex) { 
			throw ex;

		} finally { 
			session.close();
		}
		return usuario;
	}
	// -----------------------------------------------------------------------------------------------------------

	// DELETE - Normal
	public void excluir(Usuario usuario) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try { 
			transaction = session.beginTransaction();
			session.delete(usuario);
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
	// ------------------------------------------------------------------------------------------------------------*/

	// UPDATE
	public void editar(Usuario usuario) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try { 
			transaction = session.beginTransaction();
			session.update(usuario); 
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
	// ------------------------------------------------------------------------------------------------------------

	public Usuario autenticar(String cpf, String senha) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Usuario usuario = null;

		try {
			Query consulta = session.getNamedQuery("Usuario.autenticar");
			consulta.setString("cpf", cpf);
			consulta.setString("senha", senha);
			usuario = (Usuario) consulta.uniqueResult();

		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			session.close();
		}
		return usuario;
	}
	// -------
}
