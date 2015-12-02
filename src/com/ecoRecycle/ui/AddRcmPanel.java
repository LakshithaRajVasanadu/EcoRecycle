package com.ecoRecycle.ui;

import java.awt.Font;
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

public class AddRcmPanel extends JPanel{
	private Rmos rmos;
	private RmosManager rmosManager;
	private LocationService locationService = new LocationService();
	
	private JComboBox rcmComboBox;
	private JPanel rcmPanel = new JPanel();
	
	public AddRcmPanel(Rmos rmos) {
		this.rmos = rmos;
		this.rmosManager = new RmosManager(rmos);
		
		// observe all rcms
		this.addComponents();
	}
	
	private void addComponents() {
		this.add(getAddRcmPanel());
	}
	
	private JPanel getAddRcmPanel() {
		JPanel addRcmPanel = new JPanel();
		addRcmPanel.setLayout(new BoxLayout(addRcmPanel, BoxLayout.Y_AXIS));
		
		TitledBorder border = new TitledBorder("ADD RCM");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 18));
		addRcmPanel.setBorder(border);
		
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
		
		addRcmPanel.add(nameLabel);
		addRcmPanel.add(nameField);
		addRcmPanel.add(locationLabel);
		addRcmPanel.add(locationComboBox);
		addRcmPanel.add(capacityLabel);
		addRcmPanel.add(capacityField);
		addRcmPanel.add(cashValueLabel);
		addRcmPanel.add(cashField);
		addRcmPanel.add(addButton);
		
		return addRcmPanel;
	}
	

}
