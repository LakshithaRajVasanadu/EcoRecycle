package com.ecoRecycle.helper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.service.RmosManager;
import com.ecoRecycle.service.RmosService;
import com.ecoRecycle.service.StatusManager;
import com.ecoRecycle.ui.rmos.*;

//Timer to pull the messages reagrding status of the rcm
public class NotificationTimer implements ActionListener {
	private Rmos rmos;
	private RmosManager rmosManager;
	private StatusManager statusManager;
	private JPanel parentPanel;
	
	Timer t;

	public NotificationTimer(JPanel parentPanel, Rmos rmos, RmosManager rmosManager, StatusManager statusManager) {
		this.rmos = rmos;
		this.rmosManager = rmosManager;
		this.statusManager = statusManager;
		this.parentPanel = parentPanel;
		
		t = new Timer(100, this);
		t.start();
	}

	public void actionPerformed(ActionEvent ae) {
		rmos = new RmosService().getRmosByName(rmos.getName());
		rmosManager = new RmosManager(rmos);
		statusManager = new StatusManager(rmos);
		
		((NotificationPanel) parentPanel).populateMessagePanel();
	}

}
