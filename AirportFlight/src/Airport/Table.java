package Airport;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.*;
import java.awt.print.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;


public class Table extends JFrame { //             ******************** This is Table + Search Panels  *************************** //

	MyModel model = null;
	Connection conn = null;
	ResultSet result = null;
	PreparedStatement state = null;
	

	// JPanel
	JPanel searchField = new JPanel();
	JPanel tableField = new JPanel();

	// JTable
	JTable table = new JTable();
	JScrollPane scroll = new JScrollPane(table);

	// Combo
	JComboBox comboShow = new JComboBox();
	JComboBox comboSearch = new JComboBox();

	// Buttons
	JButton btnShow = new JButton("Show");
	JButton btnSearch = new JButton("Search");
	JButton btnLogOut = new JButton("Log Out");
	JButton btnRefresh = new JButton("Refresh");
	JButton btnPrint = new JButton("Print");
	
	// Border
	TitledBorder border = new TitledBorder(null, "Search", TitledBorder.CENTER, 0);

	// Combos
	JComboBox comboFlightID = new JComboBox();
	JComboBox comboReservationID = new JComboBox();
	JComboBox comboClientID = new JComboBox();
	JComboBox comboPlaneID = new JComboBox();
	

	public Table() {

		// Frame action
		this.setVisible(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Search");
		this.setBounds(0, 0, 900, 700);
		getContentPane().setLayout(null);

		// Search actions
		getContentPane().add(searchField);
		searchField.setBounds(440, 215, 425, 200);
		searchField.setLayout(null);
		searchField.setBorder(border);

		// Add Combos

		searchField.add(comboShow);
		comboShow.setBounds(12, 29, 218, 25);
		comboShow.addItem("Show Flights");
		comboShow.addItem("Show Planes");
		comboShow.addItem("Show Clients");
		comboShow.addItem("Show Reservations");
		comboShow.addItem("Show Flight\\Reservation");
		comboShow.addItem("Show Workers");

		searchField.add(comboSearch);
		comboSearch.setBounds(12, 82, 218, 25);
		comboSearch.addItem("Search Flight");
		comboSearch.addItem("Search Plane");
		comboSearch.addItem("Search Reservation");
		comboSearch.addItem("Search Client\\Reservation");

		// Add Buttons
		searchField.add(btnShow);
		btnShow.setBounds(242, 28, 100, 25);
		btnShow.setFont(new Font("Verdana", Font.PLAIN, 14));

		btnShow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String item = (String) comboShow.getSelectedItem();
				if (item.equals("Show Flights")) {
					ShowFlights();
				}
				if (item.equals("Show Planes")) {
					ShowPlanes();
				}
				if (item.equals("Show Clients")) {
					ShowClients();
				}
				if (item.equals("Show Reservations")) {
					ShowReservations();
				}
				if (item.equals("Show Flight\\Reservation")) {
					ShowFlightReservation();
				}
				if(item.equals("Show Workers")){
					ShowWorkers();
				}
				FillComboClientID();
				FillComboPlaneID();
				FillComboFlight_ID();
				FillComboReservationID();
			}
		});

		searchField.add(btnSearch);
		btnSearch.setBounds(242, 81, 100, 25);
		btnSearch.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String item = (String) comboSearch.getSelectedItem();
				if(item.equals("Search Flight")){
					SearchFlight();
				}
				if(item.equals("Search Plane")){
					SearchPlane();
				}
				if(item.equals("Search Reservation")){
					SearchReservation();
				}
				if(item.equals("Search Client\\Reservation")){
					SearchClientReservation();
				}
				FillComboClientID();
				FillComboPlaneID();
				FillComboFlight_ID();
				FillComboReservationID();
			}
		});

		searchField.add(btnLogOut);
		btnLogOut.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnLogOut.setBounds(242, 162, 100, 25);

		searchField.add(btnRefresh);
		btnRefresh.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnRefresh.setBounds(242, 124, 100, 25);	
		btnRefresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FillComboClientID();
				FillComboFlight_ID();
				FillComboPlaneID();
				FillComboReservationID();
			}
		});
		
		searchField.add(btnPrint);
		btnPrint.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnPrint.setBounds(344, 162, 73, 25);
		
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MessageFormat header = new MessageFormat("Airport Flight Form");
				MessageFormat footer = new MessageFormat("");
				
				try {
					table.print(JTable.PrintMode.FIT_WIDTH, header, footer);
				} catch (PrinterException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		// Combo actions
		searchField.add(comboFlightID);
		comboFlightID.setBounds(12, 164, 118, 22);
		
		searchField.add(comboReservationID);
		comboReservationID.setBounds(12, 120, 118, 22);	
		
		searchField.add(comboClientID);
		comboClientID.setBounds(142, 120, 88, 22);
		
		searchField.add(comboPlaneID);
		comboPlaneID.setBounds(142, 164, 88, 22);

		// Table actions

		getContentPane().add(tableField);
		tableField.setLayout(new GridLayout(1, 2));
		tableField.setBounds(12, 430, 860, 210);
		tableField.add(scroll);
		table.setModel(Model());

		FillComboFlight_ID();
		FillComboClientID();
		FillComboReservationID();
		FillComboPlaneID();
	}

	public MyModel Model() {

		conn = ConnectDB.ConnectAirportFlights();
		try {
			state = conn.prepareStatement("SELECT * FROM Flight");
			result = state.executeQuery();
			model = new MyModel(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;

	}

	// Methods Show

	public void ShowFlights() {
		conn = ConnectDB.ConnectAirportFlights();
		try {
			state = conn.prepareStatement("SELECT * FROM Flight");
			result = state.executeQuery();
			table.setModel(new MyModel(result));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ShowPlanes() {
		conn = ConnectDB.ConnectAirportFlights();
		try {
			state = conn.prepareStatement("SELECT * FROM Plane");
			result = state.executeQuery();
			table.setModel(new MyModel(result));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ShowClients() {
		conn = ConnectDB.ConnectAirportFlights();
		try {
			state = conn.prepareStatement("SELECT * FROM Client");
			result = state.executeQuery();
			table.setModel(new MyModel(result));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ShowReservations() {
		conn = ConnectDB.ConnectAirportFlights();
		try {
			state = conn.prepareStatement(
					"SELECT firnst_name, last_name, phone, id_reservation, id_flight FROM Client Join Reservation ON Client.id_client = Reservation.id_client");
			result = state.executeQuery();
			table.setModel(new MyModel(result));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ShowFlightReservation() {
		String item = (String) comboFlightID.getSelectedItem();
		conn = ConnectDB.ConnectAirportFlights();
		if (item.equals("Flight_ID")) {
			JOptionPane.showMessageDialog(null, "Chose \"id_flight\" from combo");
		} else {
			try {
				state = conn.prepareStatement(
						"SELECT Flight.id_flight, START_POINT ,END_POINT ,START_DATE ,END_DATE , Reservation.ID_RESERVATION , Reservation.ID_CLIENT, FIRNST_NAME ,LAST_NAME From FLIGHT Join RESERVATION ON Flight.id_flight = Reservation.id_flight Join CLIENT ON CLIENT .ID_CLIENT = RESERVATION.ID_CLIENT AND Flight.id_flight ="
								+ item + "");
				result = state.executeQuery();
				table.setModel(new MyModel(result));
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public void ShowWorkers() {
		conn = ConnectDB.ConnectAirportFlights();
		try {
			state = conn.prepareStatement("SELECT * FROM Worker");
			result = state.executeQuery();
			table.setModel(new MyModel(result));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Methods Search
	public void SearchFlight() {
		String item = (String) comboFlightID.getSelectedItem();
		conn = ConnectDB.ConnectAirportFlights();
		if (item.equals("Flight_ID")) {
			JOptionPane.showMessageDialog(null, "Chose \"Flight_ID\" from combo");
		} else {
			try {
				state = conn.prepareStatement(
						"SELECT Flight.ID_FLIGHT, FLIGHT_NUMBER,START_POINT, END_POINT, START_DATE, END_DATE, Plane.ID_PLANE,  places, name FROM Flight, Flight_Plane, Plane Where Flight.id_flight = Flight_Plane.id_flight AND Flight_Plane.id_plane = Plane.id_plane AND Flight.id_flight="
								+ item + "");
				result = state.executeQuery();
				table.setModel(new MyModel(result));
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void SearchPlane(){
		String item = (String) comboPlaneID.getSelectedItem();
		conn = ConnectDB.ConnectAirportFlights();
		if (item.equals("Plane_ID")) {
			JOptionPane.showMessageDialog(null, "Chose \"Plane_ID\" from combo");
		} else {
			try {
				state = conn.prepareStatement(
						"SELECT Plane.ID_PLANE,  places, name, Flight.ID_FLIGHT , FLIGHT_NUMBER ,START_POINT, END_POINT, START_DATE ,END_DATE FROM Flight, Flight_Plane, Plane Where Flight.id_flight = Flight_Plane.id_flight AND Flight_Plane.id_plane = Plane.id_plane and Plane.id_plane="
								+ item + "");
				result = state.executeQuery();
				table.setModel(new MyModel(result));
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void SearchReservation(){
		String item = (String) comboReservationID.getSelectedItem();
		conn = ConnectDB.ConnectAirportFlights();
		if (item.equals("Reservation_ID")) {
			JOptionPane.showMessageDialog(null, "Chose \"Reservation_ID\" from combo");
		} else {
			try {
				state = conn.prepareStatement(
						"select Reservation.id_reservation, Client.id_client, FIRNST_NAME ,LAST_NAME ,PHONE ,ID_FLIGHT  from Reservation JOIN Client ON Client.id_client = Reservation.id_client AND Reservation.id_reservation="
								+ item + "");
				result = state.executeQuery();
				table.setModel(new MyModel(result));
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void SearchClientReservation(){
		String item = (String) comboClientID.getSelectedItem();

		if (item.equals("Client_ID")) {
			JOptionPane.showMessageDialog(null, "Chose \"Client_ID\" from combo");
		} else {
			conn = ConnectDB.ConnectAirportFlights();
			try {
				state = conn.prepareStatement(
						"SELECT CLIENT.ID_CLIENT, Reservation.id_reservation, Flight.id_flight, FIRNST_NAME ,LAST_NAME ,PHONE , START_POINT, END_POINT, START_DATE, END_DATE  FROM CLIENT JOIN RESERVATION ON CLIENT.ID_CLIENT = RESERVATION.ID_CLIENT JOIN FLIGHT ON RESERVATION.ID_FLIGHT = FLIGHT.ID_FLIGHT AND Client.id_client="
								+ item + "");
				result = state.executeQuery();
				table.setModel(new MyModel(result));
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}


	// Methods Combo
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
	
	public void FillComboPlaneID() {
		comboPlaneID.removeAllItems();
		comboPlaneID.addItem("Plane_ID");
		conn = ConnectDB.ConnectAirportFlights();
		try {
			state = conn.prepareStatement("SELECT id_plane FROM PLANE");
			result = state.executeQuery();
			while (result.next()) {
				comboPlaneID.addItem(result.getString("id_plane"));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
}
