package com.ecoRecycle.ui.rmos;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.ecoRecycle.helper.Message;
import com.ecoRecycle.model.Item;
import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.service.ItemManager;
import com.ecoRecycle.service.LocationService;
import com.ecoRecycle.service.RmosManager;

public class ManageItemPanel extends JPanel {
	private Rmos rmos;
	private RmosManager rmosManager;
	private ItemManager itemManager;
	private LocationService locationService = new LocationService();
	
	private JComboBox rcmComboBox;
	private JPanel rcmPanel = new JPanel();
	
	public ManageItemPanel(Rmos rmos, ItemManager itemManager) {
		this.rmos = rmos;
		this.rmosManager = new RmosManager(rmos);
		this.itemManager = itemManager;
		setBackground(new Color(245, 214, 196));
		this.addComponents();	
	}
	
	private void addComponents() {
		this.add(getItemPanel());
	}
	
	//Panel to add or remove an item
	private JPanel getItemPanel() {
		JPanel itemPanel = new JPanel();
		itemPanel.setBackground(new Color(245, 214, 196));
		itemPanel.setLayout(new GridLayout(0,1));
	
		JLabel itemLabel = new JLabel("Item");
		JComboBox<String> itemComboxBox = new JComboBox<String>();
		
		List<Item> items = itemManager.getAllItems();
		for(Item item : items)
			itemComboxBox.addItem(item.getType());
		
		JButton addButton = new JButton("Add Item");
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String itemType = itemComboxBox.getSelectedItem().toString();
				Item item = itemManager.getItemByType(itemType);
				
				Message msg = itemManager.addItem(item.getId());
				if(msg.isSuccessful()) {
					JOptionPane.showMessageDialog(null,
							"added item successfully", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					
					
				}else {
					JOptionPane.showMessageDialog(null,
							msg.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JButton removeButton = new JButton("Remove Item");
		removeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String itemType = itemComboxBox.getSelectedItem().toString();
				Item item = itemManager.getItemByType(itemType);
				
				Message msg = itemManager.removeItem(item.getId());
				if(msg.isSuccessful()) {
					JOptionPane.showMessageDialog(null,
							"removed item successfully", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					
					
				}else {
					JOptionPane.showMessageDialog(null,
							msg.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		
		JLabel priceLabel = new JLabel("Price");
		JTextField priceField = new JTextField(20);
		
		JButton changeButton = new JButton("Change");
		
		changeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String itemType = itemComboxBox.getSelectedItem().toString();
				double newPrice = Double.parseDouble(priceField.getText());
				
				Item item = itemManager.getItemByType(itemType);
				
				Message msg = itemManager.changePrice(item.getId(), newPrice);
				if(msg.isSuccessful()) {
					JOptionPane.showMessageDialog(null,
							"changed price successfully", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					
					
				}else {
					JOptionPane.showMessageDialog(null,
							msg.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		itemPanel.add(itemLabel);
		itemPanel.add(itemComboxBox);
		itemPanel.add(addButton);
		itemPanel.add(removeButton);
		itemPanel.add(priceLabel);
		itemPanel.add(priceField);
		itemPanel.add(changeButton);
		
		return itemPanel;
	}
}
