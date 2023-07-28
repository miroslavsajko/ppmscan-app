package sk.ppmscan.app.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {
	
	private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory(String resource) {
        try {
            return new Configuration().configure(resource).buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory(String resource) {
    	if (sessionFactory == null) {
    		sessionFactory = buildSessionFactory(resource);
    	}
        return sessionFactory;
    }
    
    public static SessionFactory getSessionFactory() {
    	if (sessionFactory == null) {
    		sessionFactory = buildSessionFactory();
    	}
        return sessionFactory;
    }

}
