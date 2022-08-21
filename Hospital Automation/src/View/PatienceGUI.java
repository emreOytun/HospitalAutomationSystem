package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Helper.Item;
import Model.Appointment;
import Model.Clinic;
import Model.Doctor;
import Model.HeadDoctor;
import Model.Patience;
import Model.User;
import Model.Whour;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class PatienceGUI extends JFrame {

	private JPanel w_pane;
	private static Patience patience = new Patience();
	private HeadDoctor headDoctor = new HeadDoctor();	// To use getClinicList function of headDoctor.
	
	private int selectedDoctorID = 0;
	private String selectedDoctorName = null;
	
	/* Components for table_doctor. */
	private JTable table_doctor;
	private DefaultTableModel doctorModel = null;
	private Object[] doctorData = null;
	
	/* Components for table_whour. */
	private JTable table_whour;
	private DefaultTableModel whourModel = null;
	private Object[] whourData = null;
	
	/* Components for table_appoint. */
	private JTable table_appoint;
	private DefaultTableModel appointModel = null;
	private Object[] appointData = null;
	private Appointment appoint = new Appointment();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatienceGUI frame = new PatienceGUI(patience);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public PatienceGUI(Patience patience) throws SQLException {
		
		/********** Creating doctor table. ********/
		
		/* Make all columns non-editable. */
		doctorModel = new DefaultTableModel() {
		
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		/* Set columns for table */
		Object[] colNames = new Object[2];
		colNames[0] = "ID";
		colNames[1] = "Name";
		doctorModel.setColumnIdentifiers(colNames);		
	
		/* Program will add data to table when a clinic is selected in the JComboBox(select_clinic). */
		doctorData = new Object[2];
		
		/*************** Creating whour table. ***********************/
		
		whourModel = new DefaultTableModel() {
		
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		Object[] whourColNames = new Object[2];
		whourColNames[0] = "ID";
		whourColNames[1] = "Date";
		whourModel.setColumnIdentifiers(whourColNames);		
	
		whourData = new Object[2];
		
		/********************** Creating appointment table for the patience. ******************************/
		appointModel = new DefaultTableModel() {
			
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		Object[] appointColNames = new Object[3];
		appointColNames[0] = "ID";
		appointColNames[1] = "Doctor";
		appointColNames[2] = "Date";
		appointModel.setColumnIdentifiers(appointColNames);		
	
		appointData = new Object[3];
		createAppointTableModel(patience.getId());
		
		/****************************************************************/
		
		setResizable(false);
		setTitle("Hospital Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome " + patience.getName());
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setBounds(10, 9, 357, 29);
		w_pane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("EXIT");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton.setBounds(594, 10, 128, 29);
		w_pane.add(btnNewButton);
		
		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 73, 716, 377);
		w_pane.add(w_tab);
		
		JPanel w_appointment = new JPanel();
		w_appointment.setBackground(Color.WHITE);
		w_tab.addTab("Appointment System", null, w_appointment, null);
		w_appointment.setLayout(null);
		
		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(10, 34, 280, 306);
		w_appointment.add(w_scrollDoctor);
		
		table_doctor = new JTable(doctorModel);
		w_scrollDoctor.setViewportView(table_doctor);
		
		JLabel lblNewLabel_1 = new JLabel("Doctor List :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(10, 10, 107, 17);
		w_appointment.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_4 = new JLabel("Clinic Name :");
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_4.setBounds(300, 10, 122, 30);
		w_appointment.add(lblNewLabel_1_4);
		
		JComboBox select_clinic = new JComboBox();
		select_clinic.setBounds(300, 45, 150, 30);
		
		/* Add items into the comboBox. */
		select_clinic.addItem("--Choose a clinic--"); // As DEFAULT ComboBox Value
		
		ArrayList<Clinic> clinic_list = headDoctor.getClinicList();	// Get clinic list from the headDoctor function.
		for (int i = 0; i < clinic_list.size(); i++) {
			select_clinic.addItem(new Item(clinic_list.get(i).getId(), clinic_list.get(i).getName()));
		}
		
		/* Listen to the JComboBox and detect the change. */
		select_clinic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				/* If a clinic is selected(means selectedIndex is not 0(default option index)), then update the doctor list. */
				if (select_clinic.getSelectedIndex() != 0) {
					
					JComboBox c = (JComboBox) e.getSource();
					Item item = (Item) c.getSelectedItem(); 
					System.out.println(item.getKey() + "-" + item.getValue());
					
					/* Clear the table before updating it. */
					DefaultTableModel clearingModel = (DefaultTableModel) table_doctor.getModel();
					clearingModel.setRowCount(0);
					
					/* Update the table. */
					try {
						/* Get doctor list using the clinic id. */
						ArrayList<Doctor> doctor_list = headDoctor.getClinicDoctorList(item.getKey());
						for (int i = 0; i < doctor_list.size(); i++) {
							doctorData[0] = doctor_list.get(i).getId();
							doctorData[1] = doctor_list.get(i).getName();
							doctorModel.addRow(doctorData);
						}
						
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				} 
				
				/* If there are not any clinics selected. */
				else {
					DefaultTableModel clearingModel = (DefaultTableModel) table_doctor.getModel();
					clearingModel.setRowCount(0);
				}
				
			}
			
		});
		
		w_appointment.add(select_clinic);
		
		JLabel lblNewLabel_1_4_1 = new JLabel("Select Doctor :");
		lblNewLabel_1_4_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_4_1.setBounds(300, 110, 122, 30);
		w_appointment.add(lblNewLabel_1_4_1);
		
		JButton btnNewButton_1 = new JButton("Select");
		
		/* When select doctor button is clicked...*/
		btnNewButton_1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				int selRow = table_doctor.getSelectedRow();
				
				if (selRow >= 0) {
					String value = table_doctor.getModel().getValueAt(selRow, 0).toString();
					int doctor_id = Integer.parseInt(value);
					
					DefaultTableModel clearingModel = (DefaultTableModel) table_whour.getModel();
					clearingModel.setRowCount(0);
					
					try {
						ArrayList<Whour> whour_list = patience.getWhourList(doctor_id);
						
						for (int i = 0; i < whour_list.size(); i++) {
							whourData[0] = whour_list.get(i).getId();
							whourData[1] = whour_list.get(i).getWdate();
							whourModel.addRow(whourData);
						}
						
						selectedDoctorID = doctor_id;
						selectedDoctorName = table_doctor.getModel().getValueAt(selRow, 1).toString();
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				
				} else {
					Helper.showMessage("Please select a doctor !");
					
					/* Clear the table since there is no selected doctor...*/
					DefaultTableModel clearingModel = (DefaultTableModel) table_whour.getModel();
					clearingModel.setRowCount(0);
				}
			}
		});
		
		
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_1.setBounds(300, 144, 150, 30);
		w_appointment.add(btnNewButton_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Time List : ");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(460, 10, 241, 17);
		w_appointment.add(lblNewLabel_1_1);
		
		JScrollPane w_scrollWhour = new JScrollPane();
		w_scrollWhour.setBounds(460, 34, 241, 306);
		w_appointment.add(w_scrollWhour);
		
		table_whour = new JTable(whourModel);
		w_scrollWhour.setViewportView(table_whour);
		
		JLabel lblNewLabel_1_4_1_1 = new JLabel("Appointment");
		lblNewLabel_1_4_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_4_1_1.setBounds(300, 218, 122, 30);
		w_appointment.add(lblNewLabel_1_4_1_1);
		
		JButton btn_addAppoint = new JButton("Make appointment");
		
		/* To make an appointment, when the button is clicked... */
		btn_addAppoint.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				int selRow = table_whour.getSelectedRow();
				
				if (selRow >= 0) {
					String selectedDate = table_whour.getModel().getValueAt(selRow, 1).toString();
					
					try {
						boolean control = patience.addAppointment(selectedDoctorID, patience.getId(), selectedDoctorName, patience.getName(), selectedDate);
						
						if (control) {
							Helper.showMessage("success");
							
							/* If an appointment is successful, then make this date 'p' in whour table. */
							patience.updateWhourStatus(selectedDoctorID, selectedDate);	
							updateWhourTableModel(selectedDoctorID);
							updateAppointTableModel(patience.getId());
						}
						
						else {
							Helper.showMessage("fail");
						}
					
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				} 
				
				else {
					Helper.showMessage("Please select an appointment date !");
				}
				
			}
		});
		
		btn_addAppoint.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btn_addAppoint.setBounds(300, 252, 150, 30);
		w_appointment.add(btn_addAppoint);
		
		JPanel w_appoint = new JPanel();
		w_tab.addTab("My Appointments", null, w_appoint, null);
		w_appoint.setLayout(null);
		
		JScrollPane w_scrollAppoint = new JScrollPane();
		w_scrollAppoint.setBounds(10, 10, 691, 330);
		w_appoint.add(w_scrollAppoint);
		
		table_appoint = new JTable(appointModel);
		w_scrollAppoint.setViewportView(table_appoint);
	}
	
	
	public void updateWhourTableModel(int doctor_id) throws SQLException {
		DefaultTableModel clearingModel = (DefaultTableModel) table_whour.getModel();
		clearingModel.setRowCount(0);
		
		ArrayList<Whour> whour_list = patience.getWhourList(doctor_id);
		
		for (int i = 0; i < whour_list.size(); i++) {
			whourData[0] = whour_list.get(i).getId();
			whourData[1] = whour_list.get(i).getWdate();
			whourModel.addRow(whourData);
		}	
	}
	
	private void createAppointTableModel(int patience_id) throws SQLException {
		ArrayList<Appointment> appointment_list = appoint.getAppointmentList(patience_id);
		
		for (int i = 0; i < appointment_list.size(); i++) {
			appointData[0] = appointment_list.get(i).getId();
			appointData[1] = appointment_list.get(i).getDoctorName();
			appointData[2] = appointment_list.get(i).getAppDate();
			appointModel.addRow(appointData);
		}
		
	}
	
	public void updateAppointTableModel(int patience_id) throws SQLException {
		DefaultTableModel clearingModel = (DefaultTableModel) table_appoint.getModel();
		clearingModel.setRowCount(0);
		
		createAppointTableModel(patience_id);	
	}
	
}
