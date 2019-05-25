package com.travelbinge.repository;

import java.util.Collections;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.travelbinge.model.TBUser;

@Repository
public class AuthDAO {

	private SessionFactory sessionFactory;

	@Autowired
	public AuthDAO(EntityManagerFactory factory) {
		if(factory.unwrap(SessionFactory.class) == null){
			throw new NullPointerException("Factory is not a hibernate factory");
		}
		this.sessionFactory = factory.unwrap(SessionFactory.class);
	}

	private static final String CHECK_EMAIL_ID_EXISTS = "select count(*) from TBUser u where lower(u.emailId)=lower(:emailId) ";
	private static final String CHECK_USERNAME_EXISTS = "select count(*) from TBUser u where lower(u.username)=lower(:username) ";
	private static final String GET_USER_DETAILS = " from TBUser u where lower(u.username)=lower(:username) ";
	
	public boolean usernameExists(String username) {
		
		Session currentSession = null;
		try {
			currentSession = sessionFactory.openSession();
			Query query = currentSession.createQuery(CHECK_USERNAME_EXISTS)
					.setParameter("username", username);

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

	public boolean signup(TBUser user) {
		
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

	public UserDetails loadUserByUsername(String username) {
		
		Session currentSession = null;
		try {
			currentSession = sessionFactory.openSession();
			Query query = currentSession.createQuery(GET_USER_DETAILS)
					.setParameter("username", username);

			TBUser userDetails = (TBUser) query.getSingleResult();
			
			System.out.println(userDetails.toString());
			return new User(userDetails.getUsername(), userDetails.getAuthCode(),Collections.emptyList() );
			
		} catch (NoResultException  e) {
			System.out.println("Username: " + username + " not found");
			throw new UsernameNotFoundException("Username: " + username + " not found");
		}catch(Exception e) {
			System.out.println("Exception in loadUsername");
			throw new UsernameNotFoundException("Exception in loadUsername");
		}finally {
			if(currentSession!=null) {
				currentSession.close();
			}
		}


	}

	

	
}
