package fr.fms;

import java.util.ArrayList;

import java.util.Scanner;

import fr.fms.Entities.Article;
import fr.fms.Entities.Order;
import fr.fms.Entities.User;
import fr.fms.dao.UserDao;
import fr.fms.authentication.BddConnection;
import fr.fms.business.IShopImpl;

public class MyShopApp {
	private static User loggedUser;	
	private static ArrayList<Article> articles;
	private static UserDao userDao; //juste pour le logoin, après le virer
	private static IShopImpl shopJob;
	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) throws Exception {	
		welcome();	
		login();
		startApp();
		close();
	}

	private static void login() {

	}

	private static void startApp() throws Exception {
		shopJob = new IShopImpl();
		userDao = new UserDao(); //virer ça après avoir déplacé login
		//déplacer ça dans l'impl et le masquage du password dans userDao ?
		//------
		while (loggedUser==null) {
			System.out.println("Saisissez un email valide :");
			String login = scanEmail();

			System.out.println("Maintenant saisissez votre mot de passe :");
			String password = scan.nextLine();

			try {
				User tmpUser = userDao.read(login);

				if (!tmpUser.getPassword().equals(password))
					throw new RuntimeException("Incorrect password");

				else loggedUser = new User(tmpUser.getId(), tmpUser.getLogin(), "hidden", tmpUser.getBasket(), tmpUser.getOrderHistory());
			}
			catch(Exception e) {
				System.out.println("Erreur: l'email et/ou le mot de passe sont incorrectes.");
			}
		}
		//----------------




		while(true) 
		{			
			int action = 0;
			System.out.println("Bienvenue sur notre boutique " + loggedUser.getLogin() + " !");

			System.out.println("Voici la liste de nos articles:");

			displayArticles();

			while(action != 8) 
			{	
				try {
					System.out.println("Que souhaitez-vous faire ? Saisir le numéro correspondant:");
					System.out.println("1: Ajouter un article à mon panier");
					System.out.println("2: Consulter mon panier");
					System.out.println("3: Afficher l'historique de mes commandes");
					System.out.println("4: Afficher un article avec tous ses détails");
					System.out.println("5: Naviguer parmi les catégories d'articles");
					System.out.println("6: Modifier l'email rattaché à ce compte");
					System.out.println("7: Modifier le mot de passe du compte");
					System.out.println("8: Déconnexion");

					action = scan.nextInt();

					switch(action) {	

					case 1 : 	displayArticles();
					System.out.println("Saisissez l'ID de l'article:");
					loggedUser = shopJob.addArticleToBasket(scan.nextInt(), loggedUser);						
					break;

					case 2 : 
						ArrayList<Article> basket = shopJob.showBasket(loggedUser.getId());

						if (basket.size()>0)
							for (Article a : basket)
								System.out.println(a.getId() + " - " + a.getDescription() + " - " + a.getBrand() + " - " + a.getUnitaryPrice());
						else System.out.println("Aucun article dans votre panier");

						System.out.println("1: Passer commande");
						System.out.println("2: Revenir dans la boutique");
						System.out.println("Saisir le numéro correspondant:");
						int basketMenuChoice = scan.nextInt();
						if (basketMenuChoice == 1) {
							loggedUser = shopJob.order(loggedUser);
						}
						break;

					case 3 : 
						ArrayList<Order> myOrders = shopJob.displayOrderHistory();
						if (myOrders.size()>0)
							for (Order a : myOrders)
								System.out.println(a.getOrderId() + " - " + a.getOrderDate() + " - " + a.getTotalAmount() + " - " + a.getListArticles());
						else System.out.println("Aucune commande passée à afficher");

						break;

					case 4 : displayArticles();
					System.out.println("Saisissez l'ID de l'article:");
					System.out.println(shopJob.displayOneArticle(scan.nextInt()));
					
					break;

					case 5 : System.out.println("Fonctionnalité à venir...");
					break;

					case 6 : System.out.println("Saisissez votre nouvel email:");
					String newEmail = scanEmail();
					loggedUser = shopJob.changeEmail(loggedUser.getId(), newEmail);
					break;

					case 7 : 

						System.out.println("Pour modifier votre mot de passe veuillez saisir votre ancien mot de passe:");
						String oldPassword = scan.nextLine();
						System.out.println("Saisissez le nouveau mot de passe:");
						String firstNewPasswordInput = scan.nextLine();
						System.out.println("Saisissez le nouveau mot de passe une deuxième fois:");
						String secondNewPasswordInput = scan.nextLine();
						if (firstNewPasswordInput.equals(secondNewPasswordInput)) {
							shopJob.changePassword(loggedUser.getId(), oldPassword, secondNewPasswordInput);
						} else {
							throw new RuntimeException("Erreur: Les mots de passes ne correspondent pas");
						}
						break;


					case 8 : System.out.println("Déconnexion..." + "\n");
					loggedUser = null;
					break;

					default : System.out.println("Mauvaise saisie");							
					}	
				}
				catch (Exception e) {					
					System.out.println(e.getMessage());
				}
			}				
		}
	}

	private static void displayArticles() {
		articles = shopJob.displayAllArticles();
		if (articles.size()>0)
			for (Article a : articles)
				System.out.println(a.getId() + " - " + a.getDescription() + " - " + a.getBrand() + " - " + a.getUnitaryPrice());
		else System.out.println("Aucune donnée à afficher");
	}

	private static String scanEmail() {
		String validEmail = "";
		validEmail = scan.nextLine();
		while(!validEmail.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
			System.out.println("Erreur: Vous devez saisir un email valide.");
			validEmail = scan.nextLine();
		}		
		return validEmail;
	}


	private static void welcome() {

		System.out.println("********************************************");
		System.out.println("BIENVENUE DANS My Shop !");
		System.out.println("********************************************");	
	}

	private static void close() {
		BddConnection.closeConnection();
	}

}
