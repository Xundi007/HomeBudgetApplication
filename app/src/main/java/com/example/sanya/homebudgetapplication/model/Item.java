package com.example.sanya.homebudgetapplication.model;

/**
 * Created by Sanya.
 */
public class Item extends Entity{
	private int categoryId;
	private int lastValue;
	private Boolean isIncome;

	public Item() {
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getLastValue() {
		return lastValue;
	}

	public void setLastValue(int lastValue) {
		this.lastValue = lastValue;
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
		if (!super.equals(o)) return false;

		Item item = (Item) o;

		if (getCategoryId() != item.getCategoryId()) return false;
		if (getLastValue() != item.getLastValue()) return false;
		return isIncome.equals(item.isIncome);

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + getCategoryId();
		result = 31 * result + getLastValue();
		result = 31 * result + isIncome.hashCode();
		return result;
	}


	@Override
	public String toString() {
		return "Item{" +
				"id=" + getId() +
				", name='" + getName() + '\'' +
				", categoryId=" + categoryId +
				", lastValue=" + lastValue +
				", isIncome=" + isIncome +
				'}';
	}
}
