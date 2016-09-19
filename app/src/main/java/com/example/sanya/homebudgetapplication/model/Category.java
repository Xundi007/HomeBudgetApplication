package com.example.sanya.homebudgetapplication.model;

/**
 * Created by Sanya.
 */
public class Category extends Entity {

	public Category() {
	}

	@Override
	public String toString() {
		return "Category: " + getId() + ", " + getName();
	}
}
