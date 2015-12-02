package com.ecoRecycle.ui;

import java.awt.BorderLayout;
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
import javax.swing.border.TitledBorder;

import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.service.RmosService;

public class RmosUIManager extends JFrame{
	
	private RmosService rmosService = new RmosService();
	private JPanel rmosChooserPanel = new JPanel();
	private JPanel rmosPanel = new JPanel();
	private JComboBox<String> rmosComboBox;
	private JButton logoutButton;
	
	public RmosUIManager() {
		super("RMOS");
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
		contentPane.setLayout(new BorderLayout());
		contentPane.add(rmosChooserPanel, BorderLayout.NORTH);
		contentPane.add(rmosPanel, BorderLayout.SOUTH);
		
	}
	
	private void prepareRmosChooserPanel() {
		rmosChooserPanel.setLayout(new BorderLayout());
		rmosChooserPanel.setPreferredSize(new Dimension(0, 70));
		
		TitledBorder border = new TitledBorder("CHOOSE RMOS");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 18));
		rmosChooserPanel.setBorder(border);
		
		JLabel rmosLabel = new JLabel("Choose Rmos:");
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
		rmosChooserInnerPanel.add(rmosLabel);
		rmosChooserInnerPanel.add(rmosComboBox);
		
		rmosChooserPanel.add(rmosChooserInnerPanel, BorderLayout.WEST);
		addLogoutButton();
	}
	
	private void prepareRmosPanel(String rmosName) {
		rmosPanel.removeAll();
        System.out.println("Switching to Rmos:" + rmosName);
		rmosPanel.add(new RmosUI(rmosName, this));
		
//		TitledBorder border = new TitledBorder("RMOS SPECIFIC PANEL");
//		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 18));
//		rmosPanel.setBorder(border);
		
		rmosPanel.setPreferredSize(new Dimension(0, 875));
		
		this.revalidate();
		this.repaint();
	}
	
	private void addLogoutButton() {
		logoutButton = new JButton("Logout");		
		rmosChooserPanel.add(logoutButton, BorderLayout.EAST);
	}

	public JButton getLogoutButton() {
		return logoutButton;
	}

}
