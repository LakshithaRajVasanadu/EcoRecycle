package com.ecoRecycle.ui.rmos;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.ecoRecycle.helper.Message;
import com.ecoRecycle.model.Location;
import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.service.RmosManager;
import com.ecoRecycle.service.LocationService;
import com.ecoRecycle.service.StatusManager;

public class AddRcmPanel extends JPanel{
	private Rmos rmos;
	private RmosManager rmosManager;
	private StatusManager statusManager;
	private LocationService locationService = new LocationService();
	
	private JComboBox rcmComboBox;
	private JPanel rcmPanel = new JPanel();
	
	public AddRcmPanel(Rmos rmos, RmosManager rmosManager, StatusManager statusManager) {
		this.rmos = rmos;
		this.rmosManager = rmosManager;
		this.statusManager = statusManager;
		setBackground(new Color(245, 214, 196));
		this.addComponents();
	}
	
	private void addComponents() {
		this.add(getAddRcmPanel());
	}
	
	// Panel for adding a new rcm
	private JPanel getAddRcmPanel() {
		JPanel addRcmPanel = new JPanel();
		addRcmPanel.setBackground(new Color(245, 214, 196));
		addRcmPanel.setLayout(new GridLayout(0,2));
			
		JLabel nameLabel = new JLabel("Name");
		JTextField nameField = new JTextField(20);
		
		JLabel locationLabel = new JLabel("Location");
		JComboBox<String> locationComboBox = new JComboBox<String>();
		
		List<Location> locations = locationService.getAllLocations();
		for(Location loc : locations)
			locationComboBox.addItem(loc.getCity());
		
		
		JLabel capacityLabel = new JLabel("Capacity");
		JTextField capacityField = new JTextField(20);
		
		JLabel cashValueLabel = new JLabel("Total Cash");
		JTextField cashField = new JTextField(20);
		
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String rcmName = nameField.getText();
				String location = locationComboBox.getSelectedItem().toString();
				double totalCapacity = Double.parseDouble(capacityField.getText());
				double totalCash = Double.parseDouble(cashField.getText());
				
				//Do input validation
				
				Message msg = rmosManager.addRcm(rcmName, location, totalCapacity, totalCash);
				if(msg.isSuccessful()) {
					JOptionPane.showMessageDialog(null,
							"Added successfully", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					
				}else {
					JOptionPane.showMessageDialog(null,
							msg.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		addRcmPanel.add(nameLabel, 0);
		addRcmPanel.add(nameField, 1);
		addRcmPanel.add(locationLabel, 2);
		addRcmPanel.add(locationComboBox, 3);
		addRcmPanel.add(capacityLabel, 4);
		addRcmPanel.add(capacityField, 5);
		addRcmPanel.add(cashValueLabel, 6);
		addRcmPanel.add(cashField, 7);
		addRcmPanel.add(addButton, 8);
		
		return addRcmPanel;
	}
	

}
