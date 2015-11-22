package com.ecoRecycle.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Transaction")
@DiscriminatorValue("RELOAD")
public class ReloadTransaction extends Transaction{

}
