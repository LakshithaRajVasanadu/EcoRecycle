package com.ecoRecycle.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Transaction")
@DiscriminatorValue("RECYCLE")
public class RecycleTransaction extends Transaction{
	

}
