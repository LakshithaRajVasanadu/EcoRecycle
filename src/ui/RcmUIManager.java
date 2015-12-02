package ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;


public class RcmUIManager extends JFrame
{
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					RcmUIManager window = new RcmUIManager();
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
	public RcmUIManager() 
	{
		super("RCM");

		initialize();
		
		Container contenPane = this.getContentPane();
		
		contenPane.add(new RcmUI("Rcm256"));
		setVisible(true);

	}

	/**
	* Initialize the contents of the frame.
	*/
	private void initialize() 
	{
			
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			setSize(screenSize.width/2, screenSize.height);
			//setSize(680, 730);
			
			System.out.println("Width:" + screenSize.width/2);
			System.out.println("Height:" + screenSize.height);
			
			setLocation(screenSize.width/2, 0);
			//setLocation(683,0);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			
	}
	

}
