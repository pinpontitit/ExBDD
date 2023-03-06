package fr.fms.business;

import java.util.ArrayList;
import java.util.HashMap;

import fr.fms.Entities.*;

public interface IShop {

	public User addArticleToBasket(int articleId, User user);
	public User removeArticleFromBasket(int articleId, User user);
	public User removeAllArticlesFromBasket(User user);
	public HashMap<Article, Integer> showBasket(int userId); //avec possibilité de commander donc vider
	public User order(User user);
	public ArrayList<Order> displayOrderHistory(int userId);
	public Article displayOneArticle(int articleId);
	ArrayList<Article> displayAllArticles(String order);
	public ArrayList<Category> fetchAllAvailableCategories(); //avec possibilité ensuite de choisir
	public ArrayList<Article> displayAllBrands(); //avec possibilité ensuite de choisir

	public User changeEmail(int userId, String newEmail);
	public User changePassword(int userId, String oldPassword, String newPassword);
	public User login(String email, String password);
	

}
