package fr.fms;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

import fr.fms.Entities.Article;
import fr.fms.Entities.Category;
import fr.fms.Entities.Order;
import fr.fms.Entities.User;
import fr.fms.authentication.BddConnection;
import fr.fms.business.ILogImpl;
import fr.fms.business.IShopImpl;

public class MyShopApp {

	private static User loggedUser;	
	private static ArrayList<Article> articles;
	private static String sortingFilter = "nouveautés";
	private static IShopImpl shopJob;
	private static ILogImpl logJob;
	private static Scanner scan = new Scanner(System.in);
	private static final DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);
	private static Category selectedCategory = null;
	public static final String TEXT_BLUE = "\u001B[36m";
	public static final String TEXT_GREEN = "\u001B[32m";
	public static final String TEXT_RED = "\u001B[31m";
	public static final String TEXT_RESET = "\u001B[0m";

	public static void main(String[] args) throws Exception {	
		startApp();
		closeBddConnection();
	}

	private static void startApp() throws Exception {

		logJob = new ILogImpl();
		while(true) {
			login();
			while(loggedUser != null) {	
				shopJob = new IShopImpl();
				System.out.println("\nBienvenue sur notre boutique " + TEXT_BLUE + loggedUser.getLogin() + TEXT_RESET + " !");
				int action = 0;
				while(action != 13) {
					try {
						if (selectedCategory == null) {
							articles = shopJob.displayAllArticles(sortingFilter);
						} else {
							articles = shopJob.displayAllArticlesFromACategory(selectedCategory.getId(), sortingFilter);
						}
						System.out.println("\n------------------------------------------------------------------------------------");
						displayArticles();

						System.out.println("\nQue souhaitez-vous faire ? Saisir le numéro correspondant:");
						System.out.println();
						System.out.println("1: Obtenir les infos détaillées d'un article");
						System.out.println("2: Ajouter un article à mon panier");

						System.out.println("3: Consulter mon panier " + TEXT_RED + "(" + loggedUser.getBasket().values().stream().reduce(0, (subtotal, element) -> subtotal + element) + ")" + TEXT_RESET );
						System.out.println();
						System.out.println("4: Afficher la totalité des articles disponibles");
						System.out.println("5: Afficher les articles par catégorie");
						System.out.println();
						System.out.println("6: Trier les articles par ordre alphabétique");
						System.out.println("7: Trier les articles par prix croissants");
						System.out.println("8: Trier les articles par prix décroissants");
						System.out.println("9: Trier les articles par nouveautés");
						System.out.println();
						System.out.println("10: Afficher l'historique de mes commandes");
						System.out.println("11: Modifier l'email rattaché à ce compte");
						System.out.println("12: Modifier le mot de passe du compte");
						System.out.println("13: Déconnexion");

						action = scan.nextInt();

						switch(action) {	

						case 1 : 
							showAnArticleWithAllDetails();
							break;

						case 2 : 
							addArticleToBasket();						
							break;

						case 3 : 
							showBasketMenu();
							break;

						case 4 : 
							selectedCategory = null;
							break;

						case 5 : 
							showByCategories();	
							break;

						case 6 : 
							sortingFilter = "ordre alphabétique";
							break;

						case 7 : 
							sortingFilter = "prix croissants";
							break;

						case 8 : 
							sortingFilter = "prix décroissants";
							break;

						case 9 : 
							sortingFilter = "nouveautés";
							break;

						case 10 : 
							showMyOrdersHistory();
							break;

						case 11 : 
							changeEmail();
							break;

						case 12 : 
							changePassword();
							break;

						case 13 : 
							System.out.println("\nDéconnexion...\n");
							loggedUser = null;
							scan.nextLine();
							break;

						default : 
							System.out.println(TEXT_RED + "Erreur: Mauvaise saisie"+ TEXT_RESET);							
						}	

					} catch (InputMismatchException ime) {
						System.out.println(TEXT_RED + "Erreur: Mauvaise saisie" + TEXT_RESET );
						scan.nextLine();
					} catch (IndexOutOfBoundsException ioobe) {
						System.out.println(TEXT_RED + "Erreur: Mauvaise saisie" + TEXT_RESET );
						scan.nextLine();
					} catch (Exception e) {					
						System.out.println(TEXT_RED + e.getMessage() + TEXT_RESET);
					}
				}		
			}		
		}
	}

	private static void showByCategories() {
		ArrayList<Category> categories = shopJob.fetchAllAvailableCategories();
		System.out.println("\nChoisir parmi les catégories disponibles:");
		for (int i = 0; i < categories.size(); i++) {
			System.out.println((i+1)+" - " + categories.get(i).getName() + " - " + categories.get(i).getDescription());
		}
		selectedCategory = categories.get(scan.nextInt()-1);
	}

	private static void showAnArticleWithAllDetails() {
		System.out.println("Saisissez l'ID de l'article dont vous souhaitez obtenir les détails:");
		Article detailledArticle = shopJob.displayOneArticle(scan.nextInt());
		System.out.println(TEXT_GREEN + "ID:" + detailledArticle.getId() + " - " +detailledArticle.getDescription() + " - " + detailledArticle.getUnitaryPrice() + "€   +++   Marque:" + detailledArticle.getBrand() + " - " + "Catégorie:" + detailledArticle.getCatName() + TEXT_RESET);

		System.out.println("\n1: Ajouter cet article à mon panier");
		System.out.println("2: Revenir au menu précédent");
		System.out.println("Saisir le numéro correspondant:");

		int detailledArticleChoice = scan.nextInt();

		if (detailledArticleChoice == 1) {
			loggedUser = shopJob.addArticleToBasket(detailledArticle.getId(), loggedUser);
		}
	}

	private static void showMyOrdersHistory() {
		ArrayList<Order> myOrders = shopJob.displayOrderHistory(loggedUser.getId());
		
		System.out.println(TEXT_BLUE + "\nHistorique des commandes--------");
		
		if (myOrders.size()>0) {
			for (Order a : myOrders) {
				ArrayList<String> detailledListArticles = new ArrayList<>();

				for (HashMap.Entry<Integer, Integer> entry :a.getListArticles().entrySet()) {
					detailledListArticles.add(entry.getValue() + "x " + shopJob.displayOneArticle(entry.getKey()).getDescription());
				}
				System.out.println("Commande N°"+ a.getOrderId() + " - " + df.format(a.getOrderDate()) + " - Total:" + a.getTotalAmount() + "€ - " + detailledListArticles);
				
			}
		}
		else {
			System.out.println("VIDE");
		}
		
		System.out.println("--------------------------------" + TEXT_RESET);
		System.out.println("\nSaisir n'importe quel chiffre pour revenir aux articles:");
		scan.nextInt();
	}

	private static void addArticleToBasket() {
		System.out.println("Saisissez l'ID de l'article à ajouter dans le panier:");
		loggedUser = shopJob.addArticleToBasket(scan.nextInt(), loggedUser);
	}

	private static void changeEmail() {
		System.out.println("\nSaisissez votre nouvel email:");
		scan.nextLine();
		String newEmail = scanEmail();
		loggedUser = shopJob.changeEmail(loggedUser.getId(), newEmail);
		System.out.println("Email modifié avec succès ! \nVotre nouvel email est : " + newEmail);
	}

	private static void changePassword() {
		scan.nextLine();
		System.out.println("\nPour modifier votre mot de passe veuillez saisir votre ancien mot de passe:");
		String oldPassword = scan.nextLine();
		if (!oldPassword.equals(loggedUser.getPassword())) {
			throw new RuntimeException("Erreur: Saisie erronée de l'ancien mot de passe !");
		}
		System.out.println("Saisissez maintenant le nouveau mot de passe:");
		String firstNewPasswordInput = scan.nextLine();
		System.out.println("Saisissez le nouveau mot de passe une deuxième fois:");
		String secondNewPasswordInput = scan.nextLine();
		if (firstNewPasswordInput.equals(secondNewPasswordInput)) {
			shopJob.changePassword(loggedUser.getId(), oldPassword, secondNewPasswordInput);
			System.out.println("Mot de passe modifié avec succès !");
		} else {
			throw new RuntimeException("Erreur: Les nouveaux mots de passe saisis ne correspondent pas !");
		}
	}

	private static void showBasketMenu() {
		System.out.println("------------------------------------------------------------------------------------");
		HashMap<Article, Integer> basket;
		int basketMenuChoice=0;
		while(basketMenuChoice != 4) {
			basket = shopJob.showBasket(loggedUser.getId());
			if (basket.size()>0) {

				System.out.println(TEXT_BLUE + "\nPanier-------------");
				double total=0;

				for (HashMap.Entry<Article, Integer> entry : basket.entrySet()) {
					Article a = entry.getKey();
					System.out.println(entry.getValue() + "x - ID:" + a.getId() + " - " + a.getDescription() + " - " + a.getBrand() + " - " + a.getUnitaryPrice() + "€ - sous-total: " + a.getUnitaryPrice()*entry.getValue());
					total+=a.getUnitaryPrice()*entry.getValue();
				}	

				System.out.println("Total du panier: " + total + "€");
				System.out.println("--------------------" + TEXT_RESET);
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

				} 
				else if (basketMenuChoice == 2) {
					System.out.println("Saisir l'ID de l'article à retirer du panier:");

					for (HashMap.Entry<Article, Integer> entry : basket.entrySet()) {
						Article a = entry.getKey();
						System.out.println(entry.getValue() + "x - ID:" + a.getId() + " - " + a.getDescription() + " - " + a.getBrand() + " - " + a.getUnitaryPrice() + "€ - sous-total: " + a.getUnitaryPrice()*entry.getValue());
						total+=a.getUnitaryPrice()*entry.getValue();
					}

					loggedUser = shopJob.removeArticleFromBasket(scan.nextInt(), loggedUser);

				} 
				else if (basketMenuChoice == 3) {
					loggedUser = shopJob.removeAllArticlesFromBasket(loggedUser);
				}
			}

			else {
				System.out.println(TEXT_BLUE + "\nPanier-------------");
				System.out.println("VIDE");
				System.out.println("--------------------"+ TEXT_RESET);
				System.out.println("\nSaisir n'importe quel chiffre pour revenir aux articles:");
				scan.nextInt();
				break;
			}

		}	
	}

	private static void login() {
		welcome();

		System.out.println("\nrobert@gmail.com = robertlebogossdu64\njulie@gmail.com = joliejulie40\n");
		System.out.println("Saisissez un email valide :");
		String login = scanEmail();
		System.out.println("Maintenant saisissez votre mot de passe :");
		String password = scan.nextLine();
		try {
			loggedUser = logJob.login(login, password);
		}
		catch(Exception e) {
			System.out.println(TEXT_RED + "Erreur: l'email et/ou le mot de passe sont incorrectes." + TEXT_RESET);
		}
	}

	private static void displayArticles() {
		String category = "";
		if (selectedCategory != null) {
			category = TEXT_BLUE + " de la catégorie " + selectedCategory.getName() + TEXT_RESET;
		}
		System.out.println("\nListe des articles"+ category +" triée par " + TEXT_BLUE + sortingFilter + TEXT_RESET + " :");
		System.out.print(TEXT_GREEN);
		if (articles.size()>0)
			for (Article a : articles)
				System.out.println("ID:" + a.getId() + " - " + a.getDescription() + " - " + a.getBrand() + " - " + a.getUnitaryPrice() + "€");
		else System.out.println("Désolé, tous nos articles sont en rupture de stock !");
		System.out.print(TEXT_RESET);
	}

	private static String scanEmail() {
		String validEmail = "";
		validEmail = scan.nextLine();
		while(!validEmail.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
			System.out.println(TEXT_RED + "Erreur: Veuillez saisir un email au format valide :" + TEXT_RESET);
			validEmail = scan.nextLine();
		}		
		return validEmail;
	}


	private static void welcome() {
		System.out.println("********************************************");
		System.out.println("BIENVENUE DANS My Shop !");
		System.out.println("********************************************");
	}

	private static void closeBddConnection() {
		BddConnection.closeConnection();
	}

}
