package fr.fms.authentication;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateConfigFile {

	public static void main(String[] args) {

		try (FileOutputStream fos = new FileOutputStream("db/credentials.properties")) {

			String lineSeparator = System.getProperty("line.separator");

			String driverClass = "db.driver.class=org.mariadb.jdbc.Driver";
			String url = "db.url=jdbc:mariadb://localhost:3306/Shop";
			String login = "db.login=root";
			String password = "db.password=fms2023";

			fos.write(driverClass.getBytes());
			fos.write(lineSeparator.getBytes());
			fos.write(url.getBytes());
			fos.write(lineSeparator.getBytes());
			fos.write(login.getBytes());
			fos.write(lineSeparator.getBytes());
			fos.write(password.getBytes());

		} catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} 
	}	     
}
