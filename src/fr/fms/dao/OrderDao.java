package fr.fms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import fr.fms.Entities.Order;
import fr.fms.Entities.User;
import fr.fms.authentication.BddConnection;

public class OrderDao implements Dao<Order> {

private Connection connection;
	
	public OrderDao() throws Exception {
		connection = BddConnection.getConnection();
	}
	
	@Override
	public int create(Order obj) {
		
		java.sql.Date sqlDate = new java.sql.Date(obj.getOrderDate().getTime());
		
		String strCreate = "INSERT INTO T_Orders (Date, Amount, IdUser, listArticles) VALUES (?,?,?,?);";
		try (PreparedStatement ps = connection.prepareStatement(strCreate, Statement.RETURN_GENERATED_KEYS)){  
			
			
			
			ps.setDate(1, sqlDate);
			ps.setDouble(2, obj.getTotalAmount());
			ps.setInt(3, obj.getUserId());
			ps.setString(4, obj.getListArticles().toString());
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
	public Order read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Order obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Order obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Order> readAll() {
		ArrayList<Order> orders = new ArrayList<>();
		String strSql = "SELECT * FROM T_Orders;";

		try(Statement statement = connection.createStatement ()){
			try(ResultSet resultSet = statement.executeQuery(strSql)) {
				while(resultSet.next()) {
					int rsIdOrder = resultSet.getInt(1); 
					Date rsDate = resultSet.getDate(2);
					double rsTotalAmount = resultSet.getDouble(3);
					int rsIdUser = resultSet.getInt(4);
					String rsListArticles = resultSet.getString(4);
					
				    
				    ArrayList<Integer> listArticles = new ArrayList<>();
				    if (rsListArticles.length() > 2) {
				    	rsListArticles = rsListArticles.substring(1, rsListArticles.length() - 1).replace(" ", "");
				       String[] strArr = rsListArticles.split(",");
				       for (String str : strArr)
				    	   listArticles.add(Integer.parseInt(str));
				    }
				    
					orders.add((new Order(rsIdOrder,rsDate,rsTotalAmount, rsIdUser, listArticles)));
				}
			}
		} catch(SQLException e) {
			throw new RuntimeException("Erreur lors de la récupération de l'ensemble des utilisateurs:\n" + e.getMessage());
		}
		return orders;
	}

	public ArrayList<Order> readAll(int userId) {
		ArrayList<Order> orders = new ArrayList<>();
		String strSql = "SELECT * FROM T_Orders where UserId=" + userId +";";

		try(Statement statement = connection.createStatement ()){
			try(ResultSet resultSet = statement.executeQuery(strSql)) {
				while(resultSet.next()) {
					int rsIdOrder = resultSet.getInt(1); 
					Date rsDate = resultSet.getDate(2);
					double rsTotalAmount = resultSet.getDouble(3);
					int rsIdUser = resultSet.getInt(4);
					String rsListArticles = resultSet.getString(4);
					
				    
				    ArrayList<Integer> listArticles = new ArrayList<>();
				    if (rsListArticles.length() > 2) {
				    	rsListArticles = rsListArticles.substring(1, rsListArticles.length() - 1).replace(" ", "");
				       String[] strArr = rsListArticles.split(",");
				       for (String str : strArr)
				    	   listArticles.add(Integer.parseInt(str));
				    }
				    
					orders.add((new Order(rsIdOrder,rsDate,rsTotalAmount, rsIdUser, listArticles)));
				}
			}
		} catch(SQLException e) {
			throw new RuntimeException("Erreur lors de la récupération de l'ensemble des utilisateurs:\n" + e.getMessage());
		}
		return orders;
	}
}
