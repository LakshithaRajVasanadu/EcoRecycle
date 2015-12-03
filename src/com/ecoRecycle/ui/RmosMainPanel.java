package com.ecoRecycle.ui;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.service.RmosManager;
import com.ecoRecycle.service.StatusManager;

public class RmosMainPanel extends JPanel{
	private Rmos rmos;
	private RmosManager rmosManager;
	private StatusManager statusManager;
	
	private JFrame parentFrame;
	
	public RmosMainPanel(JFrame parentFrame, Rmos rmos, RmosManager rmosManager, StatusManager statusManager) {
		this.rmos = rmos;
		this.rmosManager = rmosManager;
		this.statusManager = statusManager;
		
		this.parentFrame = parentFrame;
		
		TitledBorder border = new TitledBorder("RMOS");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 10));
		this.setBorder(border);
		
		this.addComponents();
		
	}
	
	private void addComponents() {
		
		this.add(getLeftPanel());
		this.add(getRightPanel());
		
	}
	
	private JPanel getLeftPanel() {
		JPanel leftPanel = new JPanel();
		
		TitledBorder border = new TitledBorder("Left");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 10));
		leftPanel.setBorder(border);
		
		leftPanel.setPreferredSize(new Dimension(200, 760));
		
		leftPanel.add(new StatusMonitorPanel(rmos, rmosManager, statusManager));
		leftPanel.add(new NotificationPanel(rmos, rmosManager, statusManager));
		
		return leftPanel;
	}
	
	private JPanel getRightPanel() {
		JPanel rightPanel = new JPanel();
		
		TitledBorder border = new TitledBorder("Right");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 10));
		rightPanel.setBorder(border);
		
		rightPanel.setPreferredSize(new Dimension(595, 760));
		
		// add tabbed panes
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("RCM Manager", new RcmManagerPanel(rmos, rmosManager, statusManager));
		tabbedPane.addTab("Statistics", new StatisticsPanel(rmos, rmosManager, statusManager));
		tabbedPane.setPreferredSize(new Dimension(595, 760));
		
		rightPanel.add(tabbedPane);
		
		return rightPanel;
	}
	
}
