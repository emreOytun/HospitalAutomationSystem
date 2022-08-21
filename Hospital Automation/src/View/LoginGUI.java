package View;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTabbedPane;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

import Helper.*;
import Model.Doctor;
import Model.HeadDoctor;
import Model.Patience;

public class LoginGUI extends JFrame {

	private JPanel w_pane;
	private JTextField field_patienceId;
	private JTextField field_doctorId;
	private JPasswordField field_patiencePass;
	private JPasswordField passwordField_doctorPass;
	private DBConnection conn = new DBConnection();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setResizable(false);
		setTitle("Hospital Management System ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		w_pane = new JPanel();
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel label_logo = new JLabel(new ImageIcon(getClass().getResource("logoHospital1.png")));
		label_logo.setBounds(196, 24, 87, 64);
		w_pane.add(label_logo);
		
		JLabel label_welcome = new JLabel("Welcome to the Hospital Management System");
		label_welcome.setFont(new Font("Tahoma", Font.BOLD, 16));
		label_welcome.setBounds(54, 98, 432, 36);
		w_pane.add(label_welcome);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 142, 466, 211);
		w_pane.add(tabbedPane);
		
		JPanel w_patienceLogin =  new JPanel();
		w_patienceLogin.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Patience Login", null, w_patienceLogin, null);
		w_patienceLogin.setLayout(null);
		
		JLabel label_idNumber = new JLabel("Identification Number :");
		label_idNumber.setBounds(10, 22, 221, 28);
		label_idNumber.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		w_patienceLogin.add(label_idNumber);
		
		JLabel label_password = new JLabel("Password :");
		label_password.setFont(new Font("Tahoma", Font.BOLD, 18));
		label_password.setBounds(10, 85, 110, 28);
		w_patienceLogin.add(label_password);
		
		field_patienceId = new JTextField();
		field_patienceId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		field_patienceId.setBounds(241, 22, 199, 28);
		w_patienceLogin.add(field_patienceId);
		field_patienceId.setColumns(10);
		
		/* When sign up button is clicked. */
		JButton btn_signUp = new JButton("SIGN UP");
		btn_signUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI registerGUI = new RegisterGUI();
				registerGUI.setVisible(true);
				dispose();
			}
		});
		btn_signUp.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_signUp.setBounds(20, 123, 173, 51);
		w_patienceLogin.add(btn_signUp);
		
		JButton btn_loginPatience = new JButton("LOGIN");
		
		/* Patience login. */
		btn_loginPatience.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (field_patienceId.getText().length() == 0 || field_patiencePass.getText().length() == 0) {
					Helper.showMessage("fill");
				}
				
				else {
					try {
						Connection con = conn.connDB();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");
						
						boolean isLoggedIn = false;
						while (rs.next()) {
							
							/* Check if a doctor is logged in. */
							if (field_patienceId.getText().equals(rs.getString("identity_number")) && field_patiencePass.getText().equals(rs.getString("password"))) {
								
								/* Check if the register type is patience. */
								if (rs.getString("type").equals("patience")) {
									System.out.println("AAAAAAA");
									
									Patience patience = new Patience();
									patience.setId(rs.getInt("id"));
									patience.setName(rs.getString("name"));
									patience.setPassword(rs.getString("password"));
									patience.setIdentity_number(rs.getString("identity_number"));
									patience.setType(rs.getString("type"));
									
									/* Closing one screen and opening another (Surfing around GUIs) */
									PatienceGUI patienceGUI = new PatienceGUI(patience);
									patienceGUI.setVisible(true);
									dispose();	/* Close this screen */
									
									isLoggedIn = true;
								}
								
							}
							
						}
						
						if (!isLoggedIn) {
							Helper.showMessage("Invalid id or password !");
						}
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_loginPatience.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_loginPatience.setBounds(256, 123, 173, 51);
		w_patienceLogin.add(btn_loginPatience);
		
		field_patiencePass = new JPasswordField();
		field_patiencePass.setBounds(149, 85, 226, 28);
		w_patienceLogin.add(field_patiencePass);
		
		JPanel w_doctorLogin = new JPanel();
		w_doctorLogin.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Doctor Login", null, w_doctorLogin, null);
		w_doctorLogin.setLayout(null);
		
		JLabel label_idNumber2 = new JLabel("Identification Number :");
		label_idNumber2.setFont(new Font("Tahoma", Font.BOLD, 18));
		label_idNumber2.setBounds(10, 22, 221, 28);
		w_doctorLogin.add(label_idNumber2);
		
		field_doctorId = new JTextField();
		field_doctorId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		field_doctorId.setColumns(10);
		field_doctorId.setBounds(241, 22, 199, 28);
		w_doctorLogin.add(field_doctorId);
		
		JLabel label_password2 = new JLabel("Password :");
		label_password2.setFont(new Font("Tahoma", Font.BOLD, 18));
		label_password2.setBounds(10, 85, 110, 28);
		w_doctorLogin.add(label_password2);
		
		passwordField_doctorPass = new JPasswordField();
		passwordField_doctorPass.setBounds(145, 85, 226, 28);
		w_doctorLogin.add(passwordField_doctorPass);
		
		JButton btn_loginDoctor = new JButton("LOGIN");
		btn_loginDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (field_doctorId.getText().length() == 0 || passwordField_doctorPass.getText().length() == 0) {	/* Check if the fields are filled */
					Helper.showMessage("fill");
				} 
				else {
					try {
						Connection con = conn.connDB();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");
						
						boolean isLoggedIn = false;
						while (rs.next()) {
							
							/* Check if a doctor is logged in. */
							if (field_doctorId.getText().equals(rs.getString("identity_number")) && passwordField_doctorPass.getText().equals(rs.getString("password"))) {
								
								/* Check if the doctor is headDoctor. */
								if (rs.getString("type").equals("headDoctor")) {
									HeadDoctor headDoctor = new HeadDoctor();
									headDoctor.setId(rs.getInt("id"));
									headDoctor.setName(rs.getString("name"));
									headDoctor.setPassword(rs.getString("password"));
									headDoctor.setIdentity_number(rs.getString("identity_number"));
									headDoctor.setType(rs.getString("type"));
									
									/* Closing one screen and opening another (Surfing around GUIs) */
									HeadDoctorGUI hGUI = new HeadDoctorGUI(headDoctor);
									hGUI.setVisible(true);
									dispose();	/* Close this screen */
									
									isLoggedIn = true;
								}
								
								/* Check if the doctor is normal doctor. */
								else if (rs.getString("type").equals("doctor")) {
									Doctor doctor = new Doctor();
									doctor.setId(rs.getInt("id"));
									doctor.setName(rs.getString("name"));
									doctor.setPassword(rs.getString("password"));
									doctor.setIdentity_number(rs.getString("identity_number"));
									doctor.setType(rs.getString("type"));
									
									DoctorGUI dGUI = new DoctorGUI(doctor);
									dGUI.setVisible(true);
									dispose();
								
									isLoggedIn = true;
								}
								
							}
							
						}
						
						if (!isLoggedIn) {
							Helper.showMessage("Invalid id or password !");
						}
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		btn_loginDoctor.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_loginDoctor.setBounds(45, 123, 363, 51);
		w_doctorLogin.add(btn_loginDoctor);
	
	}
}
