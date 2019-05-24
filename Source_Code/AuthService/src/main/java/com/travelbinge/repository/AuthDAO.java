package com.travelbinge.repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.travelbinge.model.User;

@Repository
public class AuthDAO {

	private SessionFactory sessionFactory;

	@Autowired
	public AuthDAO(EntityManagerFactory factory) {
		if(factory.unwrap(SessionFactory.class) == null){
			throw new NullPointerException("factory is not a hibernate factory");
		}
		this.sessionFactory = factory.unwrap(SessionFactory.class);
	}

	@Autowired
	NamedParameterJdbcTemplate mainTemplate;

	private static final String CHECK_EMAIL_ID_EXISTS = "select count(*) from User u where u.emailId=:emailId ";

	public Boolean emailIdExists(String emailId) {
		
		Session currentSession = null;
		try {
			currentSession = sessionFactory.openSession();
			Query query = currentSession.createQuery(CHECK_EMAIL_ID_EXISTS)
					.setParameter("emailId", emailId);

			long count = (long) query.getSingleResult();
			return count==0?false:true;
			
		} catch (Exception e) {
			return false;
		}finally {
			if(currentSession!=null) {
				currentSession.close();
			}
		}


	}

	public boolean signup(User user) {
		
		Session currentSession = null;
		try {
			currentSession = sessionFactory.openSession();
			Transaction tx = currentSession.getTransaction();
			tx.begin();
			
			currentSession.save(user);
			
			int userId = user.getUserId();

			tx.commit();
			
			return userId==0?false:true;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}finally {
			if(currentSession!=null) {
				currentSession.close();
			}
		}

	}
}
