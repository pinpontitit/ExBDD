package fr.fms.business;

import java.util.ArrayList;
import java.util.HashMap;

import fr.fms.Entities.*;

public interface IShop {

	public User addArticleToBasket(int articleId, User user);
	public User changeEmail(int userId, String newEmail);
	public User changePassword(int userId, String oldPassword, String newPassword);
	ArrayList<Article> displayAllArticles(String sortCriterion);
	public Article displayOneArticle(int articleId);
	public ArrayList<Order> displayOrderHistory(int userId);
	public ArrayList<Category> fetchAllAvailableCategories();
	public User order(User user);
	public User removeAllArticlesFromBasket(User user);
	public User removeArticleFromBasket(int articleId, User user);
	public HashMap<Article, Integer> showBasket(int userId);
}
