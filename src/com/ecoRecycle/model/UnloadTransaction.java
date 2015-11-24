package com.ecoRecycle.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ecoRecycle.helper.TransactionType;

@Entity
public class UnloadTransaction extends Transaction{

	public UnloadTransaction() {
		super();
		this.type = TransactionType.UNLOAD;
	}
}
