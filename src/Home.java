import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;

class Home{
	Connection connection;
  public void displayHomepage(){
	  /*DBUtility dbu = new DBUtility();
		dbu.createDBConnection(JDBCConnection.user, JDBCConnection.password);
		try {
			dbu.getStmt().executeUpdate("CREATE TABLE COFFEES1 " + "(COF_NAME VARCHAR(32), SUP_ID INTEGER, "
					+ "PRICE FLOAT, SALES INTEGER, TOTAL INTEGER)");
		} catch (SQLException e) {
			//  Auto-generated catch block
			e.printStackTrace();
		}
    */
    Statement stmt=null;
    ResultSet rs= null;

    try{
      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);

      String query= "CREATE TABLE COFFEES1 " + "(COF_NAME VARCHAR(32), SUP_ID INTEGER, "
					+ "PRICE FLOAT, SALES INTEGER, TOTAL INTEGER)";

      stmt=connection.createStatement();
      stmt.executeUpdate(query);
      rs = stmt.executeQuery("SELECT COF_NAME, PRICE FROM COFFEES1");

      while (rs.next()) {
        String s = rs.getString("COF_NAME");
        float n = rs.getFloat("PRICE");
        System.out.println(s + "   " + n);
      }

      DBUtility.close(connection);

    }catch(SQLException e){
      System.out.println("Connection Failed! Check output console");
      e.printStackTrace();
      DBUtility.close(connection);
      return;
    }
    Scanner t= new Scanner(System.in);
    SignUp signup= new SignUp();
    Login login= new Login();

    System.out.println("Enter choice (1-3)\n 1. Login\n 2. Sign Up\n 3. Exit");
    int user_choice = t.nextInt();
    switch(user_choice){
      case 1: login.displayLogin();
      break;
      case 2: signup.displaySignUp();
      break;
      case 3: System.out.println("Thanks for visiting this portal....!!!!");
      //dbu.closeDBConnection();
      break;
      default: System.out.println("Please enter a valid choice");
      displayHomepage();
    }
  }
}
