package com.ecoRecycle.ui.rmos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.service.ItemManager;
import com.ecoRecycle.service.ReloadTransactionService;
import com.ecoRecycle.service.RmosManager;
import com.ecoRecycle.service.StatusManager;
import com.ecoRecycle.service.UnloadTransactionService;

public class RmosMainPanel extends JPanel{
	private Rmos rmos;
	private RmosManager rmosManager;
	private StatusManager statusManager;
	private ItemManager itemManager;
	private UnloadTransactionService uservice;
	private ReloadTransactionService rservice;
	
	private JFrame parentFrame;
	
	public RmosMainPanel(JFrame parentFrame, Rmos rmos, RmosManager rmosManager, StatusManager statusManager, ItemManager itemManager, UnloadTransactionService uservice, ReloadTransactionService rservice) {
		this.rmos = rmos;
		this.rmosManager = rmosManager;
		this.statusManager = statusManager;
		this.itemManager = itemManager;
		
		this.uservice = uservice;
		this.rservice = rservice;
		
		this.parentFrame = parentFrame;
		this.setBackground(Color.black);
		
		TitledBorder border = new TitledBorder("RMOS");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 10));
		this.setBorder(border);
		
		this.addComponents();
		
	}
	
	private void addComponents() {
		
		this.add(getLeftPanel());
		this.add(getRightPanel());
		
	}
	
	//Panel for notifications and to show status of the rcm
	private JPanel getLeftPanel() {
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(Color.black);
		
		TitledBorder border = new TitledBorder("Left");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 10));
		leftPanel.setBorder(border);
		
		leftPanel.setPreferredSize(new Dimension(200, 760));
		
		leftPanel.add(new StatusMonitorPanel(rmos, rmosManager, statusManager));
		leftPanel.add(new NotificationPanel(rmos, rmosManager, statusManager));
		
		return leftPanel;
	}
	
	//Panel for rcm related functionalities and to view statistics
	private JPanel getRightPanel() {
		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(Color.black);
		
		TitledBorder border = new TitledBorder("Right");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 10));
		rightPanel.setBorder(border);
		
		rightPanel.setPreferredSize(new Dimension(595, 760));
		
		// add tabbed panes
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBackground(new Color(245, 214, 196));
		tabbedPane.addTab("RCM Manager", new RcmManagerPanel(rmos, rmosManager, statusManager, itemManager, uservice, rservice));
		tabbedPane.addTab("Statistics", new StatisticsPanel(rmos, rmosManager, statusManager));
		tabbedPane.setPreferredSize(new Dimension(595, 760));
		
		rightPanel.add(tabbedPane);
		
		return rightPanel;
	}
	
}
