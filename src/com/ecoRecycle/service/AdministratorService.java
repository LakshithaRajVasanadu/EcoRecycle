package com.ecoRecycle.service;

import com.ecoRecycle.model.Administrator;
import com.ecoRecycle.repository.AdministratorRepository;

public class AdministratorService {
	
	private AdministratorRepository repository = new AdministratorRepository();
	
	/*Checks if the administrator is a valid admin*/
	public boolean isUserValid(String username, String password) {
		boolean isValid = false;
		
		Administrator admin = repository.getAdmin(username);
		if(admin != null) {
			if(admin.getPassword().equals(password))
				isValid = true;
		}
		
		return isValid;
	}
	
	/*To get the username of the administrator*/
	public Administrator getAdmin(String username){
		Administrator admin = repository.getAdmin(username);
		return admin;
	}
	
}
