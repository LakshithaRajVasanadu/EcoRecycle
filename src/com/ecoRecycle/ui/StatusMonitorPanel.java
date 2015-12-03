package com.ecoRecycle.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.ecoRecycle.helper.RcmStatus;
import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.service.RmosManager;
import com.ecoRecycle.service.RmosService;
import com.ecoRecycle.service.StatusManager;

public class StatusMonitorPanel extends JPanel implements Observer{
	
	private Rmos rmos;
	private RmosManager rmosManager;
	private StatusManager statusManager;
	
	private JPanel rcmPanel = new JPanel();
	
	public StatusMonitorPanel(Rmos rmos, RmosManager rmosManager, StatusManager statusManager) {
		this.rmos = rmos;
		this.rmosManager = rmosManager;
		this.statusManager = statusManager;
		
		statusManager.addObserver(this);
		rmosManager.addObserver(this);
		
		this.addComponents();
		// observe all rcms
	}
	
	private void addComponents() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		TitledBorder border = new TitledBorder("StatusMonitor");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 10));
		this.setBorder(border);
		this.add(getMonitorIcon());
		populateRcmPanel();
		this.add(rcmPanel);
	}
	
	private JPanel getMonitorIcon() {
		JPanel monitorIconPanel = new JPanel();
		
		TitledBorder border = new TitledBorder("Icon");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 10));
		monitorIconPanel.setBorder(border);
		
		//monitorIconPanel.setSize(200, 50);
		monitorIconPanel.setPreferredSize(new Dimension(200, 100));
		
		ImageIcon imageIcon = new ImageIcon("resources/statusMonitor.jpg");
		Image image = imageIcon.getImage(); // transform it
		image = image.getScaledInstance(200, 90,
				java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(image); // transform it back
		
		JLabel monitorIconLabel = new JLabel(imageIcon);
		
		monitorIconPanel.add(monitorIconLabel);
		
		return monitorIconPanel;
	}
	
	private void populateRcmPanel() {
		TitledBorder border = new TitledBorder("Data");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 10));
		rcmPanel.setBorder(border);
		
		rcmPanel.setLayout(new GridLayout(0,1,0,3));
		
		List<Rcm> rcmList = rmosManager.getAllRcms();
		for(Rcm rcm: rcmList) {
			JLabel label = new JLabel("            " + rcm.getName());
			label.setOpaque(true);
			if(rcm.getStatus() == RcmStatus.ACTIVE)
				label.setBackground(Color.green);
			else
				label.setBackground(Color.red);
			rcmPanel.add(label);
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		rmos = new RmosService().getRmosByName(rmos.getName());
		rmosManager = new RmosManager(rmos);
		statusManager = new StatusManager(rmos);
		
		System.out.println("Called update...");
		rcmPanel.removeAll();
		populateRcmPanel();
		this.revalidate();
		this.repaint();
		// TODO Auto-generated method stub
		
	}
	
}
