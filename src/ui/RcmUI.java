package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.ecoRecycle.helper.TransactionStatus;
import com.ecoRecycle.model.Item;
import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.model.Transaction;
import com.ecoRecycle.model.TransactionItem;
import com.ecoRecycle.service.ItemManager;
import com.ecoRecycle.service.ObjFactory;
import com.ecoRecycle.service.RcmService;
import com.ecoRecycle.service.RmosService;
import com.ecoRecycle.service.TransactionItemMappingService;
import com.ecoRecycle.service.TransactionService;
import com.ecoRecycle.ui.RmosUI;

public class RcmUI extends JPanel implements Observer{
	private Rcm rcm;
	private ItemManager itemManager;
	private JPanel dispensePanel = null;
	private JPanel itemButtonPanel = null;
	private JPanel displayPanel = null;
	private JComboBox<String> rcmComboBox;
	private RcmService rcmService = new RcmService();
	private JPanel rcmPanel = new JPanel();
	
	public RcmUI(String name) {
		
		this.itemManager = ObjFactory.getInstance();
				
		RcmService service = new RcmService();
		this.rcm = service.getRcmByName(name);
		
		add(Box.createRigidArea(new Dimension(750,20)));
		add(getStatusPanel());
		displayPanel = getDisplayPanel();
		add(displayPanel);
		add(Box.createRigidArea(new Dimension(750,20)));
		itemButtonPanel = getItemButtonPanel(displayPanel);
		add(itemButtonPanel);
		//add(getItemButtonPanel(displayPanel));
		add(Box.createRigidArea(new Dimension(750,20)));
		
		dispensePanel = getDispensePanel();
		add(dispensePanel);
		add(Box.createRigidArea(new Dimension(20,20)));
		add(getExtrasPanel());
		setVisible(true);
		
		
		itemManager.addObserver(this);
		
		
	}
	
	private JPanel getStatusPanel() {
		JPanel statusPanel = new JPanel();
		statusPanel.setBackground(Color.LIGHT_GRAY);
		statusPanel.setBorder(new TitledBorder("StatusPanel"));
		JLabel statusLabel = new JLabel();
		statusLabel.setText(rcm.getStatus().toString());
		//statusLabel.setText("Hello");
		
		statusPanel.add(statusLabel);
		
		JLabel rcmLabel = new JLabel("Choose Rcm:");
		rcmComboBox = new JComboBox<String>();
		
		
		Set<Rcm> rcmList = rcmService.getAllRcms();
		for(Rcm rcm : rcmList) {
			rcmComboBox.addItem(rcm.getName());
		}
		
		rcmComboBox.setSelectedItem("Rcm1");
		
		rcmComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				JComboBox comboBox = (JComboBox) event.getSource();
				Object item = event.getItem();
				if (event.getStateChange() == ItemEvent.SELECTED) {
					prepareRcmPanel(item.toString());
				}				
			}
        });
		statusPanel.add(rcmComboBox);
		return statusPanel;
		
	}
	
	private void prepareRcmPanel(String rcmName) {
		rcmPanel.removeAll();
        System.out.println("Switching to Rcm:" + rcmName);
		rcmPanel.add(new RcmUI(rcmName));
		
//		TitledBorder border = new TitledBorder("RMOS SPECIFIC PANEL");
//		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 18));
//		rmosPanel.setBorder(border);
		
		rcmPanel.setPreferredSize(new Dimension(0, 875));
		
		this.revalidate();
		this.repaint();
	}
	
	
	private JPanel getDisplayPanel() {
		JPanel displayPanel = new JPanel();
		displayPanel.setBorder(new TitledBorder("DisplayPanel"));
		
		displayPanel.setPreferredSize(new Dimension(500, 150));
		return displayPanel;
	}
	
	private JPanel getItemButtonPanel(JPanel displayPanel) {
		JPanel itemButtonPanel = new JPanel();
		itemButtonPanel.setLayout(new GridLayout(2, 4, 10, 10));
		itemButtonPanel.setBorder(new TitledBorder("ItemButtonPanel"));
		
		List<Item> items = itemManager.getAllItems();
		for(Item item: items) {
			JButton button = new JButton(item.getType());
			button.setName(item.getType());
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					RcmService rcmserv = new RcmService();
					rcmserv.addItemToTransaction(button.getText(), rcm);
					TransactionService serv = new TransactionService();
					Transaction lastTrans = serv.getLastTransaction(rcm);
					Set<TransactionItem> retrivedItems =  lastTrans.getTransactionItems();
					
					double pricePerLb = 0;
					String str = "<html>";
					displayPanel.removeAll();
					for(TransactionItem t :  retrivedItems)
					{
						pricePerLb =  t.getPrice()/t.getWeight();
						double poundToKgs = t.getWeight() *  0.45;
						str = str + t.getItem().getType() + " :  " + t.getWeight() + " lbs " +
								"( " + poundToKgs + " kgs)" + "* " + pricePerLb + " = " + t.getPrice() + 
								"<br>";
						
					}
					str = str + "</html>";
					JLabel label = new JLabel(str);
					displayPanel.add(label);
					setVisible(true);
					displayPanel.revalidate();
					displayPanel.repaint();
					 
				}
			});
			
			itemButtonPanel.add(button);
		}
		
//		int i = 0;
//		for(i=0; i<9; i++) {
//			JButton button = new JButton("Aluminium" + i+"");
//			button.setPreferredSize(new Dimension(150,20));
//			itemButtonPanel.add(button);
//		}
		
		itemButtonPanel.setPreferredSize(new Dimension(600,100));
		return itemButtonPanel;
	}
	
	private JPanel getDispensePanel() {
		JPanel dispensePanel = new JPanel();
		dispensePanel.setBorder(new TitledBorder("DispensePanel"));
		dispensePanel.setPreferredSize(new Dimension(230, 350));
		dispensePanel.setLayout(new BoxLayout(dispensePanel, BoxLayout.Y_AXIS));

		
		ImageIcon imageIcon = new ImageIcon("resources/moneyDispenser.png");
	    Image image = imageIcon.getImage(); // transform it 
	    Image newimg = image.getScaledInstance(200, 300,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
	    ImageIcon moneyIcon = new ImageIcon(newimg);  // transform it back
	    
	    JLabel moneyImageLabel = new JLabel();
	    moneyImageLabel.setIcon(moneyIcon);
	    
	    //JLabel moneyLabel = new JLabel();
	    //moneyLabel.setText("Total Money Dispensed: $1000");
		
	    dispensePanel.add(moneyImageLabel);
	    dispensePanel.add(Box.createRigidArea(new Dimension(60,40)));
	   // dispensePanel.add(moneyLabel);
		
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
	    dispenseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Transaction disptrans;
				TransactionService serv = new TransactionService();
				disptrans = serv.getLastTransaction(rcm);
				if(disptrans.getStatus().equals(TransactionStatus.ACTIVE))
				{
					System.out.println("TotalPayment" + disptrans.getTotalPayment());
					 JLabel moneyLabel = new JLabel();
					 moneyLabel.setText("Total Money Dispensed:" + disptrans.getTotalPayment());
					 displayPanel.removeAll();
					 displayPanel.add(moneyLabel);
					 displayPanel.revalidate();
					 displayPanel.repaint();
					 setVisible(true);
					// JOptionPane.showMessageDialog( getDisplayPanel(), disptrans.getTotalPayment());
					 
					 disptrans.setStatus(TransactionStatus.DONE);
					 serv.updateTrasaction(disptrans);
					 
				}
				
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

	@Override
	public void update(Observable o, Object arg) {
		String str = (String) arg;
		ItemManager item = (ItemManager) o;
		System.out.println("ITS WORKING!!!!!!!!" + arg);
		Component[] components = itemButtonPanel.getComponents();
		for(int i = 0; i< components.length; i++){
			if(components[i].getName().equalsIgnoreCase(str)){
				components[i].setEnabled(item.isAdded());
				this.revalidate();
				this.repaint();
			}
		}
		
		
	
	
	
}}