package fr.fms.business;

import fr.fms.Entities.User;

public interface ILog {
	public User login(String email, String password);
	public User signin(String email, String password);
}
