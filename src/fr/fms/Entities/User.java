package fr.fms.Entities;

import java.util.ArrayList;


public class User {
	private int id;
	private String login;
	private String password;
	private ArrayList<Integer> basket;
	private ArrayList<Integer> orderHistory;
	
	
	public User(int id, String login, String password, ArrayList<Integer> basket, ArrayList<Integer> orderHistory) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.basket = basket;
		this.orderHistory = orderHistory;
	}

	public User(int id, String login, String password) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.basket = new ArrayList<Integer>();
		this.orderHistory = new ArrayList<Integer>();
	}



	public User(String login, String password) {
		this.login = login;
		this.password = password;
		this.basket = new ArrayList<Integer>();
		this.orderHistory = new ArrayList<Integer>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<Integer> getBasket() {
		return basket;
	}

	public void setBasket(ArrayList<Integer> basket) {
		this.basket = basket;
	}

	public ArrayList<Integer> getOrderHistory() {
		return orderHistory;
	}

	public void setOrderHistory(ArrayList<Integer> orderHistory) {
		this.orderHistory = orderHistory;
	}
	
}
