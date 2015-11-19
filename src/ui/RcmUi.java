package ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class RcmUi 
{
	JFrame frame = new JFrame();
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					RcmUi window = new RcmUi();
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
	public RcmUi() 
	{
		initialize();
	}

	/**
	* Initialize the contents of the frame.
	*/
	private void initialize() 
	{
			//frame = new JFrame();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			frame.setSize(screenSize.width/2, screenSize.height);
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			
	}
	

}
