package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.service.RcmService;

public class RcmUI extends JPanel{
	private Rcm rcm;
	
	public RcmUI(String name) {
		
		RcmService service = new RcmService();
		this.rcm = service.getRcmByName(name);
		
		add(Box.createRigidArea(new Dimension(750,20)));
		add(getStatusPanel());
		add(getDisplayPanel());
		add(Box.createRigidArea(new Dimension(750,20)));
		add(getItemButtonPanel());
		add(Box.createRigidArea(new Dimension(750,20)));
		add(getDispensePanel());
		add(Box.createRigidArea(new Dimension(20,20)));
		add(getExtrasPanel());
		setVisible(true);
		
	}
	
	private JPanel getStatusPanel() {
		JPanel statusPanel = new JPanel();
		statusPanel.setBackground(Color.LIGHT_GRAY);
		statusPanel.setBorder(new TitledBorder("StatusPanel"));
		JLabel statusLabel = new JLabel();
		statusLabel.setText(rcm.getStatus().toString());
		//statusLabel.setText("Hello");
		
		statusPanel.add(statusLabel);
		return statusPanel;
		
	}
	
	private JPanel getDisplayPanel() {
		JPanel displayPanel = new JPanel();
		displayPanel.setBorder(new TitledBorder("DisplayPanel"));
		
		displayPanel.setPreferredSize(new Dimension(600, 300));
		return displayPanel;
	}
	
	private JPanel getItemButtonPanel() {
		JPanel itemButtonPanel = new JPanel();
		itemButtonPanel.setLayout(new GridLayout(2, 4, 10, 10));
		itemButtonPanel.setBorder(new TitledBorder("ItemButtonPanel"));
		
		int i = 0;
		for(i=0; i<9; i++) {
			JButton button = new JButton("Aluminium" + i+"");
			button.setPreferredSize(new Dimension(150,20));
			itemButtonPanel.add(button);
		}
		
		itemButtonPanel.setPreferredSize(new Dimension(600,100));
		return itemButtonPanel;
	}
	
	private JPanel getDispensePanel() {
		JPanel dispensePanel = new JPanel();
		dispensePanel.setBorder(new TitledBorder("DispensePanel"));
		dispensePanel.setPreferredSize(new Dimension(300, 400));
		dispensePanel.setLayout(new BoxLayout(dispensePanel, BoxLayout.Y_AXIS));

		
		ImageIcon imageIcon = new ImageIcon("resources/moneyDispenser.png");
	    Image image = imageIcon.getImage(); // transform it 
	    Image newimg = image.getScaledInstance(200, 300,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
	    ImageIcon moneyIcon = new ImageIcon(newimg);  // transform it back
	    
	    JLabel moneyImageLabel = new JLabel();
	    moneyImageLabel.setIcon(moneyIcon);
	    
	    JLabel moneyLabel = new JLabel();
	    moneyLabel.setText("Total Money Dispensed: $1000");
		
	    dispensePanel.add(moneyImageLabel);
	    dispensePanel.add(Box.createRigidArea(new Dimension(60,40)));
	    dispensePanel.add(moneyLabel);
		
		return dispensePanel;
		
		
	}
	
	private JPanel getExtrasPanel() {
		JPanel extrasPanel = new JPanel();
		extrasPanel.setBorder(new TitledBorder("ExtrasPanel"));
		extrasPanel.setPreferredSize(new Dimension(350, 400));
		extrasPanel.setLayout(new BoxLayout(extrasPanel, BoxLayout.Y_AXIS));
		
		ImageIcon imageIcon = new ImageIcon("resources/Recycle.png");
	    Image image = imageIcon.getImage(); // transform it 
	    ImageIcon recycleIcon = new ImageIcon(image);  // transform it back
	    
	    JLabel recycleImageLabel = new JLabel();
	    recycleImageLabel.setIcon(recycleIcon);

	    JButton dispenseButton = new JButton("Dispense");
	    JButton metricConversionButton = new JButton("Convert Weight");
	    JButton helpButton = new JButton("Help");
		
	    extrasPanel.add(recycleImageLabel, extrasPanel.CENTER_ALIGNMENT);
	    extrasPanel.add(Box.createRigidArea(new Dimension(0,20)));
	    extrasPanel.add(Box.createRigidArea(new Dimension(150,0)));
	    extrasPanel.add(dispenseButton);
	    extrasPanel.add(Box.createRigidArea(new Dimension(0,20)));
	    extrasPanel.add(metricConversionButton);
	    extrasPanel.add(Box.createRigidArea(new Dimension(0,20)));
	    extrasPanel.add(helpButton);
	    
		return extrasPanel;
		
		
	}
}