package com.ecoRecycle.ui.rmos;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
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
		
		this.parentFrame = parentFrame;
		this.setBackground(Color.black);
		
		this.addComponents();
		this.registerLogoutButtonHandler();
	}
	
	private void addComponents() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(getRmosDetailsPanel());
		this.add(getCardLayoutPanel());
	}
	
	//Panel to show the details of the rmos -rmos name and rmos loaction
	private JPanel getRmosDetailsPanel() {
		JPanel rmosDetailsPanel = new JPanel();
		rmosDetailsPanel.setBackground(Color.black);
		rmosDetailsPanel.setPreferredSize(new Dimension(820, 55));
		rmosDetailsPanel.setLayout(new BorderLayout());
		
		JLabel nameLabel = new JLabel("<html><b>NAME: </b>" + rmos.getName() +"</html>");
		nameLabel.setForeground(Color.white);
		rmosDetailsPanel.add(nameLabel, BorderLayout.WEST);
		JLabel locationLabel = new JLabel("<html><b>LOCATION: </b>" + rmos.getLocation().getCity() +" </html>");
		locationLabel.setForeground(Color.white);
		rmosDetailsPanel.add(locationLabel, BorderLayout.EAST);
		
		return rmosDetailsPanel;
	}
	
	private JPanel getCardLayoutPanel() {
		cardLayoutPanel = new JPanel();
		cardLayoutPanel.setPreferredSize(new Dimension(820, 785));
		cardLayoutPanel.setLayout(new CardLayout());
		cardLayoutPanel.add(new LoginPanel(parentFrame, cardLayoutPanel, rmos), LOGIN_PANEL);
		cardLayoutPanel.add(new RmosMainPanel(parentFrame, rmos, rmosManager, statusManager, itemManager, uservice, rservice), MAIN_PANEL);
		
		return cardLayoutPanel;
	}
	
	//logout button handler
	private void registerLogoutButtonHandler() {
		JButton logoutButton = ((RmosUIManager)( this.parentFrame)).getLogoutButton();
		logoutButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cardLayoutPanel.getLayout());
				cl.show(cardLayoutPanel, RmosUI.LOGIN_PANEL);
			}
		});
	}
	
	

}
