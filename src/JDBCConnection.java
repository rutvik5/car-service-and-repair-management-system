import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCConnection {

	static final String jdbcURL = "jdbc:oracle:thin:@orca.csc.ncsu.edu:1521:orcl01";
	static String user;
	static String password;

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.println("Connecting to DB...");
		System.out.println("Enter DB userame");
		user = s.nextLine();
		System.out.println("Enter DB password");
		password = s.nextLine();
		
		
		Home home = new Home();
		home.displayHomepage();
		
		/*try {

			// Load the driver. This creates an instance of the driver
			// and calls the registerDriver method to make Oracle Thin
			// driver available to clients.

			Class.forName(".:/afs/eos.ncsu.edu/software/oracle11/app/product/11.2.0/client_1/jdbc/lib/ojdbc6.jar");

			Scanner scanner = new Scanner(System.in);
			String user = scanner.nextLine(); // For example, "jsmith"
			String passwd = scanner.nextLine(); // Your 9 digit student ID number or password
			
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;

			try {

				// Get a connection from the first driver in the
				// DriverManager list that recognizes the URL jdbcURL

				conn = DriverManager.getConnection(jdbcURL, user, passwd);

				// Create a statement object that will be sending your
				// SQL statements to the DBMS

				stmt = conn.createStatement();

				// Create the COFFEES table

				stmt.executeUpdate("CREATE TABLE COFFEES1 " + "(COF_NAME VARCHAR(32), SUP_ID INTEGER, "
						+ "PRICE FLOAT, SALES INTEGER, TOTAL INTEGER)");

				// Populate the COFFEES table

				stmt.executeUpdate("INSERT INTO COFFEES1 " + "VALUES ('Colombian', 101, 7.99, 0, 0)");

				stmt.executeUpdate("INSERT INTO COFFEES1 " + "VALUES ('French_Roast', 49, 8.99, 0, 0)");

				stmt.executeUpdate("INSERT INTO COFFEES1 " + "VALUES ('Espresso', 150, 9.99, 0, 0)");

				stmt.executeUpdate("INSERT INTO COFFEES1 " + "VALUES ('Colombian_Decaf', 101, 8.99, 0, 0)");

				stmt.executeUpdate("INSERT INTO COFFEES1 " + "VALUES ('French_Roast_Decaf', 49, 9.99, 0, 0)");

				// Get data from the COFFEES table

				rs = stmt.executeQuery("SELECT COF_NAME, PRICE FROM COFFEES1");

				// Now rs contains the rows of coffees and prices from
				// the COFFEES table. To access the data, use the method
				// NEXT to access all rows in rs, one row at a time

				while (rs.next()) {
					String s = rs.getString("COF_NAME");
					float n = rs.getFloat("PRICE");
					System.out.println(s + "   " + n);
				}

			} finally {
				close(rs);
				close(stmt);
				close(conn);
			}
		} catch (Throwable oops) {
			oops.printStackTrace();
		}
	}

	static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Throwable whatever) {
			}
		}
	}

	static void close(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (Throwable whatever) {
			}
		}
	}

	static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Throwable whatever) {
			}
		}
	}*/

}
}