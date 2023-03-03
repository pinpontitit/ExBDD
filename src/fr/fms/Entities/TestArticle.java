package fr.fms.Entities;

import fr.fms.dao.*;
import java.util.ArrayList;

public class TestArticle {

	private static ArticleDao articleDao;

	public static void main(String[] args) throws Exception {

		try {

			articleDao = new ArticleDao();

			//1er affichage----------------
			ArrayList<Article> articles = articleDao.readAll();
			if (articles.size()>0)
				for (Article a : articles)
					System.out.println(a.getId() + " - " + a.getDescription() + " - " + a.getBrand() + " - " + a.getUnitaryPrice());
			else System.out.println("Aucune donnée à afficher");

			//espace
			System.out.println();

			//insertion-----------------
			Article xiaomi10 = new Article("Xiaomi 10", "Xiaomi", 750);

//			int lastGeneratedKey = articleDao.create(xiaomi10);
//			if (lastGeneratedKey != 0) {
//				System.out.println("insertion ok");
//				System.out.println(lastGeneratedKey);
//				xiaomi10.setId(lastGeneratedKey);
//			}


			//update--------------
//			xiaomi10.setUnitaryPrice(999);
//			System.out.println(xiaomi10.getId());
//
//			if (articleDao.update(xiaomi10))
//				System.out.println("update ok");

			//delete-----------------------------------------

//			if (articleDao.delete(new Article(26, "Xiaomi 10", "Xiaomi", 750)))
//				System.out.println("delete ok");

			//read----------------------------------
			//			Article queryResult = articleDao.read(8);
			//			System.out.println(queryResult.getId() + " - " + queryResult.getDescription() + " - " + queryResult.getBrand() + " - " + queryResult.getUnitaryPrice());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
