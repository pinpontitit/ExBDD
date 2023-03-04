package fr.fms;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import fr.fms.Entities.Article;
import fr.fms.Entities.Order;
import fr.fms.Entities.User;
import fr.fms.authentication.BddConnection;
import fr.fms.business.IShopImpl;

public class MyShopApp {
	private static User loggedUser;	
	private static ArrayList<Article> articles;
	private static IShopImpl shopJob;
	private static Scanner scan = new Scanner(System.in);
	private static final DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);

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

		while(true) {

			System.out.println("\n//Pour info, utilisateurs dispo en BDD:\n//robert@gmail.com = robertlebogossdu64\n//julie@gmail.com = joliejulie40\n");
			System.out.println("Saisissez un email valide :");

			String login = scanEmail();

			System.out.println("Maintenant saisissez votre mot de passe :");
			String password = scan.nextLine();

			try {
				loggedUser = shopJob.login(login, password);
			}
			catch(Exception e) {
				System.out.println("Erreur: l'email et/ou le mot de passe sont incorrectes.");
			}

			//----------------




			while(loggedUser != null) 
			{			
				int action = 0;
				System.out.println("\nBienvenue sur notre boutique " + loggedUser.getLogin() + " !");

				System.out.println("Voici la liste de nos articles:");

				displayArticles("default sorting order");

				while(action != 11) 
				{	
					try {
						System.out.println("\nQue souhaitez-vous faire ? Saisir le numéro correspondant:");
						System.out.println("1: Ajouter un article à mon panier");
						System.out.println("2: Consulter mon panier");
						System.out.println("3: Afficher l'historique de mes commandes");
						System.out.println("4: Afficher un article avec tous ses détails");
						System.out.println("5: Naviguer parmi les catégories d'articles");
						System.out.println("6: Trier les articles par ordre alphabétique");
						System.out.println("7: Trier les articles par prix croissants");
						System.out.println("8: Trier les articles par prix décroissants");
						System.out.println("9: Modifier l'email rattaché à ce compte");
						System.out.println("10: Modifier le mot de passe du compte");
						System.out.println("11: Déconnexion");

						action = scan.nextInt();

						switch(action) {	

						case 1 : 	for (Article a : articles)
							System.out.println("ID:" + a.getId() + " - " + a.getDescription() + " - " + a.getBrand() + " - " + a.getUnitaryPrice() + "€");
						System.out.println("Saisissez l'ID de l'article:");
						loggedUser = shopJob.addArticleToBasket(scan.nextInt(), loggedUser);						
						break;

						case 2 : 
							ArrayList<Article> basket;
							int basketMenuChoice=0;
							while(basketMenuChoice != 4) {
								basket = shopJob.showBasket(loggedUser.getId());
								if (basket.size()>0) {
									System.out.println("\nPanier-------------");
									double total=0;
									for (Article a : basket) {
										System.out.println("ID:" + a.getId() + " - " + a.getDescription() + " - " + a.getBrand() + " - " + a.getUnitaryPrice() + "€");
										total+=a.getUnitaryPrice();
									}
									System.out.println("Total du panier: " + total + "€");
									System.out.println("--------------------");
									System.out.println("\n1: Passer commande");
									System.out.println("2: Supprimer un des articles du panier");
									System.out.println("3: Supprimer tous les articles du panier");
									System.out.println("4: Revenir dans la boutique");
									System.out.println("Saisir le numéro correspondant:");
									basketMenuChoice = scan.nextInt();
									if (basketMenuChoice == 1) {
										loggedUser = shopJob.order(loggedUser);
										System.out.println("Commande passée avec succès !");
										break;
									} else if (basketMenuChoice == 2) {
										System.out.println("Choisir l'article à retirer du panier:");

										for (int i = 0; i < basket.size(); i++) {
											System.out.println((i+1)+" = ID:" + basket.get(i).getId() + " - " + basket.get(i).getDescription() + " - " + basket.get(i).getBrand() + " - " + basket.get(i).getUnitaryPrice() + "€");
										}
										loggedUser = shopJob.removeArticleFromBasket(scan.nextInt(), loggedUser);

									} else if(basketMenuChoice == 3) {
										loggedUser = shopJob.removeAllArticlesFromBasket(loggedUser);

									}
								}
								else {
									System.out.println("\nPanier-------------");
									System.out.println("VIDE");
									System.out.println("--------------------");
									break;
								}

							}
							break;

						case 3 : 
							ArrayList<Order> myOrders = shopJob.displayOrderHistory(loggedUser.getId());
							if (myOrders.size()>0) {
								System.out.println("Historique de vos commandes");
								for (Order a : myOrders) {
									ArrayList<String> detailledListArticles = new ArrayList<>();
									for (int articleId : a.getListArticles())
										detailledListArticles.add(shopJob.displayOneArticle(articleId).getDescription());


									System.out.println("Commande N°"+ a.getOrderId() + " - " + df.format(a.getOrderDate()) + " - Total:" + a.getTotalAmount() + "€ - " + detailledListArticles);
								}
							}
							else {
								System.out.println("\nCommandes passées----");
								System.out.println("VIDE");
								System.out.println("--------------------");
							}

							break;

						case 4 : for (Article a : articles)
							System.out.println("ID:" + a.getId() + " - " + a.getDescription() + " - " + a.getBrand() + " - " + a.getUnitaryPrice() + "€");
						System.out.println("Saisissez l'ID de l'article dont vous souhaitez obtenir les détails:");


						Article detailledArticle = shopJob.displayOneArticle(scan.nextInt());
						System.out.println("ID:" + detailledArticle.getId() + " - " +detailledArticle.getDescription() + " - " + detailledArticle.getUnitaryPrice() + "€  +  Marque:" + detailledArticle.getBrand() + " - " + "Catégorie:" + detailledArticle.getCatName());

						break;

						case 5 : System.out.println("Fonctionnalité à venir...");
						break;
						
						case 6 : displayArticles("alphabetical");
						break;
						
						case 7 : displayArticles("priceUp");
						break;

						case 8 : displayArticles("priceDown");
						break;
						
						case 9 : System.out.println("\nSaisissez votre nouvel email:");
						scan.nextLine();
						String newEmail = scanEmail();
						loggedUser = shopJob.changeEmail(loggedUser.getId(), newEmail);
						System.out.println("Email modifié avec succès ! \nVotre nouvel email est : " + newEmail);
						break;

						case 10 : 
							scan.nextLine();
							System.out.println("\nPour modifier votre mot de passe veuillez saisir votre ancien mot de passe:");
							String oldPassword = scan.nextLine();
							System.out.println("Saisissez le nouveau mot de passe:");
							String firstNewPasswordInput = scan.nextLine();
							System.out.println("Saisissez le nouveau mot de passe une deuxième fois:");
							String secondNewPasswordInput = scan.nextLine();
							if (firstNewPasswordInput.equals(secondNewPasswordInput)) {
								shopJob.changePassword(loggedUser.getId(), oldPassword, secondNewPasswordInput);
								System.out.println("Mot de passe modifié avec succès !");
							} else {
								throw new RuntimeException("Erreur: Les mots de passes ne correspondent pas !");
							}
							break;


						case 11 : System.out.println("\nDéconnexion..." + "\n");
						loggedUser = null;
						scan.nextLine();
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
	}

	private static void displayArticles(String order) {
		articles = shopJob.displayAllArticles(order);
		if (articles.size()>0)
			for (Article a : articles)
				System.out.println("ID:" + a.getId() + " - " + a.getDescription() + " - " + a.getBrand() + " - " + a.getUnitaryPrice() + "€");
		else System.out.println("Aucune donnée à afficher");
	}

	private static String scanEmail() {
		String validEmail = "";
		validEmail = scan.nextLine();
		while(!validEmail.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
			System.out.println("Erreur: Veuillez saisir un email au format valide :");
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
