package Airport;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JButton;

public class AddWorker implements Methods {

	Connection conn = null;
	PreparedStatement state = null;
	ResultSet result = null;
	
	//JPanel
	JPanel addWorker = new JPanel();
	
	//JLabel
	JLabel lblFirstName = new JLabel("First Name:");
	JLabel lblLastName = new JLabel("Last Name:");
	JLabel lblAge = new JLabel("Age:");
	JLabel lblPhone = new JLabel("Phone:");
	JLabel lblUsername = new JLabel("Username:");
	JLabel lblPassword = new JLabel("Password:");
	
	
	//JTextField
	JTextField txtFirstName = new JTextField();
	JTextField txtAge = new JTextField();
	JTextField txtUsername = new JTextField();
	JTextField txtLastName = new JTextField();
	JTextField txtPhone = new JTextField();
	JTextField txtPassword = new JTextField();
	
	//JButton
	JButton btnAddWorker = new JButton("Add Worker");
	JButton btnEditWorker = new JButton("Edit Worker");
	JButton btnDeleteWorker = new JButton("Delete Worker");

	public AddWorker() {
		addWorker.setBorder(new TitledBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(153, 180, 209)), "Add Worker", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		addWorker.setVisible(true);
		addWorker.setBounds(12,215, 425, 200);
		addWorker.setLayout(null);
		
		//Add JLabels
		
		addWorker.add(lblFirstName);
		lblFirstName.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblFirstName.setBounds(12, 31, 106, 16);
		
		addWorker.add(lblLastName);
		lblLastName.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblLastName.setBounds(215, 32, 106, 16);
		
		addWorker.add(lblAge);
		lblAge.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblAge.setBounds(12, 60, 106, 16);
			
		addWorker.add(lblPhone);
		lblPhone.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblPhone.setBounds(215, 61, 106, 16);
		
		addWorker.add(lblUsername);
		lblUsername.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblUsername.setBounds(12, 95, 106, 16);
		
		addWorker.add(lblPassword);
		lblPassword.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblPassword.setBounds(215, 96, 106, 16);
		
		//Add TextFields
		
		addWorker.add(txtFirstName);
		txtFirstName.setColumns(10);
		txtFirstName.setBounds(100, 29, 95, 22);
			
		addWorker.add(txtAge);
		txtAge.setColumns(10);
		txtAge.setBounds(100, 61, 95, 22);
		
		addWorker.add(txtUsername);
		txtUsername.setColumns(10);
		txtUsername.setBounds(100, 93, 95, 22);
			
		addWorker.add(txtLastName);
		txtLastName.setColumns(10);
		txtLastName.setBounds(304, 30, 95, 22);
			
		addWorker.add(txtPhone);
		txtPhone.setColumns(10);
		txtPhone.setBounds(304, 61, 95, 22);
			
		addWorker.add(txtPassword);
		txtPassword.setColumns(10);
		txtPassword.setBounds(304, 93, 95, 22);
		
		//Add JButtons
		addWorker.add(btnAddWorker);
		btnAddWorker.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnAddWorker.setBounds(68, 145, 127, 25);
		btnAddWorker.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String fname = txtFirstName.getText();
				String lname = txtLastName.getText();
				String phone = txtPhone.getText();
				String username = txtUsername.getText();
				String password = txtPassword.getText();
				
				conn = ConnectDB.ConnectAirportFlights();
				
				if(fname.equals("")|| lname.equals("") || username.equals("") || password.equals("") || txtAge.equals("")){
					JOptionPane.showMessageDialog(null, "Please fill the text fields");
				}
				else{
					try {
						int age = Integer.parseInt(txtAge.getText());
						state = conn.prepareStatement("INSERT INTO Worker VALUES(?, ?, ?, ?, ?, ?)");
						state.setString(1, fname);
						state.setString(2, lname);
						state.setInt(3, age);
						state.setString(4, phone);
						state.setString(5, username);
						state.setString(6, password);
						state.execute();
						
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Worker is added ;)");
					ClearFields();
				}
			}
		});
		
		
		addWorker.add(btnDeleteWorker);
		btnDeleteWorker.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnDeleteWorker.setBounds(222, 145, 135, 25);
		btnDeleteWorker.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String item = txtUsername.getText();
				String username = null;
				conn = ConnectDB.ConnectAirportFlights();
				try {
					state = conn.prepareStatement("SELECT username from Worker");
					result = state.executeQuery();
					while(result.next()){
						username = result.getString("username");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				if(item.equals("")){
					JOptionPane.showMessageDialog(null, "Please fill the text field \"Username\"");
				}else if(!item.equals(username)){
					JOptionPane.showMessageDialog(null, "This \"username\" isn't exist");
				}else{
					try {
						state = conn.prepareStatement("DELETE FROM Worker WHERE username=?");
						state.setString(1, item);
						state.execute();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Worker Deleted !!!");
				}
				ClearFields();
				
			}
		});

	}

	@Override
	public void ClearFields() {
		txtFirstName.setText(null);
		txtLastName.setText(null);
		txtAge.setText(null);
		txtPhone.setText(null);
		txtUsername.setText(null);
		txtPassword.setText(null);	
	}
}
