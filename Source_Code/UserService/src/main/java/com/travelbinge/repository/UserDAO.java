package com.travelbinge.repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.travelbinge.model.ApiResponse;
import com.travelbinge.model.TBUser;

@Repository
public class UserDAO {
	
	
	private SessionFactory sessionFactory;
	

	@Autowired
	public UserDAO(EntityManagerFactory factory) {
		if(factory.unwrap(SessionFactory.class) == null){
			throw new NullPointerException("Factory is not a hibernate factory");
		}
		this.sessionFactory = factory.unwrap(SessionFactory.class);
	}
	
	private static final String GET_USER_DETAILS = " from TBUser u where lower(u.username)=lower(:username)";

	
	
	public ApiResponse getUserDetails(String username) {

		
		Session currentSession = null;
		try {
			currentSession = sessionFactory.openSession();
			
			Query query = currentSession.createQuery(GET_USER_DETAILS)
					.setParameter("username", username);
			
			TBUser user =  (TBUser) query.getSingleResult();
			return new ApiResponse(HttpStatus.OK, null, null, user);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse(HttpStatus.SERVICE_UNAVAILABLE, "Please try again Later", null, null);
		}finally {
			if(currentSession!=null) {
				currentSession.close();
			}
		}


	
	}



	public ApiResponse updateUserDetails(TBUser user) {

		
		Session currentSession = null;
		try {
			currentSession = sessionFactory.openSession();
			
			Query query = currentSession.createQuery(GET_USER_DETAILS)
					.setParameter("username", user.getUsername());
			
			TBUser existingUser =  (TBUser) query.getSingleResult();
			
			existingUser.setFirstName(user.getFirstName());
			existingUser.setLastName(user.getLastName());
			existingUser.setState(user.getState());
			existingUser.setCountry(user.getCountry());
			
			Transaction tx = currentSession.getTransaction();
			tx.begin();
			
			currentSession.update(existingUser);
			
			tx.commit();
			
			return new ApiResponse(HttpStatus.OK, null, null, existingUser);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse(HttpStatus.SERVICE_UNAVAILABLE, "Please try again Later", null, null);
		}finally {
			if(currentSession!=null) {
				currentSession.close();
			}
		}


	
	}
	
	

}
