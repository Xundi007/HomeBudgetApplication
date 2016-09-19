package com.example.sanya.homebudgetapplication.model;

/**
 * Created by Sanya.
 */
public class Transaction {
	private int id;
	private int userId;
	private int itemId;
	private int value;
	private Long createdTime;
	private Boolean isIncome;

	public Transaction() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public Boolean getIncome() {
		return isIncome;
	}

	public void setIncome(Boolean income) {
		isIncome = income;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Transaction that = (Transaction) o;

		if (getId() != that.getId()) return false;
		if (getUserId() != that.getUserId()) return false;
		if (getItemId() != that.getItemId()) return false;
		if (getValue() != that.getValue()) return false;
		if (!getCreatedTime().equals(that.getCreatedTime())) return false;
		return isIncome.equals(that.isIncome);

	}

	@Override
	public int hashCode() {
		int result = getId();
		result = 31 * result + getUserId();
		result = 31 * result + getItemId();
		result = 31 * result + getValue();
		result = 31 * result + getCreatedTime().hashCode();
		result = 31 * result + isIncome.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "Transaction{" +
				"id=" + id +
				", userId=" + userId +
				", itemId=" + itemId +
				", value=" + value +
				", createdTime=" + createdTime +
				", isIncome=" + isIncome +
				'}';
	}
}
