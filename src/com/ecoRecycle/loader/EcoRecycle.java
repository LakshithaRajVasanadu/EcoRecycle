package com.ecoRecycle.loader;

import java.awt.EventQueue;

import ui.RcmUIManager;
import com.ecoRecycle.ui.RmosUIManager;

public class EcoRecycle {
	
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					new RcmUIManager();
					new RmosUIManager();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

}
