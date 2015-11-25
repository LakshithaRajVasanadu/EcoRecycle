package com.ecoRecycle.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ecoRecycle.helper.TransactionType;

@Entity
public class ReloadTransaction extends Transaction{
	public ReloadTransaction() {
		super();
		this.type = TransactionType.RELOAD;
	}
}
