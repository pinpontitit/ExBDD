package fr.fms.business;

import fr.fms.Entities.User;
import fr.fms.dao.UserDao;

public class ILogImpl implements ILog {
	
	private UserDao userDao;
	
	public ILogImpl() throws Exception {
		userDao = new UserDao();
	}

	@Override
	public User login(String email, String password) {
		User tmpUser = userDao.read(email);

		if (!tmpUser.getPassword().equals(password))
			throw new RuntimeException("Incorrect password");

		else return new User(tmpUser.getId(), tmpUser.getLogin(), tmpUser.getPassword(), tmpUser.getBasket(), tmpUser.getOrderHistory());
	}

	@Override
	public User signin(String email, String password) {
		//TODO
		return null;
	}

}
