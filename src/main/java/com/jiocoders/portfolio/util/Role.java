package com.jiocoders.portfolio.util;

/**
 * Enum representing user roles in the application
 */
public enum Role {

	ADMIN("ADMIN"), USER("USER"), MEMBER("MEMBER");

	private final String value;

	Role(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public boolean isEmpty() {
		return value == null || value.trim().isEmpty();
	}

	@Override
	public String toString() {
		return value;
	}

	/**
	 * Get Role enum from string value
	 * 
	 * @param value the string representation of the role
	 * @return the corresponding Role enum
	 * @throws IllegalArgumentException if the value doesn't match any role
	 */
	public static Role fromValue(String value) {
		for (Role role : Role.values()) {
			if (role.value.equalsIgnoreCase(value)) {
				return role;
			}
		}
		throw new IllegalArgumentException("No role found for value: " + value);
	}

	/**
	 * Check if a string value is a valid role
	 * 
	 * @param value the string to check
	 * @return true if valid, false otherwise
	 */
	public static boolean isValidRole(String value) {
		try {
			fromValue(value);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

}