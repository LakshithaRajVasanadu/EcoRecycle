package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.ecoRecycle.service.AdministratorService;

import ui.AdminLogin.ButtonListener;

public class AdminPanel extends JPanel{
	
	private AdministratorService service = new AdministratorService();
	
	JTextField usernameTextField;
	JPasswordField passwordField;
	
	public AdminPanel() {
		
		TitledBorder border = new TitledBorder("Admin Login");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 18));
		setBorder(border);
		setPreferredSize(new Dimension(300, 700));
		
		preparePanel();
		
	}
	
	private void preparePanel() {
		ImageIcon imageIcon = new ImageIcon("resources/adminLogin.jpg");
		JLabel imageLabel = new JLabel(imageIcon);
		this.add(imageLabel, CENTER_ALIGNMENT);
		
		
 		this.add(Box.createRigidArea(new Dimension(80,50)));

		this.add(new JLabel("Username"));
 		this.add(Box.createRigidArea(new Dimension(0,20)));

		usernameTextField = new JTextField(20);
		this.add(usernameTextField);
		
		this.add(new JLabel("Password"));
 		this.add(Box.createRigidArea(new Dimension(0,10)));

		passwordField = new JPasswordField(20);
		this.add(passwordField);
		
 		this.add(Box.createRigidArea(new Dimension(0,30)));
		this.add(addLoginButton());
	}
	
	private JButton addLoginButton() {
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String username = usernameTextField.getText();
				String password = String.valueOf(passwordField.getPassword());
				
				if(!service.isUserValid(username, password))
					JOptionPane.showMessageDialog(null,
							"User Login Failed", "Error",
							JOptionPane.ERROR_MESSAGE);
				else
				{
					// NEed to load next ui
				}
				
			}
		});
		
		
		return loginButton;
	}

}
