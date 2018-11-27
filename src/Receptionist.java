import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Receptionist{
	String UserId;
	Connection connection;
	PreparedStatement stmt;
	ResultSet rs;
	
    public void displayReceptionistLanding(String user_id){
    UserId = user_id;
    Scanner t= new Scanner(System.in);
    Home home= new Home();
    Employee employee= new Employee();
    Customer customer = new Customer();
    
    System.out.println("Please select one of the following\n");
    System.out.println("1. Profile\n" + "2. View Customer Profile" + "\n" + "3. Register Car" + "\n" + "4. Service History" + "\n" + "5. Schedule Service" + "\n" + "6. Reschedule Service" + "\n" + "7. Invoices" + "\n"
                        +"8. Daily Task Update Inventory" + "\n" + "9. Daily Task- Record Deliveries" + "\n" + "10. Logout");

    int user_choice = t.nextInt();
    t.nextLine();
    String id ="";
    switch(user_choice){
      case 1: employee.displayProfile(user_id);
      break;
      case 2: System.out.println("Enter customer email id:");
		id =t.nextLine();
		customer.profile(id, user_id,"");
      break;
      case 3: System.out.println("Enter customer email id:");
      		id =t.nextLine();
      		customer.registerCar(id);
      break;
      case 4: System.out.println("Enter customer email id:");
		id =t.nextLine();
		customer.service(id,UserId);
      break;
      case 5: System.out.println("Enter customer email id:");
		id =t.nextLine();
		customer.service(id,UserId);
      break;
      case 6:System.out.println("Enter customer email id:");
		id =t.nextLine();
		customer.service(id,UserId);
      break;
      case 7: System.out.println("Enter customer email id:");
		id =t.nextLine();
		customer.invoices(id, UserId,"");
      break;
      case 8: 
    	  new UpdateInventory().updateDailyInventory(UserId);
      break;
      case 9: recordDeliverables();
      break;
      case 10: home.displayHomepage();
      break;
      default: System.out.println("Please enter a valid choice");
      break;
    }
  }

  public void registerCar(){
    Scanner t= new Scanner(System.in);
    String recent_service="";
    String cust_id = "";
    String center_id="";
    

    System.out.println("Please enter the following details");
    // run queries to add the following details into database
    System.out.print("A. Customer email address:\t");
    String customer_email= t.nextLine();
    System.out.println("");

    System.out.print("B. License Plate:\t");
    String license_plate= t.nextLine();
    System.out.println("");

    System.out.print("C. Purchase Date:\t");
    String purchase_date= t.nextLine();
    System.out.println("");

    System.out.print("D. Make:\t");
    String make= t.nextLine();
    System.out.println("");

    System.out.print("E. Model:\t");
    String model= t.nextLine();
    System.out.println("");
    
    System.out.print("F. Year:\t");
    String year= t.nextLine();
    System.out.println("");
    
    String car_type = make+", "+model+", "+year;

    System.out.print("G. Current Mileage:\t");
    int current_mileage= t.nextInt();
    t.nextLine();
    System.out.println("");

    System.out.print("H. Last Service Date (optional: Type NA):\t");
    String last_service_date= t.nextLine();
    System.out.println("");

    System.out.println("Please select one of the following");
    System.out.println("1. Register" + "\n" + "2. Cancel");

    int user_choice= t.nextInt();
    t.nextLine();
    switch (user_choice) {
      case 1:
    	  try{
    	      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
    	      
    	      stmt= connection.prepareStatement("SELECT Type_Recent_Service FROM Cars WHERE LicensePlateID = ? ");
    	      stmt.setString(1, license_plate);
    	      rs= stmt.executeQuery();
    	      
    	      while(rs.next())
    	    	  recent_service = rs.getString(1);  
    	      
    	      
    	      stmt.close();
    	      rs.close();
    	      
    	      stmt= connection.prepareStatement("INSERT INTO Cars VALUES (?,?,?,?,?,?)");
    	      stmt.setString(1,license_plate);
    	      stmt.setString(2, car_type);
    	      stmt.setString(3, purchase_date);
    	      stmt.setInt(4, current_mileage);
    	      stmt.setString(5, recent_service);
    	      stmt.setString(6, last_service_date);
    	      stmt.executeUpdate();
    	      
    	      stmt.close();
    	      
    	      stmt = connection.prepareStatement("SELECT CustID FROM Customer WHERE Cust_email = ?");
    	      stmt.setString(1, customer_email);
    	      rs = stmt.executeQuery();
    	      
    	      while(rs.next()) {
    	    	  cust_id = rs.getString(1);
    	      }
    	      
    	      rs.close();
    	      stmt.close();
    	      
    	      stmt= connection.prepareStatement("SELECT CenterID FROM WorksAt WHERE EmpID = ?");
    	      stmt.setString(1, UserId);
    	      rs= stmt.executeQuery();
    	      
    	      while(rs.next()) {
    	    	  center_id = rs.getString(1);
    	      }
    	      
    	      stmt.close();
    	      rs.close();
    	      
    	      stmt= connection.prepareStatement("INSERT INTO GoesTo VALUES (?, ?, ?)");
    	      stmt.setString(1, license_plate);
    	      stmt.setString(2, cust_id);
    	      stmt.setString(3, center_id);
    	      stmt.executeUpdate();
    	      
    	      stmt.close();

    	      DBUtility.close(connection);

    	    }catch(SQLException e){
    	      System.out.println("Connection Failed! Check output console");
    	      e.printStackTrace();
    	      DBUtility.close(connection);
    	      
    	    }
            System.out.println("Car Successfully Added");
            displayReceptionistLanding(UserId);
            break;
      case 2: displayReceptionistLanding(UserId);
              break;
      default: System.out.println("Enter a valid choice");
      break;
    }
  }

  /*public void serviceHistory(){
    Scanner t= new Scanner(System.in);

    System.out.println("Please enter the following details");
    // run queries to add the following details into database
    System.out.print("A. Customer email address:\t");
    String customer_email= t.next();
    System.out.println("");

     based on the customer_email, display the following:

    service_id, license_plate, service_type, mechanic_name, service_start-date/time,
    service_end-date/time(expected or actual), service_status(Pending/Ongoing/Complete) 

    System.out.println("Go Back? Enter 1");
    displayReceptionistLanding(UserId);
  }

  public void scheduleService(){

    Scanner t= new Scanner(System.in);

    System.out.println("Please enter the following details");
    // run queries to add the following details into database
    System.out.print("A. Customer email address:\t");
    String customer_email= t.next();
    System.out.println("");

    System.out.print("B. License Plate:\t");
    String license_plate= t.next();
    System.out.println("");

    System.out.print("C. Current Mileage:\t");
    String current_mileage= t.next();
    System.out.println("");

    System.out.print("D. Mechanic Name(Optional):\t");
    String mechanic_name= t.next();
    System.out.println("");

    //based on the above entered data, provide the users following options
    System.out.println("Please select one of the following");
    System.out.println("1. Schedule Maintenance" + "\n" + " 2. Schedule Repair" + "\n" + "3. Go Back");

    int user_choice= t.nextInt();
    switch (user_choice) {

      case 1: scheduleMaintenance();
break;
      case 2: scheduleRepair();
      break;
      case 3: displayReceptionistLanding(UserId);
      break;
      default: System.out.println("Enter a valid choice");

    }
 }
    public void scheduleMaintenance(){
      Scanner t= new Scanner(System.in);

      System.out.println("Please select one of the following");
      System.out.println("1. Find Service Date" + "\n" + " 2. Go Back");

      int user_choice= t.nextInt();
      switch (user_choice) {
        case 1: findServiceDate();
        break;
        case 2: scheduleService();
        break;
        default: System.out.println("Enter a valid choice");
        break;
      }
    }

    public void findServiceDate(){
      Scanner t = new Scanner(System.in);
      //display the 2 earliest available dates from the db
      System.out.println("Please select one of the following");
      System.out.println("1. Schedule on Date" + "\n" + " 2. Go Back");

      int user_choice= t.nextInt();
      switch (user_choice) {

        case 1:
                System.out.println("Service appointment successfully saved");
                scheduleService();
                break;
        case 2: scheduleMaintenance();
        break;
        default: System.out.println("Enter a valid choice");
        break;
      }
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

      System.out.println("Ask user to enter customer email-id and display the following: License Plate,"
                        +  "Service ID, Service Date, Service Type(Maintenance/Repair),"
                        +  "Service Details(Service A/B/C orProblem)");

      System.out.print("Enter the email\t");
      String customer_email= t.next();
      System.out.println("");

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

        case 2: displayReceptionistLanding(UserId);
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
                displayReceptionistLanding(UserId);

        case 2: displayReceptionistLanding(UserId);
        default: System.out.println("Enter a valid choice");
      }

    }

    public void invoices(){
      Scanner t= new Scanner(System.in);

      System.out.println("Ask user to enter customer email-id and display the following: Service ID,"
                        +  "Service Start, Date/Time, Service End, Date/Time, Licence Plate,"
                        +  "Service Type, Mechanic Name, Parts Used in service with cost of each part,"
                        +  "Total labor hours, Labor wages per hour, Total Service Cost");

      System.out.print("Enter the email\t");
      String customer_email= t.next();
      System.out.println("");

      System.out.println("Please select the following");
      System.out.println("1.Go Back");
      int user_choice= t.nextInt();
      if (user_choice == 1) displayReceptionistLanding(UserId);
    }*/

//    public void updateInventory(){
//     
//    	  
//      
//      
//      
//
//      System.out.println("Please select the following");
//      System.out.println("1.Go Back");
//      int user_choice= t.nextInt();
//      if (user_choice == 1) displayReceptionistLanding(UserId);
//    }

    public void recordDeliverables(){
      Scanner t= new Scanner(System.in);

      System.out.println("Please select one of the following");
      System.out.println("1. Start Daily Update Task" + "\n" + "2. Go Back");

      int user_choice= t.nextInt();
      switch (user_choice) {

        case 1:
        	 List <String> pending_order_ids = new ArrayList<>();
             String receiver_center_id = "", part_id="", car_model="";
             int additional_quantity = 0, current_quantity=0, quantity=0;
             
           	  //update the inventory table of the receivers side
           	  	//check status pending order id and - check center id_receiver of that order from makes table
           	  		//-update the inventory table- current quantity of the receiver center id 
           	  //update actual date and status from ordered parts table
           	  try{
           	      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
           	      
           	      stmt = connection.prepareStatement("SELECT OrderID FROM OrderPart WHERE Status = ?");
           	      stmt.setString(1, "Pending");
           	      rs = stmt.executeQuery();
           	      
           	      while(rs.next()) {
           	    	  pending_order_ids.add(rs.getString(1));
           	      }
           	      
           	      stmt.close();
           	      rs.close();
           	      
           	      for(String pending_order: pending_order_ids) {
           	    	  
           	    	  System.out.println("Did order number " + pending_order+" arrive?\n1. Yes\n2. No");
           	          int part_arrived = t.nextInt();
           	          
           	          if(part_arrived == 1) {
           	        	  stmt = connection.prepareStatement("SELECT CenterId_receiver FROM Makes WHERE OrderID =?");
           	        	  stmt.setString(1, pending_order);
           	        	  rs = stmt.executeQuery();
           	    	      
           	    	      while(rs.next()) {
           	    	    	  receiver_center_id = rs.getString(1);
           	    	      }
           	    	      stmt.close();
           	    	      rs.close();
           	    	      
           	    	      stmt = connection.prepareStatement("SELECT Part_ID, car_make FROM OrderPart WHERE CenterID = ?");
           	    	      stmt.setString(1, receiver_center_id);
           	    	      rs = stmt.executeQuery();
           	    	      while(rs.next()) {
           	    	    	  part_id = rs.getString(1);
           	    	    	  car_model = rs.getString(2);
           	    	      }
           	    	      stmt.close();
           	    	      rs.close();
           	    	      
           	    	      stmt = connection.prepareStatement("SELECT quantity FROM OrderPart WHERE CenterID = ? AND Part_ID = ? AND car_make = ?");
           	    	      stmt.setString(1, receiver_center_id);
           	    	      stmt.setString(2, part_id);
           	    	      stmt.setString(3, car_model);
           	    	      rs = stmt.executeQuery();
           	    	      while(rs.next()) {
           	    	    	  additional_quantity = rs.getInt(1);
           	    	      }
           	    	      stmt.close();
           	    	      rs.close();
           	    	      
           	    	      stmt = connection.prepareStatement("SELECT Current_quantity FROM Inventory WHERE CenterID = ? AND PartID = ? AND Car_model = ?");
           	    	      stmt.setString(1, receiver_center_id);
           	    	      stmt.setString(2, part_id);
           	    	      stmt.setString(3, car_model);
           	    	      rs = stmt.executeQuery();
           	    	      while(rs.next()) {
           	    	    	  current_quantity = rs.getInt(1);
           	    	      }
           	    	      stmt.close();
           	    	      rs.close();
           	    	      
           	    	      quantity = current_quantity + additional_quantity;
           	    	      
           	    	      stmt = connection.prepareStatement("UPDATE Inventory SET Current_quantity = ? WHERE CenterID = ? AND PartID = ? AND Car_model = ?");
           	    	      stmt.setInt(1, additional_quantity);
           	    	      stmt.setString(2, receiver_center_id);
           	    	      stmt.setString(3, part_id);
           	    	      stmt.setString(4, car_model);
           	    	      
           	    	      stmt.executeUpdate();
           	    	      stmt.close();
           	    	      
           	    	      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
           	    	  	  Date date = new Date();
           	    	  	  String current_date = (formatter.format(date));
           	    	      
           	    	  	  stmt = connection.prepareStatement("UPDATE OrderPart SET Status = 'Complete' , Actual_date = ? WHERE CenterID = ? AND Part_ID = ? AND car_make = ?");
           	    	      stmt.setString(1, current_date);
           	    	      stmt.setString(2, receiver_center_id);
           	    	      stmt.setString(3, part_id);
           	    	      stmt.setString(4, car_model);
           	    	      
           	    	      stmt.executeUpdate();
           	    	      stmt.close();
           	    	      
           	    	      
           	          }
           	      }

           	      DBUtility.close(connection);

           	    }catch(SQLException e){
           	      System.out.println("Connection Failed! Check output console");
           	      e.printStackTrace();
           	      DBUtility.close(connection);
           	      
           	    }


        case 2: displayReceptionistLanding(UserId);
        default: System.out.println("Enter a valid choice");
      }
      
//     
      
     

    }

}
