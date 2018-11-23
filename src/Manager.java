import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Manager{
	
	
	  Employee emp=new Employee();
	  Home home = new Home();
	  Connection connection;
		PreparedStatement stmt=null;
	    ResultSet rs= null;
	  String userId;
  public void displayManagerLanding(String user_id){
	  Scanner t = new Scanner(System.in);
	  userId =user_id;
	  System.out.println("Please select one of the following");
	  System.out.println("1. Profile\n" + 
	  		"2. View Customer\n" + 
	  		"Profile\n" + 
	  		"3. Add New\n" + 
	  		"Employees\n" + 
	  		"4. Payroll\n" + 
	  		"5. Inventory\n" + 
	  		"6. Orders\n" + 
	  		"7. Notifications\n" + 
	  		"8. New Car Model\n" + 
	  		"9. Car Service\n" + 
	  		"Details\n" + 
	  		"10. Service History\n" + 
	  		"11. Invoices\n" + 
	  		"12. Logout");
	  
	  int user_choice = t.nextInt();
	  switch(user_choice){
      case 1: emp.displayProfile(user_id);
      break;
      case 2: emp.viewCustomerProfile();
      break;
      case 3: addEmployee();
      break;
      case 4: payroll();
      break;
      case 5: inventory();
      break;
      case 6: orders();
      break;
      case 7: notifications();
      break;
      case 8: addNewCarModel();
      break;
      case 9: getCarServiceDetails();
      break;
      case 10:getServiceHistory();
      break;
      case 11:getInvoices();
      break;
      case 12:home.displayHomepage();
      break;
      default: System.out.println("Please enter a valid choice");
      break;
    }
	  t.close();
	  
  }


private void addEmployee () {
	  Scanner t = new Scanner(System.in);
	  
	  System.out.println("Please enter the following");

	    System.out.println("Add Role -1,2,3");
	    int role= t.nextInt();

	    System.out.println("Name:\t");
	    t.hasNextLine();
	    String name= t.nextLine();
	    
	    System.out.println("email:\t");
	    
	    String email= t.nextLine();

	    System.out.println("Address:\t");
	    String address= t.nextLine();

	    System.out.println("Phone Number:\t");
	    String phone= t.nextLine();
	    
	    System.out.println("Start Date:\t");
	    String start_date= t.nextLine();
	    
	    System.out.println("Password:\t");
	    String password= t.nextLine();
	    
	    int newId=0;
	    try{
		      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);

		      stmt=connection.prepareStatement("select max(empid) from employee");
		      rs = stmt.executeQuery();
		      while (rs.next()) {
		        newId = rs.getInt(1)+1;
		      }
		      rs.close();
		      stmt = connection.prepareStatement("insert into Employee values(?,?,?,?,?,?,?)");
		      stmt.setInt(1, newId);
		      stmt.setInt(2, role);
		      stmt.setString(3, name);
		      stmt.setString(4, email);
		      stmt.setString(5, address);
		      stmt.setString(6, phone);
		      stmt.setString(7, start_date);
		      stmt.executeUpdate();
		      
		      stmt = connection.prepareStatement("insert into login values(?,?,?)");
		      stmt.setString(1, String.valueOf(newId));
		      stmt.setString(2, password);
		      stmt.setInt(3, role);
		      stmt.executeUpdate();
		      DBUtility.close(connection);

		    }catch(SQLException e){
		      System.out.println("Connection Failed! Check output console");
		      e.printStackTrace();
		      DBUtility.close(connection);
		      
		    }
	    
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
	  displayManagerLanding(userId);
	  t.close();
  }
  
  private void payroll() {
	  Scanner t = new Scanner(System.in);
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
	  displayManagerLanding(userId);
	  t.close();
  }
  
  private void inventory() {
	  Scanner t = new Scanner(System.in);
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
	  displayManagerLanding(userId);
	  t.close();
  }
  
  private void orders() {
	  Scanner t = new Scanner(System.in);
	  System.out.println("1. Order History\n" + 
	  		"2. New Order\n" + 
	  		"3. Go Back");
	  int choice = t.nextInt();
	  switch (choice) {
	  case 1: orderHistory();
	  break;
	  case 2: newOrder();
	  break;
	  case 3: displayManagerLanding(userId);
	  break;
	  default: System.out.println("Please enter a valid choice");
	  }
	  t.close();
  }
  
  private void orderHistory() {
	  Scanner t = new Scanner(System.in);
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
		  orders();
	  t.close();
  } 
  
  private void newOrder() {
	  Scanner t = new Scanner(System.in);
	  System.out.println("1. Place Order\n" + 
		  		"2. Go Back");
	  int choice = t.nextInt();
	  switch (choice) {
	  case 1: placeorder();
	  break;
	  case 2: orders();
	  break;
	  default: System.out.println("Please enter a valid choice");
	  }
	  t.close();
  } 
  
  private void placeorder() {
	  Scanner t = new Scanner(System.in);
	  System.out.println("Please enter part id:");
	  int partId = t.nextInt();
	  System.out.println("Please enter part quantity");
	  int quantity = t.nextInt();
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
		  newOrder();
	  t.close();
  }
  
  private void notifications() {
	  Scanner t = new Scanner(System.in);
	  System.out.println("1. Order ID\n" + 
	  		"2. Go back");
	  int choice = t.nextInt();
	  switch (choice) {
	  case 1: getOrderDetails();
	  break;
	  case 2: displayManagerLanding(userId);
	  break;
	  default: System.out.println("Please enter a valid choice");
	  }
	  t.close();
  }

private void getOrderDetails() {
	Scanner t = new Scanner(System.in);
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
		  notifications();
	  t.close();
}

private void addNewCarModel() {
	Scanner t = new Scanner(System.in);
	  System.out.println("1. Add Car\n" + 
	  		"2. Go back");
	  int choice = t.nextInt();
	  switch (choice) {
	  case 1: addCarModel();
	  break;
	  case 2: displayManagerLanding(userId);
	  break;
	  default: System.out.println("Please enter a valid choice");
	  }
	  t.close();
	
}

private void addCarModel() {
	Scanner t = new Scanner(System.in);
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
		  addNewCarModel();
	  t.close();
	
}

private void getCarServiceDetails() {
	Scanner t = new Scanner(System.in);
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
		  displayManagerLanding(userId);
	  t.close();
	
}

private void getServiceHistory() {
	Scanner t = new Scanner(System.in);
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
		  displayManagerLanding(userId);
	  t.close();
}

private void getInvoices() {
	Scanner t = new Scanner(System.in);
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
		  displayManagerLanding(userId);
	  t.close();
}


  
  
}
