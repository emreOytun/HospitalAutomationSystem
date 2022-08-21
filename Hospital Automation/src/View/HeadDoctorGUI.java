package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Helper.Item;
import Model.Clinic;
import Model.Doctor;
import Model.HeadDoctor;
import Model.User;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Point;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class HeadDoctorGUI extends JFrame {
	
	static HeadDoctor headDoctor = new HeadDoctor();
	private JPanel w_pane;
	private JTextField field_doctorName;
	private JTextField field_doctorIdentity;
	private JTextField field_doctorPass;
	private JTextField field_doctorId;
	private JTable table_doctor;
	private DefaultTableModel doctorModel = null;
	private Object[] doctorData = null;
	private JTable table_clinic;
	private JTextField field_clinicName;
	private DefaultTableModel clinicModel = null;
	private Object[] clinicData = null;
	private JPopupMenu clinicPopupMenu;
	private JTable table_employee;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HeadDoctorGUI frame = new HeadDoctorGUI(headDoctor);
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
	public HeadDoctorGUI(HeadDoctor headDoctor) throws SQLException {
		
		/* Adding doctor table. */
		doctorModel = new DefaultTableModel() {
		
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int col) {
				return !(col == 0);
			}
		};
		
		/* Set columns for table */
		Object[] colNames = new Object[4];
		colNames[0] = "ID";
		colNames[1] = "Name";
		colNames[2] = "Identity No";
		colNames[3] = "Pswrd";
		doctorModel.setColumnIdentifiers(colNames);		
		
		/* Add data to table */
		doctorData = new Object[4];
		ArrayList<User> doctor_list = headDoctor.getDoctorList();
		
		for (int i = 0; i < doctor_list.size(); i++) {
			doctorData[0] = doctor_list.get(i).getId();
			doctorData[1] = doctor_list.get(i).getName();
			doctorData[2] = doctor_list.get(i).getIdentity_number();
			doctorData[3] = doctor_list.get(i).getPassword();
			doctorModel.addRow(doctorData);
		}
		
		setResizable(false);
		setTitle("Hospital Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		w_pane = new JPanel();
		w_pane.setBackground(new Color(255, 255, 255));
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome " + headDoctor.getName());
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setBounds(10, 20, 357, 29);
		w_pane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("EXIT");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton.setBounds(570, 23, 156, 29);
		w_pane.add(btnNewButton);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 76, 716, 377);
		w_pane.add(tabbedPane);
		
		JPanel w_doctorManagement = new JPanel();
		tabbedPane.addTab("Doctor Management", null, w_doctorManagement, null);
		w_doctorManagement.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Name Surname:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(549, 10, 122, 30);
		w_doctorManagement.add(lblNewLabel_1);
		
		field_doctorName = new JTextField();
		field_doctorName.setBounds(549, 37, 138, 19);
		w_doctorManagement.add(field_doctorName);
		field_doctorName.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Identity Number:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(549, 66, 138, 30);
		w_doctorManagement.add(lblNewLabel_1_1);
		
		field_doctorIdentity = new JTextField();
		field_doctorIdentity.setColumns(10);
		field_doctorIdentity.setBounds(549, 93, 138, 19);
		w_doctorManagement.add(field_doctorIdentity);
		
		JLabel lblNewLabel_1_2 = new JLabel("Password:");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_2.setBounds(549, 122, 122, 30);
		w_doctorManagement.add(lblNewLabel_1_2);
		
		field_doctorPass = new JTextField();
		field_doctorPass.setColumns(10);
		field_doctorPass.setBounds(549, 149, 138, 19);
		w_doctorManagement.add(field_doctorPass);
		
		/* Adding doctor to the database */
		JButton btnNewButton_1 = new JButton("ADD");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (field_doctorName.getText().length() == 0 || field_doctorIdentity.getText().length() == 0 || field_doctorPass.getText().length() == 0) {
					Helper.showMessage("fill");
				}
				else {
					try {
						boolean control = headDoctor.addDoctor(field_doctorIdentity.getText(), field_doctorPass.getText(), field_doctorName.getText());
						if (control) {
							Helper.showMessage("success");	// if doctor is added, print success message
							field_doctorIdentity.setText(null);	// clear text areas.
							field_doctorName.setText(null);
							field_doctorPass.setText(null);
							updateDoctorTableModel();
						}
					
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton_1.setBounds(547, 178, 140, 36);
		w_doctorManagement.add(btnNewButton_1);
		
		JLabel lblNewLabel_1_3 = new JLabel("User ID:");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_3.setBounds(549, 231, 122, 30);
		w_doctorManagement.add(lblNewLabel_1_3);
		
		field_doctorId = new JTextField();
		field_doctorId.setColumns(10);
		field_doctorId.setBounds(549, 258, 138, 19);
		w_doctorManagement.add(field_doctorId);
		
		JButton btnNewButton_1_1 = new JButton("DELETE");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (field_doctorId.getText().length() == 0) {
					Helper.showMessage("fill");
				}
				else {
					if (Helper.confirm("sure")) {	// Ask for confirm -popup confirm message(JOptionPane)-
						try {
							int selectedID = Integer.parseInt(field_doctorId.getText());
							boolean control = headDoctor.deleteDoctor(selectedID);
							
							if (control) {
								Helper.showMessage("success");
								field_doctorId.setText(null);
								updateDoctorTableModel();
							}
						
							
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton_1_1.setBounds(549, 287, 140, 36);
		w_doctorManagement.add(btnNewButton_1_1);
		
		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(10, 10, 534, 330);
		w_doctorManagement.add(w_scrollDoctor);
		
		table_doctor = new JTable(doctorModel);
		w_scrollDoctor.setViewportView(table_doctor);
		
		/* Detecting the ID of the selected row in the table. */
		table_doctor.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					field_doctorId.setText(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
				} catch(Exception ex) {}
			}
			
		});
		
		/* Updating the database when the table is changed. */
		table_doctor.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {	// It detects whether the table is changed.
				if (e.getType() == TableModelEvent.UPDATE) {	// If table is updated, then update the database.
					int selectID = Integer.parseInt(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
					String selectName = table_doctor.getValueAt(table_doctor.getSelectedRow(), 1).toString();
					String identity_no = table_doctor.getValueAt(table_doctor.getSelectedRow(), 2).toString();
					String password = table_doctor.getValueAt(table_doctor.getSelectedRow(), 3).toString();
					
					try {
						boolean control = headDoctor.updateDoctor(selectID, identity_no, password, selectName);
						if (control) {
							Helper.showMessage("success");
						} else {
							Helper.showMessage("fail");
						}
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block 
						e1.printStackTrace();
					}
				}
			}
			
		});
		
		JPanel w_clinic = new JPanel();
		w_clinic.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Clinics", null, w_clinic, null);
		w_clinic.setLayout(null);
		
		JScrollPane w_scrollClinic = new JScrollPane();
		w_scrollClinic.setBounds(10, 10, 260, 330);
		w_clinic.add(w_scrollClinic);
			
		/* Clinic model and clinic table : */
		clinicModel = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int col) {	// Override this method to disable to update the first col(id).
				return false;
			}
		};
		
		Object[] colClinic = new Object[2];
		colClinic[0] = "ID";
		colClinic[1] = "Name";
		clinicModel.setColumnIdentifiers(colClinic);
		
		/* Adding clinics to the JTable model. */
		clinicData = new Object[2];
		ArrayList<Clinic> clinics = headDoctor.getClinicList();
		for (int i = 0;  i < clinics.size(); i++) {
			clinicData[0] = clinics.get(i).getId();
			clinicData[1] = clinics.get(i).getName();
			clinicModel.addRow(clinicData);
		}
		
		/* Create popup menu for the table_clinic. */
		clinicPopupMenu = new JPopupMenu();
		JMenuItem updateOption = new JMenuItem("Update");
		JMenuItem deleteOption = new JMenuItem("Delete");
		clinicPopupMenu.add(updateOption);
		clinicPopupMenu.add(deleteOption);
		
		updateOption.addActionListener(new ActionListener() {	// When update option is selected.
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selected_clinicID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString());
				Clinic selectedClinic = headDoctor.getClinic(selected_clinicID);
				UpdateClinicGUI updateGUI = new UpdateClinicGUI(selectedClinic, headDoctor);
				updateGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				updateGUI.setVisible(true);
				
				updateGUI.addWindowListener(new WindowAdapter() {	// When updateGUI frame is closed, update clinic table.
					@Override
					public void windowClosed(WindowEvent e) {
						try {
							updateClinicTableModel();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
			}
			
		});
		
		deleteOption.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (Helper.confirm("sure")) {
					int selected_clinicID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString());
					try {
						if (headDoctor.deleteClinic(selected_clinicID)) {
							Helper.showMessage("success");
							updateClinicTableModel();
						}
						else {
							Helper.showMessage("fail");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
		});
		
		table_clinic = new JTable(clinicModel);
		table_clinic.setComponentPopupMenu(clinicPopupMenu);
		table_clinic.addMouseListener(new MouseAdapter() {	// Get the coordinates of the mouse when the mouse is pressed on the table_clinic. 
			@Override
			public void mousePressed(MouseEvent ev) {
				Point point = ev.getPoint();	// Get the point(coordinates of the mouse on the frame).
				int selectedRow = table_clinic.rowAtPoint(point); // Get the row at the selected point.
				table_clinic.setRowSelectionInterval(selectedRow, selectedRow);	// When the mouse is pressed right, then selected row of the table_clinic will be updated with helps of the MouseListener and Point.
			}
		});
		w_scrollClinic.setViewportView(table_clinic);
		
		JLabel lblNewLabel_1_4 = new JLabel("Clinic Name:");
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_4.setBounds(280, 10, 122, 30);
		w_clinic.add(lblNewLabel_1_4);
		
		field_clinicName = new JTextField();
		field_clinicName.setColumns(10);
		field_clinicName.setBounds(280, 37, 138, 19);
		w_clinic.add(field_clinicName);
		
		JButton btn_addClinic = new JButton("ADD");
		btn_addClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (field_clinicName.getText().length() == 0) {
					Helper.showMessage("fill");
				}
				else {
					try {
						boolean control = headDoctor.addClinic(field_clinicName.getText());
						if (control) {
							Helper.showMessage("success");
							field_clinicName.setText(null);
							updateClinicTableModel();
						} 
						else {
							Helper.showMessage("fail");
						}
					
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btn_addClinic.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_addClinic.setBounds(280, 66, 140, 30);
		w_clinic.add(btn_addClinic);
		
		JScrollPane w_scrollEmployee = new JScrollPane();
		w_scrollEmployee.setBounds(429, 10, 272, 330);
		w_clinic.add(w_scrollEmployee);
		
		table_employee = new JTable();
		w_scrollEmployee.setViewportView(table_employee);
		
		JComboBox select_doctor = new JComboBox();
		select_doctor.setBounds(280, 260, 138, 30);
		doctor_list = headDoctor.getDoctorList();
		for (int i = 0; i < doctor_list.size(); i++) {	// Since doctor_id is needed along with the doctor_name in the comboBox, we add items into the box with Item class we created before. It works like hashmap(key-value). 
			select_doctor.addItem(new Item(doctor_list.get(i).getId(), doctor_list.get(i).getName()));	// When an object is added into the JComboBox, toString method of this object will be called.
		}
		select_doctor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Item item = (Item) select_doctor.getSelectedItem();
				System.out.println(item.getKey() + " : " + item.getValue());	// Debug testing
			}
			
		});
		w_clinic.add(select_doctor);
		
		/* Adding employee to the selected clinic. */
		JButton btn_addEmployee = new JButton("ADD");
		
		btn_addEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	// When addEmployee button is clicked, add employee to the clinic.
				int selectedRow = table_clinic.getSelectedRow();
				
				if (selectedRow >= 0) {	// Since we don't use fieldArea to show selected row data as we did in doctor_pane, we should check if the user has already selected a row.
					int selClinicID = Integer.parseInt(table_clinic.getModel().getValueAt(selectedRow, 0).toString());
					Item selItem = (Item) select_doctor.getSelectedItem();
					
					try {
						boolean control = headDoctor.addEmployee(selItem.getKey(), selClinicID);
						if (control) {
							Helper.showMessage("success");
						} else {
							Helper.showMessage("fail");
						}
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				else {
					Helper.showMessage("Please select a clinic first !");
				}
			}
		});
		btn_addEmployee.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_addEmployee.setBounds(278, 300, 140, 30);
		w_clinic.add(btn_addEmployee);
		
		/* Emploee Table and Table Model : */
		DefaultTableModel employeeModel = new DefaultTableModel();
		Object[] colEmployee = new Object[2];
		colEmployee[0] = "ID";
		colEmployee[1] = "Name";
		employeeModel.setColumnIdentifiers(colEmployee);
		Object employeeData[] = new Object[2];
		
		
		/* Showing doctors when a clinic is selected. */
		JButton btn_employeeSelect = new JButton("SELECT");
		
		btn_employeeSelect.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table_clinic.getSelectedRow();
				
				if (selectedRow >= 0) {
					int selClinicID = Integer.parseInt(table_clinic.getModel().getValueAt(selectedRow, 0).toString());
					
					DefaultTableModel clearingModel = (DefaultTableModel) table_employee.getModel();
					clearingModel.setRowCount(0);
					
					try {
						ArrayList<Doctor> doctors_in_clinic = headDoctor.getClinicDoctorList(selClinicID);
						for (int i = 0; i < doctors_in_clinic.size(); i++) {
							employeeData[0] = doctors_in_clinic.get(i).getId();
							employeeData[1] = doctors_in_clinic.get(i).getName();
							employeeModel.addRow(employeeData);
						}
						
						table_employee.setModel(employeeModel);
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					
				} else {
					Helper.showMessage("Please select a clinic !");
				}
			}
			
		});
		btn_employeeSelect.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_employeeSelect.setBounds(280, 166, 140, 30);
		w_clinic.add(btn_employeeSelect);
		
		JLabel lblNewLabel_1_4_1 = new JLabel("Clinic Name:");
		lblNewLabel_1_4_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_4_1.setBounds(280, 129, 122, 30);
		w_clinic.add(lblNewLabel_1_4_1);
		
	}
	
	public void updateDoctorTableModel() throws SQLException {
		DefaultTableModel clearingModel = (DefaultTableModel) table_doctor.getModel();
		clearingModel.setRowCount(0);	// clear all rows.
		
		ArrayList<User> doctor_list = headDoctor.getDoctorList();
		for (int i = 0; i < doctor_list.size(); i++) {
			doctorData[0] = doctor_list.get(i).getId();
			doctorData[1] = doctor_list.get(i).getName();
			doctorData[2] = doctor_list.get(i).getIdentity_number();
			doctorData[3] = doctor_list.get(i).getPassword();
			doctorModel.addRow(doctorData);
		}
	}
	
	public void updateClinicTableModel() throws SQLException {
		DefaultTableModel clearingModel = (DefaultTableModel) table_clinic.getModel();
		clearingModel.setRowCount(0);	// clear all rows.
		
		ArrayList<Clinic> clinic_list = headDoctor.getClinicList();
		for (int i = 0; i < clinic_list.size(); i++) {
			clinicData[0] = clinic_list.get(i).getId();
			clinicData[1] = clinic_list.get(i).getName();
			clinicModel.addRow(clinicData);
		}
	}
}
