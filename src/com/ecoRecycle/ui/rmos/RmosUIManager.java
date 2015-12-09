package com.ecoRecycle.ui.rmos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.service.ItemManager;
import com.ecoRecycle.service.ReloadTransactionService;
import com.ecoRecycle.service.RmosManager;
import com.ecoRecycle.service.RmosService;
import com.ecoRecycle.service.StatusManager;
import com.ecoRecycle.service.UnloadTransactionService;
import com.ecoRecycle.ui.rcm.RcmUIManager;

import java.util.Observable;

public class RmosUIManager extends JFrame {
	
	private RmosService rmosService = new RmosService();
	private JPanel rmosChooserPanel = new JPanel();
	private JPanel rmosPanel = new JPanel();
	private JComboBox<String> rmosComboBox;
	private JButton logoutButton;
	
	private StatusManager statusManager;	
	private ItemManager itemManager;
	private RmosManager rmosManager;

	
	public RmosUIManager(StatusManager statusManager) {
		super("RMOS");
		this.statusManager = statusManager;
		initialize();
	}
	
	private void initialize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(new Dimension(screenSize.width/2, screenSize.height));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.addComponents();
		this.setVisible(true);
	}
	
	private void addComponents() {
		Container contentPane = this.getContentPane();
		prepareRmosChooserPanel();
		prepareRmosPanel(rmosComboBox.getSelectedItem().toString());
		contentPane.setBackground(Color.black);
		
		JPanel finalPanel = new JPanel(new BorderLayout());
		finalPanel.setBackground(Color.black);
		finalPanel.setBorder(new LineBorder(new Color(74, 175, 37), 3));
		finalPanel.add(rmosChooserPanel, BorderLayout.NORTH);
		finalPanel.add(rmosPanel, BorderLayout.SOUTH);
		contentPane.add(finalPanel);	
	}
	
	//Panel to choose the rmos form the list of rmos
	private void prepareRmosChooserPanel() {
		rmosChooserPanel.setLayout(new BorderLayout());
		rmosChooserPanel.setPreferredSize(new Dimension(0, 70));
		rmosChooserPanel.setBackground(Color.black);
		
		TitledBorder border = new TitledBorder("CHOOSE RMOS");
		border.setBorder(new LineBorder(Color.orange));
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 18));
		border.setTitleColor(new Color(162, 118, 237));
		rmosChooserPanel.setBorder(border);
		
		JLabel rmosLabel = new JLabel("Choose Rmos:");
		rmosLabel.setForeground(Color.white);
		rmosComboBox = new JComboBox<String>();
		
		Set<Rmos> rmosList = rmosService.getAllRmos();
		for(Rmos rmos : rmosList) {
			rmosComboBox.addItem(rmos.getName());
		}
		
		rmosComboBox.setSelectedItem("Rmos1");
		
		rmosComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				JComboBox comboBox = (JComboBox) event.getSource();
				Object item = event.getItem();
				if (event.getStateChange() == ItemEvent.SELECTED) {
					prepareRmosPanel(item.toString());
				}				
			}
        });
		
		JPanel rmosChooserInnerPanel = new JPanel();
		rmosChooserInnerPanel.setBackground(Color.black);
		rmosChooserInnerPanel.add(rmosLabel);
		rmosChooserInnerPanel.add(rmosComboBox);
		
		rmosChooserPanel.add(rmosChooserInnerPanel, BorderLayout.WEST);
		addLogoutButton();
	}
	
	private void prepareRmosPanel(String rmosName) {
		rmosPanel.removeAll();
		rmosPanel.setBackground(Color.black);
        Rmos rmos = rmosService.getRmosByName(rmosName);
        statusManager = new StatusManager(rmos);
        itemManager = ItemManager.getInstance();
        rmosManager = new RmosManager(rmos);
        
        UnloadTransactionService uservice = new UnloadTransactionService();
        ReloadTransactionService rservice = new ReloadTransactionService();
        
		rmosPanel.add(new RmosUI(rmosName, this, statusManager, itemManager, uservice, rservice, rmosManager));
		
		//Starting of Rcm UI
		new RcmUIManager(statusManager, itemManager, uservice, rservice, rmosManager);
		rmosPanel.setPreferredSize(new Dimension(0, 875));
		
		this.revalidate();
		this.repaint();
	}
	
	private void addLogoutButton() {
		logoutButton = new JButton("LOGOUT");
		rmosChooserPanel.add(logoutButton, BorderLayout.EAST);
	}

	public JButton getLogoutButton() {
		return logoutButton;
	}
	
	private void setPanelColor(JPanel panel, Color color) {
		panel.setBackground(color);

		Component[] components = panel.getComponents();

		for (int i = 0; i < components.length; i++) {
			if (components[i].getClass().getName() == "javax.swing.JPanel") {
				 setPanelColor((JPanel) components[i], color);
			}

			components[i].setBackground(color);
		}
	}

}
