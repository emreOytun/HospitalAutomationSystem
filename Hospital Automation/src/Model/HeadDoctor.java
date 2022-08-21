package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HeadDoctor extends User {
	Connection con = conn.connDB();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	public HeadDoctor(int id, String identity_number, String name, String password, String type) {
		super(id, identity_number, name, password, type);
	}
	
	public HeadDoctor() {}
	
	public ArrayList<User> getDoctorList() throws SQLException {
		ArrayList<User> list = new ArrayList<>();
		User obj;
		
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM user WHERE type = 'doctor'");	// Fetch registers whose type is 'doctor' from the 'user' table.
		
			while(rs.next()) {	// Create objects using the data.
				obj = new User(rs.getInt("id"), rs.getString("identity_number"), rs.getString("name"), rs.getString("password"), rs.getString("type"));
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
	
	public boolean addDoctor(String identity_number, String password, String name) throws SQLException {
		String query = "INSERT INTO user" + "(identity_number,password,name,type) VALUES" + "(?,?,?,?)";
		boolean isAdded = false;
		
		try {
			st = con.createStatement();
			
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, identity_number);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, name);
			preparedStatement.setString(4, "doctor");
			
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
	
	public boolean deleteDoctor(int id) throws SQLException {
		String query = "DELETE FROM user WHERE id = ?";
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
	
	public boolean updateDoctor(int id, String identity_number, String password, String name) throws SQLException {
		String query = "UPDATE user SET name = ?,identity_number=?,password=? WHERE id = ?";
		boolean isAdded = false;
		
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, identity_number);
			preparedStatement.setString(3, password);
			preparedStatement.setInt(4, id);
			
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
	
	public ArrayList<Clinic> getClinicList() throws SQLException {
		ArrayList<Clinic> list = new ArrayList<>();
		Clinic obj;
		
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM clinic");	// Fetch registers whose type is 'doctor' from the 'user' table.
		
			while(rs.next()) {	// Create objects using the data.
				obj = new Clinic(rs.getInt("id"), rs.getString("name"));
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
	
	public boolean addClinic(String name) throws SQLException {
		String query = "INSERT INTO clinic" + "(name) VALUES" + "(?)";
		boolean isAdded = false;
		
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			
			preparedStatement.setString(1, name);
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
	
	public boolean deleteClinic(int id) throws SQLException {
		String query = "DELETE FROM clinic WHERE id = ?";
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
	
	public boolean updateClinic(int id, String name) throws SQLException {
		String query = "UPDATE clinic SET name = ? WHERE id = ?";
		boolean isAdded = false;
		
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, id);
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
	
	public Clinic getClinic(int id) {
		Clinic c = new Clinic(); 
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM clinic WHERE id =" + id);
			while (rs.next()) {
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
			}
			
			st.close();
			rs.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	
		return c;
	}
	
	public boolean addEmployee(int user_id, int clinic_id) throws SQLException {
		String query = "INSERT INTO employee" + "(clinic_id, user_id) VALUES" + "(?,?)";
		boolean isAdded = false;
		int count = 0;
		
		try {
			st = con.createStatement();
			
			rs = st.executeQuery("SELECT * FROM employee WHERE clinic_id=" + clinic_id + " AND user_id=" + user_id);	// Prevent adding same register.
			while (rs.next()) {
				count++;
			}
			
			if (count == 0) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, clinic_id);
				preparedStatement.setInt(2, user_id);
				
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
	
	public ArrayList<Doctor> getClinicDoctorList(int clinic_id) throws SQLException {
		ArrayList<Doctor> list = new ArrayList<>();
		Doctor obj;
		
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT u.id,u.identity_number,u.password,u.name,u.type  FROM employee e LEFT JOIN user u ON e.user_id = u.id WHERE clinic_id = " + clinic_id);	// Fetch registers whose type is 'doctor' from the 'user' table.
		
			while(rs.next()) {	// Create objects using the data.
				obj = new Doctor(rs.getInt("u.id"), rs.getString("u.identity_number"), rs.getString("u.name"), rs.getString("u.password"), rs.getString("u.type"));
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
