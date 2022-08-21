package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Appointment {
	private int id, doctorID, patienceID;
	private String doctorName, patienceName, appDate;

	DBConnection conn = new DBConnection();
	Connection con = conn.connDB();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
		
	public Appointment() {
	}

	public Appointment(int id, int doctorID, int patienceID, String doctorName, String patienceName, String appDate) {
		super();
		this.id = id;
		this.doctorID = doctorID;
		this.patienceID = patienceID;
		this.doctorName = doctorName;
		this.patienceName = patienceName;
		this.appDate = appDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDoctorID() {
		return doctorID;
	}

	public void setDoctorID(int doctorID) {
		this.doctorID = doctorID;
	}

	public int getPatienceID() {
		return patienceID;
	}

	public void setPatienceID(int patienceID) {
		this.patienceID = patienceID;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getPatienceName() {
		return patienceName;
	}

	public void setPatienceName(String patienceName) {
		this.patienceName = patienceName;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	
	public ArrayList<Appointment> getAppointmentList(int patience_id) throws SQLException {
		ArrayList<Appointment> list = new ArrayList<>();
		Appointment obj;
		
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM appointment WHERE patience_id = " + patience_id);	// Fetch registers whose type is 'doctor' from the 'user' table.
		
			while(rs.next()) {	// Create objects using the data.
				obj = new Appointment();
				obj.setId(rs.getInt("id"));
				obj.setDoctorID(rs.getInt("doctor_id"));
				obj.setDoctorName(rs.getString("doctor_name"));
				obj.setPatienceID(rs.getInt("patience_id"));
				obj.setPatienceName(rs.getString("patience_name"));
				obj.setAppDate(rs.getString("app_date"));
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
	
	public ArrayList<Appointment> getDoctorList(int doctor_id) throws SQLException {
		ArrayList<Appointment> list = new ArrayList<>();
		Appointment obj;
		
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM appointment WHERE patience_id = " + doctor_id);	// Fetch registers whose type is 'doctor' from the 'user' table.
		
			while(rs.next()) {	// Create objects using the data.
				obj = new Appointment();
				obj.setId(rs.getInt("id"));
				obj.setDoctorID(rs.getInt("doctor_id"));
				obj.setDoctorName(rs.getString("doctor_name"));
				obj.setPatienceID(rs.getInt("patience_id"));
				obj.setPatienceName(rs.getString("patience_name"));
				obj.setAppDate(rs.getString("app_date"));
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
