import java.util.Date;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Employee{

  Scanner t =new Scanner(System.in);
  PreparedStatement stmt;
  PreparedStatement stmt1;
  ResultSet rs;
  ResultSet rs1;
  Connection connection;
  public void viewCustomerProfile(){

  }

public void displayProfile(String user_id){
	Scanner t = new Scanner(System.in);

	  System.out.println("Please select one of the following");
	  System.out.println("1. View Profile\n" +
	  		"2. Update Profile\n" +
	  		"3. Go Back");
	  int choice = t.nextInt();
	  switch (choice) {
	  case 1: viewProfile(user_id);
	  break;
	  case 2: updateProfile(user_id);
	  break;
	  case 3: redirectAccording(user_id);
	  break;

	  default: System.out.println("Please enter a valid choice");
	  displayProfile(user_id);

	  }
	  
  }

private void redirectAccording(String user_id) {
	int role=0;
	try{
	      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
	      
	      stmt= connection.prepareStatement("SELECT Role FROM Employee WHERE EmpID= ?");
	      stmt.setString(1, user_id);
	      
	      rs= stmt.executeQuery();
	      while(rs.next()) {
	    	  role = rs.getInt(1);
	      }
	      
	      DBUtility.close(connection);

	    }catch(SQLException e){
	      System.out.println("Connection Failed! Check output console");
	      e.printStackTrace();
	      DBUtility.close(connection);
	      
	    }
	Manager manager = new Manager();
	Receptionist receptionist = new Receptionist();
	
	switch(role) {
	case 1: manager.displayManagerLanding(user_id);
	case 2: receptionist.displayReceptionistLanding(user_id); 
	case 3: new Home().displayHomepage();
	}
}

private void updateProfile(String user_id) {
	System.out.println("Please select an option from below");
	System.out.println("1. Name\n" +
			"2. Address\n" +
			"3. Email Address\n" +
			"4. Phone Number\n" +
			"5. Password\n" +
			"6. Go Back");
	int choice;
	do {
		
		choice = t.nextInt();
		t.nextLine();
		
	  switch (choice) {

	  case 1: updateName(user_id);
	  	  updateProfile(user_id);
	  		  
	  case 2: updateAddress(user_id);
	          updateProfile(user_id);
	          
	  case 3: updateEmail(user_id);
	          updateProfile(user_id);
	  
	  case 4: updatePhone(user_id);
	          updateProfile(user_id);
	  
	  case 5: updatePassword(user_id);
	  	  updateProfile(user_id);
	 
	  case 6: displayProfile(user_id);
	          
	  }
	} while (choice !=6);
}


private void updatePassword(String user_id) {
	String newPassword="";
	
	System.out.println("Enter the new password");
	newPassword = t.nextLine();
	try{
	      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
	      
	      stmt= connection.prepareStatement("UPDATE Login SET Password= ? WHERE LoginID= ? ");
	      stmt.setString(1, newPassword);
	      stmt.setString(2, user_id);
	      stmt.executeUpdate();
	      
	      System.out.println("Password changed successfully");
	      
	      
	      stmt.close();
	      DBUtility.close(connection);
	      
	      

	    }catch(SQLException e){
	      System.out.println("Connection Failed! Check output console");
	      e.printStackTrace();
	      DBUtility.close(connection);
	      
	    }
	
}

private void updatePhone(String user_id) {
String newPhone="";
	
	System.out.println("Enter new phone number");
	newPhone = t.nextLine();
	try{
	      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
	      
	      stmt= connection.prepareStatement("UPDATE Employee SET Emp_Phone= ? WHERE EmpID= ? ");
	      stmt.setString(1, newPhone);
	      stmt.setString(2, user_id);
	      stmt.executeUpdate();
	      
	      System.out.println("Phone Number changed successfully");
	      
	      stmt.close();
	      DBUtility.close(connection);

	    }catch(SQLException e){
	      System.out.println("Connection Failed! Check output console");
	      e.printStackTrace();
	      DBUtility.close(connection);
	      
	    }

}

private void updateEmail(String user_id) {
String newEmail="";
	
	System.out.println("Enter new email");
	newEmail = t.nextLine();
	try{
	      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
	      
	      stmt= connection.prepareStatement("UPDATE Employee SET Emp_email= ? WHERE EmpID= ? ");
	      stmt.setString(1, newEmail);
	      stmt.setString(2, user_id);
	      stmt.executeUpdate();
	      
	      System.out.println("Email Address changed successfully");
	      
	      stmt.close();
	      DBUtility.close(connection);

	    }catch(SQLException e){
	      System.out.println("Connection Failed! Check output console");
	      e.printStackTrace();
	      DBUtility.close(connection);
	      
	    }

}

private void updateAddress(String user_id) {
String newAddress="";
	
	System.out.println("Enter new Address");
	newAddress = t.nextLine();
	try{
	      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
	      
	      stmt= connection.prepareStatement("UPDATE Employee SET Emp_address= ? WHERE EmpID= ? ");
	      stmt.setString(1, newAddress);
	      stmt.setString(2, user_id);
	      stmt.executeUpdate();
	      
	      System.out.println("Address changed successfully");
	      
	      stmt.close();
	      DBUtility.close(connection);

	    }catch(SQLException e){
	      System.out.println("Connection Failed! Check output console");
	      e.printStackTrace();
	      DBUtility.close(connection);
	      
	    }

}

private void updateName(String user_id) {
String newName="";
	
	System.out.println("Enter new Name");
	newName = t.nextLine();
	try{
	      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
	      
	      stmt= connection.prepareStatement("UPDATE Employee SET Emp_Name= ? WHERE EmpID= ? ");
	      stmt.setString(1, newName);
	      stmt.setString(2, user_id);
	      stmt.executeUpdate();
	      
	      System.out.println("Name changed successfully");
	      
	      stmt.close();
	      DBUtility.close(connection);

	    }catch(SQLException e){
	      System.out.println("Connection Failed! Check output console");
	      e.printStackTrace();
	      DBUtility.close(connection);
	    }
}


private void viewProfile(String user_id) {
	String empID="",Emp_Name="",Emp_address="", Emp_email="", Emp_Phone="", Center_Name="", Start_Date="";
	int Role=0, Salary=0;
  try{
	 
    connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
    stmt1 = connection.prepareStatement("SELECT EmpID FROM Employee WHERE EmpID = ?");
    stmt1.setString(1, user_id);

    rs1 = stmt1.executeQuery();

    while(rs1.next()){
      empID = rs1.getString("EmpID");
    }

    
    stmt = connection.prepareStatement("SELECT E.EmpID, E.Emp_Name, E.Emp_address, E.Emp_email, E.Emp_Phone, S.Center_Name, E.Role, E.Emp_Start_Date, M.Salary FROM Employee E, Monthly_Paid_Emp M, WorksAt W, Service_Center S WHERE W.CenterID = S.CenterID AND E.EmpID = M.EmpID AND E.EmpID = ?");
    stmt.setString(1, empID);
    rs = stmt.executeQuery();
    
    if (rs.next()) {
      empID = rs.getString("EmpID");
      Emp_Name = rs.getString("Emp_Name");
      Emp_address = rs.getString("Emp_address");
      Emp_email = rs.getString("Emp_email");
      Emp_Phone = rs.getString("Emp_Phone");
      Center_Name = rs.getString("Center_Name");
      Role = rs.getInt("Role");
      Start_Date = rs.getString("Emp_Start_Date");
      Salary = rs.getInt("Salary");
      
    }
    
    stmt = connection.prepareStatement("SELECT E.EmpID, E.Emp_Name, E.Emp_address, E.Emp_email, E.Emp_Phone, S.Center_Name, E.Role, E.Emp_Start_Date, M.WAGES FROM Employee E, mechanic_emp M, WorksAt W, Service_Center S WHERE W.CenterID = S.CenterID AND E.EmpID = M.EmpID AND E.EmpID = ?");
    stmt.setString(1, empID);
    rs = stmt.executeQuery();
    
    if (rs.next()) {
      empID = rs.getString("EmpID");
      Emp_Name = rs.getString("Emp_Name");
      Emp_address = rs.getString("Emp_address");
      Emp_email = rs.getString("Emp_email");
      Emp_Phone = rs.getString("Emp_Phone");
      Center_Name = rs.getString("Center_Name");
      Role = rs.getInt("Role");
      Start_Date = rs.getString("Emp_Start_Date");
      Salary = rs.getInt("Wages");
      
    }
    
    System.out.println("");
    System.out.println("Employee ID: "+empID);
    System.out.println("Name: "+Emp_Name);
    System.out.println("Address: "+Emp_address);
    System.out.println("Email: "+Emp_email);
    System.out.println("Phone: "+Emp_Phone);
    System.out.println("Service Center: "+Center_Name);
    
    switch(Role){
    case 1: 
    	System.out.println("Role: Manager");
    	break;
    case 2: 
    	System.out.println("Role: Receptionist");
    	break;
    case 3: 
    	System.out.println("Role: Mechanic");
    	break;
    }
    
    System.out.println("Start Date: "+Start_Date);
    System.out.println("Salary: "+Salary);
    System.out.println("");
    
    rs.close();
    rs1.close();
    stmt.close();
    stmt1.close();

    DBUtility.close(connection);

  }catch(SQLException e){
    System.out.println("Connection Failed! Check output console");
    e.printStackTrace();
    DBUtility.close(connection);
    return;
  }

	Scanner t = new Scanner(System.in);
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
		  displayProfile(user_id);
	  t.close();

}
}
