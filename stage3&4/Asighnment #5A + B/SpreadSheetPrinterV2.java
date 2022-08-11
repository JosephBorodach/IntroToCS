import java.lang.*;
import java.util.Arrays;
import java.util.Scanner;
public class SpreadSheetPrinterV2 {	
	public static final int PP = 0;
	public static final int CSV = 1;
	public static int FORMAT;
	public static void main (String[] args) {
		if (args.length < 3) { //#1: 1st Error Statement
			System.out.println ("Invalid input: must specify at least a format (csv or pp), as well as the highest column and row");
			return;
		}
		if (args [0].equals("pp")) {
			FORMAT = PP;
		} else if (args [0].equals("csv")) {
			FORMAT = CSV;
		} else { //#2: 2nd Error Statement - args [0] cannot be anything other than "pp" or "csv"
			System.out.print ("The first argument must specify either csv or pp" + "\n");
			return;
		} 
		Boolean validRange = validateRange (args); //#3: 3rd Error Statement
		if (validRange == false) {
			System.out.print ("Please specify a valid spreadsheet range, with highest column between A and Z and highest row as an integer > 0" + "\n");
			return;
		}
		if (args.length % 2 == 0) { //#4: 4th Error Statement
			System.out.print ("Invalid input: must specify the format, spreadsheet range, and then cell-value pairs. You entered an even number of inputs" + "\n"); //error with new line 
			return;
		}
		String [] input = Arrays.copyOfRange (args, 3, args.length);
		char lastCol = args[1].charAt(0);
		int lastRow = Integer.parseInt (args [2]); 
		if (input.length > 1) { //#6: Only run 5th and 6th Errors if there are values there
			String invalidCellLabel = validateAllCellLabels (input, lastCol, lastRow); //#7: 5th Error Statement
			if (invalidCellLabel != null) {
				System.out.print ("Invalid cell label: " + invalidCellLabel + "\n"); 
				return;
			} 
			String invalidCellValue = validateAllCellValues (input); //#8: 6th Error Statement
			if (invalidCellValue != null) {
				System.out.print ("Invalid cell value: " + invalidCellValue + "\n");
				return;
			} 
		}
		printColumnHeaders (lastCol, lastRow); //#8: Print from 'A' until ColumnRange and add in spaces before rows begin.
		if (FORMAT == PP) { //#9:
			for (int i = 1; i <= lastRow; i++) {
				System.out.print (i);
				for (char j = 'A'; j <= lastCol; j++) { //Check each column in the row
					String cellValue = getCellValue (j, i, input);
					System.out.print ("\t" + cellValue);
				}
			System.out.print ("\t" + "\n");
			}
		} else if (FORMAT == CSV) {
			for (int i = 1; i <= lastRow; i++) {
				for (char j = 'A'; j < lastCol; j++) { //Check each column in the row
					String cellValue = getCellValue (j, i, input);
					if (cellValue == " ") {
						System.out.print (",");
					} else {
						System.out.print (cellValue + ",");
					}
					if (j+1 == lastCol) { //No comma after the last row value
						cellValue = getCellValue (++j, i, input);
						if (cellValue == " ") { //No tab, only a return at the end of each line
							System.out.print ("\n");
						} else { 
							System.out.print (cellValue + "\n");
						}
					}
				}
			}
		}
	}
	public static String getCellValue(char col, int row, String[] input) {
		String returnValue = " ";
		for (int i = 0; i < input.length; i += 2) {
			boolean trueOrFalse = isCurrent (col, row, input [i]);
			if (trueOrFalse == true) {
				returnValue = input [i + 1];
			}
		} return returnValue;
	}
	public static boolean isCurrent (char col, int row, String cellLabel) {
		int inputRow = Integer.parseInt (cellLabel.substring (1));
		char inputCol = cellLabel.charAt (0); 
		if (col == inputCol && row == inputRow) {
			return true;
		} return false;
	}
	public static void printColumnHeaders (char lastColumn, int lastRow) {	
		if (FORMAT == PP) {
			for (int i = 1; i <= lastRow; i *= 10) {
				System.out.print (" ");
			}
			for (char i = 'A'; i <= lastColumn; i++) {
				System.out.print ("\t" + i);
			}
			System.out.print ("\n");
		} else if (FORMAT == CSV) {
			for (char i = 'A'; i < lastColumn; i++) {
				System.out.print (i + ",");
				if (i+1 == lastColumn) {
					System.out.print (++i + "\n");
				}
			}
		}
	}
	public static String validateAllCellLabels (String[] input, char lastCol, int lastRow) {
		String invalidCellLabel = "";
		for (int i = 0; i < input.length; i += 2) {
			invalidCellLabel = input [i]; 
			try {
				int row = Integer.parseInt (input[i].substring (1));
				char col = input[i].charAt (0);
				if (col < 'A' || col > lastCol || row > lastRow) {
					return invalidCellLabel;
				}
			} catch (NumberFormatException e) {
				return invalidCellLabel;
			}
		} return null;
	} 
	public static String validateAllCellValues (String[] input) {
		String invalidCellValue = "";
		for (int i = 1; i < input.length; i += 2) {
			try {
				if (isValidDouble (input [i]) != true) {
					return input [i];
				}
			} catch (NumberFormatException e) {
				return input [i];
			}
		} return null;
	}
	public static boolean validateRange (String[] args) {
		Boolean isItAValidRange = false;
		try {
			char column = args [1].charAt (0);
			String columnLength = args [1];
			int ifValidInteger = Integer.parseInt (args [2]);
			if (column >= 'A' && column <= 'Z' && ifValidInteger >= 1 && columnLength.length() < 2) {
				return isItAValidRange = true;
			}
		} catch (NumberFormatException e) {
			isItAValidRange = false;
		} return isItAValidRange; 
	}
	public static int getInteger (String arg) {
		try {
			return Integer.parseInt (arg);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	public static boolean isValidDouble (String arg) {
		try {
			Double.parseDouble (arg);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}	
	}
}