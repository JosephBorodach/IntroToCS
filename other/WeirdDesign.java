

/*Line 
TopHalf
	for () {
		write a bar on the output line
		write (3 - line) some spaces on the output line
		write "^" on the output line
		write (line - 1) some spaces on the output line
		write "^" on the output line
		write (3 - line) some spaces on the output line
		write a bar on the output line
		go to new output line

	}

TopHalf
Line
BottomHalf
BottomHalf
Line

drawBottomHalf ();
drawBottomHalf ();*/


public class WeirdDesign {
	public static void main (String[] args) {
		drawLine ();
		drawTopHalf ();
		drawTopHalf ();
		drawLine ();
		drawBottomHalf ();
		drawBottomHalf ():
		drawLine ();		
	}
	public static void drawLine (){
		System.out.print ("+");
		for (int i = 1; i <= 6; i++) {
			System.out.print ("-");
		}
		System.out.println ("+");
	}
	public static void drawTopHalf (){
		for (int line = 1; line <= 3; line++) {
			System.out.print ("|");
			for (int i = 1; i <= (3 - line); i++) {
				System.out.print (" ");
			}
			System.out.print ("^");
			for (int i = 1; i <= (2 * (line - 1)); i++) {
				System.out.print (" ");
			}
			System.out.print ("^");
			for (int i = 1; i <= (3 - line); i++) {
				System.out.print (" ");
			}
			System.out.println ("|");
		}	
	}
	public static void drawBottomHalf (){
		for (int line = 1; line <= 3; line++) {
			System.out.print ("|");
			for (int i = 1; i <= (3 - line); i++) {
				System.out.print (" ");
			}
			System.out.print ("^");
			for (int i = 1; i <= (2 * (line - 1)); i++) {
				System.out.print (" ");
			}
			System.out.print ("^");
			for (int i = 1; i <= (3 - line); i++) {
				System.out.print (" ");
			}
			System.out.println ("|");
}
