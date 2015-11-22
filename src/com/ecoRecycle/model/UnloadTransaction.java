package com.ecoRecycle.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Transaction")
@DiscriminatorValue("UNLOAD")
public class UnloadTransaction extends Transaction{

}
