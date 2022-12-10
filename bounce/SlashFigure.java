 		drawLine();
	}
	public static void drawLine () {
		for (int line = 0; line <= 5; line++) {
			for (int left = 1; left <= (line * 2); left++) {
				System.out.print("\\");
			}
			for (int exclamation = 0; exclamation <= (21 - line * 4); exclamation++) {
				System.out.print("!");
			}
			for (int right = 1; right <= (line * 2); right++) {
				System.out.print("/");
			}
			System.out.println();
		}
	}
}

