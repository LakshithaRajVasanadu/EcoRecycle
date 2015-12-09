package com.ecoRecycle.ui.rmos;

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
		this.setBackground(Color.black);
		
		statusManager.addObserver(this);
		rmosManager.addObserver(this);
		
		this.addComponents();
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
	
	//Panel to get the image for monitoring status
	private JPanel getMonitorIcon() {
		JPanel monitorIconPanel = new JPanel();
		monitorIconPanel.setBackground(new Color(184, 69, 67));
		
		TitledBorder border = new TitledBorder("STATUS MONITOR");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 12));
		monitorIconPanel.setBorder(border);
		monitorIconPanel.setPreferredSize(new Dimension(200, 130));
		
		ImageIcon imageIcon = new ImageIcon("resources/statusMonitor.jpg");
		Image image = imageIcon.getImage(); // transform it
		image = image.getScaledInstance(200, 90,
				java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(image); // transform it back
		
		JLabel monitorIconLabel = new JLabel(imageIcon);
		
		monitorIconPanel.add(monitorIconLabel);
		
		return monitorIconPanel;
	}
	
	//Panel to show if the rcms are active or inactive
	private void populateRcmPanel() {
		rcmPanel.setBackground(new Color(184, 69, 67));
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

	//When there are changes, update method will be executed to show the changes to the user on the UI
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
	}
	
}
