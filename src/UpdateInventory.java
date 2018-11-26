import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UpdateInventory {

	Connection connection;
	PreparedStatement stmt;
	ResultSet rs;
	String basicServiceArray[];
	String centerid="";
	String cartype="";
	ResultSet rsm;
	HashMap<Integer,Integer> partMap;
	public void updateDailyInventory(String recpId) {
		try {
			String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			connection= DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
			stmt = connection.prepareStatement("select appointmentno from appointment where app_date =?");
			stmt.setString(1, currentDate);
			rsm = stmt.executeQuery();
			while (rsm.next()) {
				deleteInventoryperAppointment(rsm.getInt(1));
			}
			System.out.println("Inventory Successfully updated...");
			CallableStatement cs = connection.prepareCall("{call notifyOrder}");
			cs.executeQuery();
			connection.close();
			new Receptionist().displayReceptionistLanding(recpId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteInventoryperAppointment(int appointmentno) {
		 Scanner t= new Scanner(System.in);
		    try {
				String query= "select s.serviceid,c.car_type,g.centerid,"
						+"r.repair_id"
						+" from service s, appointment a, cars c, repair r, linkedto l, request req, goesto g"
						+ " where s.serviceid=r.serviceid"
						+ " and s.serviceid = l.serviceid"
						+ " and l.appointmentno=a.appointmentno"
						+ " and req.appointmentno= a.appointmentno"
						+ " and req.licenseplateid=c.licenseplateid"
						+ " and g.licenseplateid = c.licenseplateid"
						+ " and a.appointmentno=?";
				stmt = connection.prepareStatement(query);
				stmt.setInt(1, appointmentno);
				rs = stmt.executeQuery();
				if (rs.next()) {
				centerid=rs.getString(3);
				cartype =rs.getString(2);
				stmt = connection.prepareStatement("select basic_services_used from repair_service_mapping where repair_id=?");
				stmt.setInt(1, rs.getInt(4));
				ResultSet rs1 = stmt.executeQuery();
		        while (rs1.next()) {
		        	basicServiceArray =rs1.getString(1).split(",");
		            }
		        
		        for (int i=0;i<basicServiceArray.length;i++) {
		        	partMap = new HashMap<Integer,Integer>();
		        	int currentBaiscServiceid = Integer.parseInt(basicServiceArray[i]);
		        	stmt = connection.prepareStatement("select part_required,part_quantity_required from partrequired_car_model where basicserviceid =? and TRIM(car_model) =?");
		        	stmt.setInt(1, currentBaiscServiceid);
		        	stmt.setString(2, rs.getString(2).split(",")[0].trim()+" "+rs.getString(2).split(",")[1].trim());
		        	ResultSet rs3= stmt.executeQuery();
		        	while (rs3.next()) {
		        		if (!partMap.containsKey(rs3.getInt(1))) {
		        			partMap.put(rs3.getInt(1), rs3.getInt(2));
		        		} else {
		        			partMap.put(rs3.getInt(1), partMap.get(rs3.getInt(1))+rs3.getInt(2));
		        		}
		        	}
		        	for (Map.Entry m: partMap.entrySet()) {
		        		stmt= connection.prepareStatement("Update inventory set CURRENT_QUANTITY = CURRENT_QUANTITY-? where PARTID=? and CENTERID=? and CAR_MODEL=?");
		        		stmt.setInt(1, (int)m.getValue());
		        		stmt.setInt(2, (int)m.getKey());
		        		stmt.setString(3, centerid);
		        		stmt.setString(4, cartype.split(",")[0].trim());
		        		stmt.executeUpdate();
		        	}
		        }
				}
				
				//Maintenance
				String query1= "select s.serviceid,c.car_type,g.centerid,"
						+"m.service_Type"
						+" from service s, appointment a, cars c, maintenance m, linkedto l, request req, goesto g"
						+ " where s.serviceid=m.serviceid"
						+ " and s.serviceid = l.serviceid"
						+ " and l.appointmentno=a.appointmentno"
						+ " and req.appointmentno= a.appointmentno"
						+ " and req.licenseplateid=c.licenseplateid"
						+ " and g.licenseplateid = c.licenseplateid"
						+ " and a.appointmentno=?";
				stmt = connection.prepareStatement(query1);
				stmt.setInt(1, appointmentno);
				rs = stmt.executeQuery();
				if (rs.next()) {
				centerid=rs.getString(3);
				cartype =rs.getString(2);
				
				stmt = connection.prepareStatement("select basic_services_used from basic_service_mapping where service_type=? and car_model=?");
				stmt.setString(1, rs.getString(4));
				stmt.setString(2, rs.getString(2).split(",")[0].trim()+ " "+rs.getString(2).split(",")[1].trim());
				ResultSet rs1= stmt.executeQuery();
				while (rs1.next()) {
		        	basicServiceArray =rs1.getString(1).split(",");
		            }
		        for (int i=0;i<basicServiceArray.length;i++) {
		        	partMap = new HashMap<Integer,Integer>();
		        	int currentBaiscServiceid = Integer.parseInt(basicServiceArray[i]);
		        	stmt = connection.prepareStatement("select part_required,part_quantity_required from partrequired_car_model where basicserviceid =? and TRIM(car_model) =?");
		        	stmt.setInt(1, currentBaiscServiceid);
		        	stmt.setString(2, rs.getString(2).split(",")[0].trim()+" "+rs.getString(2).split(",")[1].trim());
		        	ResultSet rs3= stmt.executeQuery();
		        	while (rs3.next()) {
		        		if (!partMap.containsKey(rs3.getInt(1))) {
		        			partMap.put(rs3.getInt(1), rs3.getInt(2));
		        		} else {
		        			partMap.put(rs3.getInt(1), partMap.get(rs3.getInt(1))+rs3.getInt(2));
		        		}
		        	}
		        	for (Map.Entry m: partMap.entrySet()) {
		        		stmt= connection.prepareStatement("Update inventory set CURRENT_QUANTITY = CURRENT_QUANTITY-? where PARTID=? and CENTERID=? and CAR_MODEL=?");
		        		stmt.setInt(1, (int)m.getValue());
		        		stmt.setInt(2, (int)m.getKey());
		        		stmt.setString(3, centerid);
		        		stmt.setString(4, cartype.split(",")[0].trim());
		        		stmt.executeUpdate();
		        	}
		        }
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
