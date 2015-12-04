package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.service.RcmService;
import com.ecoRecycle.service.RmosService;
import com.ecoRecycle.ui.RmosUI;


public class RcmUIManager extends JFrame
{
	
	private JPanel rcmChooserPanel = new JPanel();
	private JPanel rcmPanel = new JPanel();
	private JComboBox<String> rcmComboBox;
	
	private RcmService rcmService = new RcmService();
	


	/**
	* Create the application.
	*/
	public RcmUIManager() 
	{
		super("RCM");

		initialize();


	}

	/**
	* Initialize the contents of the frame.
	*/
	private void initialize() 
	{
			
			
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			this.setSize(new Dimension(screenSize.width/2 - 20, screenSize.height));
			setLocation(screenSize.width/2 + 10, 0);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			this.addComponents();
			
			this.setVisible(true);
			
	}
	
	private void addComponents() {
		Container contentPane = this.getContentPane();
		prepareRcmChooserPanel();
		prepareRcmPanel(rcmComboBox.getSelectedItem().toString());
		contentPane.setLayout(new BorderLayout());
		contentPane.add(rcmChooserPanel, BorderLayout.NORTH);
		contentPane.add(rcmPanel, BorderLayout.SOUTH);
		
	}
	
	private void prepareRcmChooserPanel() {
		rcmChooserPanel.setLayout(new BorderLayout());
		rcmChooserPanel.setPreferredSize(new Dimension(0, 70));
		
		TitledBorder border = new TitledBorder("CHOOSE RCM");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 18));
		rcmChooserPanel.setBorder(border);
		
		JLabel rmosLabel = new JLabel("Choose Rcm:");
		rcmComboBox = new JComboBox<String>();
		
		Set<Rcm> rcmList = rcmService.getAllRcms();
		for(Rcm rcm : rcmList) {
			rcmComboBox.addItem(rcm.getName());
		}
		
		rcmComboBox.setSelectedItem("rcm256");
		
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
		
		JPanel rcmChooserInnerPanel = new JPanel();
		rcmChooserInnerPanel.add(rmosLabel);
		rcmChooserInnerPanel.add(rcmComboBox);
		
		rcmChooserPanel.add(rcmChooserInnerPanel, BorderLayout.WEST);
	}
	
	private void prepareRcmPanel(String rcmName) {
		rcmPanel.removeAll();
        System.out.println("Switching to Rcm:" + rcmName);
	//	rcmPanel.add(new RcmUI(rcmName, this));
		
//		TitledBorder border = new TitledBorder("RMOS SPECIFIC PANEL");
//		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 18));
//		rmosPanel.setBorder(border);
		
		rcmPanel.setPreferredSize(new Dimension(0, 875));
		
		this.revalidate();
		this.repaint();
	}
	

}
