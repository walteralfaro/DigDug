package walterServerCliente;

public class DigDugLogger {

	public static void log(String logString){
		System.out.println("[INFO]:"+ logString);
	}
	public static void log(char[][] logString){
		for (int i = 0; i < logString.length; i++) {
			char[] cs = logString[i];
			String s ="";
			for (int j = 0; j < cs.length; j++) {
				char c = cs[j];
				s = s + c;	
			}
	
			System.out.println("[INFO]:"+i+" - "+ s);
		}
	}
}
