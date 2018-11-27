import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Manager{
	
	
	  Employee emp=new Employee();
	  Customer cust = new Customer();
	  Home home = new Home();
	  Connection connection;
      PreparedStatement stmt, stmt1=null;
	  ResultSet rs, rs1= null;
	  String userId;
	  
  public void displayManagerLanding(String user_id){
	  Scanner t = new Scanner(System.in);
	  userId =user_id;
	  System.out.println("Please select one of the following: \n");
	  System.out.println("1. Profile\n" + 
	  		"2. View Customer Profile\n" + 
	  		"3. Add New Employees\n" + 
	  		"4. Payroll\n" + 
	  		"5. Inventory\n" + 
	  		"6. Orders\n" + 
	  		"7. Notifications\n" + 
	  		"8. New Car Model\n" + 
	  		"9. Car Service Details\n" + 
	  		"10. Service History\n" + 
	  		"11. Invoices\n" + 
	  		"12. Logout");
	  
	  int user_choice = t.nextInt();
	  t.nextLine();
	  switch(user_choice){
      case 1: emp.displayProfile(userId);
      break;
      case 2: System.out.println("Enter customer email id:");
		String id =t.nextLine();
		cust.profile(id,"",userId);
      break;
      case 3: addEmployee();
      break;
      case 4: payroll();
      break;
      case 5: inventory(userId);
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
      case 11:System.out.println("Enter customer email id:");
		id =t.nextLine();
		cust.invoices(id, "",userId);
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

	    System.out.println("Add Role -Manager(1),Receptionist(2),Mechanic(3)");
	    int role= t.nextInt();
	    t.nextLine();
	    System.out.println("Name:\t");
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
		      System.out.println("Employee added successfully");
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
	  String emp_id="";
	  String emp_start_date="";
	  String emp_role = "";
	  String emp_name="";
	  String salary="";
	  String frequency="";
	  String first_month_days = "", units="", first_month_salary="", year_salary="";
	  int paycheck_date=0, pay_period_month=0, pay_period_year=0;
      int months_worked=0, first_paycheck_days=0;
      float wages=0, hours_worked=0;
      int pay_dates_worked, shifts_worked=0, shifts_left =0;
	  
      System.out.println("Enter the Employee ID:");
	  emp_id = t.nextLine();
	  
	  try{
	      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
	      
	      stmt = connection.prepareStatement("SELECT emp_start_date FROM Employee WHERE empid = ?");
	      stmt.setString(1, emp_id);
	      rs = stmt.executeQuery();
	      while(rs.next()) {
	    	 emp_start_date = rs.getString(1); 
	      }
	      stmt.close();
	      rs.close();
	      
	      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  
	      Date date = new Date();  
	      String current_day = formatter.format(date).split("-")[0];
	      System.out.println(current_day);
	      String current_month = formatter.format(date).split("-")[1];
	      System.out.println(current_month);
	      String current_year = formatter.format(date).split("-")[2];
	      System.out.println(current_year);
	      String emp_start_day = emp_start_date.split("-")[0];
	      System.out.println(emp_start_day);
	      String emp_start_month = emp_start_date.split("-")[1];
	      System.out.println(emp_start_month);
	      String emp_start_year = emp_start_date.split("-")[2];
	      System.out.println(emp_start_year);
	      
	      stmt = connection.prepareStatement("SELECT role,emp_name FROM Employee WHERE empid = ?");
	      stmt.setString(1, emp_id);
	      rs = stmt.executeQuery();
	      while(rs.next()) {
	    	 emp_role = rs.getString(1);
	    	 emp_name = rs.getString(2);
	      }
	      stmt.close();
	      rs.close();
	      
	      stmt = connection.prepareStatement("SELECT wages,hours_worked FROM Mechanic_Emp WHERE empid = ?");
	      stmt.setString(1, emp_id);
	      rs = stmt.executeQuery();
	      while(rs.next()) {
	    	 wages = rs.getFloat(1);
	    	 hours_worked = rs.getFloat(2);
	      }
	      
	      stmt.close();
	      rs.close();
	      

	      stmt = connection.prepareStatement("SELECT salary FROM Monthly_paid_emp WHERE empid = ?");
	      stmt.setString(1, emp_id);
	      rs = stmt.executeQuery();
	      while(rs.next()) {
	    	 salary = rs.getString(1);
	      }
	      stmt.close();
	      rs.close();
	      
	      if(emp_start_year.equalsIgnoreCase(current_year)) {
	    	months_worked =   Integer.parseInt(current_month)- Integer.parseInt(emp_start_month);
	      }     
	      
	      else {
	    	  int months_left = 12-Integer.parseInt(emp_start_month);
	    	  months_worked = months_left + Integer.parseInt(current_month) + (Integer.parseInt(current_year)-Integer.parseInt(emp_start_year)-1) * 12;  
	      }
	      
	      //System.out.println(months_worked);
	      
	      for(int i = 0; i < months_worked; i++) {
	    	  
	    	  if(emp_role.equalsIgnoreCase("1") || emp_role.equalsIgnoreCase("2") ) {
	    		  
	    		  frequency = "monthly";
	    		  int months_left = 12-Integer.parseInt(emp_start_month);
	    		  
	    		  first_month_days = Integer.toString(30 - Integer.parseInt(emp_start_day));
	    		  //paycheck_date = (Integer.parseInt(emp_start_month)+i)% 12 ;
	    		  pay_period_month = (Integer.parseInt(emp_start_month)+i) % 12;
	    		  pay_period_year = (Integer.parseInt(emp_start_year))+ (Integer.parseInt(emp_start_month)+i)/12;
	    		  if(pay_period_month==0)
	    		  {
	    			  pay_period_month = 12;
	    		  }
	    		
	    		  first_month_salary = Integer.toString(Integer.parseInt(first_month_days) * Integer.parseInt(salary)/ 30);
	    		  
	    		  if(emp_start_year.equalsIgnoreCase(current_year) || i<= months_left) {
	    			  year_salary = first_month_salary;
	    			  year_salary = Integer.toString(Integer.parseInt(year_salary)+Integer.parseInt(salary) * i);
	    		      } 
	    		  
	    		  else year_salary = Integer.toString(Integer.parseInt(salary) * pay_period_month);
	    		  
	    		  int current_earnings = Integer.parseInt(salary)*i;
	    		  
	    		  if(i==0) {  
	    			  units = first_month_days;
	    			  current_earnings = Integer.parseInt(first_month_salary);
	    		  }
	    		  
	    		  else units = "30";
	    		  
	    		  int paycheck_month = (pay_period_month+1)%12;
	    		  if(paycheck_month%12==0)
	    		  {
	    			  paycheck_month=12;
	    		  }
	    		  
	    		  System.out.println("Paycheck date: 01-" + paycheck_month + "-" + pay_period_year);
	    		  System.out.println("Pay period: "+pay_period_month+"-"+pay_period_year);
	    		  System.out.println("Employee ID: " + emp_id);
	    		  System.out.println("Employee Name: " + emp_name);
	    		  System.out.println("Compensation: " + salary);
	    		  System.out.println("Compensation Frequency: " + frequency );
	    		  System.out.println("Number of days: " + units);
	    		  System.out.println("Compensation: " + salary);
	    		  System.out.println("Current Earnings: " + current_earnings);
	    		  System.out.println("Year-to-Date Earnings: " + year_salary);
	    		  System.out.println("");
	    	  } 
	    	  else {
	    		  
	    		  if(emp_start_month.equals(current_month)) {
	    			  if(Integer.parseInt(current_day)>15)
	    				  shifts_worked= 1;
	    			  else System.out.println("Employee has not completed a single shift");
	    		  }
	    		  
	    		  if(emp_start_year.equalsIgnoreCase(current_year)) {
	    		    	if(Integer.parseInt(emp_start_day) < 15 && Integer.parseInt(current_day) < 15) {
	    		    		shifts_worked =   (Integer.parseInt(current_month)- Integer.parseInt(emp_start_month))*2;
	    		    	}
	    		    	
	    		    	else if(Integer.parseInt(emp_start_day) < 15 && Integer.parseInt(current_day) > 15) {
	    		    		shifts_worked =   ((Integer.parseInt(current_month)- Integer.parseInt(emp_start_month))*2)+ 1;
	    		    	}
	    		    	
	    		    	else if(Integer.parseInt(emp_start_day) > 15 && Integer.parseInt(current_day) > 15) {
	    		    		shifts_worked =   (Integer.parseInt(current_month)- Integer.parseInt(emp_start_month))*2 ;
	    		    		//System.out.println("shifts worked: "+ shifts_worked);
	    		    	}
	    		    	
	    		    	else if(Integer.parseInt(emp_start_day) > 15 && Integer.parseInt(current_day) < 15) {
	    		    		shifts_worked =   ((Integer.parseInt(current_month)- Integer.parseInt(emp_start_month))*2) -1;
	    		    	}
	    		    	
	    		    	else System.out.println("");
	    		    	
	    		      }     
	    		      
	    		      else if(!emp_start_year.equalsIgnoreCase(current_year)){
	    		    	  if(Integer.parseInt(emp_start_day) < 15)
	    		    		  shifts_left = (12-Integer.parseInt(emp_start_month)) * 2;
	    		    	  else
	    		    		  shifts_left = ((12-Integer.parseInt(emp_start_month)) * 2) -1;
	    		    	  
	    		    	  if(Integer.parseInt(current_day) > 15)
	    		    		  shifts_worked = shifts_left + ((Integer.parseInt(current_month))*2)-1 + ((Integer.parseInt(current_year)-Integer.parseInt(emp_start_year))-1) * 24;
	    		    	  else
	    		    		  shifts_worked = shifts_left + ((Integer.parseInt(current_month))*2)-2 + ((Integer.parseInt(current_year)-Integer.parseInt(emp_start_year))-1) * 24;
	    		    	  
	    		      }
	    		  
	    		  
	    		  int second_half = Integer.parseInt(emp_start_month);
	    		  pay_period_year = Integer.parseInt(emp_start_year.trim()); 
	    		  int emp_day=Integer.parseInt(emp_start_day);
	    		  for(i=0; i<shifts_worked; i++) {
	    			  frequency = "hourly";
	    			  hours_worked = (int)(Math.random() * 11 + 1);
		    		  
	    			  if(emp_day<15) {
		    			  first_paycheck_days = 15 - Integer.parseInt(emp_start_day);
		    			  units = "15";
		    			  float current_earnings = wages* hours_worked;
		    			  
		    			  
		    			  
		    			  year_salary = "";
		    			  
		    			  if(i%2==0) {
		    				units = Integer.toString(first_paycheck_days);  
		    			  }
		    			  System.out.println("Paycheck date: 15-" + second_half + "-" + pay_period_year);
			    		  System.out.println("Pay period: "+"-"+second_half+"-"+pay_period_year);
			    		  System.out.println("Employee ID: " + emp_id);
			    		  System.out.println("Employee Name: " + emp_name);
			    		  System.out.println("Compensation: " + wages);
			    		  System.out.println("Compensation Frequency: " + frequency );
			    		  System.out.println("Number of hours worked: " + hours_worked);
			    		  System.out.println("Current Earnings: " + current_earnings);
			    		  System.out.println("Year-to-Date Earnings: " + year_salary);
			    		  System.out.println("");
			    		  emp_day=25;
			    		  
		    			  
		    		  }
		    		  else {
		    			  first_paycheck_days = 30 - Integer.parseInt(emp_start_day); 
		    			  
		    			  float current_earnings = wages* hours_worked;
		    			  second_half++;
		    			  if (second_half==13) {
		    				  second_half=1;
		    				  pay_period_year++;
		    			  }
		    			  System.out.println("Paycheck date: 01-" + second_half + "-" + pay_period_year);
			    		  System.out.println("Pay period: "+(second_half-1)+"-"+pay_period_year);
			    		  System.out.println("Employee ID: " + emp_id);
			    		  System.out.println("Employee Name: " + emp_name);
			    		  System.out.println("Compensation: " + wages);
			    		  System.out.println("Compensation Frequency: " + frequency );
			    		  System.out.println("Number of hours worked: " + hours_worked);
			    		  System.out.println("Current Earnings: " + current_earnings);
			    		  System.out.println("Year-to-Date Earnings: " + year_salary);
			    		  System.out.println("");
			    		  emp_day=10;
		    		  } 
		    			    			  
	    		  }
	    		    
	    		  
	    		  
	    		  
	    		  
	    	  }
	    	  
	      }
	     
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
  
  private void inventory(String userId) {
	  String centerID="", partID="", partName="", currentQuantity="", thresholdMin="", minOrder="", carCompany=""; 
	  int price=0;
	  ArrayList<String> inventory = new ArrayList<>();
	  ArrayList<String> partid = new ArrayList<>();
	  ArrayList<String> car_company = new ArrayList<>();
	  ArrayList<Integer> pricelist = new ArrayList<>();
	  try{
	      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
	      
	      stmt = connection.prepareStatement("SELECT centerid from worksat WHERE empid = ?");
	      stmt.setString(1, userId);
	      rs = stmt.executeQuery();
	      while(rs.next()) {
	    	  centerID = rs.getString(1);
	      }
	      stmt.close();
	      rs.close();
	      
	      stmt = connection.prepareStatement("SELECT partid, part_name, current_quantity, threshold_min, min_order, car_model from Inventory WHERE centerid = ?");
	      stmt.setString(1, centerID);
	      rs = stmt.executeQuery();
	      while(rs.next()) {
	    	  
	    	  partID = rs.getString(1);
	    	  partid.add(partID);
	    	  
	    	  partName = rs.getString(2);
	    	  currentQuantity = rs.getString(3);
	    	  thresholdMin = rs.getString(4);
	    	  minOrder = rs.getString(5);
	    	  carCompany = rs.getString(6).split(" ")[0];
	    	  car_company.add(carCompany);
	    	  
	    	  
	    	  String temp = "Part Name: " + carCompany + " " + partName + "\nPart ID: "+ partID + "\nCurrent Quantity: "  +currentQuantity + "\nMinimum Quantity Threshold: " +thresholdMin + "\nMinimum Order Threshold: " +minOrder; 
	    	  
	    	  inventory.add(temp);	    	  
	    	  
	      }
	      
	     for(int i=0; i<partid.size(); i++) {
	    	  stmt = connection.prepareStatement("SELECT price from parts_mapping WHERE partid = ? and car_company= ?");
		      stmt.setString(1, partid.get(i));
		      stmt.setString(2, car_company.get(i));
		      rs = stmt.executeQuery();
		      while(rs.next()) {
		    	  
		    	  price = rs.getInt(1);
		    	  pricelist.add(price);
		      }
		      stmt.close();
		      rs.close();
		      
	     }
	      
	      for(int i=0; i< partid.size(); i++) {
	    	  System.out.println(inventory.get(i) + " " + "\nPrice: "+pricelist.get(i));
	    	  System.out.println("");
	      }
	      
	      DBUtility.close(connection);
	      
	      
	    }catch(SQLException e){
	      System.out.println("Connection Failed! Check output console");
	      e.printStackTrace();
	      DBUtility.close(connection);
	      
	    }
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
	  String order_date="", part_name = "", date="", supplier_name="", purchaser_name="", status="", centerID="", car_make="", sender_name="", rx_centername="";
	  int part_id=0, quantity=0, orderID=0;
	  ArrayList<Integer> orderidlist = new ArrayList<>();
	  ArrayList<Integer> partidlist = new ArrayList<>();
	  ArrayList<String> part_name_list = new ArrayList<>();
	  ArrayList<String> car_make_list = new ArrayList<>();
	  ArrayList<String> senderid_list = new ArrayList<>();
	  ArrayList<Integer> pricelist = new ArrayList<>();
	  ArrayList<String> sender_name_list = new ArrayList<>();
	  //ArrayList<String> dist_name_list = new ArrayList<>();
	  ArrayList<String> order_date_list = new ArrayList<>();
	  ArrayList<Integer> quantity_list = new ArrayList<>();
	  ArrayList<String> status_list = new ArrayList<>();
	  //ArrayList<String> center_name_list = new ArrayList<>();
	  
	  
	  try{
	      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
	      
	      stmt = connection.prepareStatement("SELECT centerid from worksat WHERE empid = ?");
	      stmt.setString(1, userId);
	      rs = stmt.executeQuery();
	      while(rs.next()) {
	    	  centerID = rs.getString(1);
	      }
	      stmt.close();
	      rs.close();
	      
	      stmt = connection.prepareStatement("SELECT centerid from worksat WHERE empid = ?");
	      stmt.setString(1, centerID);
	      rs = stmt.executeQuery();
	      while(rs.next()) {
	    	  rx_centername = rs.getString(1);
	    	  //System.out.println("Purchaser Name: " + rx_centerID);
	      }
	      stmt.close();
	      rs.close();
	      
	      	      
	      stmt = connection.prepareStatement("SELECT orderid, order_date, part_id, quantity, status, car_make from Orderpart");
	      rs = stmt.executeQuery();
	      while(rs.next()) {
	    	  orderID = rs.getInt(1);
	    	  orderidlist.add(orderID);
	    	  
	    	  //System.out.println("Order ID: " + orderID);
	    	  
	    	  order_date = rs.getString(2);
	    	  order_date_list.add(order_date);
	    	  
	    	  //System.out.println("Date: " + order_date);
	    	  
	    	  part_id = rs.getInt(3);
	    	  partidlist.add(part_id);
	    	  
	    	  quantity = rs.getInt(4);
	    	  quantity_list.add(quantity);
	    	  //System.out.println("Quantity: " + quantity);
	    	  
	    	  status = rs.getString(5);
	    	  status_list.add(status);
	    	  //System.out.println("Order Status: " + status);
	    	  
	    	  car_make = rs.getString(6);
	    	  car_make_list.add(car_make);
	    	  
	      }
	      stmt.close();
	      rs.close();
	      
	      for(int i=0; i< partidlist.size(); i++) {
	    	  try{
	    	      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
	    	      
	    	      stmt = connection.prepareStatement("SELECT part_name from Inventory where partid=? AND centerID = ? AND car_model = ?");
	    	      stmt.setInt(1, partidlist.get(i));
	    	      stmt.setString(2, centerID);
	    	      stmt.setString(3, car_make_list.get(i));
	    	      rs = stmt.executeQuery();
	    	      while(rs.next()) {
	    	    	  part_name = rs.getString(1);
	    	    	  part_name_list.add(part_name);
	    	    	  //System.out.println("Part Name: " + part_name);
	    	      }
	    	      stmt.close();
	    	      rs.close();
	    	      
	    	      DBUtility.close(connection);
	    	      
	    	      
	    	    }catch(SQLException e){
	    	      System.out.println("Connection Failed! Check output console");
	    	      e.printStackTrace();
	    	      DBUtility.close(connection);
	    	      
	    	    }
	      }
	      
	      for(int i=0; i< partidlist.size(); i++) {
	    	  try{
	    	      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
	    	      
	    	      stmt = connection.prepareStatement("SELECT price from Parts_Mapping where partid=? AND car_company = ?");
	    	      stmt.setInt(1, partidlist.get(i));
	    	      stmt.setString(2, car_make_list.get(i));
	    	      rs = stmt.executeQuery();
	    	      while(rs.next()) {
	    	    	  pricelist.add(rs.getInt(1));
	    	    	  //System.out.println("Unit Price: " + rs.getInt(1));
	    	      }
	    	      stmt.close();
	    	      rs.close();
	    	      
	    	      DBUtility.close(connection);
	    	      
	    	      
	    	    }catch(SQLException e){
	    	      System.out.println("Connection Failed! Check output console");
	    	      e.printStackTrace();
	    	      DBUtility.close(connection);
	    	      
	    	    }
	      }
	      
	      for(int i=0; i< orderidlist.size(); i++) {
	    	  try{
	    	      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
	    	      
	    	      stmt = connection.prepareStatement("SELECT senderid from Makes where orderid=?");
	    	      stmt.setInt(1, orderidlist.get(i));
	    	      //stmt.setString(2, centerID);
	    	      rs = stmt.executeQuery();
	    	      while(rs.next()) {
	    	    	  senderid_list.add(rs.getString(1));
	    	    	  
	    	    	  
	    	    	  
	    	      }
	    	      stmt.close();
	    	      rs.close();
	    	      
	    	      DBUtility.close(connection);
	    	      
	    	      
	    	    }catch(SQLException e){
	    	      System.out.println("Connection Failed! Check output console");
	    	      e.printStackTrace();
	    	      DBUtility.close(connection);
	    	      
	    	    }
	      }
	      
	     /*for(int i=0; i< orderidlist.size(); i++) {
	    	  try{
	    	      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
	    	      
	    	      stmt = connection.prepareStatement("SELECT center_name from service_center where centerid=? ");
	    	      stmt.setString(1, senderid_list.get(i));
	    	      
	    	      rs = stmt.executeQuery();
	    	      while(rs.next()) {
	    	    	  sender_name  = rs.getString(1);
	    	    	  if(sender_name.equals(null)) {
	    	    		  stmt1 = connection.prepareStatement("SELECT distname from distributor where  distid=? ");
			    	      stmt1.setString(1, senderid_list.get(i));
			    	      //stmt1.setInt(2, partidlist.get(i));
			    	      
			    	      rs1 = stmt1.executeQuery();
			    	      while(rs.next()) {
			    	    	  sender_name  = rs.getString(1);
			    	    	  sender_name_list.add(sender_name);
			    	    	  //System.out.println("Distributor Name: " + dist_name);
			    	    	  
			    	      }
			    	      stmt1.close();
			    	      rs1.close();
	    	    	  }
	    	    	  else sender_name_list.add(sender_name);
	    	    	  //System.out.println("Center Name: " + center_name);
	    	    	  
	    	      }
	    	      stmt.close();
	    	      rs.close();
	    	      
	    	      DBUtility.close(connection);
	    	      
	    	      
	    	    }catch(SQLException e){
	    	      System.out.println("Connection Failed! Check output console");
	    	      e.printStackTrace();
	    	      DBUtility.close(connection);
	    	      
	    	    }
	      }   */
	      
	      for(int i=0; i<orderidlist.size(); i++) {
	    	  System.out.println("Order ID: " + orderidlist.get(i));
	    	  System.out.println("Date: " + order_date_list.get(i));
	    	  System.out.println("Part Name: " + part_name_list.get(i));
	    	  if(senderid_list.get(i).equals("D0002"))
    			  System.out.println("Supplier Name: D2");
    	      else if(senderid_list.get(i).equals("D0001"))
    		  System.out.println("Supplier Name: D2");
    	      else System.out.println("Supplier Name: " + senderid_list.get(i));
	    	  System.out.println("Purchaser Name: " + centerID);
	    	  System.out.println("Quantity: " + quantity_list.get(i));
	    	  System.out.println("Unit Price: " + pricelist.get(i));
	    	  System.out.println("Total Cost: " + quantity_list.get(i) * pricelist.get(i));
	    	  System.out.println("Status: " + status_list.get(i));
	    	  System.out.println("");
	    	  
	      }
	      
	      DBUtility.close(connection);
	      
	      
	    }catch(SQLException e){
	      System.out.println("Connection Failed! Check output console");
	      e.printStackTrace();
	      DBUtility.close(connection);
	      
	    }
	  
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
	  String sender_id="";
	  
	  ArrayList<String> notificationid_list = new ArrayList<>();
	  ArrayList<String> orderid_list = new ArrayList<>();
	  ArrayList<String> notification_date_list = new ArrayList<>();
	  ArrayList<String> expected_date_list = new ArrayList<>();
	  ArrayList<String> actual_date_list = new ArrayList<>();
	  ArrayList<String> sender_id_list = new ArrayList<>();
	  ArrayList<String> sx_center_id_list = new ArrayList<>();
	  ArrayList<String> sx_dist_id_list = new ArrayList<>();
	  ArrayList<String> sender_name_list = new ArrayList<>();
	  try{
	      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
	      
	      stmt = connection.prepareStatement("SELECT notificationid, orderID,  notification_date  from notification");
	      rs = stmt.executeQuery();
	      while(rs.next()) {
	    	  notificationid_list.add(rs.getString(1));
	    	  orderid_list.add(rs.getString(2));
	    	  notification_date_list.add(rs.getString(3));
	      }
	      stmt.close();
	      rs.close();
	      
	      stmt = connection.prepareStatement("SELECT expected_date, actual_date from orderpart");
	      rs = stmt.executeQuery();
	      while(rs.next()) {
	    	  expected_date_list.add(rs.getString(1));
	    	  actual_date_list.add(rs.getString(2));
	    	  
	      }
	      stmt.close();
	      rs.close();
	      
	      for(int i=0; i<orderid_list.size(); i++) {
	    	  
	    	  stmt = connection.prepareStatement("SELECT senderid from makes where orderid=?");
	    	  stmt.setString(1, orderid_list.get(i));
		      rs = stmt.executeQuery();
		      while(rs.next()) {
		    	  sender_id = rs.getString(1);
		    	  sender_id_list.add(sender_id);
		      }
		      stmt.close();
		      rs.close();
	      }
	      
	      for(int i=0; i<sender_id_list.size(); i++) {
	    	  stmt = connection.prepareStatement("SELECT center_name from service_center where centerid=?");
	    	  stmt.setString(1, sender_id_list.get(i));
		      rs = stmt.executeQuery();
		      while(rs.next()) {
		    	  sx_center_id_list.add(rs.getString(1));
		      }
		      stmt.close();
		      rs.close();
	      }
	      
	      for(int i=0; i<sender_id_list.size(); i++) {
	    	  stmt = connection.prepareStatement("SELECT distname from distributor where distid=?");
	    	  stmt.setString(1, sender_id_list.get(i));
		      rs = stmt.executeQuery();
		      while(rs.next()) {
		    	  sx_dist_id_list.add(rs.getString(1));
		      }
		      stmt.close();
		      rs.close();
	      }
	      
	    /*  for(int i =0; i< sender_id_list.size(); i++) {
	    	  sender_name_list.add(sx_center_id_list.get(i) + sx_dist_id_list.get(i));
	    	  for(String item : sender_name_list) {
	    		  System.out.println(item);
	    	  }
	      }*/
	      
	    for(int i=0; i<notificationid_list.size(); i++) {
	    	  System.out.println("Notification ID: " + notificationid_list.get(i));
	    	  System.out.println("Notification Date: " + notification_date_list.get(i));
	    	  System.out.println("Order ID: " + orderid_list.get(i));
	    	  if(sender_id_list.get(i).equals("D0002"))
	    			  System.out.println("Supplier Name: D2");
	    	  else if(sender_id_list.get(i).equals("D0001"))
	    		  System.out.println("Supplier Name: D2");
	    	  else System.out.println("Supplier Name: " + sender_id_list.get(i));
	    	  System.out.println("Expected Delivery Date: " + expected_date_list.get(i));
	    	  
	    	  int delayed = Integer.parseInt(actual_date_list.get(i).split("-")[0])-Integer.parseInt(expected_date_list.get(i).split("-")[0]);
	    	  
	    	  System.out.println("Delayed By: " + delayed + " days"); 
	    	  System.out.println("");
	    	  
	      }
	  
	      DBUtility.close(connection);
	      
	      
	    }catch(SQLException e){
	      System.out.println("Connection Failed! Check output console");
	      e.printStackTrace();
	      DBUtility.close(connection);
	      
	    }
	  Scanner t = new Scanner(System.in);
	  System.out.println("1. Order ID\n" + 
	  		"2. Go back");
	  int choice = t.nextInt();
	  switch (choice) {
	  case 1:
		  System.out.println("Enter an order ID to view details: ");
		  int orderID = t.nextInt();
		  t.nextLine();
		  getOrderDetails(orderID);
	  break;
	  case 2: displayManagerLanding(userId);
	  break;
	  default: System.out.println("Please enter a valid choice");
	  }
	  t.close();
  }

private void getOrderDetails(int orderID) {
	int part_id=0,quantity=0,price=0;
	String  status="", car_make="", order_date="", center_id="", sx_center_id="", sx_dist_id="", part_name=""; 
	String sender_id="";
    try{
	      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
	      
	      System.out.println("Order ID: " + orderID);
	      
	      stmt = connection.prepareStatement("SELECT centerid, order_date, part_id, quantity, status, car_make from Orderpart where orderid = ?");
	      stmt.setInt(1, orderID);
	      rs = stmt.executeQuery();
	      while(rs.next()) {
	    	  
	    	  center_id = rs.getString(1);
	    	  System.out.println("Purchaser ID: " + center_id);
	    	  
	    	  order_date = rs.getString(2);
	    	  System.out.println("Date: " + order_date);
	    	  
	    	  part_id = rs.getInt(3);
	    	  
	    	  
	    	  quantity = rs.getInt(4);
	    	  System.out.println("Quantity: " + quantity);
	    	  
	    	  status = rs.getString(5);
	    	  //status_list.add(status);
	    	  System.out.println("Order Status: " + status);
	    	  
	    	  car_make = rs.getString(6);
	      }//car_make_list.add(car_make);
	    	  
	          stmt.close();
		      rs.close();
		      
	       	  stmt = connection.prepareStatement("SELECT senderid from makes where orderid=?");
	    	  stmt.setInt(1, orderID);
		      rs = stmt.executeQuery();
		      while(rs.next()) {
		    	  sender_id = rs.getString(1);
		    	  if(sender_id.equals("D0002"))
	    			  System.out.println("Supplier Name: D2");
	    	      else if(sender_id.equals("D0001"))
	    		  System.out.println("Supplier Name: D2");
	    	      else System.out.println("Supplier Name: " + sender_id);
		    	  //System.out.print("Sender ID: " + sender_id);
		    	  //sender_id_list.add(sender_id);
		      }
	    	  
	    	  stmt.close();
	    	  rs.close();
	    	  
	     	  stmt = connection.prepareStatement("SELECT center_name from service_center where centerid=?");
	    	  stmt.setString(1, sender_id);
		      rs = stmt.executeQuery();
		      
		  
		     while(rs.next()) {
		    	  sx_center_id = rs.getString(1);
		    	  
		      }
		     
		      stmt.close();
		      rs.close();
		       
		     
		  	  stmt = connection.prepareStatement("SELECT distname from distributor where distid=?");
	    	  stmt.setString(1, sender_id);
		      rs = stmt.executeQuery();
		      while(rs.next()) {
		    	  sx_dist_id = rs.getString(1);
		    	  //sx_dist_id_list.add(rs.getString(1));
		      }
		      stmt.close();
		      rs.close();
		      

    	      stmt = connection.prepareStatement("SELECT price from Parts_Mapping where partid=? AND car_company = ?");
    	      stmt.setInt(1, part_id);
    	      stmt.setString(2, car_make);
    	      rs = stmt.executeQuery();
    	      
    	      while(rs.next()) {
    	    	  price = rs.getInt(1);
    	    	  //pricelist.add(rs.getInt(1));
    	    	  System.out.println("Unit Price: " + rs.getInt(1));
    	      }
    	      stmt.close();
    	      rs.close();
	    	 
    	      stmt = connection.prepareStatement("SELECT part_name from Inventory where partid=? AND centerID = ? AND car_model = ?");
    	      stmt.setInt(1, part_id);
    	      stmt.setString(2, center_id);
    	      stmt.setString(3, car_make);
    	      rs = stmt.executeQuery();
    	     while(rs.next()) {
    	    	  part_name = rs.getString(1);
    	    	  //part_name_list.add(part_name);
    	    	  System.out.println("Part Name: " + part_name);
    	      }
    	      stmt.close();
    	      rs.close();
    	      
    	      System.out.println("Total cost: " + quantity * price);
    	      
    	      DBUtility.close(connection);      
	      
	    }catch(SQLException e){
	      System.out.println("Connection Failed! Check output console");
	      e.printStackTrace();
	      DBUtility.close(connection);
	      
	    }
	Scanner t = new Scanner(System.in);
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
		  notifications();
	  t.close();
}

private void addNewCarModel() {
	Scanner t = new Scanner(System.in);
	
	
	System.out.println("Enter the following details: ");
	System.out.println("Make: ");
	String make = t.nextLine();
	
	System.out.println("Model: ");
	String model = t.nextLine();
	
	System.out.println("Year");
	String year = t.nextLine();
	
	String car_name = make+" "+model;
	
	System.out.println("Service A- Miles: ");
	int a_miles = t.nextInt();
	
	
	System.out.println("Service A- Months: ");
	int a_months = t.nextInt();
	
	
	System.out.println("Enter the number of parts to be added: ");
	int num_parts = t.nextInt();
	int [] a_parts = new int [num_parts];
	int [] a_part_quantity = new int[num_parts];
	
	for(int i =0 ; i<num_parts; i++) {
		System.out.println("Service A- Enter partID for part " );
		a_parts[i] = t.nextInt();
		//System.out.println("Enter quantity required for part");
		//a_part_quantity[i] = t.nextInt();
	}
	
	System.out.println("Service B- Miles: ");
	int b_miles = t.nextInt();
	
	
	System.out.println("Service B- Months: ");
	int b_months = t.nextInt();
	
	
	System.out.println("Enter the number of parts to be added that are different from A: ");
	int num_parts_b = t.nextInt();
	int [] b_parts = new int [num_parts_b];
	int [] b_part_quantity = new int[num_parts_b];
	
	for(int i =0 ; i<num_parts_b; i++) {
		System.out.println("Service B- Enter partID for part " );
		b_parts[i] = t.nextInt();
		//System.out.println("Enter quantity required for part" );
		//b_part_quantity[i] = t.nextInt();
	}
	
	System.out.println("Service C- Miles: ");
	int c_miles = t.nextInt();
	
	
	System.out.println("Service C- Months: ");
	int c_months = t.nextInt();
	
	
	System.out.println("Enter the number of parts to be added that are different from A and B: ");
	int num_parts_c = t.nextInt();
	int [] c_parts = new int [num_parts_c];
	int [] c_part_quantity = new int[num_parts_c];
	
	for(int i =0 ; i<num_parts_c; i++) {
		System.out.println("Service C- Enter partID for part ");
		c_parts[i] = t.nextInt();
		//System.out.println("Enter quantity required for part" );
		//c_part_quantity[i] = t.nextInt();
	}
	
	
	
	 System.out.println("1. Add Car\n" + 
	  		"2. Go back");
	  int choice = t.nextInt();
	  
	  int [] a_parts_basicservice = new int [a_parts.length];
	  int [] b_parts_basicservice = new int [a_parts.length + b_parts.length];
	  int [] c_parts_basicservice = new int [a_parts.length + b_parts.length + c_parts.length];
	  String a_basic_service_name="", b_basic_service_name="", c_basic_service_name="";
	  
	  switch (choice) {
	  case 1: 
		  for(int i=0; i<a_parts.length; i++) {
			  try{
			      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
			     
			      stmt = connection.prepareStatement("SELECT basicserviceid from partrequired_car_model where part_required=? AND TRIM(car_model) = 'Toyota Prius' " );
			      
			      stmt.setInt(1, a_parts[i]);
			      rs = stmt.executeQuery();
			      while(rs.next()) {
			    	  a_parts_basicservice [i]= rs.getInt(1);
			    	  b_parts_basicservice [i] = rs.getInt(1);
			    	  c_parts_basicservice [i] = rs.getInt(1);
			      }
			      stmt.close();
			      rs.close();
			      
			      DBUtility.close(connection);
			      
			      
			    }catch(SQLException e){
			      System.out.println("Connection Failed! Check output console");
			      e.printStackTrace();
			      DBUtility.close(connection);
			      
			    }
			  a_basic_service_name = a_basic_service_name+ Integer.toString(a_parts_basicservice[i]) + ",";
			 // System.out.println(a_basic_service_name);
		     }
		  for(int i=0; i<b_parts.length; i++) {
			  try{
			      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
			     
			      stmt = connection.prepareStatement("SELECT basicserviceid from partrequired_car_model where car_model =? AND part_required=?");
			      stmt.setString(1, car_name);
			      stmt.setInt(2, b_parts[i]);
			      rs = stmt.executeQuery();
			      while(rs.next()) {
			    	  System.out.println("B");
			    	  System.out.println(rs.getInt(1));
			    	  b_parts_basicservice [a_parts_basicservice.length+i] = rs.getInt(1);
			    	  c_parts_basicservice [a_parts_basicservice.length+i] = rs.getInt(1);
			      }
			      stmt.close();
			      rs.close();
			      
			      DBUtility.close(connection);
			      
			      
			    }catch(SQLException e){
			      System.out.println("Connection Failed! Check output console");
			      e.printStackTrace();
			      DBUtility.close(connection);
			      
			    }
			  b_basic_service_name = b_basic_service_name+ Integer.toString(b_parts_basicservice[i]) + ",";
		     }
		  for(int i=0; i<c_parts.length; i++) {
			  try{
			      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
			     
			      stmt = connection.prepareStatement("SELECT basicserviceid from partrequired_car_model where car_model =? AND part_required=?");
			      stmt.setString(1, car_name);
			      stmt.setInt(2, c_parts[i]);
			      rs = stmt.executeQuery();
			      while(rs.next()) {
			    	  
			    	  //System.out.println("C");
			    	  //System.out.println(rs.getInt(1));
			    	  c_parts_basicservice [b_parts_basicservice.length+i] = rs.getInt(1);
			      }
			      stmt.close();
			      rs.close();
			      
			      DBUtility.close(connection);
			      
			      
			    }catch(SQLException e){
			      System.out.println("Connection Failed! Check output console");
			      e.printStackTrace();
			      DBUtility.close(connection);
			      
			    }
			  c_basic_service_name = c_basic_service_name+ Integer.toString(c_parts_basicservice[i]) + ",";
		     }
		  
		  String service_type="", basic_services_used="" ;
		  int miles=0;
		 for(int i=0; i< 3; i++) {
			 try{
				  if(i==0) {
					  service_type="A";
					  miles = a_miles;
					  basic_services_used = a_basic_service_name;
				  }
				  else if(i==1) {
					  service_type = "B";
					  miles = b_miles;
					  basic_services_used = b_basic_service_name;
				  }
					  
				  else {
					  service_type = "C";
					  miles = c_miles;
					  basic_services_used = c_basic_service_name;
				  }
				  
				  
			      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
			      
			      stmt = connection.prepareStatement("INSERT INTO basic_service_mapping VALUES (?,?,?,?)");
			      stmt.setString(1, car_name);
			      stmt.setString(2, service_type);
			      stmt.setInt(3, miles);
			      stmt.setString(4, basic_services_used);
			      
			      stmt.executeUpdate();
			      stmt.close();
			     
			      DBUtility.close(connection);
			      
			      
			    }catch(SQLException e){
			      System.out.println("Connection Failed! Check output console");
			      e.printStackTrace();
			      DBUtility.close(connection);
			      
			    }
		 }
		 
//		 int [] basic_service_id = new int [c_parts_basicservice.length];
//		 
//		 for(int i=0; i<c_parts_basicservice.length; i++) {
//			 basic_service_id[i] = Integer.
//		 }
		 
		/* for(int i=0; i<c_parts_basicservice.length; i++) {
			 try{
			      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
			      
			      stmt = connection.prepareStatement("INSERT INTO partrequired_car_model VALUES(?,?,?,?)");
			      stmt.setInt(1, c_parts_basicservice[i]);
			      stmt.setString(2, car_name);
			      if(i<a_parts.length) {
			    	  stmt.setInt(3, a_parts[i]);
			    	  stmt.setInt(4, a_part_quantity[i]);
			      }
			    	  
			      else if (i>a_parts.length && i<b_parts.length) {
			    	  stmt.setInt(3, b_parts[i-a_parts.length]);
			          stmt.setInt(4, b_part_quantity[i-a_parts.length]);
			      
			      }
			    	  
			      else {
			    	  stmt.setInt(3, c_parts[i-(a_parts.length+b_parts.length)]);
			          stmt.setInt(4, c_part_quantity[i-(a_parts.length+b_parts.length)]);
			      
			      }
			    	  
			      stmt.executeUpdate();
			      
			      stmt.close();
			      rs.close();
			      
			      DBUtility.close(connection);
			      
			      
			    }catch(SQLException e){
			      System.out.println("Connection Failed! Check output console");
			      e.printStackTrace();
			      DBUtility.close(connection);
			      
			    }
		 }*/
		 
		 
	  System.out.println("Car Successfully Added"); 
	  System.out.println("1. Go Back");
	  int inner_choice = t.nextInt();
	  if (inner_choice ==1 )
		  displayManagerLanding(userId);
	  t.close();
	  break;
	  
	  //case 2: displayManagerLanding(userId);
	  //break;
	  default: System.out.println("Please enter a valid choice");
	  break;
	  }
	  t.close();
	
}


private void getCarServiceDetails() {
	
	try{
	      connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
	      
	      stmt = connection.prepareStatement("SELECT TRIM(car_model), TRIM(service_type), TRIM(miles), TRIM(basic_services_used) from basic_service_mapping");
	      rs = stmt.executeQuery();
	      while(rs.next()) {
	    	  System.out.println("Car Name: " + rs.getString(1));
	    	  System.out.println("Service Type: " + rs.getString(2));
	    	  System.out.println("Miles: " + rs.getString(3));
	    	  System.out.println("Basic Services Used: " + rs.getString(4));
	    	  System.out.println("");
	      }
	      stmt.close();
	      rs.close();
	      
	      DBUtility.close(connection);
	      
	      
	    }catch(SQLException e){
	      System.out.println("Connection Failed! Check output console");
	      e.printStackTrace();
	      DBUtility.close(connection);
	      
	    }
	Scanner t = new Scanner(System.in);
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
		  displayManagerLanding(userId);
	  t.close();
	
}

private void getServiceHistory() {
	try{
        connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);

        String query= "Select S.ServiceID, C.LicensePlateID, C.Type_Recent_Service, R.Mechanic_Preference, A.start_slot, A.App_Date, A.end_slot, Cu.Cust_Name" + 
        		" from GoesTo G, Cars C, Request R,Customer Cu, Appointment A, LinkedTo L, Service S" + 
        		" WHERE G.LicensePlateID = C.LicensePlateID" + 
        		" AND   C.LicensePlateID = R.LicensePlateID" + 
        		" AND   R.Appointmentno = A.Appointmentno" + 
        		" AND   A.Appointmentno = L.Appointmentno" + 
        		" AND   L.ServiceID = S.ServiceID"+
        		" AND   G.CustID = Cu.CustID";
        stmt = connection.prepareStatement(query);
        //stmt.setInt(1, customerId);
        rs = stmt.executeQuery();
        
        while (rs.next()) {
            System.out.println("Service Id: "+rs.getInt(1));
            System.out.println("Customer Name: "+rs.getString(8));
            System.out.println("License plate id:"+rs.getString(2));
            System.out.println("Recent service Type:"+rs.getString(3));  
            System.out.println("Preffered mechanic:"+rs.getString(4));
            System.out.println("Start time:"+convertSlotToTime(rs.getInt(5)));
            System.out.println("Appointment date:"+rs.getString(6));
            System.out.println("Total time:"+(rs.getInt(7)-rs.getInt(5)+1)*30+ " minutes");
            System.out.println("Service status:"+getStatus());
            System.out.println("");
            }
        
        
        	rs.close();
        	
        
        DBUtility.close(connection);

      }catch(SQLException e){
        System.out.println("Connection Failed! Check output console");
        e.printStackTrace();
        
      }
	Scanner t = new Scanner(System.in);
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
		  displayManagerLanding(userId);
	  t.close();
}

 private String convertSlotToTime(int slot) {
	 String myTime = "08:00";
	 SimpleDateFormat df = new SimpleDateFormat("HH:mm");
	 Date d = null;
	try {
		d = df.parse(myTime);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	 Calendar cal = Calendar.getInstance();
	 cal.setTime(d);
	 cal.add(Calendar.MINUTE, (slot-1)*30);
	 String newTime = df.format(cal.getTime());
	 return newTime;
 }

private String getStatus() {
	String status="";
	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	try {
		Date d1 = df.parse(rs.getString(6)+" "+convertSlotToTime(rs.getInt(5)));
		Date d2 = df.parse(rs.getString(6)+" "+convertSlotToTime(rs.getInt(7)));
		Timestamp s = new Timestamp(d1.getTime());
		Timestamp e = new Timestamp(d2.getTime());
		Date current = new Date();
		String currentDate =df.format(current);
		current = df.parse(currentDate);
		Timestamp currentTimestamp = new Timestamp(current.getTime());
		if (currentTimestamp.before(s)) {
			status="Pending";
		} else if (currentTimestamp.after(e)) {
			status="Complete";
		} else {
			status="Ongoing";
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return status;
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
