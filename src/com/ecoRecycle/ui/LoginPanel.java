package com.ecoRecycle.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.border.TitledBorder;

import ui.RmosUI;

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
		
		TitledBorder border = new TitledBorder("LOGIN PANEL");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 10));
		this.setBorder(border);
		this.setPreferredSize(new Dimension(300, 700));
		
		this.addComponents();
	}
	
	private void addComponents() {
		
		this.disableLogoutButton();
		this.add(Box.createRigidArea(new Dimension(0,650)));
		this.add(getAdminPanel());
	}
	
	private JPanel getAdminPanel() {
		JPanel adminPanel = new JPanel();
		
		TitledBorder border = new TitledBorder("Admin Login");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 8));
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
	
	private JPanel getUsernamePanel() {
		JPanel userNamePanel = new JPanel();
		userNamePanel.setSize(new Dimension(15, 0));
				
		JLabel usernameLabel = new JLabel("USERNAME ");
		usernameLabel.setFont(new Font("Courier New", Font.BOLD, 17));
		usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		usernameTextField = new JTextField(15);
		
		userNamePanel.add(usernameLabel);
		userNamePanel.add(usernameTextField);
		
		return userNamePanel;
	}
	
	private JPanel getPasswordPanel() {
		JPanel passwordPanel = new JPanel();
		passwordPanel.setSize(new Dimension(15, 0));
				
		JLabel passwordLabel = new JLabel("PASSWORD ");
		passwordLabel.setFont(new Font("Courier New", Font.BOLD, 17));
		passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		passwordField = new JPasswordField(15);
		
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		
		return passwordPanel;
	}
	
	private JPanel getLoginButtonPanel() {
		JPanel loginButtonPanel = new JPanel();
		loginButtonPanel.setSize(new Dimension(15, 0));
		
		loginButtonPanel.setLayout(new BorderLayout());
		
		JButton loginButton = new JButton("LOGIN");
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String username = usernameTextField.getText();
				String password = String.valueOf(passwordField.getPassword());
				
				if(adminService.isUserValid(username, password)) {
					Administrator admin = adminService.getAdmin(username);
					
					if(admin.getId() == rmos.getAdmin().getId()) {
						System.out.println("Login success");
						
						enableLogoutButton();
						
						CardLayout cl = (CardLayout) (parentPanel.getLayout());
						cl.show(parentPanel, RmosUI.MAIN_PANEL);
					}
					else {
						System.out.println("Login failed - admin doesn not belong to this Rmos");
						JOptionPane.showMessageDialog(null,
								"User Login Failed", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
					
				}
				else {
					System.out.println("Login failed - username password do not match");
					JOptionPane.showMessageDialog(null,
							"User Login Failed", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		loginButtonPanel.add(loginButton, BorderLayout.EAST);
		
		return loginButtonPanel;
	}

	private void enableLogoutButton() {
		JButton logoutButton = ((RmosUIManager)( this.parentFrame)).getLogoutButton();
		logoutButton.setVisible(true);
	}
	
	private void disableLogoutButton() {
		((RmosUIManager)( this.parentFrame)).getLogoutButton().setVisible(false);
	}
}
