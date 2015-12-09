package com.ecoRecycle.service;

import java.util.List;
import java.util.Observable;

import com.ecoRecycle.helper.Message;
import com.ecoRecycle.model.Item;
import com.ecoRecycle.repository.ItemRepository;

public class ItemManager extends Observable  {
	
	private static ItemManager instance = null; 
	
	public static ItemManager getInstance() 
	{ 
		if(instance == null) 
		{ 
			instance = new ItemManager(); 
		} 
		return instance; 
	}
	
	private ItemManager() {
		
	}
	
	private boolean isAdded;
	public boolean isAdded() {
		return isAdded;
	}

	public void setAdded(boolean isAdded) {
		this.isAdded = isAdded;
	}

	private ItemRepository repository = new ItemRepository();
	
	/*To get an item by its id*/
	public Item getItemById(int id) {
		Item item = repository.getItemById(id);
		return item;
	}
	
	/*To get an item by its type*/
	public Item getItemByType(String type) {
		Item item = repository.getItemByType(type);
		return item;
	}
	
	/*To get all the valid items*/
	public List<Item> getAllValidItems() {
		List<Item> items = repository.getItemByValidity(true);
		return items;
	}
	
	/*To get all the items*/
	public List<Item> getAllItems() {
		List<Item> items = repository.getAllItems();
		return items;
	} 
	
	/*To display a message when the item is added succesfully and to save the item*/
	public Message addItem(int id){
		Item item = getItemById(id);
		if(item != null) {
			item.setIsValid(true);
		}
		
		boolean isSuccessful = repository.updateItem(item);
		
		Message msg = new Message();
		msg.setSuccessful(isSuccessful);
		if(!isSuccessful)
			msg.setMessage("Could not add item");
		else
		{
			setAdded(true);
			setChanged();
			notifyObservers(item.getType());
		}
		
		return msg;
	}
	
	/*To remove an item and retun a message*/
	public Message removeItem(int id){
		Item item = getItemById(id);
		if(item != null) {
			item.setIsValid(false);
		}
		
		boolean isSuccessful = repository.updateItem(item);
		
		Message msg = new Message();
		msg.setSuccessful(isSuccessful);
		if(!isSuccessful)
			msg.setMessage("Could not remove item");
		else
		{
			setAdded(false);
			setChanged();
			notifyObservers(item.getType());
		}
		
		return msg;
	}
	
	/*To change price of an existing item*/
	public Message changePrice(int id, double newPrice){
		Item item = getItemById(id);
		if(item != null) {
			item.setPricePerLb(newPrice);
		}
		
		boolean isSuccessful = repository.updateItem(item);
		
		Message msg = new Message();
		msg.setSuccessful(isSuccessful);
		if(!isSuccessful)
			msg.setMessage("Could not change price");
		
		return msg;
	}

}
