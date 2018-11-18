import java.sql.SQLException;
import java.util.Scanner;

class Home{
  public void displayHomepage(){
	  DBUtility dbu = new DBUtility();
		dbu.createDBConnection(JDBCConnection.user, JDBCConnection.password);
		try {
			dbu.getStmt().executeUpdate("CREATE TABLE COFFEES1 " + "(COF_NAME VARCHAR(32), SUP_ID INTEGER, "
					+ "PRICE FLOAT, SALES INTEGER, TOTAL INTEGER)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
      dbu.closeDBConnection();
      break;
      default: System.out.println("Please enter a valid choice");
displayHomepage();
    }
  }
}
