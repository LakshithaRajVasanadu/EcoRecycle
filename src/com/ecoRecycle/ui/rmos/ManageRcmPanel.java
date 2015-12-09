package com.ecoRecycle.ui.rmos;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ecoRecycle.helper.Message;
import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.service.RcmService;
import com.ecoRecycle.service.ReloadTransactionService;
import com.ecoRecycle.service.RmosManager;
import com.ecoRecycle.service.StatusManager;
import com.ecoRecycle.service.UnloadTransactionService;

public class ManageRcmPanel extends JPanel{
	private Rmos rmos;
	private RmosManager rmosManager;
	private StatusManager statusManager;
	private UnloadTransactionService uservice;
	private ReloadTransactionService rservice;
	
	private JComboBox rcmComboBox;
	private JPanel rcmPanel = new JPanel();
	
	public ManageRcmPanel(Rmos rmos,  RmosManager rmosManager, StatusManager statusManager, UnloadTransactionService uservice, ReloadTransactionService rservice) {
		this.rmos = rmos;
		this.rmosManager = rmosManager;
		this.statusManager = statusManager;
		setBackground(new Color(245, 214, 196));
		
		this.uservice = uservice;
		this.rservice = rservice;
		
		// observe all rcms
		this.addComponents();
	}
	
	private void addComponents() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(getInputPanel());
		rcmPanel.setBackground(new Color(245, 214, 196));
		this.add(rcmPanel);
		
	}
	
	//Panel to manage an rcm - to unload, to reload, to add money to rcm
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
	
	private void prepareRcmPanel(String rcmName) {
		rcmPanel.removeAll();
		rcmPanel.setBackground(new Color(245, 214, 196));
		
        System.out.println("Switching to Rcm:" + rcmName);
		
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
        
        JButton activateButton = new JButton("Activate");
		activateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Rcm rcm = new RcmService().getRcmByName(rcmName);
				if(rcm == null){
					JOptionPane.showMessageDialog(null,
							"No such rcm", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Message msg = statusManager.activateRcm(rcm.getId());
				
				if(msg.isSuccessful()) {
					JOptionPane.showMessageDialog(null,
							"activated successfully", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					prepareRcmPanel(rcmName);
					rcmPanel.revalidate();
					rcmPanel.repaint();
					
					
				}else {
					JOptionPane.showMessageDialog(null,
							msg.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JButton deactivateButton = new JButton("Deactivate");
		deactivateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				Rcm rcm = new RcmService().getRcmByName(rcmName);
				if(rcm == null){
					JOptionPane.showMessageDialog(null,
							"No such rcm", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				Message msg = statusManager.deactivateRcm(rcm.getId() , "Admin change");
				
				if(msg.isSuccessful()) {
					JOptionPane.showMessageDialog(null,
							"deactivated successfully", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					prepareRcmPanel(rcmName);
					rcmPanel.revalidate();
					rcmPanel.repaint();
					
					
				}else {
					JOptionPane.showMessageDialog(null,
							msg.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JButton unloadButton = new JButton("Unload Rcm");
		unloadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Rcm rcm = new RcmService().getRcmByName(rcmName);
				
				Message msg = uservice.unloadRcm(rcm);
				
				if(msg.isSuccessful()) {
					JOptionPane.showMessageDialog(null,
							"unloaded contents successfully", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					prepareRcmPanel(rcmName);
					rcmPanel.revalidate();
					rcmPanel.repaint();
					
					
				}else {
					JOptionPane.showMessageDialog(null,
							msg.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JTextField cashField = new JTextField(20);
		
		JButton loadButton = new JButton("Reload Cash");
		loadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Rcm rcm = new RcmService().getRcmByName(rcmName);
				
				double money = Double.parseDouble(cashField.getText());
				
				Message msg = rservice.reloadRcm(rcm, money);
				
				if(msg.isSuccessful()) {
					JOptionPane.showMessageDialog(null,
							"loaded cash successfully", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					prepareRcmPanel(rcmName);
					rcmPanel.revalidate();
					rcmPanel.repaint();
					
					
				}else {
					JOptionPane.showMessageDialog(null,
							msg.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		
		
		rcmPanel.add(activateButton);
		rcmPanel.add(deactivateButton);
		rcmPanel.add(unloadButton);
		rcmPanel.add(cashField);
		rcmPanel.add(loadButton);
		rcmPanel.revalidate();
		rcmPanel.repaint();
	}

}
