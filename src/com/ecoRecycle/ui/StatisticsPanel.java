package com.ecoRecycle.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.service.RmosManager;
import com.ecoRecycle.service.StatusManager;

public class StatisticsPanel extends JPanel{
	private Rmos rmos;
	private RmosManager rmosManager;
	private StatusManager statusManager;
	
	private JPanel inputPanel = new JPanel();
	private JPanel statisticsPanel = new JPanel();
	private JPanel visualizationPanel = new JPanel();
	
	public StatisticsPanel(Rmos rmos, RmosManager rmosManager, StatusManager statusManager) {
		this.rmos = rmos;
		this.rmosManager = rmosManager;
		this.statusManager = statusManager;
		// observe all rcms
		
		this.addComponents();
	}
	
	private void addComponents() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(getInputPanel());
		this.add(getStatisticsPanel());
		this.add(getVisualizationPanel());
	}
	
	private JPanel getInputPanel() {
		TitledBorder border = new TitledBorder("Input panel");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 10));
		inputPanel.setBorder(border);
		//inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
		//inputPanel.setLayout(new GridLayout(0,1));
				
		JPanel durationPanel = new JPanel();
		durationPanel.setPreferredSize(new Dimension(500, 50));
		durationPanel.setBorder(border);
		
		JLabel durationLabel = new JLabel("Choose duration: ");	
		
		JRadioButton dailyButton = new JRadioButton("Daily");
		dailyButton.setSelected(true);

		JRadioButton weeklyButton = new JRadioButton("Weekly");
		
		JRadioButton monthlyButton = new JRadioButton("Monthly");
		
		JRadioButton rangeButton = new JRadioButton("Range");
		
		ButtonGroup durationGroup = new ButtonGroup();
		durationGroup.add(dailyButton);
		durationGroup.add(weeklyButton);
		durationGroup.add(monthlyButton);
		durationGroup.add(rangeButton);
	    
		durationPanel.add(durationLabel);
		durationPanel.add(dailyButton);
		durationPanel.add(weeklyButton);
		durationPanel.add(monthlyButton);
		durationPanel.add(rangeButton);
		
		inputPanel.add(durationPanel);
	    
		JPanel machinePanel = new JPanel();
		JLabel machineLabel = new JLabel("Choose Rcm: ");
		
		JRadioButton allButton = new JRadioButton("All    ");
		allButton.setSelected(true);

		JRadioButton specificRcmButton = new JRadioButton("Specific RCM");
		
		ButtonGroup machineGroup = new ButtonGroup();
		machineGroup.add(allButton);
		machineGroup.add(specificRcmButton);
		
		machinePanel.add(machineLabel);
		machinePanel.add(allButton);
		machinePanel.add(specificRcmButton);
		machinePanel.add(Box.createRigidArea(new Dimension(96, 20)));
		
		inputPanel.add(machinePanel);
		
		return inputPanel;
	}
	
	private JPanel getStatisticsPanel() {
		TitledBorder border = new TitledBorder("Statistics panel");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 10));
		statisticsPanel.setBorder(border);
		
		return statisticsPanel;
	}
	
	private JPanel getVisualizationPanel() {
		TitledBorder border = new TitledBorder("Visualization panel");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 10));
		visualizationPanel.setBorder(border);
		
		return visualizationPanel;
	}
	

}
