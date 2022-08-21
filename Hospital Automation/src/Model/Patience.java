package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.Helper;

public class Patience extends User {
	Connection con = conn.connDB();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	public Patience() {
	}

	public Patience(int id, String identity_number, String name, String password, String type) {
		super(id, identity_number, name, password, type);
	}

	public boolean register(String identity_number, String password, String name) throws SQLException {
		int key = 0;
		String query = "INSERT INTO user(identity_number,password,name,type) VALUES (?,?,?,?)";
		
		
		boolean isAdded = false;
		try {
			st = con.createStatement();
			
			/* Check if the same patience register is added into this doctor before. */
			rs = st.executeQuery("SELECT * FROM user WHERE identity_number = '" + identity_number + "'");
			
			boolean isAddedBefore = false;
			while(rs.next()) {
				isAddedBefore = true;
				break;
			}
			
			if (isAddedBefore) {
				Helper.showMessage("This register is added before !");
			} else {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, identity_number);
				preparedStatement.setString(2, password);
				preparedStatement.setString(3, name);
				preparedStatement.setString(4, "patience");
				
				preparedStatement.executeUpdate();
				isAdded = true;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			st.close();
			preparedStatement.close();
		}
		
		return isAdded;
	}

	public ArrayList<Whour> getWhourList(int doctor_id) throws SQLException {
		ArrayList<Whour> list = new ArrayList<>();
		Whour obj;
		
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM workhour WHERE status = 'a' AND doctor_id = " + doctor_id);	// Fetch registers whose type is 'doctor' from the 'user' table.
		
			while(rs.next()) {	// Create objects using the data.
				obj = new Whour();
				obj.setId(rs.getInt("id"));
				obj.setDoctor_id(rs.getInt("doctor_id"));
				obj.setDoctor_name(rs.getString("doctor_name"));
				obj.setStatus(rs.getString("status"));
				obj.setWdate(rs.getString("wdate"));
				list.add(obj);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			st.close();
			rs.close();
		}
		
		return list;
	}
	
	public boolean addAppointment(int doctor_id, int patience_id, String doctor_name, String patience_name, String appDate) throws SQLException {
		int key = 0;
		String query = "INSERT INTO appointment(doctor_id,doctor_name,patience_id,patience_name,app_date) VALUES (?,?,?,?,?)";
		
		
		boolean isAdded = false;
		try {
			st = con.createStatement();
			
			/* Check if the same patience register is added into this doctor before. */
			rs = st.executeQuery("SELECT * FROM appointment WHERE app_date = '" + appDate + "'" + " AND doctor_id = " + doctor_id);
			
			boolean isAddedBefore = false;
			while(rs.next()) {
				isAddedBefore = true;
				break;
			}
			
			if (isAddedBefore) {
				Helper.showMessage("This appointment is taken before !");
			} 
			
			else {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, doctor_id);
				preparedStatement.setString(2, doctor_name);
				preparedStatement.setInt(3, patience_id);
				preparedStatement.setString(4, patience_name);
				preparedStatement.setString(5, appDate);
				
				preparedStatement.executeUpdate();
				isAdded = true;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			st.close();
			preparedStatement.close();
		}
		
		return isAdded;
	}
	
	public boolean updateWhourStatus(int doctor_id, String appDate) throws SQLException {
		int key = 0;
		String query = "UPDATE workhour SET status = ? WHERE doctor_id = ? AND wdate = ?";
		
		boolean isAdded = false;
		try {
		
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, "p");
			preparedStatement.setInt(2, doctor_id);
			preparedStatement.setString(3, appDate);
			
			preparedStatement.executeUpdate();
			isAdded = true;
				
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			st.close();
			preparedStatement.close();
		}
		
		return isAdded;
	}
	
}
