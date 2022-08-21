package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Clinic;
import Model.HeadDoctor;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class UpdateClinicGUI extends JFrame {

	private JPanel contentPane;
	private JTextField field_clinicName;
	private static Clinic clinic;
	private static HeadDoctor headDoctor;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateClinicGUI frame = new UpdateClinicGUI(clinic, headDoctor);
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
	public UpdateClinicGUI(Clinic clinic, HeadDoctor headDoctor) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 225, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1_4 = new JLabel("Clinic Name:");
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_4.setBounds(10, 10, 122, 30);
		contentPane.add(lblNewLabel_1_4);
		
		field_clinicName = new JTextField();
		field_clinicName.setText(clinic.getName());
		field_clinicName.setColumns(10);
		field_clinicName.setBounds(10, 37, 138, 19);
		contentPane.add(field_clinicName);
		
		JButton btn_updateClinic = new JButton("UPDATE");
		btn_updateClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	// Update clinic
				if (Helper.confirm("sure")) {
					try {
						headDoctor.updateClinic(clinic.getId(), field_clinicName.getText());
						Helper.showMessage("success");
						dispose();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btn_updateClinic.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_updateClinic.setBounds(10, 66, 191, 30);
		contentPane.add(btn_updateClinic);
	}

}
