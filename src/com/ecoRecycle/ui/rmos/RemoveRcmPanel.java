package com.ecoRecycle.ui.rmos;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.ecoRecycle.helper.Message;
import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.service.LocationService;
import com.ecoRecycle.service.RcmService;
import com.ecoRecycle.service.RmosManager;
import com.ecoRecycle.service.StatusManager;

public class RemoveRcmPanel extends JPanel{
	private Rmos rmos;
	private RmosManager rmosManager;
	private StatusManager statusManager;
	private LocationService locationService = new LocationService();
	
	private JPanel removeRcmPanel = new JPanel();
	
	public RemoveRcmPanel(Rmos rmos,  RmosManager rmosManager, StatusManager statusManager) {
		this.rmos = rmos;
		this.rmosManager = rmosManager;
		this.statusManager = statusManager;
		setBackground(new Color(245, 214, 196));
		this.addComponents();
	}
	
	private void addComponents() {
		prepareRemoveRcmPanel();
		this.add(removeRcmPanel);
	}

	//Panel for remove rcm
	private void prepareRemoveRcmPanel() {
		
		removeRcmPanel.removeAll();
		removeRcmPanel.setLayout(new BoxLayout(removeRcmPanel, BoxLayout.Y_AXIS));
		removeRcmPanel.setBackground(new Color(245, 214, 196));
		JLabel rcmLabel = new JLabel("Rcm Name");
		JComboBox<String> rcmComboxBox = new JComboBox<String>();
		
		List<Rcm> rcms = rmosManager.getAllRcms();
		for(Rcm rcm : rcms)
			rcmComboxBox.addItem(rcm.getName());
		
		JButton removeButton = new JButton("Remove");
		removeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane
						.showConfirmDialog(null, "Do you want to remove?",
								"Confirm", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {
					String rcmName = rcmComboxBox.getSelectedItem().toString();
					Rcm rcm = new RcmService().getRcmByName(rcmName);
					
					Message msg = rmosManager.removeRcm(rcm.getId());
					if(msg.isSuccessful()) {
						JOptionPane.showMessageDialog(null,
								"Removed successfully", "Info",
								JOptionPane.INFORMATION_MESSAGE);
						prepareRemoveRcmPanel() ;
						
						
					}else {
						JOptionPane.showMessageDialog(null,
								msg.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		removeRcmPanel.add(rcmLabel);
		removeRcmPanel.add(rcmComboxBox);
		removeRcmPanel.add(removeButton);
		
		removeRcmPanel.revalidate();
		removeRcmPanel.repaint();
		
	}
}
