package com.example.sanya.homebudgetapplication.model;

/**
 * Created by Sanya.
 */
public class User extends Entity{
	private String password;

	public User() {
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (getId() != user.getId()) return false;
		if (!getName().equals(user.getName())) return false;
		return getPassword().equals(user.getPassword());

	}

	@Override
	public int hashCode() {
		int result = getId();
		result = 31 * result + getName().hashCode();
		result = 31 * result + getPassword().hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "User{" +
				"i='" + getId() + '\'' +
				", u='" + getName() + '\'' +
				", p='" + password + '\'' +
				'}';
	}
}
