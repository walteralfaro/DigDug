package common;

public class Logger {
	
	public static void info(String message) {
		System.out.println("INFO: " + message);
	}
	
	public static void warn(String message) {
		System.out.println("WARN: " + message);
	}
	
	public static void init(String message) {
		System.out.println("INIT: " + message);
	}
	
	public static void end(String message) {
		System.out.println("END: " + message);
	}
	
	public static void log(String message) {
	  System.out.println("END: " + message);
	}
}
