package fr.fms.business;

import java.util.ArrayList;

import fr.fms.Entities.*;

public interface IShop {
	
	public User addArticleToBasket(int articleId, User user);
	public User removeArticleFromBasket(int articleId, User user);
	public ArrayList<Article> showBasket(int userId); //avec possibilité de commander donc vider
	public User order(User user);
	
	public ArrayList<Order> displayOrderHistory();
	
	public Article displayOneArticle(int articleId);
	public ArrayList<Article> displayAllArticles();
	public ArrayList<Article> sortAndDisplayAllArticles(String sortChoice);

	public ArrayList<Article> displayAllCategories(); //avec possibilité ensuite de choisir

	public ArrayList<Article> displayAllBrands(); //avec possibilité ensuite de choisir
	
	

	public User changeEmail(int userId, String newEmail);
	public User changePassword(int userId, String oldPassword, String newPassword);

}
