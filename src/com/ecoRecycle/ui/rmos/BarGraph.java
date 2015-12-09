package com.ecoRecycle.ui.rmos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JPanel;

import com.ecoRecycle.helper.UsageStatisticsModel;
import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.service.RmosManager;
import com.ecoRecycle.service.RmosService;
import com.ecoRecycle.service.StatisticsManager;
import com.ecoRecycle.service.StatusManager;

public class BarGraph extends JPanel{
	private Rmos rmos;
	private RmosManager rmosManager;
	private StatusManager statusManager;
	private StatisticsManager statisticsManager;
	
	private Date startDate;
	private Date endDate;
	private boolean allRcms = true;
	private String selectedRcmName;
	private HashMap<Rcm, UsageStatisticsModel> statisticsDataMap;
	
	private static final int MAX_NO_OF_ITEMS = 500;
	private static final int MAX_WEIGHT = 200;
	private static final int MAX_AMOUNT = 500;
	private static final int MAX_NO_OF_TIMES_EMPTIED = 100;
	private static final int MAGNIFICATION_FACTOR = 300;
	
	//To draw a bar graph to show various usage statistics
	public BarGraph(Rmos rmos,  RmosManager rmosManager, StatusManager statusManager,  Date startDate, Date endDate, boolean allRcms, String selectedRcmName, HashMap<Rcm, UsageStatisticsModel> statisticsDataMap) {
		this.rmos = rmos;
		this.rmosManager = rmosManager;
		this.statusManager = statusManager;
		this.statisticsManager = new StatisticsManager(rmos);
		this.statisticsDataMap = statisticsDataMap;
		setBackground(new Color(245, 214, 196));
		
		this.startDate = startDate;
		this.endDate = startDate;
		this.allRcms = allRcms;
		this.selectedRcmName = selectedRcmName;
		
	}
	
	//Called by repaint()
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(new Font("TimesNewRoman", Font.BOLD, 10));
				
		int x = 30;
		int y = 10;
		
		rmos = new RmosService().getRmosByName(rmos.getName());
		rmosManager = new RmosManager(rmos);
		statusManager = new StatusManager(rmos);
		statisticsManager = new StatisticsManager(rmos);
		
		
		//Draw bargraph
		
		for (Entry<Rcm, UsageStatisticsModel> i : statisticsDataMap.entrySet()){
			Rcm rcm = i.getKey();
			UsageStatisticsModel dataModel = i.getValue();
			
			
			if(allRcms == false) {
				if(!(rcm.getName().equals(selectedRcmName)))
					continue;
			}
			
			int itemCountPercentage = (int) Math.round(((dataModel.getNumberOfItems() * MAGNIFICATION_FACTOR) / MAX_NO_OF_ITEMS));
			if(itemCountPercentage > MAGNIFICATION_FACTOR)
				itemCountPercentage = MAGNIFICATION_FACTOR;
			
			int weightPercentage = (int) Math.round(((dataModel.getTotalWeight() * MAGNIFICATION_FACTOR) / MAX_WEIGHT));
			if(weightPercentage > MAGNIFICATION_FACTOR)
				weightPercentage = MAGNIFICATION_FACTOR;
			
			int amountPercentage = (int) Math.round(((dataModel.getTotalValue() * MAGNIFICATION_FACTOR) / MAX_AMOUNT));
			if(amountPercentage > MAGNIFICATION_FACTOR)
				amountPercentage = MAGNIFICATION_FACTOR;
			
			int numberOfTimesEmptiedPercentage = (int) Math.round(((dataModel.getNumberOfTimesEmptied() * MAGNIFICATION_FACTOR) / MAX_NO_OF_TIMES_EMPTIED));
			if(numberOfTimesEmptiedPercentage > MAGNIFICATION_FACTOR)
				numberOfTimesEmptiedPercentage = MAGNIFICATION_FACTOR;
			
			x = 30;
			g2.setColor(Color.black);
			g2.drawString(rcm.getName(), x, y + 20);
			x += 100;
			
			g2.setColor(new Color(255, 122, 66));
			g2.fillRect(x, y, itemCountPercentage, 10);
			if(itemCountPercentage > 0)
				g2.drawString((int)dataModel.getNumberOfItems() + "", x + itemCountPercentage + 15, y + 7);
			y += 10;
			
			g2.setColor(new Color(71, 95, 84));
			g2.fillRect(x, y, weightPercentage, 10);
			if(weightPercentage > 0)
				g2.drawString(dataModel.getTotalWeight() + " lbs", x + weightPercentage + 15, y + 7);
			y += 10;
			
			g2.setColor(new Color(224, 73, 87));
			g2.fillRect(x, y, amountPercentage, 10);
			if(amountPercentage > 0)
				g2.drawString(dataModel.getTotalValue() + " $", x + amountPercentage + 15, y + 7);
			y += 10;
			
			g2.setColor(Color.blue);
			g2.fillRect(x, y, numberOfTimesEmptiedPercentage, 10);
			if(numberOfTimesEmptiedPercentage > 0)
				g2.drawString((int)dataModel.getNumberOfTimesEmptied() + "", x + numberOfTimesEmptiedPercentage + 15, y + 9);
			y += 10;
			
			y += 20;
			
			
		}
		
		//Draw Legend
		x = 30;
		y += 20;
		
		g2.setColor(new Color(255, 122, 66));
		g2.fillRect(x, y, 20, 10);
		g2.setColor(Color.black);
		x += 22;
		g2.drawString("Item Count", x, y + 10);
		x += 65;
		
		g2.setColor(new Color(71, 95, 84));
		g2.fillRect(x, y, 20, 10);
		g2.setColor(Color.black);
		x += 22;
		g2.drawString("Total Weight", x, y + 10);
		x += 75;
		
		g2.setColor(new Color(224, 73, 87));
		g2.fillRect(x, y, 20, 10);
		g2.setColor(Color.black);
		x += 22;
		g2.drawString("Total Amount", x, y + 10);
		x += 80;
		
		g2.setColor(Color.blue);
		g2.fillRect(x, y, 20, 10);
		g2.setColor(Color.black);
		x += 22;
		g2.drawString("Number of Times Emptied", x, y + 10);
			
	}

	// repaint graph when display is updated
	public void updateDisplay(Date startDate, Date endDate, boolean allRcms, String selectedRcmName) {
		this.startDate = startDate;
		this.endDate = startDate;
		this.allRcms = allRcms;
		this.selectedRcmName = selectedRcmName;
		
		revalidate();
		repaint();
	}

	public Dimension getPreferredSize() {
		return new Dimension(570, 325);
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

}
