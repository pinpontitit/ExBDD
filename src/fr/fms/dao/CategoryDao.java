package fr.fms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.fms.Entities.Category;
import fr.fms.authentication.BddConnection;

public class CategoryDao implements Dao<Category> {
	
	private Connection connection;
	
	public CategoryDao() throws Exception {
		connection = BddConnection.getConnection();
	}

	@Override
	public int create(Category obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Category read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Category obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Category obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Category> readAll() {
		ArrayList<Category> articles = new ArrayList<>();
		String strSql = "select * from t_categories";

		try(Statement statement = connection.createStatement ()){
			try(ResultSet resultSet = statement.executeQuery(strSql)) {
				while(resultSet.next()) {
					int rsIdCategory = resultSet.getInt(1); 
					String rsCatName = resultSet.getString(2);
					String rsDescription = resultSet.getString(3);
					
					articles.add((new Category(rsIdCategory,rsCatName,rsDescription)));
				}
			}


		} catch(SQLException e) {
			throw new RuntimeException("Erreur lors de la récupération de l'ensemble des catégories:\n" + e.getMessage());
		}
		return articles;
	}	

}
