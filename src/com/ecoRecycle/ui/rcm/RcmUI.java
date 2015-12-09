package com.ecoRecycle.ui.rcm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javafx.scene.text.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.ecoRecycle.helper.Message;
import com.ecoRecycle.helper.Notifiable;
import com.ecoRecycle.helper.RcmStatus;
import com.ecoRecycle.helper.TransactionStatus;
import com.ecoRecycle.model.Item;
import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Transaction;
import com.ecoRecycle.model.TransactionItem;
import com.ecoRecycle.service.ItemManager;
import com.ecoRecycle.service.RcmService;
import com.ecoRecycle.service.ReloadTransactionService;
import com.ecoRecycle.service.RmosManager;
import com.ecoRecycle.service.StatusManager;
import com.ecoRecycle.service.TransactionService;
import com.ecoRecycle.service.UnloadTransactionService;

public class RcmUI extends JPanel implements Observer, Notifiable{
	private JFrame parentFrame;
	private Rcm rcm;
	private RcmService rcmService = new RcmService();
	
	private RmosManager rmosManager;
	private StatusManager statusManager;
	private ItemManager itemManager;
	
	private JLabel statusLabel = new JLabel();
	private JPanel rcmDetailsPanel;
	
	private JPanel displayPanel;
	private JLabel latestDisplayLabel = new JLabel();
	
	JPanel itemButtonPanel = new JPanel();
	JPanel dispensePanel = new JPanel();
	JPanel extrasPanel = new JPanel();
	JButton dispenseButton = new JButton("Dispense");
    JLabel newMoneyLabel = new JLabel();

	
	private UnloadTransactionService uservice;
	private ReloadTransactionService rservice;
	
	public RcmUI(String name, JFrame parentFrame, StatusManager statusManager, ItemManager itemManager, UnloadTransactionService uservice, ReloadTransactionService rservice) {
		rcm = rcmService.getRcmByName(name);
		rmosManager = new RmosManager(rcm.getRmos());
		this.statusManager = statusManager;
		this.itemManager = itemManager;

		this.itemManager.addObserver(this);
		this.statusManager.addObserver(this);
		
		this.uservice = uservice;
		this.rservice = rservice;
		
		this.uservice.addObserver(this);
		this.rservice.addObserver(this);
		
		this.parentFrame = parentFrame;
		this.addComponents();
	}
	
	private void addComponents() {
		this.setBackground(Color.black);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(getRcmDetailsPanel());
		this.add(getDisplayPanel());
		prepareItemButtonPanel();
		this.add(itemButtonPanel);
		
		JPanel newPanel = new JPanel();
		newPanel.setBackground(Color.black);
		newPanel.add(getDispensePanel());
		newPanel.add(Box.createRigidArea(new Dimension(50,20)));
		newPanel.add(getExtrasPanel());
		
		this.add(newPanel);
	}
	
	//Panel to provide information of a particular rcm
	private JPanel getRcmDetailsPanel() {
		rcmDetailsPanel = new JPanel();
		rcmDetailsPanel.setBackground(Color.black);
		rcmDetailsPanel.setPreferredSize(new Dimension(780, 55));
		rcmDetailsPanel.setLayout(new BorderLayout());
		
		JLabel nameLabel = new JLabel("NAME: " + rcm.getName() + 
				"				                                  " +
				"                            ");
		rcmDetailsPanel.add(nameLabel,  BorderLayout.WEST);
		
		prepareStatusLabel();
		
		JLabel locationLabel = new JLabel("Location: " + rcm.getLocation().getCity());
		rcmDetailsPanel.add(locationLabel, BorderLayout.EAST);
		
		return rcmDetailsPanel;
	}

	//Label to represent if the rcm is active or inactive
	private void prepareStatusLabel() {
		rcmDetailsPanel.remove(statusLabel);
		statusLabel = new JLabel();
		statusLabel.setText("Status: " + rcm.getStatus().toString());
		statusLabel.setOpaque(true);
		if(rcm.getStatus() == RcmStatus.ACTIVE) {
			statusLabel.setBackground(Color.green);
			rcmDetailsPanel.setBackground(Color.green);
		}
		else {
			statusLabel.setBackground(Color.red);
			rcmDetailsPanel.setBackground(Color.red);
		}
		rcmDetailsPanel.add(statusLabel, BorderLayout.CENTER);
	}
	
	//panel for the display screen
	private JPanel getDisplayPanel() {
		displayPanel = new JPanel();
		displayPanel.setBorder(new TitledBorder("DISPLAY"));
		displayPanel.setBackground(new Color(244, 237, 218));
		latestDisplayLabel.setText("<html>Welcome to Recycling Station!! <br/><br/> Please add items to start recycling! </html>");
		
		displayPanel.setPreferredSize(new Dimension(200, 300));
		displayPanel.add(latestDisplayLabel);
		return displayPanel;
	}
	
	//Panel that contains the item buttons that can be recycled
	private void prepareItemButtonPanel() {
		itemButtonPanel.removeAll();
		
		itemButtonPanel.setLayout(new GridLayout(2, 4, 10, 10));
		itemButtonPanel.setBackground(Color.black);
		itemButtonPanel.setBorder(new LineBorder(Color.orange));
		
		List<Item> items = itemManager.getAllItems();
		for(Item item: items) {
			JButton button = new JButton(item.getType());
			button.setName(item.getType());
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					newMoneyLabel.setText("");
					RcmService rcmserv = new RcmService();
					String str = "<html><ul>";
					Message message = rcmserv.addItemToTransactionV2(button.getText(), rcm, statusManager);
					displayPanel.removeAll();
					
					if(message.isSuccessful() == false) {
						
						TransactionService serv = new TransactionService();
						Transaction lastTrans = serv.getLastTransaction(rcm);
						Set<TransactionItem> retrivedItems =  lastTrans.getTransactionItems();
						
						double pricePerLb = 0;
						for(TransactionItem t :  retrivedItems)
						{
							pricePerLb =  t.getPrice()/t.getWeight();
							double poundToKgs = t.getWeight() *  0.45;
							str = str + "<li>ITEM: " + t.getItem().getType().toUpperCase() + " , WEIGHT: " + t.getWeight() + " lbs " +
									"( " + poundToKgs + " kgs)" + "* " + pricePerLb  + "$/lb = $" + t.getPrice() + 
									"</li>";
							
						}
						str += "<li>" + message.getMessage() + "</li> ";
					} 
					else {
					TransactionService serv = new TransactionService();
					Transaction lastTrans = serv.getLastTransaction(rcm);
					Set<TransactionItem> retrivedItems =  lastTrans.getTransactionItems();
					
					double pricePerLb = 0;
					for(TransactionItem t :  retrivedItems)
					{
						pricePerLb =  t.getPrice()/t.getWeight();
						double poundToKgs = t.getWeight() *  0.45;
						str = str + "<li>ITEM: " + t.getItem().getType().toUpperCase() + " , WEIGHT: " + t.getWeight() + " lbs " +
								"( " + poundToKgs + " kgs)" + "* " + pricePerLb  + "$/lb = $" + t.getPrice() + 
								"</li>";
						
					}
					}
					str = str + "</ul></html>";
					
					JLabel label = new JLabel(str);
					displayPanel.add(label);
					setVisible(true);
					
					displayPanel.revalidate();
					displayPanel.repaint();
					 
				}
			});
			button.setEnabled(item.getIsValid());
			if(rcm.getStatus() == RcmStatus.INACTIVE || rcm.getStatus() == RcmStatus.REMOVED) {
				button.setEnabled(false);
			}
			itemButtonPanel.add(button);
		}
		itemButtonPanel.setPreferredSize(new Dimension(600,100));
		itemButtonPanel.revalidate();
		itemButtonPanel.repaint();
	}
	
	//DispenserPanel to show that the user can collect the amount form the dispenser
	private JPanel getDispensePanel() {
		
		dispensePanel.setBorder(new TitledBorder("DISPENSER"));
		dispensePanel.setPreferredSize(new Dimension(280, 400));
		dispensePanel.setLayout(new BoxLayout(dispensePanel, BoxLayout.Y_AXIS));
		dispensePanel.setBackground(new Color(244, 237, 218));
		
		ImageIcon imageIcon = new ImageIcon("resources/moneyDispenser.png");
	    Image image = imageIcon.getImage(); // transform it 
	    Image newimg = image.getScaledInstance(200, 300,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
	    ImageIcon moneyIcon = new ImageIcon(newimg);  // transform it back
	    
	    JLabel moneyImageLabel = new JLabel();
	    moneyImageLabel.setIcon(moneyIcon);
	    dispensePanel.add(moneyImageLabel);
	    dispensePanel.add(Box.createRigidArea(new Dimension(100, 10)));
	    dispensePanel.add(newMoneyLabel);
	    dispensePanel.add(Box.createRigidArea(new Dimension(60,40)));
	  
		return dispensePanel;
	}
	
	//Panel that has the dispense button and few other button that provide information regarding the rcm
	private JPanel getExtrasPanel() {
		
		JPanel extrasPanel = new JPanel();
		extrasPanel.setBackground(Color.black);

		extrasPanel.setPreferredSize(new Dimension(350, 400));
		extrasPanel.setLayout(new BoxLayout(extrasPanel, BoxLayout.Y_AXIS));
		
		ImageIcon imageIcon = new ImageIcon("resources/Recycle.png");
	    Image image = imageIcon.getImage(); // transform it 
	    ImageIcon recycleIcon = new ImageIcon(image);  // transform it back
	    
	    JLabel recycleImageLabel = new JLabel();
	    recycleImageLabel.setIcon(recycleIcon);

	    
	    dispenseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Transaction disptrans;
				TransactionService serv = new TransactionService();
				Message msg = serv.dispense(rcm);
				
				String dispenseStr = "";
				if(msg.getAmount() > 0) {
					dispenseStr += "<html>Thanks for Recycling! Please collect your " + msg.getPaymentType().toString() + " for $" + msg.getAmount() + "<br> Please add items to start recycling! </html>";
					newMoneyLabel.setText(msg.getPaymentType().toString() + ": $" + msg.getAmount());
				} else {
					dispenseStr += "You have no pending payments. Please add items to start Recycling!";
				}
				
					 JLabel moneyLabel = new JLabel(dispenseStr);
					 displayPanel.removeAll();
					 displayPanel.add(moneyLabel);
					 displayPanel.revalidate();
					 displayPanel.repaint();
					 setVisible(true);
				}
			});

	    JButton help = new JButton("Help");
	    help.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog( displayPanel, 
						"<html>WELCOME<br> <br>"
						+ "<html><ul>Insert item one by one into the receptacle</ul> "
						+ "<html><ul>Click on dispense button to collect money</ul> </html>");
			}
		});
	    JButton infoButton = new JButton("Info");
	    infoButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog( displayPanel, 
						"<html>Recycling is a process to convert waste materials into reusable<br> "
						+ "<html>material to prevent waste of potentially useful materials,reduce <br> "
						+ "<html>the consumption of fresh raw materials, reduce energy usage,reduce<br> "
						+ "<html>air pollution and water pollution by reducing the need for conventional <br>"
						+ "<html>waste disposal and lower greenhouse gas emissions as compared to<br>"
						+ "<html>plastic production.");
			}
		});
		
	   
	    extrasPanel.add(recycleImageLabel, extrasPanel.CENTER_ALIGNMENT);
	    extrasPanel.add(Box.createRigidArea(new Dimension(0,20)));
	    extrasPanel.add(Box.createRigidArea(new Dimension(150,0)));
	    extrasPanel.add(dispenseButton);
	    extrasPanel.add(Box.createRigidArea(new Dimension(0,20)));
	    extrasPanel.add(help);
	    extrasPanel.add(Box.createRigidArea(new Dimension(0,20)));
	    extrasPanel.add(infoButton);
	    
		return extrasPanel;
	}

	//The rcm has to change the properties of the ui when it becomes inactive or when it is removed
	@Override
	public void update(Observable o, Object arg) {
		rcm = rcmService.getRcmByName(rcm.getName());
		prepareStatusLabel();
		prepareItemButtonPanel();
		
		if(rcm.getStatus() == RcmStatus.INACTIVE || rcm.getStatus() == RcmStatus.REMOVED) {
			setPanelEnabled(itemButtonPanel, false);
		}
		
		this.revalidate();
		this.repaint();
		
	}
	
	private void setPanelEnabled(JPanel panel, Boolean isEnabled) {
		panel.setEnabled(isEnabled);

		Component[] components = panel.getComponents();

		for (int i = 0; i < components.length; i++) {
			if (components[i].getClass().getName() == "javax.swing.JPanel") {
				setPanelEnabled((JPanel) components[i], isEnabled);
			}

			components[i].setEnabled(isEnabled);
		}
	}
}

