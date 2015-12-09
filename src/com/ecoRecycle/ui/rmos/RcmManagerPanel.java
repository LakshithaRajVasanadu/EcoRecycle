package com.ecoRecycle.ui.rmos;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.service.ItemManager;
import com.ecoRecycle.service.ReloadTransactionService;
import com.ecoRecycle.service.RmosManager;
import com.ecoRecycle.service.StatusManager;
import com.ecoRecycle.service.UnloadTransactionService;

public class RcmManagerPanel extends JPanel{
	private Rmos rmos;
	private RmosManager rmosManager;
	private StatusManager statusManager;
	private ItemManager itemManager;
	
	private UnloadTransactionService uservice;
	private ReloadTransactionService rservice;
	
	private JPanel displayPanel;
	
	public RcmManagerPanel(Rmos rmos, RmosManager rmosManager, StatusManager statusManager, ItemManager itemManager, UnloadTransactionService uservice, ReloadTransactionService rservice) {
		this.rmos = rmos;
		this.rmosManager = rmosManager;
		this.statusManager = statusManager;
		this.itemManager = itemManager;
		setBackground(new Color(245, 214, 196));
		this.uservice = uservice;
		this.rservice = rservice;
		this.addComponents();
	}
	
	private void addComponents() {
		this.add(getControlPanel());
		this.add(getDisplayPanel());
	}
	
	//Panel for the functionalities perfored by the admin on the rcm -add rcm, remove rcm, manage rcm,
	private JPanel getControlPanel() {
		JPanel controlPanel = new JPanel();
		
		TitledBorder border = new TitledBorder("  CONTROL PANEL");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 12));
		controlPanel.setBorder(border);
		controlPanel.setBackground(new Color(245, 214, 196));
		controlPanel.setPreferredSize(new Dimension(595, 50));
		
		JRadioButton viewRcmButton = new JRadioButton("View RCM");
		viewRcmButton.setSelected(true);
		
		JRadioButton addRcmButton = new JRadioButton("Add RCM");
		
		JRadioButton removeRcmButton = new JRadioButton("Remove RCM");
		
		JRadioButton manageRcmButton = new JRadioButton("Manage RCM");
		
		JRadioButton manageItemButton = new JRadioButton("Manage Items");
		
		ButtonGroup group = new ButtonGroup();
	    group.add(viewRcmButton);
	    group.add(addRcmButton);
	    group.add(removeRcmButton);
	    group.add(manageRcmButton);
	    group.add(manageItemButton);
	    
	    viewRcmButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				viewRcmButtonHandler();
				
			}
		});
	    
	    addRcmButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				addRcmButtonHandler();
				
			}
		});
	    
	    removeRcmButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				removeRcmButtonHandler();
				
			}
		});
	    
	    manageRcmButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				manageRcmButtonHandler();
				
			}
		});

		
	    manageItemButton.addActionListener(new ActionListener() {
	
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		manageItemButtonHandler();
		
	    	}
	    });
	    controlPanel.add(viewRcmButton);
	    controlPanel.add(addRcmButton);
	    controlPanel.add(removeRcmButton);
	    controlPanel.add(manageRcmButton);
	    controlPanel.add(manageItemButton);
	    
		return controlPanel;
	}
	
	
	
	private JPanel getDisplayPanel() {
		displayPanel = new JPanel();
		displayPanel.setBackground(new Color(245, 214, 196));
		
		displayPanel.setPreferredSize(new Dimension(595, 650));
		viewRcmButtonHandler();
		
		return displayPanel;
	}
	
	//When view rcm button ic clicked, the screen should refresh to get viewRcmPanel
	private void viewRcmButtonHandler() {
		
		TitledBorder border = new TitledBorder("    VIEW RCM");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 12));
		displayPanel.setBorder(border);
		
		displayPanel.removeAll();
		displayPanel.add(new ViewRcmPanel(rmos));
		displayPanel.revalidate();
		displayPanel.repaint();
	}
	
	//When add rcm button ic clicked, the screen should refresh to get addRcmPanel
	private void addRcmButtonHandler() {
		TitledBorder border = new TitledBorder("   ADD RCM");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 12));
		displayPanel.setBorder(border);
		
		displayPanel.removeAll();
		displayPanel.add(new AddRcmPanel(rmos, rmosManager, statusManager));
		displayPanel.revalidate();
		displayPanel.repaint();
	}
	
	//When remove rcm button ic clicked, the screen should refresh to get removeRcmPanel
	private void removeRcmButtonHandler() {
		TitledBorder border = new TitledBorder("   REMOVE RCM");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 12));
		displayPanel.setBorder(border);
		
		displayPanel.removeAll();
		displayPanel.add(new RemoveRcmPanel(rmos, rmosManager, statusManager));
		displayPanel.revalidate();
		displayPanel.repaint();
	}
	
	//When manage rcm button ic clicked, the screen should refresh to get manageRcmPanel
	private void manageRcmButtonHandler() {
		TitledBorder border = new TitledBorder("   MANAGE RCM");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 12));
		displayPanel.setBorder(border);
		
		displayPanel.removeAll();
		displayPanel.add(new ManageRcmPanel(rmos, rmosManager, statusManager, uservice, rservice));
		displayPanel.revalidate();
		displayPanel.repaint();
	}
	
	//When manage item button ic clicked, the screen should refresh to get manageItemPanel
	private void manageItemButtonHandler() {
		TitledBorder border = new TitledBorder("   MANAGE ITEM");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 12));
		displayPanel.setBorder(border);
		
		displayPanel.removeAll();
		displayPanel.add(new ManageItemPanel(rmos, itemManager));
		displayPanel.revalidate();
		displayPanel.repaint();
	}

}
