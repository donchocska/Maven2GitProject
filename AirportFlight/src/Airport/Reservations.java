package Airport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;

public class Reservations extends AddFlightPanel {

	MyModel model = null;
	Connection conn = null;
	ResultSet result = null;
	PreparedStatement state = null;

	JPanel reservationsFrame = new JPanel();

	// Combo
	JComboBox comboFlightID = new JComboBox();
	JComboBox comboClientID = new JComboBox();
	JComboBox comboReservationID = new JComboBox();

	// Labels
	JLabel lblNewLabel = new JLabel("First Name:");
	JLabel lblLastName = new JLabel("Last Name:");
	JLabel lblPhone = new JLabel("Phone:");
	JLabel lblFlightId = new JLabel("Flight ID:");

	// TextFields

	public JTextField txtFirstName;
	public JTextField txtLastName;
	public JTextField txtPhone = new JTextField();;

	// JButton
	JButton btnAddReservation = new JButton("Add Reservation");
	JButton btnRemoveReservation = new JButton("Remove Reservation");
	JButton btnRemoveClient = new JButton("Remove Client");
	JButton btnReservationexistingUser = new JButton("Reservation/Existing User");

	// -----------------Constructor --------------//
	public Reservations() {
		reservationsFrame.setVisible(true);
		reservationsFrame.setBounds(12, 215, 425, 200);
		reservationsFrame.setLayout(null);

		// Add Combo
		reservationsFrame.add(comboFlightID);
		comboFlightID.setBounds(116, 132, 92, 25);

		// Add Labels
		reservationsFrame.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblNewLabel.setBounds(12, 27, 92, 16);

		reservationsFrame.add(lblLastName);
		lblLastName.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblLastName.setBounds(12, 64, 92, 16);

		reservationsFrame.add(lblPhone);
		lblPhone.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblPhone.setBounds(12, 103, 92, 16);

		reservationsFrame.add(lblFlightId);
		lblFlightId.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblFlightId.setBounds(12, 135, 92, 16);

		// Add Border
		TitledBorder border = new TitledBorder(null, "Add Reservation", TitledBorder.CENTER, 0);
		reservationsFrame.setBorder(border);

		// Add TextFields

		txtFirstName = new JTextField();
		txtFirstName.setBounds(116, 25, 116, 25);
		reservationsFrame.add(txtFirstName);
		txtFirstName.setColumns(10);

		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		txtLastName.setBounds(116, 61, 116, 25);
		reservationsFrame.add(txtLastName);
		
		reservationsFrame.add(txtPhone);
		txtPhone.setColumns(10);
		txtPhone.setBounds(116, 94, 116, 25);
	
		try {
			MaskFormatter maskphone = new MaskFormatter("HHHHHHHHHH");
			JFormattedTextField txtPhone = new JFormattedTextField(maskphone);
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}


		// Add Buttons

		reservationsFrame.add(btnAddReservation);
		btnAddReservation.setBounds(244, 24, 169, 25);
		btnAddReservation.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String item = (String) comboFlightID.getSelectedItem();
				String fname = txtFirstName.getText();
				String lname = txtLastName.getText();
				String phone = txtPhone.getText();
				String flight = (String) comboFlightID.getSelectedItem();
				conn = ConnectDB.ConnectAirportFlights();
				if (fname.equals("") || lname.equals("") || phone.equals("") || item.equals("Flight_ID")) {

					JOptionPane.showMessageDialog(null, "Please fill all Text Fields and chose Flight_ID");

				} else {
					try {
						state = conn.prepareStatement("INSERT INTO Client VALUES(null, ?, ?, ?)");
						state.setString(1, fname);
						state.setString(2, lname);
						state.setString(3, phone);
						state.execute();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

					conn = ConnectDB.ConnectAirportFlights();
					try {
						state = conn.prepareStatement(
								"INSERT INTO Reservation VALUES(null, ?,Select id_client from Client where firnst_name=? and phone=?)");
						state.setString(1, flight);
						state.setString(2, fname);
						state.setString(3, phone);
						state.execute();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					ClearFields();
					FillComboFlight_ID();
					FillComboClientID();
					FillComboReservationID();
					JOptionPane.showMessageDialog(null, "Reservation Added");
				}
			}
		});

		reservationsFrame.add(btnRemoveReservation);
		btnRemoveReservation.setBounds(244, 61, 169, 25);
		btnRemoveReservation.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String item = (String) comboReservationID.getSelectedItem();
				conn = ConnectDB.ConnectAirportFlights();
				if (item.equals("Reservation_ID")) {
					JOptionPane.showMessageDialog(null, "Please chose Reservation_ID");
				} else {
					try {
						state = conn.prepareStatement("DELETE FROM Reservation WHERE id_reservation=?");
						state.setString(1, item);
						state.execute();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					FillComboReservationID();
					JOptionPane.showMessageDialog(null, "Reservation Removed");
				}
			}
		});

		reservationsFrame.add(btnRemoveClient);
		btnRemoveClient.setBounds(244, 94, 169, 25);
		btnRemoveClient.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String item = (String) comboClientID.getSelectedItem();
				conn = ConnectDB.ConnectAirportFlights();
				if (item.equals("Client_ID")) {
					JOptionPane.showMessageDialog(null, "Chose \"CLient_ID\"");
				} else {
					int response = JOptionPane.showConfirmDialog(null, "Do you want to delete client?", "Confirm",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(response == JOptionPane.NO_OPTION){
						FillComboClientID();
					}if(response == JOptionPane.YES_OPTION){
						
					try {
						state = conn.prepareStatement("DELETE FROM Client WHERE id_client=?");
						state.setString(1, item);
						state.execute();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					FillComboClientID();
					JOptionPane.showMessageDialog(null, "Client Deleted");
				}
			}
			}
		});

		reservationsFrame.add(btnReservationexistingUser);
		btnReservationexistingUser.setBounds(220, 132, 193, 25);
		btnReservationexistingUser.setToolTipText("Chose \"Client_ID\" and \" Flight_ID\"");
		btnReservationexistingUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Integer client = Integer.parseInt((String) comboClientID.getSelectedItem());
				Integer flight = Integer.parseInt((String) comboFlightID.getSelectedItem());
				conn = ConnectDB.ConnectAirportFlights();
				if(client.equals("Client_ID") || flight.equals("Flight_ID")){
					JOptionPane.showMessageDialog(null, "Please chose \"Client_ID\" and \" Flight_ID\"");
				}
				else{
					try {
						state = conn.prepareStatement("INSERT INTO Reservation VALUES(null, ?, ?)");
						state.setInt(1, flight);
						state.setInt(2, client);
						state.execute();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					FillComboReservationID();
					FillComboClientID();
					FillComboFlight_ID();
					JOptionPane.showMessageDialog(null, "Reservation added");
				}
			}
		});

		// Add Combo
		reservationsFrame.add(comboClientID);
		comboClientID.setBounds(116, 165, 92, 22);

		reservationsFrame.add(comboReservationID);
		comboReservationID.setBounds(220, 165, 193, 22);

		FillComboFlight_ID();
		FillComboClientID();
		FillComboReservationID();

	}

	//Methods
	
	public void FillComboFlight_ID() {
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

	@Override
	public void ClearFields() {
		txtFirstName.setText(null);
		txtLastName.setText(null);
		txtPhone.setText(null);

	}

	public void FillComboReservationID() {

		comboReservationID.removeAllItems();
		comboReservationID.addItem("Reservation_ID");
		conn = ConnectDB.ConnectAirportFlights();
		try {
			state = conn.prepareStatement("SELECT id_reservation FROM Reservation");
			result = state.executeQuery();
			while (result.next()) {
				comboReservationID.addItem(result.getString("id_reservation"));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void FillComboClientID() {
		comboClientID.removeAllItems();
		comboClientID.addItem("Client_ID");
		conn = ConnectDB.ConnectAirportFlights();
		try {
			state = conn.prepareStatement("SELECT id_client FROM Client");
			result = state.executeQuery();
			while (result.next()) {
				comboClientID.addItem(result.getString("id_client"));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

}
