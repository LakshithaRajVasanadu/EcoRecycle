package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.ecoRecycle.service.AdministratorService;


public class AdministratorLogin 
{
	JFrame frame;
	JPanel panel1,panel2, panel3, panel4;
	JLabel label,username, password;
	JTextField usertext;
	JPasswordField passwordtext;
	JButton login;


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
					AdministratorLogin window = new AdministratorLogin();
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
	 *Create the application.
	 * @return 
	 */
	public void adminLogin()
	{
		panel1 = new JPanel();
		label = new JLabel("Please enter login details");
		panel1.add(label, BorderLayout.CENTER);
		frame.add(panel1);
		
		panel2 = new JPanel();
		username = new JLabel("USERNAME");
		panel2.add(username, BorderLayout.NORTH);
		usertext = new JTextField();
		panel2.add(usertext,BorderLayout.SOUTH);
		panel1.add(panel2);
		
		panel3 = new JPanel();
		password = new JLabel("Password");
		panel3.add(password, BorderLayout.NORTH);
		passwordtext = new JPasswordField();
		panel3.add(passwordtext,BorderLayout.SOUTH);
		panel2.add(panel3);
		
		panel4 = new JPanel();
		login = new JButton("LOGIN");
		login.addActionListener(new ButtonListener());
		
		initialize();
	}
	
	public void initialize()
	{
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		//frame.setBounds(50, 50, 300, 150);
		//frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("ADMIN LOGIN");
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
					//as.userAuthentication(value1, str);
					
				}
			}
		}
	}	
		

	


