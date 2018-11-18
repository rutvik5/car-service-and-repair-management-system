import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class DBUtility {

	static final String jdbcURL = "jdbc:oracle:thin:@orca.csc.ncsu.edu:1521:orcl01";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	public void createDBConnection(String user, String password) {
		if (conn ==null && stmt ==null) {
			try {

				// Load the driver. This creates an instance of the driver
				// and calls the registerDriver method to make Oracle Thin
				// driver available to clients.

				Class.forName(".:/afs/eos.ncsu.edu/software/oracle11/app/product/11.2.0/client_1/jdbc/lib/ojdbc6.jar");
				
				try {
					setConn(DriverManager.getConnection(jdbcURL, user, password));

					// Create a statement object that will be sending your
					// SQL statements to the DBMS
					setStmt(conn.createStatement());

					
				}
				catch (Exception e) {
					System.out.println(e);
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			
			finally {
				close(rs);
				close(stmt);
				close(conn);
				}
		}
		
	}
	
	public void closeDBConnection() {
		close(rs);
		close(stmt);
		close(conn);
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
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
	
	
}
