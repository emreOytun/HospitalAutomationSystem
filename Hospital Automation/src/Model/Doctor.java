package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.Helper;

public class Doctor extends User {
	Connection con = conn.connDB();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	public Doctor() {
		super();
	}
	
	public Doctor(int id, String identity_number, String name, String password, String type) {
		super(id, identity_number, name, password, type);
	}
	
	public boolean addWhour(int doctor_id, String doctor_name, String wdate) throws SQLException {
		int key = 0;
		String query = "INSERT INTO workHour(doctor_id,doctor_name,wdate) VALUES (?,?,?)";
		
		
		boolean isAdded = false;
		try {
			st = con.createStatement();
			
			/* Check if the same wdate is added into this doctor before. */
			rs = st.executeQuery("SELECT * FROM workhour WHERE status = 'a' AND doctor_id = " + doctor_id + " AND wdate = '" + wdate + "'");
			boolean isAddedBefore = false;
			while(rs.next()) {
				isAddedBefore = true;
				break;
			}
			
			if (isAddedBefore) {
				Helper.showMessage("This work hour is added before !");
			} else {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, doctor_id);
				preparedStatement.setString(2, doctor_name);
				preparedStatement.setString(3, wdate);
				
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
	
	public boolean deleteWhour(int id) throws SQLException {
		String query = "DELETE FROM workhour WHERE id = ?";
		boolean isAdded = false;
		
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			
			preparedStatement.setInt(1, id);
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
	
}
