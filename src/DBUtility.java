import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.SQLException;

public class DBUtility {

	static final String jdbcURL = "jdbc:oracle:thin:@orca.csc.ncsu.edu:1521:orcl01";

	public static Connection connectDB(String username, String password) throws SQLException{
		 Connection conn=null;	
		try {


				// Load the driver. This creates an instance of the driver
				// and calls the registerDriver method to make Oracle Thin
				// driver available to clients.
				
					Class.forName("oracle.jdbc.driver.OracleDriver");
				

				try {
					
					conn= DriverManager.getConnection(jdbcURL, username, password);

					if(conn != null){
					}
					else{
						System.out.println("Failed to connect");
					}

					
					
				}catch (SQLException e) {
					System.out.println("Connection failed!");
					e.printStackTrace();
					throw e;
					}
				
			}catch (Exception e) {
			  System.out.println("Connection failed!");
			  e.printStackTrace();
			 }
		return conn;
		

	}


	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Throwable whatever) {
			}
		}
	}



}
