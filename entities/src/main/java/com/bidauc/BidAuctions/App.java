package com.bidauc.BidAuctions;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hello world!
 *
 */
public class App 
{
    private static Session session;
    private static SessionFactory sessionFactory;

    public App() {
    }

    private static SessionFactory getSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserClass.class);
        ServiceRegistry serviceRegistry = (new StandardServiceRegistryBuilder())
                .applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static void InitilaizeData() throws Exception {
    }
    public static void main(String[] args) {
        try {
            sessionFactory = getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            InitilaizeData();
            session.getTransaction().commit();
        } catch (Exception var10) {
            if (session != null) {
                session.getTransaction().rollback();
            }

            System.err.println("An error occured, changes have been rolled back.");
            var10.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }

        }

    }
}
