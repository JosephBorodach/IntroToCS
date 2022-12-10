public class SlashFigure2 {
	public static final int SIZE = 6;

	public static void main (String[] args) {
		drawLine();
	}
	public static void drawLine () {
		for (int line = 1; line <= SIZE; line++) {
			for (int left = 1; left <= ((2 * line) - 2); left++) {
				System.out.print("\\");
			}
			for (int exclamation = 1; exclamation <= ((-4 * line) + (4 * SIZE + 2)); exclamation++) {
				System.out.print("!");
			}
			for (int right = 1; right <= ((2 * line) - 2); right++) {
				System.out.print("/");
			}
			System.out.println();
		}
	}
}
