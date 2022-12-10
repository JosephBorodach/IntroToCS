/*
 * A control character or non-printing character (NPC) is a code point (a number) in a character set, that does not represent a written symbol. They are used as in-band signaling to cause effects other than the addition of a symbol to the text (definition from Wikipedia)
 * Java characters are Unicode characters, represented as 4 digit hexidecimal codes. To specify a character's Unicode number, write a slash, a lowercase u, and then followed by the 4 digit code (see https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Character.html)
 * All escape / control characters start with the "escape" character followed by "[". The code for the escape key is "001b", as you can see here: http://www.unicode.org/charts/PDF/U0000.pdf
 * To clear the screen, the sequence is 001b[2J. You can send the cursor to the top left of the screen via 001b[H. (see http://ascii-table.com/ansi-escape-sequences.php).
 */
public class Bounce {
    public static void main(String[] args) throws InterruptedException {
        String CLS = "\u001b[2J";
        String HOME = "\u001b[H";
        String BOLD = "\u001b[1m";
        String BELL="\u0007"; //in Windows 10, this plays the "Critical Stop" system sound. I set mine to https://www.wavsource.com/snds_2020-10-01_3728627494378403/sfx/phasesr2.wav
        String HIDE_CURSOR="\u001b[?25l";
        String SHOW_CURSOR="\u001b[?25h";
        int delay = 100; //how many miliseconds each frame will remain on the screen before the next one is drawn

        //set font to bold, hide the cursor
        System.out.println(CLS + BOLD + HIDE_CURSOR);

        //start animation frame
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println("O");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay); //tell the computer to parse for the given number of miliseconds


        //first move
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println(" "); //in each frame, have to "erase" the "O" from the previous frame by writing over it
        System.out.println(" O");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //second move
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println("  ");
        System.out.println("  O");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //third move
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println();
        System.out.println("   ");
        System.out.println("   O");
        System.out.println();
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //fourth move
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("    ");
        System.out.println("    O");
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //fifth move
        System.out.println(HOME + BELL);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("     ");
        System.out.println("     O");
        System.out.println("================================");
        Thread.sleep(delay);


        //sixth move
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("      O");
        System.out.println("      ");
        System.out.println("================================");
        Thread.sleep(delay);


        //seventh move
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("       O");
        System.out.println("       ");
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //eighth move
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println();
        System.out.println("        O");
        System.out.println("        ");
        System.out.println();
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //ninth move
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println("         O");
        System.out.println("         ");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //tenth move
        System.out.println(HOME + BELL);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println("          O");
        System.out.println("          ");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //eleventh move
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println("           ");
        System.out.println("           O");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //twelfth move
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println("            ");
        System.out.println("            O");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //twelfth move
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println();
        System.out.println("             ");
        System.out.println("             O");
        System.out.println();
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //thirteenth move
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("              ");
        System.out.println("              O");
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //fourteenth move
        System.out.println(HOME + BELL);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("               ");
        System.out.println("               O");
        System.out.println("================================");
        Thread.sleep(delay);


        //fifteenth move
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("                O");
        System.out.println("                ");
        System.out.println("================================");
        Thread.sleep(delay);


        //sixteenth move
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("                 O");
        System.out.println("                 ");
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //seventeenth move
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println();
        System.out.println("                  O");
        System.out.println("                  ");
        System.out.println();
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //eighteenth move
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println("                   O");
        System.out.println("                   ");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //nineteenth move
        System.out.println(HOME + BELL);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println("                    O");
        System.out.println("                    ");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //move 20
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println("                     ");
        System.out.println("                     O");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //move 21
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println("                      ");
        System.out.println("                      O");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //move 22
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println();
        System.out.println("                       ");
        System.out.println("                       O");
        System.out.println();
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //move 23
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("                        ");
        System.out.println("                        O");
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //move 24
        System.out.println(HOME + BELL);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("                         ");
        System.out.println("                         O");
        System.out.println("================================");
        Thread.sleep(delay);


        //move 25
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("                          O");
        System.out.println("                          ");
        System.out.println("================================");
        Thread.sleep(delay);


        //move 26
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("                           O");
        System.out.println("                           ");
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //move 27
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println();
        System.out.println("                            O");
        System.out.println("                            ");
        System.out.println();
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //move 28
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println();
        System.out.println("                             O");
        System.out.println("                             ");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //move 29
        System.out.println(HOME + BELL);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println("                              O");
        System.out.println("                              ");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //move 30
        System.out.println(HOME);
        System.out.println();
        System.out.println();
        System.out.println("================================");
        System.out.println("                               ");
        System.out.println("                               O");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("================================");
        Thread.sleep(delay);


        //reset the screen
        System.out.println(SHOW_CURSOR);
    }
}