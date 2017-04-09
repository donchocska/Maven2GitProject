package Airport;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.SwingConstants;

import org.omg.CORBA.Bounds;


import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class GUI extends JFrame implements Methods{

	MyModel model = null;
	Connection conn = null;
	PreparedStatement state = null;
	ResultSet result = null;
	
	Table table = new Table();
	
	// Add Janel //

	JPanel panelLoginFrame = new JPanel();
	JPanel panelAdminFrame = new JPanel();
	JPanel panelWorkerFrame = new JPanel();
	JPanel tableField = new JPanel();

	
	//Add TextField //
	JTextField txtUsername = new JTextField();
	JTextField txtPassword = new JTextField();
	
	//Add JLabel //
	JLabel lblLoginTitle = new JLabel("Login");
	JLabel lblUserName = new JLabel("Username:");
	JLabel lblPassword = new JLabel("Password:");
	
	//Add JButton //
	
	JButton btnLogin = new JButton("Login");
	//JButton btnLogout = new JButton("Log Out");
	
	
	public GUI() {
		this.setVisible(true);
		this.setSize(900, 700);
		//this.setBounds(100, 100, 450, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new CardLayout(0, 0));
		this.setTitle("Airport Reservation Form");
		

		
		// Panel Login with functions
		getContentPane().add(panelLoginFrame, "LginForm");
		panelLoginFrame.setLayout(null);
		
		
		lblLoginTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginTitle.setFont(new Font("Verdana", Font.BOLD, 30));
		lblLoginTitle.setBounds(370, 131, 146, 45);
		panelLoginFrame.add(lblLoginTitle);
		
		
		lblUserName.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblUserName.setBounds(318, 208, 114, 45);
		panelLoginFrame.add(lblUserName);
		
		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Verdana", Font.PLAIN, 13));
		txtUsername.setColumns(10);
		txtUsername.setBounds(444, 220, 116, 22);
		panelLoginFrame.add(txtUsername);
		
		
		lblPassword.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblPassword.setBounds(318, 285, 97, 33);
		panelLoginFrame.add(lblPassword);
		
		txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Verdana", Font.PLAIN, 13));
		txtPassword.setBounds(444, 291, 116, 22);
		panelLoginFrame.add(txtPassword);
		
		
		btnLogin.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		btnLogin.setBounds(463, 356, 100, 27);
		panelLoginFrame.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String username = txtUsername.getText();
				String pass = String.valueOf(((JPasswordField) txtPassword).getPassword());
				boolean flag = false;
				conn = ConnectDB.ConnectAirportFlights();
					try {
						state = conn.prepareStatement(
								"SELECT username, password FROM Admin WHERE username=? AND password=?");
						state.setString(1, username);
						state.setString(2, pass);
						result = state.executeQuery();
						
						if (result.next()) {
							flag = true;
							panelLoginFrame.setVisible(false);
							panelWorkerFrame.setVisible(false);
							panelAdminFrame.setVisible(true);			
						
						}else if(flag==false){
						
						state = conn.prepareStatement(
								"SELECT username, password FROM Worker WHERE username=? AND password=?");
						state.setString(1, username);
						state.setString(2, pass);
						result = state.executeQuery();
						if(result.next()){
							panelLoginFrame.setVisible(false);
							panelWorkerFrame.setVisible(true);
							panelAdminFrame.setVisible(false);		
						}
						else{
						JOptionPane.showMessageDialog(null, "Incorect \"username\" or \"password\"");
						}
						}
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
				
			}
		});
		

		// ----------- Panel Admin ------------- //
		getContentPane().add(panelAdminFrame, "AdminFrame");
		panelAdminFrame.setLayout(null);
		
		//Panel Add Flight
		AddFlightPanel addflight = new AddFlightPanel();
		panelAdminFrame.add(addflight.addFlightPanel);
		
		//Panel Add Plane
		AddPlanePanel addplane = new AddPlanePanel();
		panelAdminFrame.add(addplane.addPlanePanel);
		
		//Add Worker Panel
		
		AddWorker addworker = new AddWorker();
		panelAdminFrame.add(addworker.addWorker);
		
		
		//TablePanel
		Table table = new Table();
		panelAdminFrame.add(table.tableField);
		//Search Panel
		panelAdminFrame.add(table.searchField);
		table.btnLogOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int response = JOptionPane.showConfirmDialog(null, "Do you want to EXIT", "Confirm",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (response == JOptionPane.NO_OPTION) {
					JOptionPane.showMessageDialog(null, "OK Keep Working");
				}
				if (response == JOptionPane.YES_OPTION) {	

					panelLoginFrame.setVisible(true);
					panelWorkerFrame.setVisible(false);
					panelAdminFrame.setVisible(false);	
					ClearFields();
				}
			}
		});
		
		
		// ---- Worlker Panel ----- //
		getContentPane().add(panelWorkerFrame, "WorkerFrame");
		panelWorkerFrame.setLayout(null);
		
		Reservations reservations = new Reservations();
		panelWorkerFrame.add(reservations.reservationsFrame);

		
		Table table1 = new Table();
		panelWorkerFrame.add(table1.tableField);
		panelWorkerFrame.add(table1.searchField);
		table1.btnLogOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int response = JOptionPane.showConfirmDialog(null, "Do you want to EXIT", "Confirm",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (response == JOptionPane.NO_OPTION) {
					JOptionPane.showMessageDialog(null, "OK Keep Working");
				}
				if (response == JOptionPane.YES_OPTION) {	

					panelLoginFrame.setVisible(true);
					panelWorkerFrame.setVisible(false);
					panelAdminFrame.setVisible(false);	
					ClearFields();
				}
			}
		});
		
	}
	@Override
	public void ClearFields() {
		txtUsername.setText(null);
		txtPassword.setText(null);
		
	}
	
	
}
