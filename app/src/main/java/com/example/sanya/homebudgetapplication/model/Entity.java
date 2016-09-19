package com.example.sanya.homebudgetapplication.model;

/**
 * Created by Sanya.
 */
public class Entity {
	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Entity entity = (Entity) o;

		if (getId() != entity.getId()) return false;
		return getName().equals(entity.getName());

	}

	@Override
	public int hashCode() {
		int result = getId();
		result = 31 * result + getName().hashCode();
		return result;
	}
}
