package com.ecoRecycle.service;

public class ObjFactory
{
	private static ItemManager instance = null; 
	
	public static ItemManager getInstance() 
	{ 
		if(instance == null) 
		{ 
			//instance = new ItemManager(); 
		} 
		return instance; 
	} 
}
