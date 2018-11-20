import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.SQLException;

public class DBUtility {

	static final String jdbcURL = "jdbc:oracle:thin:@orca.csc.ncsu.edu:1521:orcl01";

	public static Connection connectDB(String username, String password) throws SQLException{
			try {


				// Load the driver. This creates an instance of the driver
				// and calls the registerDriver method to make Oracle Thin
				// driver available to clients.

				Class.forName(".:/afs/eos.ncsu.edu/software/oracle11/app/product/11.2.0/client_1/jdbc/lib/ojdbc6.jar");

				try {
					Connection conn= DriverManager.getConnection(jdbcURL, username, password);

					if(conn != null){
						System.out.println("Connection Successful");
					}
					else{
						System.out.println("Failed to connect");
					}

					return conn;

				}catch (SQLException e) {
					System.out.println("Connection failed!");
					e.printStackTrace();
					throw e;
					}

			}catch (SQLException e) {
			  System.out.println("Connection failed!");
			  e.printStackTrace();
			  throw e;
			 }


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
