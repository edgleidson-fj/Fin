package Util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();
//----------------------------------------------------------------------------------------------------------------
    
    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml        
        	Configuration configuration = new Configuration();
        	configuration.configure();
        	
        	ServiceRegistry serviceResgistry = new StandardServiceRegistryBuilder()
        			.applySettings(configuration.getProperties()).build();
        	
        	SessionFactory sessionFactory = configuration.buildSessionFactory(serviceResgistry);
        	
        	return sessionFactory;
        	
        }  
        
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("_____ERRO____________________." + ex);
            
            throw new ExceptionInInitializerError(ex);
        }
    }
//----------------------------------------------------------------------------------------------------------------
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
//-------------
}