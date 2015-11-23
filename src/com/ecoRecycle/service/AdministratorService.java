package com.ecoRecycle.service;

import com.ecoRecycle.model.Administrator;
import com.ecoRecycle.repository.AdministratorRepository;

public class AdministratorService {
	
	private AdministratorRepository repository = new AdministratorRepository();
	
	public boolean isUserValid(String username, String password) {
		boolean isValid = false;
		
		Administrator admin = repository.getAdmin(username);
		if(admin != null) {
			if(admin.getPassword().equals(password))
				isValid = true;
		}
		
		return isValid;
	}
	
	public Administrator getAdmin(String username){
		Administrator admin = repository.getAdmin(username);
		return admin;
	}
	
}
