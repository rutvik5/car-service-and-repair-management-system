import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class Login{
	Connection connection;
	PreparedStatement stmt=null;
    ResultSet rs= null;
  public void displayLogin(){
    Scanner t= new Scanner(System.in);
    Home home= new Home();
    String s="";
    int r=0;
    System.out.println("Please enter the following");
    // write logic to implement login
    System.out.print("A. User ID:\t");
    String user_id= t.nextLine().trim();

    System.out.print("Password:\t");
    String user_password= t.nextLine().trim();
    
    try{
        connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);

        stmt=connection.prepareStatement("SELECT PASSWORD,ROLE FROM LOGIN WHERE TRIM(LoginId)=?");
        stmt.setString(1, user_id);
        rs = stmt.executeQuery();
        while (rs.next()) {
          s = rs.getString(1).trim();
          r = Integer.parseInt(rs.getString("Role").trim());
        }
        
        rs.close();
        DBUtility.close(connection);

      }catch(SQLException e){
        System.out.println("Connection Failed! Check output console");
        e.printStackTrace();
        DBUtility.close(connection);
        
      }
    
    if (user_password.equals(s)) {
    	enterPortal(home,r,user_id);
    } else {
    	System.out.println("Invalid Username/password");
    	home.displayHomepage();
    }

    
  }

private void enterPortal(Home home,int r,String user_id) {
     int user_choice= r;
    switch (user_choice) {
      // check if credentials are correct and render Manager or Receptionist landing page
      case 1: 
    	  Manager manager= new Manager();
    	  manager.displayManagerLanding(user_id);
    	  break;
      case 2: 
    	  Receptionist recp = new Receptionist();
    	  recp.displayReceptionistLanding(user_id);
    	  break;
      case 3:
    	  new Employee().displayProfile(user_id);
    	  break;
      case 4:
    	  Customer cust = new Customer();
    	  cust.displayCustomerLanding(user_id);
    	  break;
      case 0:
    	  home.displayHomepage();
    	  break;
      default: System.out.println("System error occured");
    }
}

}
