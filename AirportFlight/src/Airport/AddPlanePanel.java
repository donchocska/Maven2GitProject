package Airport;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class AddPlanePanel implements Methods {

	Connection conn = null;
	PreparedStatement state = null;
	ResultSet result = null;

	// JPanel
	JPanel addPlanePanel = new JPanel();

	// JLabel
	JLabel lblPlaneModel = new JLabel("Plane Model:");
	JLabel lblPlaces = new JLabel("Places:");
	JLabel lblPlaneID = new JLabel("Plane ID:");
	// JTextField
	JTextField txtPlaneModel = new JTextField();
	JTextField txtPlaces = new JTextField();

	// JComboBox //
	JComboBox comboPlaneID = new JComboBox();

	// JButton
	JButton btnAddPlane = new JButton("Add Plane");
	JButton btnEditPlane = new JButton("Edit Plane");
	JButton btnDeletePlane = new JButton("Delete Plane");

	public AddPlanePanel() {
		addPlanePanel.setBorder(new TitledBorder(
				new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP,
						null, new Color(0, 0, 0)),
				"Add Plane", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));

		addPlanePanel.setVisible(true);
		addPlanePanel.setBounds(440, 13, 425, 200);
		addPlanePanel.setLayout(null);

		// Add Label

		addPlanePanel.add(lblPlaneModel);
		lblPlaneModel.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblPlaneModel.setBounds(83, 26, 106, 16);

		addPlanePanel.add(lblPlaces);
		lblPlaces.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblPlaces.setBounds(83, 67, 106, 16);

		addPlanePanel.add(lblPlaneID);
		lblPlaneID.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblPlaneID.setBounds(83, 102, 106, 16);

		// Add TextFields

		addPlanePanel.add(txtPlaneModel);
		txtPlaneModel.setColumns(10);
		txtPlaneModel.setBounds(201, 24, 158, 22);

		addPlanePanel.add(txtPlaces);
		txtPlaces.setColumns(10);
		txtPlaces.setBounds(201, 65, 158, 22);
		comboPlaneID.setFont(new Font("Verdana", Font.PLAIN, 13));

		// Add Combo
		addPlanePanel.add(comboPlaneID);
		comboPlaneID.setBounds(201, 100, 158, 20);
		FillComboPlaneID();

		// Add JButton
		addPlanePanel.add(btnAddPlane);
		btnAddPlane.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnAddPlane.setBounds(12, 130, 125, 25);
		btnAddPlane.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = txtPlaneModel.getText();
				String places = txtPlaces.getText();
				conn = ConnectDB.ConnectAirportFlights();

				if (name.equals("") || places.equals("")) {
					JOptionPane.showMessageDialog(null, "Please Insert values for Plane Model and Places ");
				} else {
					try {
						state = conn.prepareStatement("INSERT INTO Plane VALUES(null, ?, ?)");
						state.setString(1, name);
						state.setString(2, places);
						state.execute();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					FillComboPlaneID();
					ClearFields();
					JOptionPane.showMessageDialog(null, "Plane is added successfully");
				}
			}
		});

		addPlanePanel.add(btnEditPlane);
		btnEditPlane.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnEditPlane.setBounds(149, 130, 125, 25);
		btnEditPlane.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = txtPlaneModel.getText();
				String places = txtPlaces.getText();

				String item = (String) comboPlaneID.getSelectedItem();

				conn = ConnectDB.ConnectAirportFlights();

				if (item.equals("Plane ID") || name.equals("") || places.equals("")) {
					JOptionPane.showMessageDialog(null,
							"Please Chose \"PlaneID\" and Insert values for Plane Model and Places ");
				} else {
					try {
						state = conn
								.prepareStatement("UPDATE Plane Set name=?, places=? where id_plane='" + item + "'");
						state.setString(1, name);
						state.setString(2, places);
						// state.setString(3, item);
						state.execute();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					ClearFields();
					FillComboPlaneID();
					JOptionPane.showMessageDialog(null, "Plane is Edited!!!");
				}
			}
		});

		addPlanePanel.add(btnDeletePlane);
		btnDeletePlane.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnDeletePlane.setBounds(288, 130, 125, 25);
		btnDeletePlane.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String item = (String) comboPlaneID.getSelectedItem();
				conn = ConnectDB.ConnectAirportFlights();

				if (item.equals("Plane_ID")) {
					JOptionPane.showMessageDialog(null, "Please Chose \"Plane ID\"");
				} else {
					try {
						state = conn.prepareStatement("DELETE FROM Plane where id_plane=?");
						state.setString(1, item);
						state.execute();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					FillComboPlaneID();
				}
			}
		});

	}

	@Override
	public void ClearFields() {
		txtPlaneModel.setText(null);
		txtPlaces.setText(null);

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