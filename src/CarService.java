import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class CarService {
	Connection connection;
	  PreparedStatement stmt=null;
	  ResultSet rs= null;
	  int id=0;
	  String serviceCenter="";
public void AddCar(String carOwner) {
	 Scanner t= new Scanner(System.in);

	    System.out.println("Please enter the following details");
	    // run queries to add the following details into database
	    System.out.print("B. License Plate:\t");
	    String license_plate= t.next();
	    System.out.println("");

	    System.out.print("C. Purchase Date:\t");
	    String purchase_date= t.next();
	    System.out.println("");

	    System.out.print("D. Make:\t");
	    String make= t.next();
	    System.out.println("");

	    System.out.print("E. Model:\t");
	    String model= t.next();
	    System.out.println("");

	    System.out.print("F. Year:\t");
	    String year= t.next();
	    System.out.println("");

	    System.out.print("G. Current Mileage:\t");
	    String current_mileage= t.next();
	    System.out.println("");

	    String carType= make +","+ model +","+ year;
	    
	    try{
	        connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);

	        stmt = connection.prepareStatement("select custid from Customer where cust_email = ?");
	        stmt.setString(1, carOwner);
	        rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            id = rs.getInt(1);
	            }
	        
	        stmt = connection.prepareStatement("insert into cars values(?,?,?,?,null,null)");
		      stmt.setString(1, license_plate);
		      stmt.setString(2, carType);
		      stmt.setString(3, purchase_date);
		      stmt.setString(4, current_mileage);
		      stmt.executeUpdate();
		      
		    stmt = connection.prepareStatement("select LICENSEPLATEID from goesto where custid=? and LICENSEPLATEID='00000000'");
	        stmt.setInt(1, id);
	        rs =stmt.executeQuery();
	        if (rs.next()) {
	        	stmt = connection.prepareStatement("update goesto set LICENSEPLATEID =? where custid=?");
	        	stmt.setString(1, license_plate);
	        	stmt.setInt(2, id);
	        	stmt.executeUpdate();
	        } else {
	        	stmt = connection.prepareStatement("select centerid,LICENSEPLATEID from goesto where custid=?");
	        	stmt.setInt(1, id);
	        	rs = stmt.executeQuery();
	        	while (rs.next()) {
	        		serviceCenter = rs.getString(1);
	        	}
	        	rs.close();
	        	stmt = connection.prepareStatement("insert into goesto values(?,?,?)");
	      	      stmt.setString(1, license_plate);
	        		stmt.setInt(2, id);
	      	      stmt.setString(3, serviceCenter);
	      	      stmt.executeUpdate();
	      	      stmt.close();
	        }
	        
	        
	        DBUtility.close(connection);

	      }catch(SQLException e){
	        System.out.println("Connection Failed! Check output console");
	        e.printStackTrace();
	        DBUtility.close(connection);
	        
	      }
	    
	    
        System.out.println("Car Successfully Added");
        
}

public void serviceHistory(int customerId) {
	try{
        connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);

        String query= "Select S.ServiceID, C.LicensePlateID, C.Type_Recent_Service, R.Mechanic_Preference, A.start_slot, A.App_Date, A.end_slot" + 
        		" from GoesTo G, Cars C, Request R, Appointment A, LinkedTo L, Service S\r\n" + 
        		" WHERE G.LicensePlateID = C.LicensePlateID" + 
        		" AND   C.LicensePlateID = R.LicensePlateID" + 
        		" AND   R.Appointmentno = A.Appointmentno" + 
        		" AND   A.Appointmentno = L.Appointmentno" + 
        		" AND   L.ServiceID = S.ServiceID" + 
        		" AND   G.CustID = ?";
        stmt = connection.prepareStatement(query);
        stmt.setInt(1, customerId);
        rs = stmt.executeQuery();
        
        while (rs.next()) {
            System.out.println("service Id"+rs.getInt(1));
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

}
