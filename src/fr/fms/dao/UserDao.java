package fr.fms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.fms.Entities.User;
import fr.fms.authentication.BddConnection;

public class UserDao implements Dao<User> {
	
private Connection connection;
	
	public UserDao() throws Exception {
		connection = BddConnection.getConnection();
	}

	@Override
	public int create(User obj) {
		String strCreate = "INSERT INTO T_Users (Login, Password, Basket, Orders) VALUES (?,?,?,?);";
		try (PreparedStatement ps = connection.prepareStatement(strCreate, Statement.RETURN_GENERATED_KEYS)){  
			ps.setString(1, obj.getLogin());
			ps.setString(2, obj.getPassword());
			ps.setString(3, obj.getBasket().toString());
			ps.setString(4, obj.getOrderHistory().toString());
			if ( ps.executeUpdate () == 1 ) {
				ResultSet rs = ps.getGeneratedKeys();
				int last_inserted_id=0;
                if(rs.next())
                    last_inserted_id = rs.getInt(1);
                return last_inserted_id;
			}
				
		} catch (SQLException e) {	
			throw new RuntimeException("Erreur lors de l'insertion de l'utilisateur en bdd:\n" + e.getMessage());
		}
		return 0;
	}

	@Override
	public User read(int id) {
		User queryResult=null;

		String strReadOne = "select * from t_users where IdUser=?";

		try (PreparedStatement ps = connection.prepareStatement(strReadOne)){
			ps.setInt(1, id);
			try(ResultSet resultSet = ps.executeQuery()) {
				if(resultSet.next()) {
					int rsIdUser = resultSet.getInt(1); 
					String rsLogin = resultSet.getString(2);
					String rsPassword = resultSet.getString(3);
					String rsBasket = resultSet.getString(4);
					String rsOrders = resultSet.getString(4);
					ArrayList<Integer> listBasket = new ArrayList<>();
				    if (rsBasket.length() > 2) {
				    	rsBasket = rsBasket.substring(1, rsBasket.length() - 1).replace(" ", "");
				       String[] strArr = rsBasket.split(",");
				       for (String str : strArr)
				    	   listBasket.add(Integer.parseInt(str));
				    }
				    
				    ArrayList<Integer> listOrders = new ArrayList<>();
				    if (rsOrders.length() > 2) {
				    	rsOrders = rsOrders.substring(1, rsOrders.length() - 1).replace(" ", "");
				       String[] strArr = rsOrders.split(",");
				       for (String str : strArr)
				    	   listOrders.add(Integer.parseInt(str));
				    }
				    
					queryResult = new User(rsIdUser,rsLogin,rsPassword, listBasket, listOrders);
				}
			}

		} catch(SQLException e) {
			throw new RuntimeException("Erreur lors de la récupération de l'utilisateur:\n" + e.getMessage());
		}
		return queryResult;
	}
	
	public User read(String login) {
		User queryResult=null;

		String strReadOne = "select * from t_users where Login=?";

		try (PreparedStatement ps = connection.prepareStatement(strReadOne)){
			ps.setString(1, login);
			try(ResultSet resultSet = ps.executeQuery()) {
				if(resultSet.next()) {
					int rsIdUser = resultSet.getInt(1); 
					String rsLogin = resultSet.getString(2);
					String rsPassword = resultSet.getString(3);
					String rsBasket = resultSet.getString(4);
					String rsOrders = resultSet.getString(4);
					
					ArrayList<Integer> listBasket = new ArrayList<>();
				    if (rsBasket.length() > 2) {
				    	rsBasket = rsBasket.substring(1, rsBasket.length() - 1).replace(" ", "");
				       String[] strArr = rsBasket.split(",");
				       for (String str : strArr)
				    	   listBasket.add(Integer.parseInt(str));
				    }
				    
				    ArrayList<Integer> listOrders = new ArrayList<>();
				    if (rsOrders.length() > 2) {
				    	rsOrders = rsOrders.substring(1, rsOrders.length() - 1).replace(" ", "");
				       String[] strArr = rsOrders.split(",");
				       for (String str : strArr)
				    	   listOrders.add(Integer.parseInt(str));
				    }
				    
					queryResult = new User(rsIdUser,rsLogin,rsPassword, listBasket, listOrders);
				}
			}

		} catch(SQLException e) {
			throw new RuntimeException("Erreur lors de la récupération de l'utilisateur:\n" + e.getMessage());
		}
		return queryResult;
	}

	@Override
	public boolean update(User obj) {
		String strUpd = "update t_users set Login=?, Password=?, Basket=?, Orders=? where IdUser=?;";
		try (PreparedStatement ps = connection.prepareStatement(strUpd)){
			ps.setString(1, obj.getLogin());
			ps.setString(2, obj.getPassword());
			ps.setString(3, obj.getBasket().toString());
			ps.setString(4, obj.getOrderHistory().toString());
			ps.setInt(5, obj.getId());
			if ( ps.executeUpdate () == 1 )
				return true;
		} catch (SQLException e) {
			throw new RuntimeException("Erreur lors de la mise à jour de l'utilisateur:\n" + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean delete(User obj) {
		String strDel = "delete from t_users where IdUser=?;";
		try (PreparedStatement ps = connection.prepareStatement(strDel)){
			ps.setInt(1, obj.getId());
			if ( ps.executeUpdate () == 1 ) 
				return true;
		} catch (SQLException e) {
			throw new RuntimeException("Erreur lors de la suppression de l'utilisateur:\n" + e.getMessage());
		}
		return false;
	}

	@Override
	public ArrayList<User> readAll() {
		ArrayList<User> users = new ArrayList<>();
		String strSql = "SELECT * FROM T_Users";

		try(Statement statement = connection.createStatement ()){
			try(ResultSet resultSet = statement.executeQuery(strSql)) {
				while(resultSet.next()) {
					int rsIdUser = resultSet.getInt(1); 
					String rsLogin = resultSet.getString(2);
					String rsPassword = resultSet.getString(3);
					String rsBasket = resultSet.getString(4);
					String rsOrders = resultSet.getString(4);
					
					ArrayList<Integer> listBasket = new ArrayList<>();
				    if (rsBasket.length() > 2) {
				    	rsBasket = rsBasket.substring(1, rsBasket.length() - 1).replace(" ", "");
				       String[] strArr = rsBasket.split(",");
				       for (String str : strArr)
				    	   listBasket.add(Integer.parseInt(str));
				    }
				    
				    ArrayList<Integer> listOrders = new ArrayList<>();
				    if (rsOrders.length() > 2) {
				    	rsOrders = rsOrders.substring(1, rsOrders.length() - 1).replace(" ", "");
				       String[] strArr = rsOrders.split(",");
				       for (String str : strArr)
				    	   listOrders.add(Integer.parseInt(str));
				    }
				    
					users.add((new User(rsIdUser,rsLogin,rsPassword, listBasket, listOrders)));
				}
			}
		} catch(SQLException e) {
			throw new RuntimeException("Erreur lors de la récupération de l'ensemble des utilisateurs:\n" + e.getMessage());
		}
		return users;
	}

}
