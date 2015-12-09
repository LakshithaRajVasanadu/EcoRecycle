package com.ecoRecycle.ui.rmos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.service.RcmService;
import com.ecoRecycle.service.RmosManager;

public class ViewRcmPanel extends JPanel{
	private Rmos rmos;
	private RmosManager rmosManager;
	
	private JComboBox rcmComboBox;
	private JPanel rcmPanel = new JPanel();
	
	public ViewRcmPanel(Rmos rmos) {
		this.rmos = rmos;
		this.rmosManager = new RmosManager(rmos);
		setBackground(new Color(245, 214, 196));
		this.addComponents();
	}
	
	private void addComponents() {
		this.setLayout(new GridLayout(0,1));
		this.add(getInputPanel());
		rcmPanel.setBackground(new Color(245, 214, 196));
		this.add(rcmPanel);
		
	}
	
	//To choose an rcm from the list to view the details
	private JPanel getInputPanel() {
		JPanel inputPanel = new JPanel();
		inputPanel.setBackground(new Color(245, 214, 196));
		
		JLabel rcmLabel = new JLabel("Rcm: ");
		rcmComboBox = new JComboBox<String>();
		
		List<Rcm> rcms = rmosManager.getAllRcms();
		rcmComboBox.addItem("--Choose One--");
		for(Rcm rcm : rcms)
			rcmComboBox.addItem(rcm.getName());
		
		rcmComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				JComboBox comboBox = (JComboBox) event.getSource();
				Object item = event.getItem();
				if (event.getStateChange() == ItemEvent.SELECTED) {
					if(!(item.toString().equals("--Choose One--")))
						prepareRcmPanel(item.toString());
				}				
			}
        });
		
		inputPanel.add(rcmLabel);
		inputPanel.add(rcmComboBox);
		
		return inputPanel;
	}
	
	//Panel to display the details of a particular rcm selected
	private void prepareRcmPanel(String rcmName) {
		rcmPanel.removeAll();
		
		rcmPanel.setBackground(new Color(245, 214, 196));
		rcmPanel.setLayout(new GridLayout(0,1));
		
        Rcm rcm = new RcmService().getRcmByName(rcmName);
        rcmPanel.setLayout(new GridLayout(0,1));
        rcmPanel.add(new JLabel("Name: " + rcm.getName()));
        rcmPanel.add(new JLabel("Location: " + rcm.getLocation().getCity()));
        rcmPanel.add(new JLabel("Status:" + rcm.getStatus().toString()));
        rcmPanel.add(new JLabel("Total Amount: $" + rcm.getTotalCashValue()));
        rcmPanel.add(new JLabel("Current Available Amount: $" + (rcm.getTotalCashValue() - rcm.getCurrentCashValue())));
        rcmPanel.add(new JLabel("Total Capacity: " + rcm.getTotalCapacity() + " lbs"));
        rcmPanel.add(new JLabel("Current Available Capacity: " + (rcm.getTotalCapacity() - rcm.getCurrentCapacity()) + " lbs"));
        if(rcm.isFull())
        	rcmPanel.add(new JLabel("Rcm is Full! Please empty it"));
		
		rcmPanel.revalidate();
		rcmPanel.repaint();
	}
}
