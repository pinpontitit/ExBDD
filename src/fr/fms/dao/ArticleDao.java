package fr.fms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.fms.Entities.Article;
import fr.fms.authentication.BddConnection;

public class ArticleDao implements Dao<Article> {
	
	private Connection connection;
	
	public ArticleDao() throws Exception {
		connection = BddConnection.getConnection();
	}

	@Override
	public int create(Article obj) {
		String strCreate = "INSERT INTO T_Articles (Description, Brand, UnitaryPrice) VALUES (?,?,?);";
		try (PreparedStatement ps = connection.prepareStatement(strCreate, Statement.RETURN_GENERATED_KEYS)){ 
			ps.setString(1, obj.getDescription());
			ps.setString(2, obj.getBrand());
			ps.setDouble(3, obj.getUnitaryPrice());
			if ( ps.executeUpdate () == 1 ) {
				ResultSet rs = ps.getGeneratedKeys();
				int last_inserted_id=0;
                if(rs.next())
                    last_inserted_id = rs.getInt(1);
                return last_inserted_id;
			}
				
		} catch (SQLException e) {	
			throw new RuntimeException("Erreur lors de l'insertion de l'article en bdd:\n" + e.getMessage());
		}
		return 0;
	}

	

	@Override
	public boolean update(Article obj) {
		String strUpd = "update t_articles set Description=?, Brand=?, UnitaryPrice=? where IdArticle=?;";
		try (PreparedStatement ps = connection.prepareStatement(strUpd)){
			ps.setString(1, obj.getDescription());
			ps.setString(2, obj.getBrand());
			ps.setDouble(3, obj.getUnitaryPrice());
			ps.setInt(4, obj.getId());
			if ( ps.executeUpdate () == 1 )
				return true;
		} catch (SQLException e) {
			throw new RuntimeException("Erreur lors de la mise à jour de l'article:\n" + e.getMessage());
		}
		return false;
	}

	
	
	@Override
	public boolean delete(Article obj) {
		String strDel = "delete from t_articles where IdArticle=?;";
		try (PreparedStatement ps = connection.prepareStatement(strDel)){
			ps.setInt(1, obj.getId());
			if ( ps.executeUpdate () == 1 ) 
				return true;
		} catch (SQLException e) {
			throw new RuntimeException("Erreur lors de la suppression de l'article:\n" + e.getMessage());
		}
		return false;
	}
	
	
	
	@Override
	public Article read(int id) {
		Article queryResult=null;
		String strReadOne = "select IdArticle, t_articles.Description,Brand,UnitaryPrice, catname  from t_articles inner join t_categories on t_articles.idcategory=t_categories.idcategory where IdArticle=?;";

		try (PreparedStatement ps = connection.prepareStatement(strReadOne)){
			ps.setInt(1, id);
			try(ResultSet resultSet = ps.executeQuery()) {
				if(resultSet.next()) {
					int rsIdArticle = resultSet.getInt(1); 
					String rsDescription = resultSet.getString(2);
					String rsBrand = resultSet.getString(3);
					double rsPrice = resultSet.getDouble(4);
					String rsCatName = resultSet.getString(5);
					
					queryResult = new Article(rsIdArticle,rsDescription,rsBrand,rsPrice,rsCatName);
				} else {throw new RuntimeException("Erreur: Aucun article avec cet ID dans notre boutique.");}
			}

		} catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return queryResult;
	}

	
	
	@Override
	public ArrayList<Article> readAll() {
		ArrayList<Article> articles = new ArrayList<>();
		String strSql = "select IdArticle, t_articles.Description,Brand,UnitaryPrice,catname  from t_articles inner join t_categories on t_articles.idcategory=t_categories.idcategory";

		try(Statement statement = connection.createStatement ()){
			try(ResultSet resultSet = statement.executeQuery(strSql)) {
				while(resultSet.next()) {
					int rsIdArticle = resultSet.getInt(1); 
					String rsDescription = resultSet.getString(2);
					String rsBrand = resultSet.getString(3);
					double rsPrice = resultSet.getDouble(4);
					String rsCatName = resultSet.getString(5);
//					int rsIdCategory = resultSet.getInt(6);
					
					articles.add((new Article(rsIdArticle,rsDescription,rsBrand,rsPrice,rsCatName)));
				}
			}
		} catch(SQLException e) {
			throw new RuntimeException("Erreur lors de la récupération de l'ensemble des articles:\n" + e.getMessage());
		}
		return articles;
	}
	
	
	
	public ArrayList<Article> readAll(String order) {
		ArrayList<Article> articles = new ArrayList<>();
		String strSql;
		if (order.equals("prix croissants")) {
			strSql = "SELECT * FROM T_Articles ORDER BY UnitaryPrice";
		} else if (order.equals("prix décroissants")) {
			strSql = "SELECT * FROM T_Articles ORDER BY UnitaryPrice desc";
		} else if (order.equals("ordre alphabétique")) {
			strSql = "SELECT * FROM T_Articles order by Description";
		} else { //tri par défaut, ça pourrait être par nouveauté ou popularité ou autre
			strSql = "SELECT * FROM T_Articles order by IdArticle desc";
		}
		

		try(Statement statement = connection.createStatement ()){
			try(ResultSet resultSet = statement.executeQuery(strSql)) {
				while(resultSet.next()) {
					int rsIdArticle = resultSet.getInt(1); 
					String rsDescription = resultSet.getString(2);
					String rsBrand = resultSet.getString(3);
					double rsPrice = resultSet.getDouble(4);

					articles.add((new Article(rsIdArticle,rsDescription,rsBrand,rsPrice)));
				}
			}


		} catch(SQLException e) {
			throw new RuntimeException("Erreur lors de la récupération de l'ensemble des articles:\n" + e.getMessage());
		}
		return articles;
	}
	
	public ArrayList<Article> readAll(int idCategory, String order) {
		ArrayList<Article> articles = new ArrayList<>();
		
		String strSql;
		if (order.equals("prix croissants")) {
			strSql = "select IdArticle, t_articles.Description,Brand,UnitaryPrice from t_articles where idCategory=" + idCategory + " order by UnitaryPrice;";
		} else if (order.equals("prix décroissants")) {
			strSql = "select IdArticle, t_articles.Description,Brand,UnitaryPrice from t_articles where idCategory=" + idCategory + " order by UnitaryPrice desc;";
		} else if (order.equals("ordre alphabétique")) {
			strSql = "select IdArticle, t_articles.Description,Brand,UnitaryPrice from t_articles where idCategory=" + idCategory + " order by Description;";
		} else { //tri par défaut, ça pourrait être par nouveauté ou popularité ou autre
			strSql = "select IdArticle, t_articles.Description,Brand,UnitaryPrice from t_articles where idCategory=" + idCategory + " order by IdArticle desc;";
		}

		try(Statement statement = connection.createStatement ()){
			try(ResultSet resultSet = statement.executeQuery(strSql)) {
				while(resultSet.next()) {
					int rsIdArticle = resultSet.getInt(1); 
					String rsDescription = resultSet.getString(2);
					String rsBrand = resultSet.getString(3);
					double rsPrice = resultSet.getDouble(4);
					
					articles.add((new Article(rsIdArticle,rsDescription,rsBrand,rsPrice)));
				}
			}

		} catch(SQLException e) {
			throw new RuntimeException("Erreur lors de la récupération des articles de cette catégorie:\n" + e.getMessage());
		}
		return articles;
	}

}
