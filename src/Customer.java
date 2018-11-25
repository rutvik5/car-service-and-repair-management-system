import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
 String carType="";
 String basicServiceArray[];
 String centerID;
 int maxWindow=0;
 String appDate;
 int start_slot=0;
 int end_slot=0;
 float totalHours=0;
 int appointment=0;
 int serviceId=0;
 String date1 ="";
 String date2="";
 float totalPrice=0;
 int RepairMaintenanceFlag=0;
 String problem_case="";
 int dignostic_fees=0;
 boolean rescheduleFlag=false;
 String receptionistId="";
 HashMap<Integer,Integer> partMap = new HashMap<Integer,Integer>();
 Receptionist r = new Receptionist();
  public void displayCustomerLanding(String user_id){
	  userId =user_id;
    System.out.println("1.Profile\n2.Register Car\n3.Service\n4.Invoices\n5.Logout");
    Scanner t= new Scanner(System.in);
    int option= t.nextInt();
    switch(option){
      case 1:
            profile(userId,receptionistId);
            break;
      case 2:
            registerCar(userId);
            break;
      case 3:
            service(userId,receptionistId);
            break;
      case 4:
            invoices(userId,receptionistId);
            break;
      case 5:
            home.displayHomepage();

    }
  }

  public void profile(String CutomerId,String recpId){
	  receptionistId=recpId;
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
      case 3:if(receptionistId.equals("")) {
    	  displayCustomerLanding(CutomerId);
      } else {
    	  receptionist.displayReceptionistLanding(receptionistId);
      }
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
        //rs.close();
        
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
            profile(CutomerId,receptionistId);
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
        //rs.close();
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
            profile(CutomerId,receptionistId);
            break;
    }

  }
  public void registerCar(String useremailId){
	  userId=useremailId;
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

  public void service(String userEmailId,String recpId){
	  userId=userEmailId;
	  receptionistId=recpId;
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
        //rs.close();
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
    	  rescheduleFlag=true;
            rescheduleService();
            break;
      case 4:if(receptionistId.equals("")) {
    	  displayCustomerLanding(userId);
      } else {
    	  r.displayReceptionistLanding(receptionistId);
    }           break;
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
      service(userId,receptionistId);
    }
  }

  public void scheduleService(){

    //based on the above entered data, provide the users following options
    System.out.println("Please select one of the following");
    System.out.println("1. Schedule Maintenance" + "\n" + " 2. Schedule Repair" + "\n" + "3. Go Back");
    Scanner t= new Scanner(System.in);
    int user_choice= t.nextInt();
    RepairMaintenanceFlag=user_choice;
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

        System.out.print("D. Mechanic Name:\t");
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
      break;
      case 2: scheduleService();
      break;
      default: System.out.println("Enter a valid choice");
      break;
    }
  }
  public void findServiceDate(){
    Scanner t= new Scanner(System.in);
    try{
    	connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
    	if (RepairMaintenanceFlag==1) {
    		getServiceType();
    	} 
    	
    	if (RepairMaintenanceFlag==2) {
    		stmt = connection.prepareStatement("select type_recent_service,car_type,last_mileage from Cars where licenseplateid = ?");
    		stmt.setString(1, license_plate);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			carType=rs.getString(2).split(",")[0].trim()+ " "+rs.getString(2).split(",")[1].trim();
    		}
    	}
        getBasicID();
        if (RepairMaintenanceFlag==2) {
        	getRepairDetails();
        }
        
        DBUtility.close(connection);

      }catch(SQLException e){
        System.out.println("Connection Failed! Check output console");
        e.printStackTrace();
      }
    
    System.out.println("Please select one of the following");
    System.out.println("1. Schedule on Date" + "\n" + " 2. Go Back");
    int user_choice= t.nextInt();
    switch (user_choice) {

      case 1: postAppointmentConfirmtion();
              System.out.println("Service appointment successfully saved");
              scheduleService();
              break;

      case 2: if (RepairMaintenanceFlag==1) {
    	  scheduleMaintenance(license_plate,current_mileage,mechanic_name);
      } else {
    	  scheduleRepair();
      }
      break;
      default: System.out.println("Enter a valid choice");
      break;
    }
  }

private void getRepairDetails() {
	try {
		stmt = connection.prepareStatement("select repair_name, dignostic_solution, diagnostic_fee from repair_service_mapping where repair_id=?");
		stmt.setInt(1, Integer.parseInt(serviceType));
		rs = stmt.executeQuery();
		while (rs.next()) {
			
			System.out.println("Repair Type:"+rs.getString(1));
			problem_case=rs.getString(1);
			System.out.println("Dignostic Solution:"+rs.getString(2));
			dignostic_fees=rs.getInt(3);
			System.out.println("Dignostic fees:"+rs.getInt(3));
			System.out.println("");
		}
		for (Map.Entry m : partMap.entrySet()) {
			stmt = connection.prepareStatement("select part_name,price from parts_mapping where partid =? and car_company=?");
			stmt.setInt(1, (int)m.getKey());
			stmt.setString(2, carType.split(" ")[0]);
			rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.println("Part Name-"+rs.getString(1)+"|Price-$"+rs.getInt(2)+"|Qty-"+(int)m.getValue());
			}
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	
}

private void postAppointmentConfirmtion() {
	try {
		connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
		UpdateAppointment();  // update of appointment table && update linked to table
		UpdateCustomerRequest(); //update request table
		DecreaseSender(); // reduce items from inventory
		setTypeRecentService(); //set type recent service
		connection.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	
}

private void setTypeRecentService() {
	String typeRecentService="";
	try {
		
		if (RepairMaintenanceFlag==2) {
			stmt = connection.prepareStatement("select repair_name from repair_service_mapping where repair_id=?");
			stmt.setInt(1, Integer.parseInt(serviceType));
			rs =stmt.executeQuery();
			if (rs.next()) {
				typeRecentService=rs.getString(1);
			}
		}
		if (RepairMaintenanceFlag==1) {
			typeRecentService=serviceType;
		}
		
 stmt = connection.prepareStatement("Update Cars set type_recent_service = ? where licenseplateid = ?");
  stmt.setString(1, typeRecentService);
    stmt.setString(2, license_plate);
    stmt.executeQuery(); 
    } catch (Exception e) {
    	e.printStackTrace();
    }
}

private void getBasicID() {
	try{
		totalHours=0;
		totalPrice=0;
		partMap = new HashMap<Integer,Integer>();
		if (RepairMaintenanceFlag==1) {
			stmt = connection.prepareStatement("select basic_services_used from basic_service_mapping where car_model = ? and service_type=?");
	        stmt.setString(1, carType);
	        stmt.setString(2, serviceType);
	        rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	        	basicServiceArray =rs.getString(1).split(",");
	            }
		}
		
		if (RepairMaintenanceFlag==2) {
			stmt = connection.prepareStatement("select basic_services_used from repair_service_mapping where repair_id=?");
	        stmt.setInt(1, Integer.parseInt(serviceType));
	        rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	        	basicServiceArray =rs.getString(1).split(",");
	            }
		}
        
        //rs.close();
        for (int i=0;i<basicServiceArray.length;i++) {
        	int currentBaiscServiceid = Integer.parseInt(basicServiceArray[i]);
        	stmt = connection.prepareStatement("select part_required,part_quantity_required from partrequired_car_model where basicserviceid =? and TRIM(car_model) =?");
        	stmt.setInt(1, currentBaiscServiceid);
        	stmt.setString(2, carType);
        	rs= stmt.executeQuery();
        	
        	while (rs.next()) {
        		if (!partMap.containsKey(rs.getInt(1))) {
        			partMap.put(rs.getInt(1), rs.getInt(2));
        		} else {
        			partMap.put(rs.getInt(1), partMap.get(rs.getInt(1))+rs.getInt(2));
        		}
        	}
        	//rs.close();
        }
        
        for (int i=0;i<basicServiceArray.length;i++) {
        	int currentBaiscServiceid = Integer.parseInt(basicServiceArray[i]);
        	stmt = connection.prepareStatement("select hours_required from basicservice where basicserviceid=?");
        	stmt.setInt(1, currentBaiscServiceid);
        	rs = stmt.executeQuery();
        	if (rs.next()) {
        		totalHours = totalHours+rs.getFloat(1);
        	}
        }
        
        for (Map.Entry m: partMap.entrySet()) {
        	stmt = connection.prepareStatement("select price from parts_mapping where partId=? and car_company=?");
        	stmt.setInt(1, (int)m.getKey());
        	stmt.setString(2, carType.split(" ")[0]);
        	rs = stmt.executeQuery();
        	if (rs.next()) {
        		totalPrice = totalPrice + (rs.getInt(1)*(int)m.getValue());
        	}
        }
        
        stmt = connection.prepareStatement("select centerid from goesto where custid=?");
        stmt.setInt(1, custid);
        rs = stmt.executeQuery();
        while (rs.next()) {
        	centerID=rs.getString(1);
        }
        
        connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
    	stmt = connection.prepareStatement("select e.emp_name from employee e, worksat w where TRIM(e.emp_name)=? and e.role=3 and e.empid = w.empid and centerid=?");
    	stmt.setString(1, mechanic_name);
    	stmt.setString(2, centerID);
    	rs = stmt.executeQuery();
    	if (!rs.next()) {
    		stmt = connection.prepareStatement("select e.emp_name from (select * from employee order by DBMS_RANDOM.VALUE) e,worksat w where rownum=1 and e.role=3 and e.empid=w.empid and w.centerid=?");
    		stmt.setString(1, centerID);
    		rs = stmt.executeQuery();
    		while (rs.next()) {
    			mechanic_name = rs.getString(1);
    		}
    		System.out.println("You have entered mechanic not present in our records.Assigning mechanic:"+mechanic_name);
    	}

        for (Map.Entry m: partMap.entrySet()) {
        	
        	int window=0;
        	stmt = connection.prepareStatement("select current_quantity,min_order from inventory where centerid=? and partid=? and car_model=?");
        	stmt.setString(1, centerID);
        	stmt.setInt(2, (int)m.getKey());
        	stmt.setString(3, carType.split(" ")[0]);
        	rs = stmt.executeQuery();
        	if (rs.next()) {
        		if (rs.getInt(1)<(int)m.getValue()) {
        			//rs.close();
        			stmt = connection.prepareStatement("select current_quantity,threshold_min from inventory where centerid!=? and partid=? and car_model=? and current_quantity-?>threshold_min");
        			stmt.setString(1, centerID);
                	stmt.setInt(2, (int)m.getKey());
                	stmt.setString(3, carType.split(" ")[0]);
                	stmt.setInt(4,Math.max(rs.getInt(2), (int)m.getValue()));
                	rs = stmt.executeQuery();
                	if(rs.next()) {
                		window=1;
                	} else {
                		stmt = connection.prepareStatement("select min(delivery_window)from distributor where partid=?");
                		stmt.setInt(1, (int)m.getKey());
                		rs = stmt.executeQuery();
                		if (rs.next()) {
                			window =rs.getInt(1);
                		}
                	}
                	if (window> maxWindow) {
                		maxWindow=window;
                	}
        		}
        	} 
        }
        
    	getAppointmentDate(); // appointment date is chosen by customer
      }catch(Exception e){
        System.out.println("Connection Failed! Check output console");
        e.printStackTrace();
      }
}

private void DecreaseSender() {
	try {
	for (Map.Entry m: partMap.entrySet()) {
    	int partQuantityRequired=0;
    	int maxorderid=0;
    	String senderId="";
		stmt = connection.prepareStatement("select current_quantity,min_order,threshold_min from inventory where centerid=? and partid=? and car_model=?");
    	stmt.setString(1, centerID);
    	stmt.setInt(2, (int)m.getKey());
    	stmt.setString(3, carType.split(" ")[0]);
    	rs = stmt.executeQuery();
    	if (rs.next()) {
    		if (rs.getInt(1)-(int)m.getValue()<rs.getInt(3)) {
    			//rs.close();
    			stmt = connection.prepareStatement("select current_quantity,threshold_min,centerid from inventory where centerid!=? and partid=? and car_model=? and current_quantity-?>threshold_min");
    			stmt.setString(1, centerID);
            	stmt.setInt(2, (int)m.getKey());
            	stmt.setString(3, carType.split(" ")[0]);
            	partQuantityRequired=Math.max(rs.getInt(2), (int)m.getValue());
            	stmt.setInt(4,partQuantityRequired);
            	rs = stmt.executeQuery();
            	if(rs.next()) {
            		stmt = connection.prepareStatement("Update inventory set current_quantity=?-? where centerid=? and partid=? and car_model=?");
            		stmt.setInt(1,rs.getInt(1));
            		stmt.setInt(2, partQuantityRequired);
            		stmt.setString(3, rs.getString(3));
            		senderId =rs.getString(3);
            		stmt.setInt(4,(int)m.getKey());
            		stmt.setString(5, carType.split(" ")[0]);
            		stmt.executeUpdate();
            		
            		//Updating order part
            		
            		stmt =connection.prepareStatement("select max(orderid) from orderpart");
            		rs = stmt.executeQuery();
            		if (rs.next()) {
            			maxorderid=rs.getInt(1)+1;
            		}
            	} else {
            		stmt =connection.prepareStatement("select max(orderid) from orderpart");
            		rs = stmt.executeQuery();
            		if (rs.next()) {
            			maxorderid=rs.getInt(1)+1;
            		}
            		
            		stmt = connection.prepareStatement("select distId from distributor where delivery_window =(select min(delivery_window)from distributor where partid=?) and partid=?");
            		stmt.setInt(1, (int)m.getKey());
            		stmt.setInt(2, (int)m.getKey());
            		rs = stmt.executeQuery();
            		if (rs.next()) {
            			senderId = rs.getString(1);
            		}
            	}
            	
            	
            	
            	stmt = connection.prepareStatement("Insert into orderpart values (?,?,?,?,?,'Pending',?,null,?)");
        		stmt.setString(1, centerID);
        		stmt.setInt(2, maxorderid);
        		stmt.setString(3,new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        		stmt.setInt(4, (int)m.getKey());
        		stmt.setInt(5, partQuantityRequired);
        		stmt.setString(6, date1);
        		stmt.setString(7, carType.split(" ")[0]);
        		stmt.executeUpdate();
        		
        		stmt = connection.prepareStatement("Insert into makes values(?,?,?,?)");
            	stmt.setString(1, senderId);
            	stmt.setString(2, centerID);
            	stmt.setInt(3, maxorderid);
            	stmt.setInt(4, (int)m.getKey());
            	stmt.executeUpdate();
    		}
    	} 
    } } catch (Exception e) {
    	System.out.println("Your car is not register and please start again");
    	service(userId, receptionistId);
    	e.printStackTrace();
    }
	
}

private void getAppointmentDate() throws ParseException, SQLException {
	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	Calendar cal = Calendar.getInstance();
	Date current;
	if (rescheduleFlag==false) {
		 current = new Date();
		String currentDate =df.format(current);
		current = df.parse(currentDate);
		cal.setTime(current);
		cal.add(Calendar.DATE, maxWindow+1);
	} else {
		Date maxDate;
		maxDate=df.parse("01-01-1000");
		Date currentDate;
		stmt = connection.prepareStatement("select app_date from appointment");
		rs = stmt.executeQuery();
		while (rs.next())
		{
			currentDate = df.parse(rs.getString(1));
			if(currentDate.compareTo(maxDate)>0) {
				maxDate=currentDate;
			}
		}
		cal.setTime(maxDate);
		cal.add(Calendar.DATE, 1);
		// Deleting service and appointment
		int appointmenttoDelete=0;
		stmt = connection.prepareStatement("select appointmentno from linkedto where serviceid=?");
		stmt.setInt(1, serviceId);
		rs = stmt.executeQuery(); 
		while(rs.next()) {
			appointmenttoDelete= rs.getInt(1);
			}
		
		stmt =connection.prepareStatement("delete from appointment where appointmentno=?");
		stmt.setInt(1, appointmenttoDelete);
		stmt.executeUpdate();
		
		stmt =connection.prepareStatement("delete from service where serviceid=?");
		stmt.setInt(1, serviceId);
		stmt.executeUpdate();
	}
		while(true) {
		
		date1=df.format(cal.getTime());
			stmt = connection.prepareStatement("select max(end_slot) from appointment where TRIM(app_Date)=? and TRIM(mechanic)=?");
			stmt.setString(1, date1);
			stmt.setString(2, mechanic_name);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				end_slot =rs.getInt(1);
				if (end_slot==0) {
					start_slot=1;
					end_slot=(int)Math.ceil(totalHours*2);
					break;
				} else {
					if(end_slot+(int)Math.ceil(totalHours*2)+1<22) {
						start_slot = end_slot+1;
						end_slot = end_slot+(int)Math.ceil(totalHours*2);
						break;
					} else {
						cal.add(Calendar.DATE,1);
					}
				}
			} 
		}
		System.out.println("Choose one of the below available dates: 1 or 2");
		System.out.println("Date-"+ date1 +" Slot-"+convertSlotToTime(start_slot)+"-"+convertSlotToTime(end_slot));
		cal.add(Calendar.DATE,1);
		date2=df.format(cal.getTime());
		System.out.println("Date-"+ date2 +" Slot-"+convertSlotToTime(start_slot)+"-"+convertSlotToTime(end_slot));
		Scanner s = new Scanner(System.in);
		int choice = s.nextInt();
		s.nextLine();
		if (choice==1) {
			appDate=date1; 
		} else {
			appDate=date2;
		}
}

private void UpdateCustomerRequest() {
	try {
		stmt = connection.prepareStatement("insert into request values(?,?,?,?)");
		stmt.setInt(1, appointment);
		stmt.setString(2, license_plate);
		stmt.setFloat(3, current_mileage);
		stmt.setString(4, mechanic_name);
		stmt.executeUpdate();
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}

private void UpdateAppointment() {
	try {
		stmt = connection.prepareStatement("select max(appointmentno) from appointment");
		rs = stmt.executeQuery();
		while (rs.next()) {
			appointment=rs.getInt(1)+1;
		}
		stmt = connection.prepareStatement("Insert into appointment values(?,?,?,?,?)");
		stmt.setInt(1, appointment);
		stmt.setString(2, appDate);
		stmt.setInt(3, start_slot);
		stmt.setInt(4, end_slot);
		stmt.setString(5, mechanic_name);
		stmt.executeUpdate();
		
		stmt = connection.prepareStatement("select max(serviceid) from service");
		rs = stmt.executeQuery();
		while (rs.next()) {
			 serviceId = rs.getInt(1)+1;
		} 
		
		stmt = connection.prepareStatement("Insert into service values(?,?,?)");
		stmt.setInt(1, serviceId);
		stmt.setFloat(2, totalPrice);
		stmt.setFloat(3, totalHours);
		stmt.executeUpdate();
		
		if(RepairMaintenanceFlag==1) {
			stmt = connection.prepareStatement("Insert into maintenance values(?,?)");
			stmt.setInt(1, serviceId);
			stmt.setString(2, serviceType);
			stmt.executeUpdate();
		}
		
		if (RepairMaintenanceFlag==2) {
			stmt = connection.prepareStatement("Insert into repair values(?,?,?,?)");
			stmt.setInt(1, Integer.parseInt(serviceType));
			stmt.setInt(2, serviceId);
			stmt.setString(3, problem_case);
			stmt.setInt(4, dignostic_fees);
			stmt.executeUpdate();
		}
		
		
		stmt = connection.prepareStatement("Insert into linkedto values(?,?)");
		stmt.setInt(1, appointment);
		stmt.setInt(2, serviceId);
		stmt.executeUpdate();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
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

private void getServiceType() throws SQLException {
	
	int mileage=0;
	stmt = connection.prepareStatement("select type_recent_service,car_type,last_mileage from Cars where licenseplateid = ?");
	stmt.setString(1, license_plate);
	rs = stmt.executeQuery();
	if(rs.next()) {
		carType=rs.getString(2).split(",")[0].trim()+ " "+rs.getString(2).split(",")[1].trim();
		mileage=rs.getInt(3);
		if (rs.getString(1)==null) {
			//rs.close();
	        stmt = connection.prepareStatement("select miles from basic_service_mapping where car_model = ?");
	        stmt.setString(1, carType);
	        rs = stmt.executeQuery();
	        int m[]=new int[3];
	        int i=0;
	        while(rs.next()) {
	        	m[i]=rs.getInt(1);
	        	i++;
	        }
	        //rs.close();
	        if (mileage<m[0]) {
	        	serviceType="A";
	        } else if (mileage>m[2]) {
	        	serviceType="C";
	        } else {
	        	serviceType="B";
	        }
		} else if (rs.getString(1).trim().equalsIgnoreCase("A")){
			serviceType="B";
		} else if (rs.getString(1).trim().equalsIgnoreCase("B")){
			serviceType="C";
		} else {
			serviceType="A";
		}
	    } 
	//rs.close();
}

  public void scheduleRepair(){
    Scanner t= new Scanner(System.in);

    System.out.println("Please select one of the following encountered problem");
    System.out.println("1. Engine knock" + "\n" + "2. Car drifts in a particular direction" + "\n" + "3. Battery does not hold charge" + "\n" + "4. Black/unclean exhaust" + "\n"
    + "5. A/C- Heater not working" + "\n" + "6. Headlapms/Tail lamps not working" + "\n" + "7. Check engine light" + "\n" + "8.Go Back");

    int user_choice= t.nextInt();
    switch (user_choice) {
      //based on the repair display the report, 2 identifed service dates, mechanic_name
      case 1: serviceType="1";
      findServiceDate();
    	  break;
      case 2:serviceType="2";
      findServiceDate();
    	  break;
      case 3:serviceType="3";
      findServiceDate();
    	  break;
      case 4:serviceType="4";
      findServiceDate();
    	  break;
      case 5:serviceType="5";
      findServiceDate();
    	  break;
      case 6:serviceType="6";
      findServiceDate();
    	  break;
      case 7:serviceType="7";
      findServiceDate();
    	  break;
      case 8: 
    	  scheduleService();
    	  break;
      default: System.out.println("Enter a valid choice");
      selectRepairDate();
      break;
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

    System.out.println("Please enter serviceId for which you wish to reschedule service");
    System.out.println("service ID is sent to you in appointment confirmation email");
    serviceId=t.nextInt();

    System.out.println("Please select one of the following");
    System.out.println("1. Reschedule Service" + "\n" + "2. Go Back");

    int user_choice= t.nextInt();
    switch (user_choice) {

      case 1:
              scheduleService();
              rescheduleFlag=false;

      case 2: displayCustomerLanding(userId);
      default: System.out.println("Enter a valid choice");
    }
  }

  public void invoices(String userEmailId,String recp){
	  userId=userEmailId;
	  receptionistId=recp;
    Scanner t= new Scanner(System.in);
    System.out.println("Please select the following");
    System.out.println("1.View Invoice Details\n2. Go Back");
    int user_choice= t.nextInt();
    if (user_choice == 1) viewInvoiceDetails();
    if (user_choice != 1) {
    	if (receptionistId.equals(""))
    	displayCustomerLanding(userId);
    } else {
    	receptionist.displayReceptionistLanding(receptionistId);
    }
  }

  public void viewInvoiceDetails(){
    Scanner t= new Scanner(System.in);
    System.out.println("Please enter service id");
    serviceId = t.nextInt();
    System.out.println("Invoice details:");
    try {
		connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
		String query= "select s.serviceid, a.appointmentno, a.app_date, a.start_slot,a.end_slot,a.mechanic, c.licenseplateid, c.car_type,"
				+"r.problem_cause,r.repair_id,r.diagnostic_fees,s.total_hours,s.total_fees"
				+" from service s, appointment a, cars c, repair r, linkedto l, request req"
				+ " where s.serviceid=r.serviceid"
				+ " and s.serviceid = l.serviceid"
				+ " and l.appointmentno=a.appointmentno"
				+ " and req.appointmentno= a.appointmentno"
				+ " and req.licenseplateid=c.licenseplateid"
				+ " and s.serviceid= ?";
		stmt = connection.prepareStatement(query);
		stmt.setInt(1, serviceId);
		rs = stmt.executeQuery();
		if (rs.next()) {
		System.out.println("service Id:"+rs.getInt(1));
		System.out.println("Service Date"+rs.getString(3));
		System.out.println("service Start time:"+convertSlotToTime(rs.getInt(4)));
		System.out.println("service End time:"+convertSlotToTime(rs.getInt(5)));
		System.out.println("Licenseplateid:"+rs.getString(7));
		System.out.println("Service Type:"+rs.getString(9));
		System.out.println("Mechanic Name:"+rs.getString(6));
		System.out.println("Total labour hours:"+rs.getFloat(12));

		stmt = connection.prepareStatement("select basic_services_used from repair_service_mapping where repair_id=?");
		stmt.setInt(1, rs.getInt(10));
		ResultSet rs1 = stmt.executeQuery();
        while (rs1.next()) {
        	basicServiceArray =rs1.getString(1).split(",");
            }
        
        for (int i=0;i<basicServiceArray.length;i++) {
        	partMap = new HashMap<Integer,Integer>();
        	int currentBaiscServiceid = Integer.parseInt(basicServiceArray[i]);
        	stmt = connection.prepareStatement("select part_required,part_quantity_required from partrequired_car_model where basicserviceid =? and TRIM(car_model) =?");
        	stmt.setInt(1, currentBaiscServiceid);
        	stmt.setString(2, rs.getString(8).split(",")[0].trim()+" "+rs.getString(8).split(",")[1].trim());
        	ResultSet rs3= stmt.executeQuery();
        	
        	while (rs3.next()) {
        		if (!partMap.containsKey(rs3.getInt(1))) {
        			partMap.put(rs3.getInt(1), rs3.getInt(2));
        		} else {
        			partMap.put(rs3.getInt(1), partMap.get(rs3.getInt(1))+rs3.getInt(2));
        		}
        	}
        	for (Map.Entry m: partMap.entrySet()) {
        		stmt = connection.prepareStatement("select part_name,price, warranty from parts_mapping where partid=? and car_company=?");
            	stmt.setInt(1, (int)m.getKey());
            	stmt.setString(2, rs.getString(8).split(",")[0].trim());
            	ResultSet rs4 = stmt.executeQuery();
            	while(rs4.next()) {
            		System.out.println("Part Name:"+rs4.getString(1)+" Qty:"+(int)m.getValue()+" price:"+rs4.getInt(2)+" Warranty:"+rs4.getInt(3));
            	}
        	}
        }
        stmt = connection.prepareStatement("select m.wages from mechanic_emp m , employee e where TRIM(e.emp_name) =? and e.empid=m.empid");
        stmt.setString(1, rs.getString(6));
        ResultSet rs5 = stmt.executeQuery();
        if(rs5.next()) {
        	float total = rs.getFloat(13)+rs.getFloat(11)+rs5.getInt(1)*rs.getFloat(12);
        	System.out.println("Total cost: $"+ total);
        }
		}
		
		//Maintenance
		String query1= "select s.serviceid, a.appointmentno, a.app_date, a.start_slot,a.end_slot,a.mechanic, c.licenseplateid, c.car_type,"
				+"m.service_type,s.total_hours,s.total_fees"
				+" from service s, appointment a, cars c, maintenance m, linkedto l, request req"
				+ " where s.serviceid=m.serviceid"
				+ " and s.serviceid = l.serviceid"
				+ " and l.appointmentno=a.appointmentno"
				+ " and req.appointmentno= a.appointmentno"
				+ " and req.licenseplateid=c.licenseplateid"
				+ " and s.serviceid= ?";
		stmt = connection.prepareStatement(query1);
		stmt.setInt(1, serviceId);
		rs = stmt.executeQuery();
		if (rs.next()) {
		System.out.println("service Id:"+rs.getInt(1));
		System.out.println("Service Date"+rs.getString(3));
		System.out.println("service Start time:"+convertSlotToTime(rs.getInt(4)));
		System.out.println("service End time:"+convertSlotToTime(rs.getInt(5)));
		System.out.println("Licenseplateid:"+rs.getString(7));
		System.out.println("Service Type:"+rs.getString(9));
		System.out.println("Mechanic Name:"+rs.getString(6));
		System.out.println("Total labour hours:"+rs.getFloat(10));

		stmt = connection.prepareStatement("select basic_services_used from basic_service_mapping where service_type=? and car_model=?");
		stmt.setString(1, rs.getString(9));
		stmt.setString(2, rs.getString(8).split(",")[0].trim()+" "+rs.getString(8).split(",")[1].trim());
		ResultSet rs1= stmt.executeQuery();
		while (rs1.next()) {
        	basicServiceArray =rs1.getString(1).split(",");
            }
        
        for (int i=0;i<basicServiceArray.length;i++) {
        	partMap = new HashMap<Integer,Integer>();
        	int currentBaiscServiceid = Integer.parseInt(basicServiceArray[i]);
        	stmt = connection.prepareStatement("select part_required,part_quantity_required from partrequired_car_model where basicserviceid =? and TRIM(car_model) =?");
        	stmt.setInt(1, currentBaiscServiceid);
        	stmt.setString(2, rs.getString(8).split(",")[0].trim()+" "+rs.getString(8).split(",")[1].trim());
        	ResultSet rs3= stmt.executeQuery();
        	
        	while (rs3.next()) {
        		if (!partMap.containsKey(rs3.getInt(1))) {
        			partMap.put(rs3.getInt(1), rs3.getInt(2));
        		} else {
        			partMap.put(rs3.getInt(1), partMap.get(rs3.getInt(1))+rs3.getInt(2));
        		}
        	}
        	for (Map.Entry m: partMap.entrySet()) {
        		stmt = connection.prepareStatement("select part_name,price, warranty from parts_mapping where partid=? and car_company=?");
            	stmt.setInt(1, (int)m.getKey());
            	stmt.setString(2, rs.getString(8).split(",")[0].trim());
            	ResultSet rs4 = stmt.executeQuery();
            	while(rs4.next()) {
            		System.out.println("Part Name:"+rs4.getString(1)+" Qty:"+(int)m.getValue()+" price:"+rs4.getInt(2)+" Warranty:"+rs4.getInt(3));
            	}
        	}
        }
        stmt = connection.prepareStatement("select m.wages from mechanic_emp m , employee e where TRIM(e.emp_name) =? and e.empid=m.empid");
        stmt.setString(1, rs.getString(6));
        ResultSet rs5 = stmt.executeQuery();
        if(rs5.next()) {
        	float total = rs.getFloat(11)+rs5.getInt(1)*rs.getFloat(10);
        	System.out.println("Total cost: $"+ total);
        }
		}
		
		connection.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
    
    System.out.println("Enter 1 to Go Back");
    int user_choice= t.nextInt();
    if (user_choice == 1) invoices(userId,receptionistId);
  }



}
