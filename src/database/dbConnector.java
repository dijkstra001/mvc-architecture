package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class dbConnector {
	
	private static Connection connection = null;
	
	public static Connection getConnection() {
		if (connection == null) {
			try {
			Properties properties = loadProperties();
			String url = properties.getProperty("dburl");
			
			connection = DriverManager.getConnection(url, properties);
			}
			catch (SQLException exception) {
				throw new dbException(exception.getMessage());
			}
		}
		return connection;
	}
	
	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			}
			catch(SQLException exception) {
				throw new dbException(exception.getMessage());
			}
		}
	}
	
	private static Properties loadProperties() {
		try (FileInputStream file = new FileInputStream("db.properties")){
			Properties properties = new Properties();
			properties.load(file);
			
			return properties;
		}
		
		catch (IOException exception) {
			throw new dbException(exception.getMessage());
		}
	}
	
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new dbException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new dbException(e.getMessage());
			}
		}
	}
}
