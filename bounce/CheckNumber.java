
public class CheckNumber {
	public static void main(String[] args) {
		isValidDouble ();
	}

	public static boolean isValidDouble (String str) {
		try {
			Double.parseDouble (str);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
}


