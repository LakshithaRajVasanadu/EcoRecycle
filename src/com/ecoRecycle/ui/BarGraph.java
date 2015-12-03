package com.ecoRecycle.ui;

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
	private static final int MAGNIFICATION_FACTOR = 500;
	
	public BarGraph(Rmos rmos,  RmosManager rmosManager, StatusManager statusManager,  Date startDate, Date endDate, boolean allRcms, String selectedRcmName, HashMap<Rcm, UsageStatisticsModel> statisticsDataMap) {
		this.rmos = rmos;
		this.rmosManager = rmosManager;
		this.statusManager = statusManager;
		this.statisticsManager = new StatisticsManager(rmos);
		this.statisticsDataMap = statisticsDataMap;
		
		//////
	/*	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String startDateStr = "2015-11-22";
		String endDateStr = "2015-11-25";
		Date sdate = null, edate = null;
		try {

			sdate = formatter.parse(startDateStr);
			edate = formatter.parse(endDateStr);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		startDate = sdate;
		startDate = edate;*/
		//////
		this.startDate = startDate;
		this.endDate = startDate;
		System.out.println("Bar graph: date:" + startDate + endDate);
		this.allRcms = allRcms;
		this.selectedRcmName = selectedRcmName;
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(new Font("TimesNewRoman", Font.BOLD, 10));
		System.out.println("In paint comp..");
		
		int x = 30;
		int y = 10;
		
		rmos = new RmosService().getRmosByName(rmos.getName());
		rmosManager = new RmosManager(rmos);
		statusManager = new StatusManager(rmos);
		statisticsManager = new StatisticsManager(rmos);
		
		
		//Draw bargraph
		//HashMap<Rcm, UsageStatisticsModel> statisticsDataMap = statisticsManager.getUsageStatistics(startDate, endDate);
		for (Entry<Rcm, UsageStatisticsModel> i : statisticsDataMap.entrySet()){
			Rcm rcm = i.getKey();
			UsageStatisticsModel dataModel = i.getValue();
			
			
			if(allRcms == false) {
				if(!(rcm.getName().equals(selectedRcmName)))
					continue;
			}
			
			int itemCountPercentage = (int) Math.round(((dataModel.getNumberOfItems() * MAGNIFICATION_FACTOR) / MAX_NO_OF_ITEMS));
			int weightPercentage = (int) Math.round(((dataModel.getTotalWeight() * MAGNIFICATION_FACTOR) / MAX_WEIGHT));
			int amountPercentage = (int) Math.round(((dataModel.getTotalValue() * MAGNIFICATION_FACTOR) / MAX_AMOUNT));
			int numberOfTimesEmptiedPercentage = (int) Math.round(((dataModel.getNumberOfTimesEmptied() * MAGNIFICATION_FACTOR) / MAX_NO_OF_TIMES_EMPTIED));
			
			System.out.println("BarGraph: rcm name:" + rcm.getName());
			System.out.println("BarGraph: rcm name:" + itemCountPercentage);
			System.out.println("BarGraph: rcm name:" + weightPercentage);
			System.out.println("BarGraph: rcm name:" + numberOfTimesEmptiedPercentage);
			System.out.println("BarGraph: rcm name:" + amountPercentage);
			
			x = 30;
			g2.setColor(Color.black);
			g2.drawString(rcm.getName(), x, y + 20);
			x += 100;
			
			g2.setColor(Color.pink);
			g2.fillRect(x, y, itemCountPercentage, 10);
			if(itemCountPercentage > 0)
				g2.drawString((int)dataModel.getNumberOfItems() + "", x + itemCountPercentage + 15, y + 7);
			y += 10;
			
			g2.setColor(Color.yellow);
			g2.fillRect(x, y, weightPercentage, 10);
			if(weightPercentage > 0)
				g2.drawString(dataModel.getTotalWeight() + " lbs", x + weightPercentage + 15, y + 7);
			y += 10;
			
			g2.setColor(Color.orange);
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
		
		g2.setColor(Color.pink);
		g2.fillRect(x, y, 20, 10);
		g2.setColor(Color.black);
		x += 22;
		g2.drawString("Item Count", x, y + 10);
		x += 65;
		
		g2.setColor(Color.yellow);
		g2.fillRect(x, y, 20, 10);
		g2.setColor(Color.black);
		x += 22;
		g2.drawString("Total Weight", x, y + 10);
		x += 75;
		
		g2.setColor(Color.orange);
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
		
		
		
/////////////////////////////////////////////////////////////////
		
	} // end method paintComponent

	// repaint graph when display is updated
	public void updateDisplay(Date startDate, Date endDate, boolean allRcms, String selectedRcmName) {
		this.startDate = startDate;
		this.endDate = startDate;
		this.allRcms = allRcms;
		this.selectedRcmName = selectedRcmName;
		
		System.out.println("Bar graph Update Method: date:" + startDate + endDate);
		
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
