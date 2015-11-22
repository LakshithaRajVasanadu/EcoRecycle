package ui;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import com.ecoRecycle.repository.RcmRepository;
import com.ecoRecycle.service.ItemManager;
import com.ecoRecycle.service.LocationService;
import com.ecoRecycle.service.RcmService;

import ui.AdminLogin.ButtonListener;

public class RmosUi 
{
	/*creating a frame for the rmos window*/
	private JFrame frame;
	private JPanel contentPane;
		
	/**
	* Launch the application.
	*/
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					RmosUi window = new RmosUi();
					window.frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	* Create the application.
	*/
	public RmosUi() 
	{
		initialize();
	}

	/**
	* Initialize the contents of the frame.
	*/
	private void initialize() 
	{
			frame = new JFrame();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			frame.setSize(screenSize.width/2, screenSize.height);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			//contentPane = new JPanel();
			
		    
			createMenuBar();
	}
	
	/**
	 * Menu bar creation for the RMOS window
	 */
	private void createMenuBar() 
	{
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBorder(new BevelBorder(BevelBorder.RAISED));
        frame.add(menuBar);
        
        /*Creating 2 menu's on the menu bar*/
        JMenu rcmMenu = new JMenu("OPERATIONS");
        menuBar.add(rcmMenu);
        
        JMenu logOut = new JMenu("LOGOUT");
        logOut.add(Box.createHorizontalGlue());
        menuBar.add(logOut);
        
        /*Creation of submenu under "operations" menu and submenus under "RCM Operation*/
        JMenu rcmOperations = new JMenu("RCM OPERATIONS");
        rcmMenu.add(rcmOperations);
        
        JMenuItem addRcm = new JMenuItem("ADD RCM");
        rcmOperations.add(addRcm);
        
        JMenuItem remRcm = new JMenuItem("REMOVE RCM");
        rcmOperations.add(remRcm);
        
        JMenuItem rcmStatus = new JMenuItem("RCM STATUS");
        rcmOperations.add(rcmStatus);
        
        /*Creation of submenu under "operations" menu and submenus under "Item Operation*/
        
        JMenu itemOperations = new JMenu("ITEM OPERATIONS");
        rcmMenu.add(itemOperations);
        
        JMenuItem addItem = new JMenuItem("ADD ITEM");
        itemOperations.add(addItem);
        
        JMenuItem removeItem = new JMenuItem("REMOVE ITEM");
        itemOperations.add(removeItem);
        
        JMenuItem changePrice = new JMenuItem("CHANGE PRICE OF AN ITEM");
        itemOperations.add(changePrice);
        
        /*Creation of submenu under "operations" menu and submenus under "Rcm status reports*/
        JMenu rcmStatusMenu = new JMenu("RCM STATUS REPORTS");
        rcmMenu.add(rcmStatusMenu);
        
        JMenuItem amountInRcm = new JMenuItem("AMOUNT IN RCM");
        rcmStatusMenu.add(amountInRcm);
        
        JMenuItem currentCapacity = new JMenuItem("CURRENT CAPACITY IN RCM");
        rcmStatusMenu.add(currentCapacity);
        
        JMenuItem activateRcm = new JMenuItem("ACTIVATE RCM");
        rcmStatusMenu.add(activateRcm);
        
        /**
         * Action listener for Add Rcm function
         */
        
        class AddRcmAction implements ActionListener 
        {
        	public void actionPerformed (ActionEvent e)
        	{
        		
        		 JLabel headingLabel, rcmName, locationName, totCapacity, totCash, rcmstatus, comboHeader;
                 JTextField rcmNameText,locationNameText,totCapacityText, totCashText, statusText;
                 JButton addButton;
                 
                 JPanel pane = new JPanel();
                 pane.setPreferredSize(new Dimension(20, 10));
       		  	 pane.setBackground(Color.red);
       		     pane.setAlignmentX(pane.LEFT_ALIGNMENT);
       		     pane.setBorder(BorderFactory.createEmptyBorder(200,200,200,200));
                 pane.setLayout(new BoxLayout(pane,BoxLayout.Y_AXIS));
        		
        		frame.add(pane);
                pane.revalidate();
                pane.repaint();
                
                pane.add(Box.createRigidArea(new Dimension(50,50)));
                headingLabel = new JLabel("Enter RCM details");
                headingLabel.setAlignmentX(pane.LEFT_ALIGNMENT);
                
                pane.add(headingLabel);
               
                pane.add(Box.createRigidArea(new Dimension(10,10)));
                
                rcmName = new JLabel("RCM NAME: ");
                rcmName.setAlignmentX(pane.LEFT_ALIGNMENT);
               // rcmName.setAlignmentY(pane.CENTER_ALIGNMENT);
                pane.add(rcmName);
                
                pane.add(Box.createRigidArea(new Dimension(10,10)));
                
                rcmNameText = new JTextField(10);
               // rcmNameText.setPreferredSize(new Dimension(5, 1));
                rcmNameText.setMaximumSize( rcmNameText.getPreferredSize() );
                rcmNameText.setMinimumSize( rcmNameText.getPreferredSize() );
                rcmName.setAlignmentX(pane.CENTER_ALIGNMENT);
                pane.add(rcmNameText);
                pane.add(Box.createRigidArea(new Dimension(10,10)));
                
                /*Combobox creation to show all the locations*/
                comboHeader = new JLabel("LOCATIONS");
                pane.add(comboHeader);
                
                LocationService ls = new LocationService();
                ArrayList<String> returnedLocations = ls.getAllLocations();
                
                JComboBox comboBox = new JComboBox(returnedLocations.toArray());
                
                pane.add(comboBox);
                pane.add(Box.createRigidArea(new Dimension(10,10)));
                                         
                totCapacity = new JLabel("TOTAL CAPACITY OF RCM:");
           
                pane.add(totCapacity);
                totCapacityText = new JTextField(10);
                totCapacityText.setMaximumSize( totCapacityText.getPreferredSize() );
                totCapacityText.setMinimumSize( totCapacityText.getPreferredSize() );
                //totCapacityText.setPreferredSize(new Dimension(5, 10));
                pane.add(totCapacityText);
                pane.add(Box.createRigidArea(new Dimension(10,10)));
                
                totCash = new JLabel("TOTAL CASH VALUE OF RCM:");
                pane.add(totCash);
                totCashText = new JTextField(10);
                totCashText.setMaximumSize( totCashText.getPreferredSize());
                totCashText.setMinimumSize( totCashText.getPreferredSize());
                pane.add(totCashText);
                pane.add(Box.createRigidArea(new Dimension(10,10)));
                
                
                
                addButton = new JButton("ADD RCM");
                pane.add(addButton);
                pane.add(Box.createRigidArea(new Dimension(10,300)));
                
                /**
                 * Action Listener for "Add Rcm" Button
                 */
                class ButtonListener implements ActionListener 
                {
                	ButtonListener()
            		{
            			
            		}
                	public void actionPerformed (ActionEvent e)
                	{
                		System.out.println("Button is clicked");
                		String value1 =rcmNameText.getText();
                		
                		
                		String value2 =comboBox.getSelectedItem().toString();
                		
                		String value3 = totCapacityText.getText();
                		int val3 = Integer.parseInt(value3);
                		
                		String value4 = totCashText.getText();
                		int val4 = Integer.parseInt(value4);
                		
                		
                		
                		RcmService rs = new RcmService();
                		rs.addRcm(value1,value2 , val3, val4);
                	}
                }
                
                addButton.addActionListener(new ButtonListener());
        	}
        }
        
        addRcm.addActionListener(new AddRcmAction() );
        
        /**
         * Action Listener for Remove Rcm
         */
        class RemoveRcmAction implements ActionListener 
        {
        	public void actionPerformed (ActionEvent e)
        	{
        		JButton removeButton;
        		JLabel headerLabel;
        		JPanel pane = new JPanel();
        		frame.add(pane);
        		pane.setBackground(Color.red);
                pane.revalidate();
                pane.repaint();
                
                JLabel comboRcmHeader = new JLabel("SELECT RCM TO BE REMOVED");
                pane.add(comboRcmHeader);
                
                RcmService rs = new RcmService();
                
                ArrayList<String> returnedNames = rs.getAllRcm();
                System.out.println(returnedNames.size());
       			JComboBox comboBox = new JComboBox(returnedNames.toArray());
                pane.add(comboBox);
                
                removeButton = new JButton("REMOVE RCM");
                pane.add(removeButton);
                
                /**
                 * ActionListener for Remove Rcm Button
                 */
                class ComboListener implements ActionListener 
                {
                	ComboListener()
            		{
                		
            		}
                	public void actionPerformed (ActionEvent e)
                	{
                		Object removedRcm = comboBox.getSelectedItem();
                		String value1 =comboBox.getSelectedItem().toString();
                		RcmService rs = new RcmService();
                		
                		if(rs.removeRcm(value1))
                		{
                			comboBox.removeItem(removedRcm);
                			comboBox.revalidate();
                			comboBox.repaint();
                		}
                		
                	}                	
                }
                removeButton.addActionListener(new ComboListener());
        	}
                
        }
        remRcm.addActionListener(new RemoveRcmAction() );
        
       
        
       /**
         * Action listener for Add Item function
         */
        
        class AddItemAction implements ActionListener 
        {
        	public void actionPerformed (ActionEvent ae)
        	{ 
        		JPanel pane = new JPanel();
                frame.add(pane);
                pane.revalidate();
                pane.repaint();
                
                JLabel itemName, isbio, isValid, pricePerLb,itemComboHeader;
                JTextField itemNameText, isbioText, isValidText, pricePerLbText;
                JButton addItemButton;
				
                pane.setLayout(new BoxLayout(pane,BoxLayout.Y_AXIS));
                
                itemName = new JLabel("ITEM NAME ");
                                
                pane.add(itemName);
                itemNameText = new JTextField();
                itemNameText.setPreferredSize(new Dimension(5, 1));
                pane.add(itemNameText);
                
                /*Combobox creation to show all the locations*/
                /*itemComboHeader = new JLabel("ITEMS");
                pane.add(itemComboHeader);
                ItemService Is = new ItemService();
                ArrayList<String> returnedItems = Is.getAllItems();
                JComboBox comboBox = new JComboBox(returnedItems.toArray());
                pane.add(comboBox);*/
                                         
               
                
                pricePerLb = new JLabel("ENTER Price Per lb");
                pane.add(pricePerLb);
                pricePerLbText = new JTextField();
                pane.add(pricePerLbText);
                
                addItemButton = new JButton("ADD ITEM");
                pane.add(addItemButton);
                
                /**
                 * Action Listener for "Add Item" Button
                 */
                class AddItemButtonListener implements ActionListener 
                {
                	AddItemButtonListener()
            		{
            			
            		}
                	public void actionPerformed (ActionEvent ae)
                	{
                		System.out.println("Button is clicked");
                		String value1 =itemNameText.getText();
                		
                		
                		//String value2 =comboBox.getSelectedItem().toString();
                		
                		//int val3 = Integer.parseInt(value3);
                		
                		//int val4 = Integer.parseInt(value4);
                		
                		String value4 = pricePerLbText.getText();
                		double val4 = Double.parseDouble(value4);
                		
                		ItemManager is = new ItemManager();
                		is.addItem(value1, val4);
                	}
                }
                
                addItemButton.addActionListener(new AddItemButtonListener());
        	}
        }
        
        addItem.addActionListener(new AddItemAction() );
        
        /**
         * Action Listener for Remove Item
         */
        class RemoveItemAction implements ActionListener 
        {
        	public void actionPerformed (ActionEvent e)
        	{
        		JButton removeItemButton;
        		JLabel headerLabel;
        		JPanel pane = new JPanel();
        		frame.add(pane);
        		pane.setBackground(Color.red);
                pane.revalidate();
                pane.repaint();
                
                JLabel comboItemHeader = new JLabel("SELECT ITEM TO BE REMOVED");
                pane.add(comboItemHeader);
                
                ItemManager is = new ItemManager();
                
                ArrayList<String> returnedItems = is.getAllItems();
                System.out.println(returnedItems.size());
       			JComboBox comboBox = new JComboBox(returnedItems.toArray());
                pane.add(comboBox);
                
                removeItemButton = new JButton("REMOVE ITEM");
                pane.add(removeItemButton);
                
                /**
                 * ActionListener for RemoveItemButton
                 */
                class ComboItemListener implements ActionListener 
                {
                	ComboItemListener()
            		{
                		
            		}
                	public void actionPerformed (ActionEvent e)
                	{
                		Object updatedItem = comboBox.getSelectedItem();
                		String value1 =comboBox.getSelectedItem().toString();
                		
                		
                		
                		ItemManager is = new ItemManager();
                		
                		if(is.updateItem(value1))
                		{
                			comboBox.removeItem(updatedItem);;
                			comboBox.revalidate();
                			comboBox.repaint();
                		}
                		
                	}                	
                }
                removeItemButton.addActionListener(new ComboItemListener());
        	}
                
        }
        removeItem.addActionListener(new RemoveItemAction() );
        
        /*change price of an item*/
        class ChangeItemPriceAction implements ActionListener 
        {
        	public void actionPerformed (ActionEvent e)
        	{
        		JButton changePriceButton;
        		JLabel headerLabel;
        		JPanel pane = new JPanel();
        		pane.setLayout(new BoxLayout(pane,BoxLayout.Y_AXIS));
        		frame.add(pane);
        		pane.setBackground(Color.blue);
                pane.revalidate();
                pane.repaint();
                
                JLabel comboItemPriceChangeHeader = new JLabel("SELECT ITEM TO CHANGE THE PRICE");
                pane.add(comboItemPriceChangeHeader);
                
                ItemManager is = new ItemManager();
                
                ArrayList<String> returnedItems = is.getAllItems();
                System.out.println(returnedItems.size());
       			JComboBox comboBox = new JComboBox(returnedItems.toArray());
                pane.add(comboBox);
                
                
                JTextField price = new JTextField(40);
                price.setMaximumSize( price.getPreferredSize() );
                price.setMinimumSize( price.getPreferredSize() );
                pane.add(price);
                
                changePriceButton = new JButton("CHANGE PRICE");
                pane.add(changePriceButton);
                
                
                
                /**
                 * ActionListener for ChangePriceButton
                 */
                class ComboItemPriceChangeListener implements ActionListener 
                {
                	ComboItemPriceChangeListener()
            		{
                		
            		}
                	public void actionPerformed (ActionEvent e)
                	{
                		Object priceChangedItem = comboBox.getSelectedItem();
                		String value1 =comboBox.getSelectedItem().toString();
                		
                		
                    	String value5 = price.getText();
                		if(!(null == value5 || value5.isEmpty()))
                		{
                			int val5 = Integer.parseInt(value5);
         
                		
                			ItemManager is = new ItemManager();
                		
                			if(is.changePrice(value1, val5))
                			{
                				JOptionPane.showMessageDialog(frame, "Price Changed for item " + value1 + " to " +val5);
                			}
                			else
                			{
                				JOptionPane.showMessageDialog(frame, "Unable to change price");
                			}
                		}
                		
                	}                	
                }
                changePriceButton.addActionListener(new ComboItemPriceChangeListener());
        	}
                
        }
        changePrice.addActionListener(new ChangeItemPriceAction() );
      
        frame.setJMenuBar(menuBar);

	}
}

