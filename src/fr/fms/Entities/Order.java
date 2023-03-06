package fr.fms.Entities;

import java.util.Date;
import java.util.HashMap;

public class Order {
	private int orderId;
	private Date orderDate;
	private double totalAmount;
	private int userId;
	private HashMap<Integer, Integer> listArticles;

	public Order(int orderId, Date orderDate, double totalAmount, int userId, HashMap<Integer, Integer> listArticles) {
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.totalAmount = totalAmount;
		this.userId = userId;
		this.listArticles = listArticles;
	}
	
	public Order(Date orderDate, double totalAmount, int userId, HashMap<Integer, Integer> listArticles) {
		this.orderDate = orderDate;
		this.totalAmount = totalAmount;
		this.userId = userId;
		this.listArticles = listArticles;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public HashMap<Integer, Integer> getListArticles() {
		return listArticles;
	}

	public void setListArticles(HashMap<Integer, Integer> listArticles) {
		this.listArticles = listArticles;
	}

	@Override
	public String toString() {
		return "Order "+ orderId + ": orderDate=" + orderDate + ", totalAmount=" + totalAmount + ", userId="
				+ userId + "\n, listArticles=" + listArticles + "]";
	}

}