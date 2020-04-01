package Main;

import Util.HibernateUtil;

public class GeraTabela {

	public static void main(String[] args) {
		
		HibernateUtil.getSessionFactory();
	System.out.println("--------------------");
		HibernateUtil.getSessionFactory().close();
	}
}
