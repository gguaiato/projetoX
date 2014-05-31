package com.projetox.bd;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;

import com.projetox.bd.model.User;
import com.projetox.rest.WebServices;

public class UserRepository extends Repository<User>{

	private static Logger logger = Logger.getLogger(UserRepository.class);
	private static final long serialVersionUID = 926118319362559453L;
	private static UserRepository instance;
	
	private UserRepository() {
		super();
	}
	
	public static UserRepository instance() {
		if(instance==null)
			instance = new UserRepository();
		return instance;
	}
	
	private User findByFacebookId(String facebookId) {
		return findUniqueByCriteria(Restrictions.eq("facebookId", facebookId));
	}
	
	
	public boolean insert(User user) {
		logger.info("Inserindo usuario na base de dados com facebookId = " + user.getFacebookId());
		User existingUser = findByFacebookId(user.getFacebookId());
		if (existingUser == null) {
			existingUser = save(user);
			return true;
		}
		return false;
	}
	
	public boolean delete(String facebookId) {
		logger.info("Deletando usuario na base de dados com facebookId = " + facebookId);
		User existingUser = findByFacebookId(facebookId);
		if (existingUser != null) {
			delete(existingUser);
			return true;
		}
		return false;
	}
	
	public List<User> list(int limitQtd) {
		return super.list(limitQtd);
	}
}
