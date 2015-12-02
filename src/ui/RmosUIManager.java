package ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;


public class RmosUIManager extends JFrame
{
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					RmosUIManager window = new RmosUIManager();
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
	public RmosUIManager() 
	{
		super("RMOS");

		initialize();
		
		Container contenPane = this.getContentPane();
		
		contenPane.add(new RmosUI("Rmos1"));
		setVisible(true);

	}

	/**
	* Initialize the contents of the frame.
	*/
	private void initialize() 
	{
			
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			//setSize(screenSize.width/2, screenSize.height);
			setSize(680,730);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			
	}
	

}
