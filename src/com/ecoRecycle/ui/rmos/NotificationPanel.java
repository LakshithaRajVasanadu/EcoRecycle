package com.ecoRecycle.ui.rmos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.ecoRecycle.helper.NotificationTimer;
import com.ecoRecycle.helper.RcmStatus;
import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.service.RmosManager;
import com.ecoRecycle.service.RmosService;
import com.ecoRecycle.service.StatusManager;

import javax.swing.Timer;

public class NotificationPanel extends JPanel{
	private Rmos rmos;
	private RmosManager rmosManager;
	private StatusManager statusManager;
	private boolean blinkingOn = true;
	
	private JLabel messageIconLabel;
	
	private JPanel messagePanel = new JPanel();
	
	public NotificationPanel(Rmos rmos, RmosManager rmosManager, StatusManager statusManager) {
		this.rmos = rmos;
		this.rmosManager = rmosManager;
		this.statusManager = statusManager;
		this.setBackground(new Color(184, 69, 67));
		
		this.addComponents();
		new NotificationTimer(this, rmos, rmosManager, statusManager);
		
	}
	
	private void addComponents() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel label = new JLabel("NOTIFICATIONS");
		label.setFont(new Font("TimesNewRoman", Font.BOLD, 12));
		this.add(label);
		this.add(getMessageIcon());
		populateMessagePanel();
		this.add(messagePanel);
	}
	
	//Panel to add the message icon
	private JPanel getMessageIcon() {
		JPanel messageIconPanel = new JPanel();
		messageIconPanel.setBackground(new Color(184, 69, 67));
		
		messageIconPanel.setPreferredSize(new Dimension(90, 120));
		
		ImageIcon imageIcon = new ImageIcon("resources/messageIcon.png");
		Image image = imageIcon.getImage(); 
		image = image.getScaledInstance(150, 100,
				java.awt.Image.SCALE_SMOOTH); 
		imageIcon = new ImageIcon(image); 
		
		messageIconLabel = new JLabel(imageIcon);
		
		messageIconPanel.add(messageIconLabel);
		
		return messageIconPanel;
	}
	
	//Panel to display the incoming notification messages
	public void populateMessagePanel() {
		messagePanel.removeAll();
		
		rmos = new RmosService().getRmosByName(rmos.getName());
		rmosManager = new RmosManager(rmos);
		statusManager = new StatusManager(rmos);
		messagePanel.setOpaque(true);
		messagePanel.setBackground(new Color(184, 69, 67));
		
		messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
		JLabel labelOuter = new JLabel("                                     ");
		
		List<Rcm> rcmList = rmosManager.getAllRcms();
		Collections.sort(rcmList, new Comparator<Rcm>(){
		       public int compare(Rcm o1, Rcm o2) {
		           return o1.getName().compareTo(o2.getName());
		        }
		    });
		
		messagePanel.add(labelOuter);
		for(Rcm rcm: rcmList) {
			if(rcm.getStatus() == RcmStatus.INACTIVE && (!(rcm.getReason().equals("Admin change")))) {
				JLabel label = new JLabel("<html><ul><li><b><u>" + rcm.getName() + "</u></b> "
											+ "	<br/> has been <br/>deactivated."
											+ " <br/> " + rcm.getReason() 
											+ "</li><ul></html>");
				label.setHorizontalTextPosition(SwingConstants.LEADING);
				label.setPreferredSize(new Dimension(600, 300));
			
			messagePanel.add(label);
			}
		}
		
		messagePanel.revalidate();
		messagePanel.repaint();
	}
	
	public void setBlinking(boolean flag) {
	    this.blinkingOn = flag;
	  }
	  public boolean getBlinking() {
	    return this.blinkingOn;
	  }

	public JLabel getMessageIconLabel() {
		return messageIconLabel;
	}
}

 class TimerListener implements ActionListener {
    private NotificationPanel bl;
    private Color bg;
    private Color fg;
    private boolean isForeground = true;
    
    public TimerListener(NotificationPanel bl) {
      this.bl = bl;
    }
 
    public void actionPerformed(ActionEvent e) {
      if (bl.getBlinking()) {
        if (isForeground) {
          bl.getMessageIconLabel().setVisible(true);
        }
        else {
          bl.getMessageIconLabel().setVisible(false);
        }
        isForeground = !isForeground;
      }
      else {
        // here we want to make sure that the label is visible
        // if the blinking is off.
        if (isForeground) {
          bl.getMessageIconLabel().setVisible(true);
          isForeground = false;
        }
      }
    }
    
  }
 
