package fr.fms.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;


import fr.fms.Entities.Article;
import fr.fms.Entities.Order;
import fr.fms.Entities.User;
import fr.fms.dao.UserDao;
import fr.fms.dao.ArticleDao;
import fr.fms.dao.OrderDao;

public class IShopImpl implements IShop {

	private UserDao userDao;
	private ArticleDao articleDao;
	private OrderDao orderDao;

	public IShopImpl() throws Exception {
		userDao = new UserDao();
		articleDao = new ArticleDao();
		orderDao = new OrderDao();
	}

	@Override
	public User addArticleToBasket(int articleId, User user) {
		//User tmpUser = new User(user.getId(),user.getLogin(),user.getPassword(),user.getBasket(),user.getOrderHistory()); TODO gérer le cas où ça n'a pas marché

		user.getBasket().add(articleId);

		userDao.update(user);
		return user;
	}

	@Override
	public User removeArticleFromBasket(int articlePosition, User user) {
		//		Iterator<Integer> new_Iterator = user.getBasket().iterator();
		//
		//		while (new_Iterator.hasNext()) {
		//			if (new_Iterator.next() == articleId)
		//				 new_Iterator.remove();
		//		}
		user.getBasket().remove(articlePosition-1);

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
	public ArrayList<Article> showBasket(int userId) {
		ArrayList<Integer> userBasketIds = userDao.read(userId).getBasket();

		ArrayList<Article> articles = new ArrayList<>();

		for (int id : userBasketIds) {
			articles.add(articleDao.read(id));
		}

		return articles;
	}

	@Override
	public User order(User user) {

		ArrayList<Double> prices = new ArrayList<>();
		user.getBasket().stream().forEach(x->prices.add(articleDao.read(x).getUnitaryPrice()));
		Order order = new Order(new Date(), prices.stream().reduce(0.0, (subtotal, element) -> subtotal + element), user.getId(), user.getBasket());
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

//	@Override
//	public ArrayList<Article> sortAndDisplayAllArticles(String sortChoice) {
//		ArrayList<Article> articles = articleDao.readAll();
//
//		if (sortChoice.equals("alphabetical")) {
//			Collections.sort(articles, 
//					(o1, o2) -> o1.getDescription().compareToIgnoreCase(o2.getDescription()));
//			return articles;
//		} else if (sortChoice.equals("price")) {
//			Collections.sort(articles, 
//					Comparator.comparingDouble(Article::getUnitaryPrice));
//			return articles;
//		}
//		//et tout ce qui est imaginable encore...
//		return articles;
//	}

	@Override
	public ArrayList<Article> displayAllCategories() {
		return null;
		//	return categoryDao.readAll();
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

}
