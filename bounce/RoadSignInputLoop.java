import java.util.Arrays;

public class RoadSignInputLoop {
    static String CLS = "\u001b[2J";
    static String HOME = "\u001b[H";

    /**
     * @param args number of repeats, followed by delay in milliseconds, followed by messages to print
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        //never make assumptions about your input!
        //what if the user didn't provide both numbers
        if (args.length < 3) {
            //tell the user what went wrong
            System.out.println("Enter the # of times to repeat, delay in ms, at least one message");
            return;
        }
        //how many times to repeat?
        int repeat = getNumber(args[0], 1);
        if (repeat == -1) {
            return;
        }
        //how long of a delay between each message?
        int delay = getNumber(args[1],100);
        if (delay < 100) {
            return;
        }
        //Get the messages
        String[] messages = Arrays.copyOfRange(args,2,args.length);
        //do it!
        setupScreen();
        for (int count = 0; count < repeat; count++) {
            for(int index = 0; index < messages.length; index++){
                printMessage(messages[index]);
                Thread.sleep(delay);
            }
        }
        resetScreen();
    }

    /**
     * Parses a command line arg into an Integer, outputting an error if it's below the minimum
     * @param arg the argument to parse as an Integer
     * @param minimum the minimum legal value of the int
     * @return the number if valid, -1 if not
     */
    private static int getNumber(String arg, int minimum) {
        int input = Integer.parseInt(arg);
        if (input < minimum) {
            System.out.println(input + " is not >= " + minimum + ". Please input a number >= " + minimum);
            return -1;
        }
        return input;
    }

    /**
     * Clears the screen, moves the cursor home, and
     * prints the message on the third line of the screen
     * @param message
     */
    private static void printMessage(String message) {
        System.out.println(HOME + CLS);
        System.out.println();
        System.out.println();
        System.out.println(message);
    }

    /**
     * clears the screen, hides the cursor, sets the
     * font to bold and yellow
     */
    private static void setupScreen() {
        String BOLD = "\u001b[1m";
        String YELLOW = "\u001b[33m";
        String HIDE_CURSOR = "\u001b[?25l";
        System.out.println(CLS + BOLD + HIDE_CURSOR + YELLOW);
    }

    /**
     * resets the font, shows the cursor
     */
    private static void resetScreen() {
        String RESET_FONT = "\u001b[0m";
        String SHOW_CURSOR = "\u001b[?25h";
        System.out.println(SHOW_CURSOR + RESET_FONT);
    }
}