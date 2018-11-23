import java.util.Date;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Employee{
  PreparedStatement stmt, stmt1;
  ResultSet rs, rs1;
  Connection connection;
  public void viewCustomerProfile(){
	  
	  //Wrong code
/*Customer cust = new Customer();
Scanner t = new Scanner(System.in);
System.out.println("Please enter customer email id:");
String id = t.nextLine();
cust.profile(id);*/
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
	  case 3: redirectAccording();
	  break;
	  default: System.out.println("Please enter a valid choice");
	  displayProfile(user_id);
	  }
	  t.close();
  }

private void redirectAccording() {
	System.out.println("Add logic to show landing page according to role - now pointing to display page");
	new Home().displayHomepage();
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
		Scanner t =new Scanner(System.in);
		choice = t.nextInt();
	  switch (choice) {
	  case 1: updateName();
	  break;
	  case 2: updateAddress();
	  break;
	  case 3: updateEmail();
	  break;
	  case 4: updatePhone();
	  break;
	  case 5: updatePassword();
	  break;
	  case 6: displayProfile(user_id);
	  break;
	  }
	} while (choice !=6);
}

private void updatePassword() {
	// TODO Auto-generated method stub

}

private void updatePhone() {
	// TODO Auto-generated method stub

}

private void updateEmail() {
	// TODO Auto-generated method stub

}

private void updateAddress() {
	// TODO Auto-generated method stub

}

private void updateName() {
	// TODO Auto-generated method stub

}

private void viewProfile(String user_id) {
	String empID="";
  try{
	 
    connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
    stmt1 = connection.prepareStatement("SELECT EmpID FROM Employee WHERE Emp_email = ?");
    stmt1.setString(1, user_id);

    rs1 = stmt1.executeQuery(user_id);

    while(rs.next()){
      empID = rs1.getString("EmpID");
    }

    
    stmt = connection.prepareStatement("SELECT E.EmpID, E.Emp_Name, E.Emp_address, E.Emp_email, E.Emp_Phone, S.Center_Name, E.Role, E.Emp_Start_Date, M.Salary FROM Employee E, Monthly_Paid_Emp M, WorksAt W, Service_Center S WHERE W.CenterID = S.CenterID AND E.EmpID = M.EmpID AND E.EmpID = ?");
    stmt.setString(1, empID);
    rs = stmt.executeQuery();
    
    while (rs.next()) {
      empID = rs.getString("EmpID");
      String Emp_Name = rs.getString("Emp_Name");
      String Emp_address = rs.getString("Emp_address");
      String Emp_email = rs.getString("Emp_email");
      String Emp_Phone = rs.getString("Emp_Phone");
      String Center_Name = rs.getString("Center_Name");
      int Role = rs.getInt("Role");
      Date Start_Date = rs.getDate("Start_Date");
      int Salary = rs.getInt("Emp_Name");
      System.out.println(empID + "   " + Emp_Name);
    }

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
