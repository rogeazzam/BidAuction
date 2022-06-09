package com.bidauc.BidAuctions;

import com.bidauc.BidAuctions.ocsf.AbstractServer;
import com.bidauc.BidAuctions.ocsf.ConnectionToClient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SimpleServer extends AbstractServer {
	private static SessionFactory sessionFactory;

	public SimpleServer(int port) {
		super(port);
		sessionFactory=getSessionFactory();
	}

	private static SessionFactory getSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(UserClass.class);
		ServiceRegistry serviceRegistry = (new StandardServiceRegistryBuilder())
				.applySettings(configuration.getProperties()).build();
		return configuration.buildSessionFactory(serviceRegistry);
	}

	public static <T> List<T> getAll(Class<T> object,Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = builder.createQuery(object);
		Root<T> rootEntry = criteriaQuery.from(object);
		CriteriaQuery<T> allCriteriaQuery = criteriaQuery.select(rootEntry);

		TypedQuery<T> allQuery = session.createQuery(allCriteriaQuery);
		return allQuery.getResultList();
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String msgString = msg.toString();
		System.out.println(msgString);
		Session newsession = sessionFactory.openSession();
		Transaction tx=null;
		try {
			tx=newsession.beginTransaction();
			if(msgString.startsWith("#updatePrice")){
				String[] parts=msgString.split("%%");
				double newprice=Double.valueOf(parts[1]);
				sendToAllClients((Object) newprice);
			}
			if(msgString.startsWith("#getclient")){
				String[] parts=msgString.split("%%");
				double newprice=Double.valueOf(parts[1]);
				sendToAllClients((Object) newprice);
			}
			if(msgString.startsWith("putUser")){
				String[] parts=msgString.split("%%");
				UserClass usr=new UserClass(parts[1],parts[2]);
				newsession.save(usr);
				newsession.flush();
			}
			tx.commit();
		} catch (Exception var10) {
			if (newsession != null) {
				newsession.getTransaction().rollback();
			}

			System.err.println("An error occured, changes have been rolled back.");
			var10.printStackTrace();
		} finally {
			if (newsession != null) {
				newsession.close();
			}

		}
	}

	public static double compareTwoTimeStamps(java.sql.Timestamp currentTime, java.sql.Timestamp movieTime)
	{
		double milliseconds1 = movieTime.getTime();
		double milliseconds2 = currentTime.getTime();

		double diff = milliseconds1 - milliseconds2;
		double diffHours = diff / (60 * 60 * 1000);

		return diffHours;
	}

}