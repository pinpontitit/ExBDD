package fr.fms.authentication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class BddConnection {

	//	private static BddConnection single_instance = null;
	private static volatile Connection connection = null;

	private BddConnection() throws IOException {
		Properties prop = readPropertiesFile("db/credentials.properties");

		try {
			Class.forName(prop.getProperty("db.driver.class"));
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}

		String url = prop.getProperty("db.url");
		String login = prop.getProperty("db.login");
		String password = prop.getProperty("db.password");

		try{
			connection = DriverManager.getConnection(url, login, password);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	private static Properties readPropertiesFile(String string) throws IOException {
		Properties prop = null;
		try (FileInputStream fis = new FileInputStream(string)) {
			prop = new Properties();
			prop.load(fis);
		} catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} 
		return prop;
	}

	public static Connection getConnection() throws Exception {
		//		if (single_instance == null)
		//            single_instance = new BddConnection();


		if(connection == null) {
			new BddConnection();
		}
		return connection;
	}

	public static void closeConnection() {
		try { 
			connection.close();
		} catch (Exception e) 
		{ /* Ignored */ }
	}



}

//public class BddConnection {
//	private static Connection connection;
//	static {
//		try {
//			Properties prop = readPropertiesFile("db/credentials.properties");
//			
//			Class.forName(prop.getProperty("db.driver.class"));
//			
//			connection = DriverManager.getConnection(prop.getProperty("db.url"), prop.getProperty("db.login"),prop.getProperty("db.password"));
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public static Connection getCon() {
//		return conn;
//		}
//}
