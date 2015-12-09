package com.ecoRecycle.ui.rmos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.ecoRecycle.helper.DateLabelFormatter;
import com.ecoRecycle.helper.UsageStatisticsModel;
import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.service.RmosManager;
import com.ecoRecycle.service.RmosService;
import com.ecoRecycle.service.StatisticsManager;
import com.ecoRecycle.service.StatusManager;

public class StatisticsPanel extends JPanel{
	private Rmos rmos;
	private RmosManager rmosManager;
	private StatusManager statusManager;
	private StatisticsManager statisticsManager;
	
	private JPanel inputPanel = new JPanel();
	private JPanel statisticsPanel = new JPanel();
	private JPanel visualizationPanel = new JPanel();
	
	private Date startDate;
	private Date endDate;
	private boolean isRange;
	private boolean allRcms = true;
	private String selectedRcmName;
	
	JTable table;
	Object[] columnNames = {"Rcm Name", "Location", "Last Emptied Date", "Unload Count", "Item Count", "Weight (lb)", "Value ($)"};
	
	public StatisticsPanel(Rmos rmos, RmosManager rmosManager, StatusManager statusManager) {
		this.rmos = rmos;
		this.rmosManager = rmosManager;
		this.statusManager = statusManager;	
		this.statisticsManager = new StatisticsManager(rmos);
		setBackground(new Color(245, 214, 196));
		this.initialize();

		this.addComponents();
	}
	
	private void addComponents() {
		inputPanel.setBackground(new Color(245, 214, 196));
		this.add(getInputPanel());
		prepareStatisticsPanel();
		
		statisticsPanel.setBackground(new Color(245, 214, 196));
		this.add(statisticsPanel);
		
		visualizationPanel.setBackground(new Color(245, 214, 196));
		prepareVisualizationPanel();
		this.add(visualizationPanel);
	}
	
	private void initialize() {
		startDate = new Date();
		endDate = new Date();
		isRange = false;
		allRcms = true;
		selectedRcmName = null;
	}
	
	//Panel to perform various functions of statistics
	private JPanel getInputPanel() {
		TitledBorder border = new TitledBorder("  Input panel");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 10));
		inputPanel.setBorder(border);
		inputPanel.setPreferredSize(new Dimension(590, 160));
		
		JPanel durationPanel = new JPanel();
		durationPanel.setBackground(new Color(245, 214, 196));
		durationPanel.setLayout(new BoxLayout(durationPanel, BoxLayout.Y_AXIS));

		JPanel innerOneDurationPanel = new JPanel();
		innerOneDurationPanel.setBackground(new Color(245, 214, 196));
		
		//To choose the duration to view the sstatistics for - daily, weekly, monthly, range
		JLabel durationLabel = new JLabel("Choose duration: ");	
		
		JRadioButton dailyButton = new JRadioButton("Daily");
		dailyButton.setSelected(true);
		dailyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				isRange = false;
				startDate = new Date();
				endDate = new Date();
				
			}
		});

		JRadioButton weeklyButton = new JRadioButton("Weekly");
		weeklyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				isRange = false;
				endDate = new Date(); 
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.DAY_OF_WEEK, 1);
				startDate = cal.getTime();
				
			}
		});
		
		JRadioButton monthlyButton = new JRadioButton("Monthly");
		monthlyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				isRange = false;
				endDate = new Date();
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.DAY_OF_MONTH, 1);
				startDate = cal.getTime(); 
				
			}
		});
		
		innerOneDurationPanel.add(durationLabel);
		innerOneDurationPanel.add(dailyButton);
		innerOneDurationPanel.add(weeklyButton);
		innerOneDurationPanel.add(monthlyButton);
		
		JPanel innerTwoDurationPanel = new JPanel();
		innerTwoDurationPanel.setBackground(new Color(245, 214, 196));
		
		JRadioButton rangeButton = new JRadioButton("Range");
		rangeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				isRange = true;
				
			}
		});
		UtilDateModel fromDatemodel = new UtilDateModel();

		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		
		JDatePanelImpl fromDatePanel = new JDatePanelImpl(fromDatemodel, p);
		JDatePickerImpl fromDatePicker = new JDatePickerImpl(fromDatePanel,  new DateLabelFormatter());
		fromDatePicker.setPreferredSize(new Dimension(155,30));
		
		UtilDateModel toDatemodel = new UtilDateModel();

		JDatePanelImpl toDatePanel = new JDatePanelImpl(toDatemodel, p);
		JDatePickerImpl toDatePicker = new JDatePickerImpl(toDatePanel,  new DateLabelFormatter());
		toDatePicker.setPreferredSize(new Dimension(155,30));

		ButtonGroup durationGroup = new ButtonGroup();
		durationGroup.add(dailyButton);
		durationGroup.add(weeklyButton);
		durationGroup.add(monthlyButton);
		durationGroup.add(rangeButton);
	    
		innerOneDurationPanel.add(rangeButton);
		innerOneDurationPanel.add(Box.createRigidArea(new Dimension(85, 0)));
		innerTwoDurationPanel.add(new JLabel("  Start Date:"));
		innerTwoDurationPanel.add(fromDatePicker);
		innerTwoDurationPanel.add(new JLabel("  End Date:"));
		innerTwoDurationPanel.add(toDatePicker);

		durationPanel.add(innerOneDurationPanel);

		durationPanel.add(innerTwoDurationPanel);
		
		inputPanel.add(durationPanel);
	    
		JPanel machinePanel = new JPanel();
		machinePanel.setBackground(new Color(245, 214, 196));
		
		JLabel machineLabel = new JLabel("Choose Rcm:    ");
		
		JRadioButton allButton = new JRadioButton("All");
		allButton.setSelected(true);
		allButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				allRcms = true;
			}
		});

		JRadioButton specificRcmButton = new JRadioButton("Specific RCM");
		
		
		JComboBox<String> rcmComboxBox = new JComboBox<String>();
		
		List<Rcm> rcms = rmosManager.getAllRcms();
		for(Rcm rcm : rcms)
			rcmComboxBox.addItem(rcm.getName());
		
		specificRcmButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				allRcms = false;
				selectedRcmName = rcmComboxBox.getSelectedItem().toString();
			}
		});
		
		rcmComboxBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				JComboBox comboBox = (JComboBox) event.getSource();
				Object item = event.getItem();
				if (event.getStateChange() == ItemEvent.SELECTED) {
					selectedRcmName = item.toString();
				}				
			}
        });
		
		JButton loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isRange == true) {
					startDate = (Date) fromDatePicker.getModel().getValue();
					endDate = (Date) toDatePicker.getModel().getValue();
					
					if(startDate == null || endDate == null) {
						JOptionPane.showMessageDialog(null,
								"Choose a date range", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if(startDate.getTime() > endDate.getTime()) {
						JOptionPane.showMessageDialog(null,
								"Invalid Date range", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					
				}
				prepareStatisticsPanel();
				prepareVisualizationPanel();
			}
		});
		
		ButtonGroup machineGroup = new ButtonGroup();
		machineGroup.add(allButton);
		machineGroup.add(specificRcmButton);
		
		machinePanel.add(machineLabel);
		machinePanel.add(allButton);
		machinePanel.add(specificRcmButton);
		machinePanel.add(rcmComboxBox);
		machinePanel.add(loadButton);
		
		inputPanel.add(machinePanel);

			      
		return inputPanel;
	}
	
	//Statistics panel
	private void prepareStatisticsPanel() {
		statisticsPanel.removeAll();
		
		rmos = new RmosService().getRmosByName(rmos.getName());
		rmosManager = new RmosManager(rmos);
		statusManager = new StatusManager(rmos);
		statisticsManager = new StatisticsManager(rmos);
		
		
		TitledBorder border = new TitledBorder("  Statistics panel");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 10));
		statisticsPanel.setBorder(border);
		
	
		statisticsPanel.setPreferredSize(new Dimension(590, 200));
		
		String mfuString = "";
		
		List<Entry<Rcm, Integer>> mfuList = statisticsManager.getMostFrequentlyUsedRcm(startDate, endDate);
		for (Entry<Rcm, Integer> temp : mfuList) {
			if(temp.getValue() > 0)
				mfuString += temp.getKey().getName() + " ,";
			 
		}
		
		mfuString = removeLastChar(mfuString);
		
		JLabel mfuLabel = new JLabel("Most Frequently Used: " + (mfuString == null ||  mfuString.length() == 0? "-" : mfuString));
	
		
		HashMap<Rcm, UsageStatisticsModel> statisticsDataMap = statisticsManager.getUsageStatistics(startDate, endDate);
		
		DefaultTableModel model = (new DefaultTableModel(columnNames, 0) {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		});
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		for (Entry<Rcm, UsageStatisticsModel> i : statisticsDataMap.entrySet()){
			Rcm rcm = i.getKey();
			UsageStatisticsModel dataModel = i.getValue();
			
			if(allRcms == false) {
				if(!(rcm.getName().equals(selectedRcmName)))
					continue;
			}
			
			Object[] rowData = new Object[] { rcm.getName(), 
											  rcm.getLocation().getCity(), 
											  (rcm.getLastEmptied() == null)? "-" : formatter.format(rcm.getLastEmptied()),
											  dataModel.getNumberOfTimesEmptied(),
											  dataModel.getNumberOfItems(),
											  dataModel.getTotalWeight(),
											  dataModel.getTotalValue()
											};
			model.addRow(rowData);
		}
		

		table = new JTable(model);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(90);
		
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(575, 150));
		//table.setFillsViewportHeight(true);
		
		statisticsPanel.add(scrollPane);
		statisticsPanel.add(mfuLabel);

		
		
		
		statisticsPanel.revalidate();
		statisticsPanel.repaint();
	}
	
	//Visualization panel to display the bar graph
	private void prepareVisualizationPanel() {
		visualizationPanel.removeAll();
		
		rmos = new RmosService().getRmosByName(rmos.getName());
		rmosManager = new RmosManager(rmos);
		statusManager = new StatusManager(rmos);
		statisticsManager = new StatisticsManager(rmos);
		
		TitledBorder border = new TitledBorder("Visualization panel");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 10));
		visualizationPanel.setBorder(border);
		
		visualizationPanel.setPreferredSize(new Dimension(570, 325));
		rmos = new RmosService().getRmosByName(rmos.getName());
		rmosManager = new RmosManager(rmos);
		statusManager = new StatusManager(rmos);
		
		HashMap<Rcm, UsageStatisticsModel> statisticsDataMap = statisticsManager.getUsageStatistics(startDate, endDate);
		
		BarGraph graph = new BarGraph(rmos, rmosManager, statusManager, startDate, endDate, allRcms, selectedRcmName, statisticsDataMap);
		graph.updateDisplay(startDate, endDate, allRcms, selectedRcmName);
		visualizationPanel.add(graph);
		
		
		visualizationPanel.revalidate();
		visualizationPanel.repaint();
		
	}
	
	private  String removeLastChar(String str) {
		String newStr = null;
		if(str != null && str.length() > 0)
			newStr = str.substring(0,str.length()-1);
        return newStr;
    }
	

}
