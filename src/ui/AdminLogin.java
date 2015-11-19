package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
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

import com.ecoRecycle.service.AdministratorService;


public class AdminLogin 
{
	JFrame frame;
	private JPanel panel;
	private JLabel username, password,  picLabel,label1, label2;
	
	private JTextField usertext;
	private JPasswordField passwordtext;
	private JButton login;
	BufferedImage myPicture;
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
					AdminLogin window = new AdminLogin();
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
	public AdminLogin() 
	{
		
		panel = new JPanel();
		
		
		try
		{
			myPicture = ImageIO.read(new File("resources/adminLogin.jpg"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		picLabel = new JLabel(new ImageIcon(myPicture));

	    picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

	    
		username = new JLabel("USERNAME");
		username.setFont(new Font("Courier New", Font.BOLD, 17));
		username.setAlignmentX(Component.CENTER_ALIGNMENT);
		username.add(Box.createRigidArea(new Dimension(70,70)));
		
		//label1 = new JLabel("   ");
		//label2= new JLabel("   ");
		password = new JLabel("PASSOWRD");
		password.setFont(new Font("Courier New", Font.BOLD, 17));
		password.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		usertext = new JTextField(20);
		usertext.setMaximumSize(usertext.getPreferredSize() );
		usertext.setMinimumSize(usertext.getPreferredSize() );
		//usertext.setPreferredSize(new Dimension(5, 10));
		//usertext.setBounds(10, 10, 10, 5);
		usertext.setAlignmentX(Component.CENTER_ALIGNMENT);
		//usertext.add(Box.createRigidArea(new Dimension(70,100)));
		
		passwordtext = new JPasswordField(20);
		passwordtext.setMaximumSize( passwordtext.getPreferredSize() );
		passwordtext.setMinimumSize( passwordtext.getPreferredSize() );
		passwordtext.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		login = new JButton("LOGIN");
		
		//login.setMaximumSize( passwordtext.getPreferredSize() );
		//login.setMinimumSize( passwordtext.getPreferredSize() );
		
		
		//login.setBorder(BorderFactory.createLineBorder(Color.black));
		login.setAlignmentX(Component.CENTER_ALIGNMENT);
		login.addActionListener(new ButtonListener());
		
		initialize();	
	}
	

	/**
	* Initialize the contents of the frame.
	*/
	private void initialize() 
	{
		
		frame = new JFrame();
		frame.setBounds(50, 50, 450, 450);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("ADMIN LOGIN");
		
		setBoxLayout();
		
	}
	
	public void setBoxLayout()
	{
 		// the panel or the container must be passed in as a parameter to the constructor
 		panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
 		panel.add(picLabel);
 		panel.add(Box.createRigidArea(new Dimension(10,10)));
 		panel.add(username);
 		panel.add(Box.createRigidArea(new Dimension(10,10)));
 		panel.add(usertext);
 		panel.add(Box.createRigidArea(new Dimension(10,10)));
 		panel.add(password);
 		panel.add(Box.createRigidArea(new Dimension(10,10)));
 		panel.add(passwordtext);
 		panel.add(Box.createRigidArea(new Dimension(10,10)));
 		panel.add(login);	
 		panel.add(Box.createRigidArea(new Dimension(10,10)));
 		frame.add(panel);
 		panel.add(Box.createRigidArea(new Dimension(30,30)));
 	
 	}
	
	class ButtonListener implements ActionListener
	{
		ButtonListener()
		{
			
		}
		
		public void actionPerformed(ActionEvent e)
		{
			if (e.getActionCommand().equals("LOGIN"))
			{
				System.out.println("Button is clicked");
				String value1 =usertext.getText();
				char[] value2 = passwordtext.getPassword();
				String str = String.valueOf(value2);
				AdministratorService as = new AdministratorService();
				System.out.println(str);
				
				if(as.userDoesNotExist(value1))
				{
					JOptionPane.showMessageDialog(frame, "User doesnt exist");
				}
				else if(as.incorrectPassword(value1, str))
				{
					JOptionPane.showMessageDialog(frame, "Password is incorrect");
				}
				else
				{
					//to do: change from login screen to rmos ui screen
				}
				//as.userAuthentication(value1, str);
				
			}
		}
	}
}	
	
	



	
