package com.peanutBarrel.errorLogging;

public class ErrorLogger {
	
	public static void LogError(Exception exception) {
		System.out.print(exception.getStackTrace());
	}
}
