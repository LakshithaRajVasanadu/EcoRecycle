package com.ecoRecycle.ui.rmos;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


import com.ecoRecycle.model.Administrator;
import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.service.AdministratorService;

public class LoginPanel extends JPanel{
	private AdministratorService adminService = new AdministratorService();
	
	JTextField usernameTextField;
	JPasswordField passwordField;
	
	private JPanel parentPanel ;
	private JFrame parentFrame;
	private Rmos rmos;
	
	public LoginPanel(JFrame parentFrame, JPanel parent, Rmos rmos) {
		
		this.parentFrame = parentFrame;
		this.parentPanel = parent;
		this.rmos = rmos;
		this.setBackground(Color.black);
		this.setPreferredSize(new Dimension(300, 700));
		this.addComponents();
	}
	
	//add components to the login panel
	private void addComponents() {
		this.disableLogoutButton();
		JLabel welcomeLabel = new JLabel("                   Welcome to EcoRecycle Monitoring System!!!");
		welcomeLabel.setFont(new Font("TimesNewRoman", Font.ITALIC, 20));		
		welcomeLabel.setForeground(new Color(74, 175, 37));
		this.add(welcomeLabel);
		this.add(Box.createRigidArea(new Dimension(100,100)));
		this.add(getAdminPanel());
		this.add(Box.createRigidArea(new Dimension(500,200)));
		this.add(getBorderPanel());
	}
	
	private JPanel getAdminPanel() {
		JPanel adminPanel = new JPanel();
		adminPanel.setBackground(Color.black);
		
		TitledBorder border = new TitledBorder("ADMIN LOGIN");
		border.setBorder(new LineBorder(Color.orange));
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 18));
		border.setTitleColor(new Color(162, 118, 237));
		adminPanel.setBorder(border);
		
		adminPanel.setLayout(new BoxLayout(adminPanel, BoxLayout.Y_AXIS));
	
		adminPanel.add(getPicturePanel());
		adminPanel.add(getUsernamePanel());
		adminPanel.add(getPasswordPanel());
		adminPanel.add(getLoginButtonPanel());
		
		return adminPanel;
	}
	
	private JPanel getPicturePanel() {
		JPanel picPanel = new JPanel();
		picPanel.setSize(new Dimension(15, 0));
		picPanel.setBackground(Color.black);
		
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("resources/adminLogin.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		picPanel.add(picLabel);
		return picPanel;
	}
	
	//Panel for username
	private JPanel getUsernamePanel() {
		JPanel userNamePanel = new JPanel();
		userNamePanel.setSize(new Dimension(15, 0));
		userNamePanel.setBackground(Color.black);
				
		JLabel usernameLabel = new JLabel("USERNAME ");
		usernameLabel.setForeground(Color.white);
		usernameLabel.setFont(new Font("TimesNewRoman", Font.BOLD, 16));
		usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		usernameTextField = new JTextField(15);
		
		userNamePanel.add(usernameLabel);
		userNamePanel.add(usernameTextField);
		
		return userNamePanel;
	}
	
	//Panel for password
	private JPanel getPasswordPanel() {
		JPanel passwordPanel = new JPanel();
		passwordPanel.setSize(new Dimension(15, 0));
		passwordPanel.setBackground(Color.black);
				
		JLabel passwordLabel = new JLabel("PASSWORD ");
		passwordLabel.setForeground(Color.white);
		passwordLabel.setFont(new Font("TimesNewRoman", Font.BOLD, 16));
		passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		passwordField = new JPasswordField(15);
		
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		
		return passwordPanel;
	}
	
	//Panel for login button
	private JPanel getLoginButtonPanel() {
		JPanel loginButtonPanel = new JPanel();
		loginButtonPanel.setSize(new Dimension(15, 0));
		loginButtonPanel.setBackground(Color.black);
		
		loginButtonPanel.setLayout(new BorderLayout());
		
		JButton loginButton = new JButton("LOGIN");
		loginButton.setBackground(Color.white);
		
		loginButton.addActionListener(new ActionListener() {
			
			//Authentication of username and password
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String username = usernameTextField.getText();
				String password = String.valueOf(passwordField.getPassword());
				
				if(adminService.isUserValid(username, password)) {
					Administrator admin = adminService.getAdmin(username);
					
					if(admin.getId() == rmos.getAdmin().getId()) {
						enableLogoutButton();
						
						CardLayout cl = (CardLayout) (parentPanel.getLayout());
						cl.show(parentPanel, RmosUI.MAIN_PANEL);
					}
					else {
						JOptionPane.showMessageDialog(null,
								"User Login Failed", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
					
				}
				else {
					JOptionPane.showMessageDialog(null,
							"User Login Failed", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		loginButtonPanel.add(loginButton, BorderLayout.EAST);
		
		return loginButtonPanel;
	}

	//Logout button to be enabled when then RmosUiManager is opened
	private void enableLogoutButton() {
		JButton logoutButton = ((RmosUIManager)( this.parentFrame)).getLogoutButton();
		logoutButton.setVisible(true);
	}
	
	//To disable the logout button
	private void disableLogoutButton() {
		((RmosUIManager)( this.parentFrame)).getLogoutButton().setVisible(false);
	}
	
	//Panel for image on the login screen
	private JPanel getBorderPanel() {
		JPanel picPanel = new JPanel();
		picPanel.setSize(new Dimension(15, 0));
		picPanel.setBackground(Color.black);
		
		ImageIcon imageIcon = new ImageIcon("resources/border.jpg");
		Image image = imageIcon.getImage(); // transform it
		Image newimg = image.getScaledInstance(650, 150,
				java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg); // transform it back
		
		JLabel picLabel = new JLabel(imageIcon);
		picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		picPanel.add(picLabel);
		return picPanel;
	}
}
