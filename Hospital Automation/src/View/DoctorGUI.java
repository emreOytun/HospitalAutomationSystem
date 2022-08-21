package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.Doctor;
import Model.User;
import Model.Whour;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import com.toedter.calendar.JDateChooser;

import Helper.Helper;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class DoctorGUI extends JFrame {
	
	private JPanel w_pane;
	private static Doctor doctor = new Doctor();
	private DefaultTableModel whourModel;
	private Object[] whourData = null;
	private JTable table_whour;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorGUI frame = new DoctorGUI(doctor);
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
	public DoctorGUI(Doctor doctor) throws SQLException {
		
		/* Create whour table model. */
		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID:";
		colWhour[1] = "Date:";
		whourModel.setColumnIdentifiers(colWhour);
		
		/* Add data to the table. */
		whourData = new Object[2];
		ArrayList<Whour> wHour_list = doctor.getWhourList(doctor.getId());
		for (int i = 0; i < wHour_list.size(); i++) {
			whourData[0] = wHour_list.get(i).getId();
			whourData[1] = wHour_list.get(i).getWdate();
			whourModel.addRow(whourData);
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
		
		JLabel lblNewLabel = new JLabel("Welcome " + doctor.getName());
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setBounds(10, 10, 357, 29);
		w_pane.add(lblNewLabel);
		
		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 76, 716, 377);
		w_pane.add(w_tab);
		
		JPanel w_workHour = new JPanel();
		w_tab.addTab("Work Hours", null, w_workHour, null);
		w_workHour.setLayout(null);
		
		JDateChooser select_date = new JDateChooser();
		select_date.setBounds(10, 10, 130, 20);
		w_workHour.add(select_date);
		
		JComboBox select_time = new JComboBox();
		select_time.setFont(new Font("Tahoma", Font.BOLD, 11));
		select_time.setModel(new DefaultComboBoxModel(new String[] {"10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:30", "14:00", "14:30", "15:00", "15:30"}));
		select_time.setBounds(146, 10, 92, 20);
		w_workHour.add(select_time);
		
		JButton btn_addWhour = new JButton("ADD");
		btn_addWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/* Check if the date string is empty or not.*/
				if (select_date.getDate() == null) {
					Helper.showMessage("Please enter a valid date !");
				} else {
					/* Use SimpleDateFormat to format our date object coming from the JDateChooser. */
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String date = sdf.format(select_date.getDate());
					
					String time = " " + select_time.getSelectedItem().toString() + ":00";
					String selectedDate = date + time;
					
					try {
						boolean control = doctor.addWhour(doctor.getId(), doctor.getName(), selectedDate);
						if (control) {
							Helper.showMessage("success");
							updateWhourTableModel(doctor);
							
						} else {
							Helper.showMessage("fail");
						}
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				
				
				
			}
		});
		btn_addWhour.setFont(new Font("Tahoma", Font.BOLD, 11));
		btn_addWhour.setBounds(248, 10, 92, 20);
		w_workHour.add(btn_addWhour);
		
		JScrollPane w_scrollWhour = new JScrollPane();
		w_scrollWhour.setBounds(10, 40, 691, 300);
		w_workHour.add(w_scrollWhour);
		
		table_whour = new JTable(whourModel);
		w_scrollWhour.setViewportView(table_whour);
		
		JButton btn_deleteWhour = new JButton("DELETE");
		
		/* Delete the selected date. */
		btn_deleteWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/* Get selected row from the table and check if a row is selected. */
				int selRow = table_whour.getSelectedRow();
				if (selRow >= 0) {
					/* If a row is selected, then get the ID of the whour. */
					String selectedRow = table_whour.getModel().getValueAt(selRow, 0).toString();
					int selID = Integer.parseInt(selectedRow);
					
					try {
						boolean control = doctor.deleteWhour(selID);
						if (control) {
							Helper.showMessage("success");
							updateWhourTableModel(doctor);
						} else {
							Helper.showMessage("fail");
						}
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				} else {
					Helper.showMessage("Please select a date !");
				}
			}
		});
		btn_deleteWhour.setFont(new Font("Tahoma", Font.BOLD, 11));
		btn_deleteWhour.setBounds(609, 10, 92, 20);
		w_workHour.add(btn_deleteWhour);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(46, 10, 2, 2);
		w_pane.add(scrollPane);
		
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
	}

	public void updateWhourTableModel(Doctor doctor) throws SQLException {
		DefaultTableModel clearingModel = (DefaultTableModel) table_whour.getModel();
		clearingModel.setRowCount(0);	// clear all rows.
		
		ArrayList<Whour> wHour_list = doctor.getWhourList(doctor.getId());
		for (int i = 0; i < wHour_list.size(); i++) {
			whourData[0] = wHour_list.get(i).getId();
			whourData[1] = wHour_list.get(i).getWdate();
			whourModel.addRow(whourData);
		}
	}

}
