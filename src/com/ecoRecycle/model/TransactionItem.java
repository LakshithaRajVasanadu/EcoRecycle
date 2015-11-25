package com.ecoRecycle.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TransactionItemMapping")
public class TransactionItem {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "transactionId")
	private Transaction transaction;

	@ManyToOne
	@JoinColumn(name = "itemId")
	private Item item;

	@Column(name = "weight")
	private double weight;

	@Column(name = "price")
	private double price;

	@Column(name = "isAccepted")
	private boolean isAccepted;

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

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isAccepted() {
		return isAccepted;
	}

	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
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

}
