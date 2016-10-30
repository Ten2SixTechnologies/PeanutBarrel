package com.peanutBarrel.constants;


public enum UserType {

    ADMINISTRATOR (3),
    OWNER (2),
    USER (1),
    NEW_USER (0),
    INVALID (-1);
	
	private final int key;
	
	private UserType(int key) {
		this.key = key;
	}
	
	public int getKey() {
		return this.key;
	}
	
	public static UserType getUserType(int key) {
		UserType userTypeOutput = UserType.INVALID;
		
		for (UserType userType : values()) {
			if(userType.getKey() == key) {
				return userType;
			}
		}
		
		return userTypeOutput;
	}
}