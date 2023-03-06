package fr.fms.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import fr.fms.Entities.Article;
import fr.fms.Entities.Category;
import fr.fms.Entities.Order;
import fr.fms.Entities.User;
import fr.fms.dao.UserDao;
import fr.fms.dao.ArticleDao;
import fr.fms.dao.CategoryDao;
import fr.fms.dao.OrderDao;

public class IShopImpl implements IShop {

	private UserDao userDao;
	private ArticleDao articleDao;
	private OrderDao orderDao;
	private CategoryDao categoryDao;

	public IShopImpl() throws Exception {
		userDao = new UserDao();
		articleDao = new ArticleDao();
		orderDao = new OrderDao();
		categoryDao = new CategoryDao();
	}

	@Override
	public User addArticleToBasket(int articleId, User user) {
		user.getBasket().merge(articleId, 1, Integer::sum);
		userDao.update(user);
		return user;
	}

	@Override
	public User removeArticleFromBasket(int articleId, User user) {
		user.getBasket().remove(articleId);
		userDao.update(user);
		return user;
	}

	@Override
	public User removeAllArticlesFromBasket(User user) {
		user.getBasket().clear();
		userDao.update(user);
		return user;
	}

	@Override
	public HashMap<Article, Integer> showBasket(int userId) {
		HashMap<Integer, Integer> userBasketIds = userDao.read(userId).getBasket();
 	   
		HashMap<Article, Integer> articles = new HashMap<Article, Integer>();
		for (HashMap.Entry<Integer, Integer> entry : userBasketIds.entrySet()) {
			articles.put(articleDao.read(entry.getKey()), entry.getValue());
		}
		
		return articles;
	}

	@Override
	public User order(User user) {
//		ArrayList<Double> prices = new ArrayList<>();
		double totalPrice=0;
		
		for (HashMap.Entry<Integer, Integer> entry : user.getBasket().entrySet()) {
//			prices.add(articleDao.read(entry.getKey()).getUnitaryPrice() * entry.getValue());
			totalPrice += articleDao.read(entry.getKey()).getUnitaryPrice() * entry.getValue(); 
		}
		
		
		Order order = new Order(new Date(), totalPrice, user.getId(), user.getBasket());
		int orderId = orderDao.create(order);

		user.getOrderHistory().add(orderId);
		user.getBasket().clear();

		userDao.update(user);

		return user;
	}

	@Override
	public ArrayList<Order> displayOrderHistory(int userId) {
		return orderDao.readAll(userId);
	}

	@Override
	public Article displayOneArticle(int articleId) {
		return articleDao.read(articleId);
	}

	@Override
	public ArrayList<Article> displayAllArticles(String order) {
		return articleDao.readAll(order);
	}

	@Override
	public ArrayList<Category> fetchAllAvailableCategories() {
		return categoryDao.readAll();	
	}

	@Override
	public ArrayList<Article> displayAllBrands() {
		//	return categoryDao.readAll();
		return null;
	}

	@Override
	public User changeEmail(int userId, String newEmail) {
		User user = userDao.read(userId);
		user.setLogin(newEmail);
		userDao.update(user);
		return user;
	}

	@Override
	public User changePassword(int userId, String oldPassword, String newPassword) {
		User user = userDao.read(userId);
		if (oldPassword.equals(user.getPassword()))
			user.setPassword(newPassword);
		else throw new RuntimeException("Erreur: Ancien mot de passe erroné!");
		userDao.update(user);
		return user;
	}

	@Override
	public User login(String email, String password) {
		User tmpUser = userDao.read(email);

		if (!tmpUser.getPassword().equals(password))
			throw new RuntimeException("Incorrect password");

		else return new User(tmpUser.getId(), tmpUser.getLogin(), tmpUser.getPassword(), tmpUser.getBasket(), tmpUser.getOrderHistory());
	}

	public ArrayList<Article> displayAllArticlesFromACategory(int idCategory, String order) {
		return articleDao.readAll(idCategory, order);
	}

}
