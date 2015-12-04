package com.ecoRecycle.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.service.ItemManager;
import com.ecoRecycle.service.ReloadTransactionService;
import com.ecoRecycle.service.RmosManager;
import com.ecoRecycle.service.RmosService;
import com.ecoRecycle.service.StatusManager;
import com.ecoRecycle.service.UnloadTransactionService;

public class RmosUI extends JPanel{
	
	private RmosService rmosService = new RmosService();
	private Rmos rmos;
	private RmosManager rmosManager;
	private StatusManager statusManager;
	private ItemManager itemManager;
	private UnloadTransactionService uservice;
	private ReloadTransactionService rservice;
	
	private JFrame parentFrame;
	private JPanel cardLayoutPanel;
	
    public static final String LOGIN_PANEL = "LoginPanel";
    public static final String MAIN_PANEL = "RmosMainPanel";
	
	public RmosUI(String name, JFrame parentFrame, StatusManager statusManager, ItemManager itemManager, UnloadTransactionService uservice, ReloadTransactionService rservice, RmosManager rmosManager) {
		rmos = rmosService.getRmosByName(name);
		this.rmosManager = rmosManager;
		this.statusManager = statusManager;
		this.itemManager = itemManager;
		this.uservice = uservice;
		this.rservice = rservice;
		
		//this.statusManager = new StatusManager(rmos);
		
		this.parentFrame = parentFrame;
		this.addComponents();
		this.registerLogoutButtonHandler();
	}
	
	private void addComponents() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(getRmosDetailsPanel());
		this.add(getCardLayoutPanel());
	}
	
	private JPanel getRmosDetailsPanel() {
		JPanel rmosDetailsPanel = new JPanel();
		
//		TitledBorder border = new TitledBorder("RMOS DETAILS PANEL");
//		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 10));
//		rmosDetailsPanel.setBorder(border);
		
		rmosDetailsPanel.setPreferredSize(new Dimension(820, 55));
		rmosDetailsPanel.setLayout(new BorderLayout());
		
		rmosDetailsPanel.add(new JLabel("Name: " + rmos.getName()), BorderLayout.WEST);
		rmosDetailsPanel.add(new JLabel("Location: " + rmos.getLocation().getCity()), BorderLayout.EAST);
		
		return rmosDetailsPanel;
	}
	
	private JPanel getCardLayoutPanel() {
		cardLayoutPanel = new JPanel();
//		TitledBorder border = new TitledBorder("CARD LAYOUT PANEL");
//		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 10));
//		cardLayoutPanel.setBorder(border);
		
		cardLayoutPanel.setPreferredSize(new Dimension(820, 785));
		
		cardLayoutPanel.setLayout(new CardLayout());
		cardLayoutPanel.add(new LoginPanel(parentFrame, cardLayoutPanel, rmos), LOGIN_PANEL);
		cardLayoutPanel.add(new RmosMainPanel(parentFrame, rmos, rmosManager, statusManager, itemManager, uservice, rservice), MAIN_PANEL);
		
		return cardLayoutPanel;
	}
	
	private void registerLogoutButtonHandler() {
		System.out.println("Here...");
		JButton logoutButton = ((RmosUIManager)( this.parentFrame)).getLogoutButton();
		logoutButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Here 2...");
				CardLayout cl = (CardLayout) (cardLayoutPanel.getLayout());
				cl.show(cardLayoutPanel, RmosUI.LOGIN_PANEL);
			}
		});
	}
	
	

}
