package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Patience;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

	private JPanel w_pane;
	private JTextField field_name;
	private JPasswordField field_password;
	private JTextField field_identityNo;
	private Patience patience = new Patience();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		setResizable(false);
		setTitle("Hospital Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 330);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Name Surname:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(10, 10, 122, 30);
		w_pane.add(lblNewLabel_1);
		
		field_name = new JTextField();
		field_name.setColumns(10);
		field_name.setBounds(10, 37, 262, 30);
		w_pane.add(field_name);
		
		JLabel lblNewLabel_1_1 = new JLabel("Identity No:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1.setBounds(10, 66, 122, 30);
		w_pane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Password:");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_2.setBounds(10, 138, 122, 30);
		w_pane.add(lblNewLabel_1_2);
		
		field_password = new JPasswordField();
		field_password.setBounds(10, 173, 262, 30);
		w_pane.add(field_password);
		
		field_identityNo = new JTextField();
		field_identityNo.setColumns(10);
		field_identityNo.setBounds(10, 98, 262, 30);
		w_pane.add(field_identityNo);
		
		JButton btn_register = new JButton("Register");
		
		/* When register button is pressed. - Register process */
		btn_register.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if (field_identityNo.getText().length() == 0 || field_password.getText().length() == 0 || field_name.getText().length() == 0) {
					Helper.showMessage("Fill in the blanks first !");				
				} 
				
				else {
					try {
						boolean control = patience.register(field_identityNo.getText(), field_password.getText(), field_name.getText());
						
						/* If register is successful, then return back to the loginGUI. */
						if (control) {
							Helper.showMessage("success");
							LoginGUI loginGUI = new LoginGUI();
							loginGUI.setVisible(true);
							dispose();
						
						} else {
							Helper.showMessage("fail");
						}
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			
			}
		});
		
		btn_register.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_register.setBounds(10, 216, 262, 30);
		w_pane.add(btn_register);
		
		JButton btnNewButton_1_1 = new JButton("Return back");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI loginGUI = new LoginGUI();
				loginGUI.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton_1_1.setBounds(10, 256, 262, 30);
		w_pane.add(btnNewButton_1_1);
	}
}
