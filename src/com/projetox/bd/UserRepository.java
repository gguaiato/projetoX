package com.projetox.bd;

import java.util.List;

import org.hibernate.criterion.Restrictions;

public class UserRepository extends Repository<User, Long>{

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
		User existingUser = findByFacebookId(user.getFacebookId());
		if (existingUser == null) {
			existingUser = save(user);
			return true;
		}
		return false;
	}
	
	public boolean delete(String facebookId) {
		User existingUser = findByFacebookId(facebookId);
		if (existingUser != null) {
			delete(existingUser);
			return true;
		}
		return false;
	}
	
	public List<User> list(int limitQtd) {
		return list(limitQtd);
	}
}
