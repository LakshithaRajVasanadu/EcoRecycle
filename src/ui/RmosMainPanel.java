package ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.ecoRecycle.helper.Message;
import com.ecoRecycle.model.Item;
import com.ecoRecycle.model.Location;
import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.repository.RmosRepository;
import com.ecoRecycle.service.ItemManager;
import com.ecoRecycle.service.LocationService;
import com.ecoRecycle.service.RcmService;
import com.ecoRecycle.service.ReloadTransactionService;
import com.ecoRecycle.service.RmosManager;
import com.ecoRecycle.service.StatusManager;
import com.ecoRecycle.service.UnloadTransactionService;

public class RmosMainPanel extends JPanel implements Observer{
	
	private LocationService locationService;
	private RmosManager rmosManager;
	private RcmService rcmService;
	private StatusManager statusManager;
	private ItemManager itemManager;
	private Rmos rmos;
	
	JComboBox<String> rcmComboxBox;
	
	JTable table;
	Object[] columnNames = {"Rcm Name", "Status"};


	

	public RmosMainPanel(Rmos rmos) {
		this.rmos = rmos;
		
		locationService = new LocationService();
		rmosManager = new RmosManager(rmos);
		statusManager = new StatusManager(rmos);
		itemManager = new ItemManager();
		
		rmosManager.addObserver(this);
		statusManager.addObserver(this);
	//	rmos.addObserver(this);
		
		rcmService = new RcmService();
		
		//setPreferredSize(new Dimension(300, 700));
		this.add(addRcmPanel());
		this.add(removeRcmPanel());
		this.add(checkStatusPanel());
		this.add(itemPanel());
		
		
	}
	
	private JPanel addRcmPanel() {
		JPanel addRcmPanel = new JPanel();
		addRcmPanel.setLayout(new BoxLayout(addRcmPanel, BoxLayout.Y_AXIS));
		
		TitledBorder border = new TitledBorder("ADD RCM");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 18));
		addRcmPanel.setBorder(border);
		
		JLabel nameLabel = new JLabel("Name");
		JTextField nameField = new JTextField(20);
		
		JLabel locationLabel = new JLabel("Location");
		JComboBox<String> locationComboBox = new JComboBox<String>();
		
		List<Location> locations = locationService.getAllLocations();
		for(Location loc : locations)
			locationComboBox.addItem(loc.getCity());
		
		
		JLabel capacityLabel = new JLabel("Capacity");
		JTextField capacityField = new JTextField(20);
		
		JLabel cashValueLabel = new JLabel("Total Cash");
		JTextField cashField = new JTextField(20);
		
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String rcmName = nameField.getText();
				String location = locationComboBox.getSelectedItem().toString();
				double totalCapacity = Double.parseDouble(capacityField.getText());
				double totalCash = Double.parseDouble(cashField.getText());
				
				//Do input validation
				
				Message msg = rmosManager.addRcm(rcmName, location, totalCapacity, totalCash);
				if(msg.isSuccessful()) {
					JOptionPane.showMessageDialog(null,
							"Added successfully", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					updatePanel();
					
				}else {
					JOptionPane.showMessageDialog(null,
							msg.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		addRcmPanel.add(nameLabel);
		addRcmPanel.add(nameField);
		addRcmPanel.add(locationLabel);
		addRcmPanel.add(locationComboBox);
		addRcmPanel.add(capacityLabel);
		addRcmPanel.add(capacityField);
		addRcmPanel.add(cashValueLabel);
		addRcmPanel.add(cashField);
		addRcmPanel.add(addButton);
		
		return addRcmPanel;
	}
	
	private JPanel removeRcmPanel() {
		JPanel removeRcmPanel = new JPanel();
		removeRcmPanel.setLayout(new BoxLayout(removeRcmPanel, BoxLayout.Y_AXIS));
		
		TitledBorder border = new TitledBorder("Remove RCM");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 18));
		removeRcmPanel.setBorder(border);
		
		JLabel rcmLabel = new JLabel("Rcm Name");
		rcmComboxBox = new JComboBox<String>();
		
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
					Rcm rcm = rcmService.getRcmByName(rcmName);
					
					Message msg = rmosManager.removeRcm(rcm.getId());
					if(msg.isSuccessful()) {
						JOptionPane.showMessageDialog(null,
								"Removed successfully", "Info",
								JOptionPane.INFORMATION_MESSAGE);
						updatePanel();
						
						
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
		
		return removeRcmPanel;
	}
	
	private JPanel checkStatusPanel(){
		
		JPanel checkStatusPanel = new JPanel();
		checkStatusPanel.setLayout(new BoxLayout(checkStatusPanel, BoxLayout.Y_AXIS));
		
		TitledBorder border = new TitledBorder("Status");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 18));
		checkStatusPanel.setBorder(border);
		
		DefaultTableModel model = (new DefaultTableModel(columnNames, 0) {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		});
		List<Rcm> rcmList = rmosManager.getAllRcms();
		for(Rcm rcm : rcmList) {
			Object[] rowData = new Object[] { rcm.getName(), rcm.getStatus().toString()};
			model.addRow(rowData);
		}

		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		checkStatusPanel.add(scrollPane);
		
		JLabel nameLabel = new JLabel("Name:");
		JTextField nameField = new JTextField(20);
		
		JButton activateButton = new JButton("Activate");
		activateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				rmos = new RmosRepository().getRmosByName(rmos.getName());
//				rmosManager = new RmosManager(rmos);
//				statusManager = new StatusManager(rmos);
				
				String rcmName = nameField.getText();
				
				Rcm rcm = rcmService.getRcmByName(rcmName);
				if(rcm == null){
					JOptionPane.showMessageDialog(null,
							"No such rcm", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Message msg = statusManager.activateRcm(rcm.getId());
				
				if(msg.isSuccessful()) {
					JOptionPane.showMessageDialog(null,
							"activated successfully", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					updatePanel();
					
					
				}else {
					JOptionPane.showMessageDialog(null,
							msg.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JButton deactivateButton = new JButton("Deactivate");
		deactivateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				rmos = new RmosRepository().getRmosByName(rmos.getName());
//				rmosManager = new RmosManager(rmos);
//				statusManager = new StatusManager(rmos);
				
				String rcmName = nameField.getText();
				
				Rcm rcm = rcmService.getRcmByName(rcmName);
				if(rcm == null){
					JOptionPane.showMessageDialog(null,
							"No such rcm", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				Message msg = statusManager.deactivateRcm(rcm.getId());
				
				if(msg.isSuccessful()) {
					JOptionPane.showMessageDialog(null,
							"deactivated successfully", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					updatePanel();
					
					
				}else {
					JOptionPane.showMessageDialog(null,
							msg.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JButton checkMoneyButton = new JButton("Check Amount");
		JLabel moneyLabel = new JLabel();
		
		checkMoneyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String rcmName = nameField.getText();
				Rcm rcm = rcmService.getRcmByName(rcmName);
				
				moneyLabel.setText("$" + (rcm.getTotalCashValue() - rcm.getCurrentCashValue()));
				
			}
		});
		
		JButton capacityButton = new JButton("Check Capacity");
		JLabel capacityLabel = new JLabel();
		
		capacityButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String rcmName = nameField.getText();
				Rcm rcm = rcmService.getRcmByName(rcmName);
				
				String status="";
				if(rcm.isFull())
					status = "FULL";
				
				capacityLabel.setText("Current:" + rcm.getCurrentCapacity() + "Total:" + rcm.getTotalCapacity() + status);
				
			}
		});
		
		JButton unloadButton = new JButton("Unload Rcm");
		unloadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String rcmName = nameField.getText();
				Rcm rcm = rcmService.getRcmByName(rcmName);
				
				UnloadTransactionService uservice = new UnloadTransactionService();
				Message msg = uservice.unloadRcm(rcm);
				
				if(msg.isSuccessful()) {
					JOptionPane.showMessageDialog(null,
							"unloaded contents successfully", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					updatePanel();
					
					
				}else {
					JOptionPane.showMessageDialog(null,
							msg.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JTextField cashField = new JTextField(20);
		
		JButton loadButton = new JButton("load Cash");
		loadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String rcmName = nameField.getText();
				Rcm rcm = rcmService.getRcmByName(rcmName);
				
				double money = Double.parseDouble(cashField.getText());
				
				ReloadTransactionService rservice = new ReloadTransactionService();
				Message msg = rservice.reloadRcm(rcm, money);
				
				if(msg.isSuccessful()) {
					JOptionPane.showMessageDialog(null,
							"loaded cash successfully", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					updatePanel();
					
					
				}else {
					JOptionPane.showMessageDialog(null,
							msg.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		
		checkStatusPanel.add(nameLabel);
		checkStatusPanel.add(nameField);
		checkStatusPanel.add(activateButton);
		checkStatusPanel.add(deactivateButton);
		checkStatusPanel.add(checkMoneyButton);
		checkStatusPanel.add(moneyLabel);
		checkStatusPanel.add(capacityButton);
		checkStatusPanel.add(capacityLabel);
		checkStatusPanel.add(unloadButton);
		checkStatusPanel.add(cashField);
		checkStatusPanel.add(loadButton);
		
		return checkStatusPanel;
	}
	
	private JPanel itemPanel(){
		
		JPanel itemPanel = new JPanel();
		itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
		
		TitledBorder border = new TitledBorder("Item Panel");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 18));
		itemPanel.setBorder(border);
		
		JLabel itemLabel = new JLabel("Item");
		JComboBox<String> itemComboxBox = new JComboBox<String>();
		
		List<Item> items = itemManager.getAllItems();
		for(Item item : items)
			itemComboxBox.addItem(item.getType());
		
		JButton addButton = new JButton("Add Item");
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String itemType = itemComboxBox.getSelectedItem().toString();
				Item item = itemManager.getItemByType(itemType);
				
				Message msg = itemManager.addItem(item.getId());
				if(msg.isSuccessful()) {
					JOptionPane.showMessageDialog(null,
							"added item successfully", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					updatePanel();
					
					
				}else {
					JOptionPane.showMessageDialog(null,
							msg.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JButton removeButton = new JButton("Remove Item");
		removeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String itemType = itemComboxBox.getSelectedItem().toString();
				Item item = itemManager.getItemByType(itemType);
				
				Message msg = itemManager.removeItem(item.getId());
				if(msg.isSuccessful()) {
					JOptionPane.showMessageDialog(null,
							"removed item successfully", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					updatePanel();
					
					
				}else {
					JOptionPane.showMessageDialog(null,
							msg.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		
		JLabel priceLabel = new JLabel("Price");
		JTextField priceField = new JTextField(20);
		
		JButton changeButton = new JButton("Change");
		
		changeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String itemType = itemComboxBox.getSelectedItem().toString();
				double newPrice = Double.parseDouble(priceField.getText());
				
				Item item = itemManager.getItemByType(itemType);
				
				Message msg = itemManager.changePrice(item.getId(), newPrice);
				if(msg.isSuccessful()) {
					JOptionPane.showMessageDialog(null,
							"changed price successfully", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					updatePanel();
					
					
				}else {
					JOptionPane.showMessageDialog(null,
							msg.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		
		itemPanel.add(itemLabel);
		itemPanel.add(itemComboxBox);
		itemPanel.add(addButton);
		itemPanel.add(removeButton);
		itemPanel.add(priceLabel);
		itemPanel.add(priceField);
		itemPanel.add(changeButton);
		
		return itemPanel;
		
		
	}
	

	@Override
	public void update(Observable o, Object arg) {
		
		// TODO Auto-generated method stub
		System.out.println("In update...\n");
		
		this.rmos = new RmosRepository().getRmosByName(rmos.getName());
		rmosManager = new RmosManager(this.rmos);
		statusManager = new StatusManager(this.rmos);
		
		rcmComboxBox.removeAllItems();
		
		List<Rcm> rcms = rmosManager.getAllRcms();
		for(Rcm rcm : rcms)
			rcmComboxBox.addItem(rcm.getName());
		
		rcmComboxBox.revalidate();
		rcmComboxBox.repaint();
		
		
		DefaultTableModel model = (new DefaultTableModel(columnNames, 0) {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		});
		
		List<Rcm> rcmList = rmosManager.getAllRcms();
		for(Rcm rcm : rcmList) {
			Object[] rowData = new Object[] { rcm.getName(), rcm.getStatus().toString()};
			model.addRow(rowData);
		}
		
		table.setModel(model);
		table.revalidate();
		table.repaint(); 
		
		
		
	}
	
	
	public void updatePanel() {
		// TODO Auto-generated method stub
				System.out.println("In update...\n");
				
				this.rmos = new RmosRepository().getRmosByName(rmos.getName());
				rmosManager = new RmosManager(this.rmos);
				statusManager = new StatusManager(this.rmos);
				
				rcmComboxBox.removeAllItems();
				
				List<Rcm> rcms = rmosManager.getAllRcms();
				for(Rcm rcm : rcms)
					rcmComboxBox.addItem(rcm.getName());
				
				rcmComboxBox.revalidate();
				rcmComboxBox.repaint();
				
				
				DefaultTableModel model = (new DefaultTableModel(columnNames, 0) {

				    @Override
				    public boolean isCellEditable(int row, int column) {
				       return false;
				    }
				});
				
				List<Rcm> rcmList = rmosManager.getAllRcms();
				for(Rcm rcm : rcmList) {
					Object[] rowData = new Object[] { rcm.getName(), rcm.getStatus().toString()};
					model.addRow(rowData);
				}
				
				table.setModel(model);
				table.revalidate();
				table.repaint(); 
				
	}
	

}
