package com.ecoRecycle.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ITEM")
public class Item {

	@Id 
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "type", unique = true)
	private String type;
	
	@Column(name = "isBiodegradable")
	private String isBiodegradable;
	
	@Column(name = "isValid")
	private String isValid;
	
	@Column(name = "pricePerLb")
	private int pricePerLb;
	
	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public int getPricePerLb() {
		return pricePerLb;
	}

	public void setPricePerLb(int pricePerLb2) {
		this.pricePerLb = pricePerLb2;
	}

	@Column(name = "createDateTime")
	private Date createDateTime;
	
	@Column(name = "updateDateTime")
	private Date updateDateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String isBiodegradable() {
		return isBiodegradable;
	}

	public void setBiodegradable(String isBiodegradable) {
		this.isBiodegradable = isBiodegradable;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", type=" + type + ", isBiodegradable="
				+ isBiodegradable + ", isValid=" + isValid + ", pricePerLb="
				+ pricePerLb + ", createDateTime=" + createDateTime
				+ ", updateDateTime=" + updateDateTime + "]";
	}


}
