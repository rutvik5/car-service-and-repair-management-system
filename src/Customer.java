import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Customer{
String userId;
  Receptionist receptionist= new Receptionist();
  Home home= new Home();
  Connection connection;
  PreparedStatement stmt=null;
  ResultSet rs= null;
  int custid;
  String license_plate="";
 int current_mileage=0;
 String mechanic_name="";
 String serviceType="";
  public void displayCustomerLanding(String user_id){
	  userId =user_id;
    System.out.println("1.Profile\n2.Register Car\n3.Service\n4.Invoices\n5.Logout");
    Scanner t= new Scanner(System.in);
    int option= t.nextInt();
    switch(option){
      case 1:
            profile(userId);
            break;
      case 2:
            registerCar();
            break;
      case 3:
            service();
            break;
      case 4:
            invoices();
            break;
      case 5:
            home.displayHomepage();

    }
  }

  public void profile(String CutomerId){
    System.out.println("Enter one of the following");
    System.out.println("1.View Profile\n2. Update Profile\n3. Go Back");

    Scanner t= new Scanner(System.in);
    int option= t.nextInt();
    

    switch(option){
      case 1:
            viewProfile(CutomerId);
            break;
      case 2:
            updateProfile(CutomerId);
            break;
      case 3:
            displayCustomerLanding(CutomerId);
            break;
    }
  }
  public void viewProfile(String CutomerId){
    System.out.println("DB:Display details of the customer ID, name, address, email, phone, list of cars w/ details");

    
    try{
        connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);

        stmt = connection.prepareStatement("select custid from Customer where cust_email = ?");
        stmt.setString(1, CutomerId);
        rs = stmt.executeQuery();
        int id=0;
        while (rs.next()) {
            id = rs.getInt(1);
            }
        stmt=connection.prepareStatement("select * from Customer where CustId = ?");
        stmt.setInt(1, id);
        rs = stmt.executeQuery();
        while (rs.next()) {
        System.out.println ("Id:"+rs.getInt(1));
        System.out.println ("Name:"+rs.getString(2));
        System.out.println ("Email:"+rs.getString(3));
        System.out.println ("Address:"+rs.getString(4));
        System.out.println ("Phone:"+rs.getInt(5));
        System.out.println("");
        }
        rs.close();
        
        String query1= "SELECT GoesTo.CustID, Cars.LicensePlateID, Cars.Car_Type,Cars.Date_Purchase, Cars.Last_Mileage, Cars.Type_Recent_Service, Cars.Date_Recent_service" + 
        		" FROM GoesTo" + 
        		" JOIN Cars ON GoesTo.LicensePlateID = Cars.LicensePlateID" + 
        		" and GoesTo.CustID = ?";
        stmt=connection.prepareStatement(query1);
        stmt.setInt(1, id);
        rs = stmt.executeQuery();
        while (rs.next()) {
        	System.out.println("Car details:-");
            System.out.println ("License Plate Id:"+rs.getString(2));
            System.out.println ("Car-Type:"+rs.getString(3));
            System.out.println ("Date Purchased:"+rs.getString(4));
            System.out.println ("Last milage:"+rs.getInt(5));
            System.out.println ("Recent Service Type:"+rs.getString(6));
            System.out.println ("Recent Service Date"+rs.getString(7));
            System.out.println("");
            }
        
        DBUtility.close(connection);

      }catch(SQLException e){
        System.out.println("Connection Failed! Check output console");
        e.printStackTrace();
        DBUtility.close(connection);
        
      }
    System.out.println("1. Go Back");

    Scanner t= new Scanner(System.in);
    int option= t.nextInt();
    switch(option){
      case 1:
            profile(CutomerId);
            break;
    }
  }

  public void updateProfile(String CutomerId){
    System.out.println("Enter one of the following to update");
    int id=0;
    System.out.println("1. Name\n2. Address\n3. Phone Number\n4. Password\n5. Go Back");
    Scanner t= new Scanner(System.in);
    int option= t.nextInt();
    t.nextLine();
    try{
        connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
        stmt = connection.prepareStatement("select custid from Customer where cust_email = ?");
        stmt.setString(1, CutomerId);
        rs = stmt.executeQuery();
        while (rs.next()) {
            id = rs.getInt(1);
            }
        rs.close();
      }catch(SQLException e){
        System.out.println("Connection Failed! Check output console");
        e.printStackTrace();
      }
    

    switch(option){
      case 1:
            System.out.println("Enter new name\t");
            String name= t.nextLine();
		try {
			stmt = connection.prepareStatement("update customer set cust_name=? where custid=?");
			stmt.setString(1, name);
			stmt.setInt(2,id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
            System.out.println("Database Updated");
            updateProfile(CutomerId);
            break;
      case 2:
            System.out.println("Enter new address\t");
            String address= t.nextLine();
            try {
    			stmt = connection.prepareStatement("update customer set Cust_address=? where custid=?");
    			stmt.setString(1, address);
    			stmt.setInt(2,id);
    			stmt.executeUpdate();
    		} catch (SQLException e) {
    			e.printStackTrace();
    		} 
            System.out.println("Database Updated");
            updateProfile(CutomerId);
            break;
      case 3:
            System.out.println("Enter new phone number\t");
            String phoneNumber= t.nextLine();
            try {
    			stmt = connection.prepareStatement("update customer set Cust_Phone=? where custid=?");
    			stmt.setString(1, phoneNumber);
    			stmt.setInt(2,id);
    			stmt.executeUpdate();
    		} catch (SQLException e) {
    			e.printStackTrace();
    		} 
            System.out.println("Database Updated");
            updateProfile(CutomerId);
            break;
      case 4:
            System.out.println("Enter new password\t");
            String password= t.nextLine();
            try {
    			stmt = connection.prepareStatement("update login set password= ? where loginid=?");
    			stmt.setString(1, password);
    			stmt.setString(2,CutomerId);
    			stmt.executeUpdate();
    		} catch (SQLException e) {
    			e.printStackTrace();
    		} 
            System.out.println("Database Updated");
            updateProfile(CutomerId);
            break;  
      case 5:
            profile(CutomerId);
            break;
    }

  }
  public void registerCar(){
    System.out.println("Please select one of the following");
    System.out.println("1. Register" + "\n" + "2. Cancel");
    Scanner t = new Scanner(System.in);
    int user_choice= t.nextInt();
    switch (user_choice) {
      case 1:
            CarService cs = new CarService();
            cs.AddCar(userId);
            displayCustomerLanding(userId);
            break;
      case 2: displayCustomerLanding(userId);
      break;

    }

  }

  public void service(){
    System.out.println("Enter one of the following");
    System.out.println("1.View Service History\n2. Schedule Service\n3. Reschedule Service\n4. Go Back");

    Scanner t= new Scanner(System.in);
    int option= t.nextInt();
    try{
        connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);

        stmt = connection.prepareStatement("select custid from Customer where cust_email = ?");
        stmt.setString(1, userId);
        rs = stmt.executeQuery();
        
        while (rs.next()) {
            custid = rs.getInt(1);
            }
        rs.close();
        DBUtility.close(connection);

      }catch(SQLException e){
        System.out.println("Connection Failed! Check output console");
        e.printStackTrace();
      }
    
    switch(option){
      case 1:
            viewServiceHistory(custid);
            break;
      case 2:
            scheduleService();
            break;
      case 3:
            rescheduleService();
            break;
      case 4:
            displayCustomerLanding(userId);
    }
  }

  public void viewServiceHistory(int custId){
    Scanner t= new Scanner(System.in);

    System.out.println("Service History Details");
    CarService cs = new CarService();
    cs.serviceHistory(custId);
    System.out.println("Press 1 to Go Back");
    int option= t.nextInt();
    if(option==1){
      service();
    }
  }

  public void scheduleService(){

    //based on the above entered data, provide the users following options
    System.out.println("Please select one of the following");
    System.out.println("1. Schedule Maintenance" + "\n" + " 2. Schedule Repair" + "\n" + "3. Go Back");
    Scanner t= new Scanner(System.in);
    int user_choice= t.nextInt();
    t.nextLine();
    if (user_choice==1 || user_choice==2) {
        
        System.out.println("Please enter the following details");
        // run queries to add the following details into database

        System.out.print("B. License Plate:\t");
        license_plate= t.nextLine();
        System.out.println("");

        System.out.print("C. Current Mileage:\t");
        current_mileage= t.nextInt();
        t.nextLine();
        System.out.println("");

        System.out.print("D. Mechanic Name(Optional):\t");
        mechanic_name= t.nextLine();
        System.out.println("");
    }
    switch (user_choice) {

      case 1: scheduleMaintenance(license_plate, current_mileage,mechanic_name);
      break;
      case 2: scheduleRepair();
      break;
      case 3: displayCustomerLanding(userId);
      break;

    }
  }
  public void scheduleMaintenance(String license_plate, int current_mileage,String mechanic_name){
    Scanner t= new Scanner(System.in);
    System.out.println("Please select one of the following");
    System.out.println("1. Find Service Date" + "\n" + " 2. Go Back");

    int user_choice= t.nextInt();
    switch (user_choice) {
      case 1: findServiceDate();
      case 2: scheduleService();
      default: System.out.println("Enter a valid choice");
    }
  }
  public void findServiceDate(){
    Scanner t= new Scanner(System.in);
    try{
        connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
        getServiceType();
        getBasicID();
        
        DBUtility.close(connection);

      }catch(SQLException e){
        System.out.println("Connection Failed! Check output console");
        e.printStackTrace();
      }

    
    
    
    
    
    
    
    
    
    
    
    System.out.println("Please select one of the following");
    System.out.println("1. Schedule on Date" + "\n" + " 2. Go Back");
    int user_choice= t.nextInt();
    switch (user_choice) {

      case 1: 
              System.out.println("Service appointment successfully saved");
              scheduleService();

      case 2: scheduleMaintenance(license_plate,current_mileage,mechanic_name);
      default: System.out.println("Enter a valid choice");
    }
  }

private void getBasicID() {
	
	
}

private void getServiceType() throws SQLException {
	String carType="";
	int mileage=0;
	stmt = connection.prepareStatement("select type_recent_service from Cars where licenseplateid = ?");
	stmt.setString(1, license_plate);
	rs = stmt.executeQuery();
	
	if (rs.next()) {
		if (rs.getString(1)==null) {
			rs.close();
			stmt = connection.prepareStatement("select last_mileage,car_type from Cars where licenseplateid = ?");
	    	stmt.setString(1, license_plate);
	        rs = stmt.executeQuery();
	        if (rs.next()) {
	        	mileage=rs.getInt(1);
	        	carType=rs.getString(2).split(",")[0].trim()+ " "+rs.getString(2).split(",")[1].trim();
	        } 
	        rs.close();
	        stmt = connection.prepareStatement("select miles from basic_service_mapping where car_model = ?");
	        stmt.setString(1, carType);
	        rs = stmt.executeQuery();
	        int m[]=new int[3];
	        int i=0;
	        while(rs.next()) {
	        	m[i]=rs.getInt(1);
	        	i++;
	        }
	        rs.close();
	        if (mileage<m[0]) {
	        	serviceType="A";
	        } else if (mileage>m[2]) {
	        	serviceType="C";
	        } else {
	        	serviceType="B";
	        }
	        stmt = connection.prepareStatement("Update Cars set type_recent_service = ? where licenseplateid = ?");
	        stmt.setString(1, serviceType);
	        stmt.setString(2, license_plate);
	        stmt.executeQuery();
		} else if (rs.getString(1).trim().equalsIgnoreCase("A")){
			serviceType="B";
		} else if (rs.getString(1).trim().equalsIgnoreCase("B")){
			serviceType="C";
		} else {
			serviceType="A";
		}
	    } 
	rs.close();
}

  public void scheduleRepair(){
    Scanner t= new Scanner(System.in);

    System.out.println("Please select one of the following encountered problem");
    System.out.println("1. Engine knock" + "\n" + "2. Car drifts in a particular direction" + "\n" + "3. Battery does not hold charge" + "\n" + "4. Black/unclean exhaust" + "\n"
    + "5. A/C- Heater not working" + "\n" + "6. Headlapms/Tail lamps not working" + "\n" + "7. Check engine light" + "\n" + "8.Go Back");

    int user_choice= t.nextInt();
    switch (user_choice) {
      //based on the repair display the report, 2 identifed service dates, mechanic_name
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8: scheduleService();
      default: System.out.println("Enter a valid choice");

      selectRepairDate();
    }
  }

  public void selectRepairDate(){
    Scanner t= new Scanner(System.in);

    System.out.println("Please select one of the following");
    System.out.println("1. Repair on Date" + "\n" + "2. Go Back");

    int user_choice= t.nextInt();
    switch (user_choice) {

      case 1:
              System.out.println("Repair appointment successfully saved");
              scheduleService();

      case 2: scheduleRepair();
      default: System.out.println("Enter a valid choice");
    }
  }

  public void rescheduleService(){
    Scanner t= new Scanner(System.in);

    System.out.println(" display the following: License Plate,"
                      +  "Service ID, Service Date, Service Type(Maintenance/Repair),"
                      +  "Service Details(Service A/B/C orProblem)");

    System.out.println("Please select one of the following");
    System.out.println("1. Pick a Service" + "\n" + "2. Go Back");

    int user_choice= t.nextInt();
    switch (user_choice) {

      case 1:
              System.out.println("If the user chooses 1,ask him to enter one of the service IDs"
                                +"to select the service to be rescheduled and then find two"
                                  +"earliest available maintenance/repair dates that are at least"
                                +"one day after the current service date");
              pickService();

      case 2: displayCustomerLanding(userId);
      default: System.out.println("Enter a valid choice");
    }
  }

  public void pickService(){
    Scanner t= new Scanner(System.in);

    System.out.println("SQL: display the 2 identified dates and mechanic name");

    System.out.println("Please select one of the following");
    System.out.println("1. Reschedule Date" + "\n" + "2. Go Back");

    int user_choice= t.nextInt();
    switch (user_choice) {

      case 1:
              System.out.println("Ask the user to pick one fo the dates shown and add to db");
              displayCustomerLanding(userId);

      case 2: displayCustomerLanding(userId);
      default: System.out.println("Enter a valid choice");
    }

  }

  public void invoices(){
    Scanner t= new Scanner(System.in);

    System.out.println("Ask user to enter customer email-id and display the following: Service ID,"
                      +  "Service Start, Date/Time, Service End, Date/Time, Licence Plate,"
                      +  "Service Type, Mechanic Name, Total Service Cost");


    System.out.println("Please select the following");
    System.out.println("1.View Invoice Details\n2. Go Back");
    int user_choice= t.nextInt();
    if (user_choice == 1) viewInvoiceDetails();
    if (user_choice == 2) displayCustomerLanding(userId);
  }

  public void viewInvoiceDetails(){
    Scanner t= new Scanner(System.in);
    
    System.out.print("Enter the Service ID:\t");
    int serviceId= t.nextInt();

    System.out.println("based on serive id display follwoing from DB: serviceID, start/end date time, license plate, service type, mechanic name, parts used in service with cost of each part, total labour hours, labor wages per hour, total service cost ");

    System.out.println("Enter 1 to Go Back");
    int user_choice= t.nextInt();
    if (user_choice == 1) invoices();
  }

}
