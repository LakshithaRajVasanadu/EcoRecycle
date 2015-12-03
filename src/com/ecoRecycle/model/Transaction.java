package com.ecoRecycle.model;

import javax.persistence.*;

import com.ecoRecycle.helper.PaymentType;
import com.ecoRecycle.helper.TransactionStatus;
import com.ecoRecycle.helper.TransactionType;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.HashSet;


@Entity
@Table(name = "TRANSACTION")

//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
/*http://stackoverflow.com/questions/29246707/hibernate-parameter-index-out-of-range-8-number-of-parameters-which-is-7*/
/*@DiscriminatorColumn(
    name="type",
    discriminatorType=DiscriminatorType.STRING
)*/
public class Transaction {

	
	@Id 
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name="type", columnDefinition="enum('QUERY','RECYCLE', 'RELOAD', 'UNLOAD')")
	@Enumerated(EnumType.STRING)
	public TransactionType type;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "rcmId")
	private Rcm rcm;
	
	@Column(name="paymentType", columnDefinition="enum('Cash','Coupon')")
	@Enumerated(EnumType.STRING)
	public PaymentType paymentType;
	
	@Column(name="status", columnDefinition="enum('ACTIVE','DONE')")
	@Enumerated(EnumType.STRING)
	public TransactionStatus status;
	
	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	@Column(name = "totalWeight")
	private double totalWeight;
	
	@Column(name = "totalPayment")
	private double totalPayment;
	
	@Column(name = "createDateTime")
	private Date createDateTime;
	
	@Column(name = "updateDateTime")
	private Date updateDateTime;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "transaction", cascade = CascadeType.ALL)
	private Set<TransactionItem> transactionItems = new LinkedHashSet<TransactionItem>();
	
    public Set<TransactionItem> getTransactionItems() {
        return transactionItems;
    }
 
    public void setTransactionItems(Set<TransactionItem> transactionItems) {
        this.transactionItems = transactionItems;
    }
     
    public void addTransactionItem(TransactionItem transactionItem) {
        this.transactionItems.add(transactionItem);
    }
    

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public Rcm getRcm() {
		return rcm;
	}

	public void setRcm(Rcm rcm) {
		this.rcm = rcm;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public double getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(double totalPayment) {
		this.totalPayment = totalPayment;
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
		return "Transaction [id=" + id + ", type=" + type + ", rcm=" + rcm
				+ ", paymentType=" + paymentType + ", totalWeight="
				+ totalWeight + ", totalPayment=" + totalPayment + "]";
	}  
  
}
