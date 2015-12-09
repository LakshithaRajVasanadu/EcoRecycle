package com.ecoRecycle.ui.loader;

import java.awt.EventQueue;

import com.ecoRecycle.service.StatusManager;
import com.ecoRecycle.ui.rcm.RcmUIManager;
import com.ecoRecycle.ui.rmos.RmosUIManager;

//Class to load the main UI
public class EcoRecycle {
	
	
	
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				StatusManager statusManager = null;
				try 
				{
					new RmosUIManager(statusManager);
					
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

}
