package fr.fms.Entities;

import java.util.ArrayList;

import fr.fms.dao.*;

public class TestUser {
	private static UserDao userDao;

	public static void main(String[] args) {
		try {

			userDao = new UserDao();

			//1er affichage----------------
			ArrayList<User> users = userDao.readAll();
			if (users.size()>0)
				for (User user : users)
					System.out.println(user.getId() + " - " + user.getLogin() + " - " + user.getPassword());
			else System.out.println("Aucune donnée à afficher");

			//espace
			System.out.println();

			//insertion-----------------
			User jeanpaul2 = new User("jeanpaul2@gmail.com", "LePapeAnoel");

//					int lastGeneratedKey = userDao.create(jeanpaul2);
//					if (lastGeneratedKey != 0) {
//						System.out.println("insertion ok");
//						System.out.println(lastGeneratedKey);
//						jeanpaul2.setId(lastGeneratedKey);
//					}


//			update--------------
//					jeanpaul2.setPassword("LePapeIllon");
//					System.out.println(jeanpaul2.getId());
//			
//					if (userDao.update(jeanpaul2))
//						System.out.println("update ok");

			//delete-----------------------------------------

//					if (userDao.delete(new User(3, "jeanpaul2", "LePapeIllion")))
//						System.out.println("delete ok");

			//read--------------------------------------------
//						User queryResult = userDao.read(2);
//						System.out.println(queryResult.getId() + " - " + queryResult.getLogin() + " - " + queryResult.getPassword());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
