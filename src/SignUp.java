import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class SignUp{
	Connection connection;
	PreparedStatement stmt=null;
    ResultSet rs= null;
    int newId=0;
    String user_email;
    String user_password;
    String user_name;
    String user_address;
    String user_phone;
    String service_center;
    
  public void displaySignUp(){
    Scanner t= new Scanner(System.in);
    Home home= new Home();
    Login login= new Login();
    

    System.out.println("Please enter the following");

    System.out.println("A. Email Address:\t");
    user_email= t.nextLine();

    System.out.println("Password:\t");
    user_password= t.nextLine();

    System.out.println("Name:\t");
    user_name= t.nextLine();

    System.out.println("Address:\t");
    user_address= t.nextLine();

    System.out.println("Phone Number:\t");
    user_phone= t.nextLine();
    
    System.out.println("Service Center to Register with:\t");
    service_center= t.nextLine();

    System.out.println("Please select one of the following");
    System.out.println("1. Sign-Up\n 2. Go Back");
    int user_choice= t.nextInt();
    switch (user_choice) {
      case 1:
    	  signUpCustomer(); //save the user info to database
            System.out.println("User Successfully Added");
      case 2: home.displayHomepage();
      default: System.out.println("Enter a valid choice");
    }
    
  }
  
  private void signUpCustomer() {
	  try{
	      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);

	      stmt=connection.prepareStatement("select max(CustId) from customer");
	      rs = stmt.executeQuery();
	      while (rs.next()) {
	        newId = rs.getInt(1)+1;
	      }
	      rs.close();
	      stmt = connection.prepareStatement("insert into Customer values(?,?,?,?,?)");
	      stmt.setInt(1, newId);
	      stmt.setString(2, user_name);
	      stmt.setString(3, user_email);
	      stmt.setString(4, user_address);
	      stmt.setString(5, user_phone);
	      stmt.executeUpdate();
	      
	      stmt = connection.prepareStatement("insert into login values(?,?,4)");
	      stmt.setString(1, user_email);
	      stmt.setString(2, user_password);
	      stmt.executeUpdate();
	      
	      stmt = connection.prepareStatement("insert into goesto values('00000000',?,?)");
	      stmt.setInt(1, newId);
	      stmt.setString(2, service_center);
	      stmt.executeUpdate();
	      
	      DBUtility.close(connection);

	    }catch(SQLException e){
	      System.out.println("Connection Failed! Check output console");
	      e.printStackTrace();
	      DBUtility.close(connection);
	      
	    }
  }
  

}
