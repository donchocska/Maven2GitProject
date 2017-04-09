package Airport;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.UIManager;

public class AddFlightPanel extends AddPlanePanel{

	JPanel addFlightPanel = new JPanel();
	
	//JLabel //
	
	JLabel lblFlightNumber = new JLabel("Flight Number:");
	JLabel lblStartPoint = new JLabel("Start Point:");
	JLabel lblStartDate = new JLabel("Start Date:");
	JLabel lblPlaneId = new JLabel("Plane ID:");
	JLabel lblEndPoint = new JLabel("End Point:");
	JLabel lblEndDate = new JLabel("End Date:");
	
	
	//JTextField //
	JTextField txtFlightNumber = new JTextField();
	JTextField txtStartPoint = new JTextField();
	JTextField txtStartDate = new JTextField();
	JTextField txtEndPoint = new JTextField();
	JTextField txtEndDate = new JTextField();
	
	//JButton //
	JButton btnAddFlight = new JButton("Add Fligt");
	JButton btnEditFlight = new JButton("Edit Fligt");
	JButton btnDeleteFlight = new JButton("Delete Fligt");
	
	//Add Combo
	JComboBox comboFlightID = new JComboBox();
	
	public AddFlightPanel() {
		addFlightPanel.setBorder(new TitledBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(153, 180, 209)), "Add FLight", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		addFlightPanel.setLayout(null);
		addFlightPanel.setBounds(12, 13, 425, 200);
		
		//Add JLabel //
		
		addFlightPanel.add(lblFlightNumber);
		lblFlightNumber.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblFlightNumber.setBounds(12, 28, 106, 16);
			
		addFlightPanel.add(lblStartPoint);
		lblStartPoint.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblStartPoint.setBounds(12, 64, 106, 16);		
		
		addFlightPanel.add(lblStartDate);
		lblStartDate.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblStartDate.setBounds(12, 95, 106, 16);
				
		addFlightPanel.add(lblPlaneId);
		lblPlaneId.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblPlaneId.setBounds(240, 28, 106, 16);
		
		addFlightPanel.add(lblEndPoint);
		lblEndPoint.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblEndPoint.setBounds(240, 64, 106, 16);
		
		addFlightPanel.add(lblEndDate);
		lblEndDate.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblEndDate.setBounds(240, 95, 106, 16);
		
		
		
		addFlightPanel.add(comboPlaneID);
		
		//Add JTextField //
		addFlightPanel.add(txtFlightNumber);
		txtFlightNumber.setBounds(120, 26, 95, 22);
		txtFlightNumber.setColumns(10);
		
		addFlightPanel.add(txtStartPoint);
		txtStartPoint.setColumns(10);
		txtStartPoint.setBounds(120, 62, 95, 22);
		
		addFlightPanel.add(txtStartDate);
		txtStartDate.setColumns(10);
		txtStartDate.setBounds(120, 95, 95, 22);
		
		addFlightPanel.add(txtEndPoint);
		txtEndPoint.setColumns(10);
		txtEndPoint.setBounds(318, 62, 95, 22);
		
		addFlightPanel.add(txtEndDate);
		txtEndDate.setColumns(10);
		txtEndDate.setBounds(318, 93, 95, 22);
		

		//Add Combo
		addFlightPanel.add(comboPlaneID);
		comboPlaneID.setBounds(318, 26, 95, 22);
		FillComboPlaneID();
		
		comboFlightID.setBounds(284, 168, 125, 22);
		addFlightPanel.add(comboFlightID);
		FillComboFlightID();
		
		//Add Buttons
		
		addFlightPanel.add(btnAddFlight);
		btnAddFlight.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnAddFlight.setBounds(12, 130, 125, 25);
		btnAddFlight.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String number = txtFlightNumber.getText();
				String startpoint = txtStartPoint.getText();
				String endpoint = txtEndPoint.getText();
				String startdate= txtStartDate.getText();
				String enddate = txtEndDate.getText();
				
				
				conn = ConnectDB.ConnectAirportFlights();
				if(number.equals("") || startpoint.equals("") || endpoint.equals("") || startdate.equals(null) || enddate.equals(null)){
					JOptionPane.showMessageDialog(null, "Please fill text fields");
				}
				if(comboPlaneID.getSelectedItem().equals("Plane_ID")){
					JOptionPane.showMessageDialog(null, "Please chose \"Plane ID\"");
				}
				else{
					Integer plane_id = Integer.parseInt((String) comboPlaneID.getSelectedItem());
					
					try {
						state = conn.prepareStatement("INSERT INTO Flight VALUES(null, ?, ?, ?, ?, ?, ?)");
						state.setInt(1, plane_id);
						state.setString(2, number);
						state.setString(3, startpoint);
						state.setString(4, endpoint);
						state.setString(5, startdate);
						state.setString(6, enddate);
						state.execute();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					try {
						state = conn.prepareStatement("INSERT INTO Flight_Plane VALUES(Select id_flight from Flight where flight_number=?, ?)");
						state.setString(1, number);
						state.setInt(2, plane_id);
						state.execute();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					ClearFields();
					FillComboPlaneID();
					FillComboFlightID();
					JOptionPane.showMessageDialog(null, "Flight Added");
					
				}
						
			}
		});
		
		
		addFlightPanel.add(btnEditFlight);
		btnEditFlight.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnEditFlight.setBounds(147, 130, 125, 25);
		btnEditFlight.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String number = txtFlightNumber.getText();
				String startpoint = txtStartPoint.getText();
				String endpoint = txtEndPoint.getText();
				String startdate= txtStartDate.getText();
				String enddate = txtEndDate.getText();
				
				String item = (String) comboFlightID.getSelectedItem();
				conn = ConnectDB.ConnectAirportFlights();
				
				if(item.equals("Flight_ID")){
					JOptionPane.showMessageDialog(null, "Select Flight_ID");
				}else{
				try {
					Integer plane_id = Integer.parseInt((String) comboPlaneID.getSelectedItem());
					
					state = conn.prepareStatement("UPDATE Flight SET id_plane=?, Flight_number=?, Start_point=?, End_point=?, Start_date=?, End_date=? WHERE id_flight=?");
					state.setInt(1, plane_id);
					state.setString(2, number);
					state.setString(3, startpoint);
					state.setString(4, endpoint);
					state.setString(5,startdate);
					state.setString(6, enddate);
					state.setString(7, item); 
					state.execute();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Flight Updated successfully");
				ClearFields();
			}
			}
		});
		
		
		
				
		addFlightPanel.add(btnDeleteFlight);
		btnDeleteFlight.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnDeleteFlight.setBounds(284, 130, 125, 25);
		btnDeleteFlight.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String item = (String) comboFlightID.getSelectedItem();
				conn = ConnectDB.ConnectAirportFlights();
				
				if(item.equals("Flight_ID")){
					JOptionPane.showMessageDialog(null, "Chose \"Flight_ID\"");
				}else{
				try {
					state = conn.prepareStatement("Delete FROM Flight WHERE id_flight=?");
					state.setString(1, item);
					state.execute();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				FillComboFlightID();
				JOptionPane.showMessageDialog(null, "Flight Deleted ;)");
			}
			}
		});
		
	}
	
	@Override
public void ClearFields(){
	txtFlightNumber.setText(null);
	txtStartDate.setText(null);
	txtStartPoint.setText(null);
	txtEndDate.setText(null);
	txtEndPoint.setText(null);
		
}
	
	public void FillComboFlightID() {
		comboFlightID.removeAllItems();
		comboFlightID.addItem("Flight_ID");
		conn = ConnectDB.ConnectAirportFlights();
		try {
			state = conn.prepareStatement("SELECT id_flight FROM Flight");
			result = state.executeQuery();
			while (result.next()) {
				comboFlightID.addItem(result.getString("id_flight"));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
}
