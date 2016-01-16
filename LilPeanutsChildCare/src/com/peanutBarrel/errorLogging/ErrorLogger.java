package com.peanutBarrel.errorLogging;

public class ErrorLogger {
	
	public static void logError(Throwable exception) {
		System.out.print(exception.getStackTrace());
	}
}
