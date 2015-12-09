package com.ecoRecycle.helper;

//helper model for usage statistics
public class UsageStatisticsModel {
	private double totalWeight;
	private double totalValue;
	private double numberOfTimesEmptied;
	private double numberOfItems;
	
	public double getNumberOfItems() {
		return numberOfItems;
	}
	public void setNumberOfItems(double numberOfItems) {
		this.numberOfItems = numberOfItems;
	}
	public double getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}
	public double getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}
	public double getNumberOfTimesEmptied() {
		return numberOfTimesEmptied;
	}
	public void setNumberOfTimesEmptied(double numberOfTimesEmptied) {
		this.numberOfTimesEmptied = numberOfTimesEmptied;
	}
	
	@Override
	public String toString() {
		return "UsageStatisticsModel [totalWeight=" + totalWeight
				+ ", totalValue=" + totalValue + ", numberOfTimesEmptied="
				+ numberOfTimesEmptied + ", numberOfItems=" + numberOfItems
				+ "]";
	}
}
