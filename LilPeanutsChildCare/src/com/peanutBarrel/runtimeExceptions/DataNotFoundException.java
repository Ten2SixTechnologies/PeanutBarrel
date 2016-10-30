package com.peanutBarrel.runtimeExceptions;

@SuppressWarnings("serial")
public class DataNotFoundException extends RuntimeException {
	private String sql;
	
	public DataNotFoundException(String sql) {
		this.sql = sql;
	}
	
	@Override
	public String getMessage() {
		return ("Data not found when running query: ["+ this.sql +"]");
	}
}
