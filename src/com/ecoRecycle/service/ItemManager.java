package com.ecoRecycle.service;

import java.util.List;
import java.util.Observable;

import com.ecoRecycle.helper.Message;
import com.ecoRecycle.model.Item;
import com.ecoRecycle.repository.ItemRepository;

public class ItemManager extends Observable  {
	
	/*
	 * Items are added and kept manually. 
	 * UI add = make item valid,
	 * UI remove = make item invalid
	 */
	private boolean isAdded;
	public boolean isAdded() {
		return isAdded;
	}

	public void setAdded(boolean isAdded) {
		this.isAdded = isAdded;
	}

	private ItemRepository repository = new ItemRepository();
	
	public Item getItemById(int id) {
		Item item = repository.getItemById(id);
		return item;
	}
	
	public Item getItemByType(String type) {
		Item item = repository.getItemByType(type);
		return item;
	}
	
	public List<Item> getAllValidItems() {
		List<Item> items = repository.getItemByValidity(true);
		return items;
	}
	
	public List<Item> getAllItems() {
		List<Item> items = repository.getAllItems();
		return items;
	} 
	
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
