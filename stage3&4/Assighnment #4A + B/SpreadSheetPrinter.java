//Assighnment #4

import java.util.Arrays;
import java.util.Scanner;

public class SpreadSheetPrinter {	
	public static void main (String[] args) {

		//#1: 1st Error Statement
		if (args.length < 2) {
			System.out.print ("Invalid input: must specify at least highest column and row." + "\n");
			return;
		}

		//#2: 2nd Error Statement
		if (args.length % 2 != 0) {
			//error with new line 
			System.out.print ("Invalid input: must specify the spreadsheet range, followed by cell-value pairs. You entered an odd number of inputs." + "\n");
			return;
		}

		//#3: 3rd Error Statement
		Boolean validRange = validateRange (args);
		if (validRange == false) {
			System.out.print ("Please specify a valid spreadsheet range, with highest column between A and Z and highest row as an integer" + "\n");
			return;
		}

		//#4 (Do I need a try/catch here?)
		String [] input = Arrays.copyOfRange (args, 2, args.length);
		char lastCol = args[0].charAt(0);
		int lastRow = Integer.parseInt (args [1]); 

		//#5: Only run 4th and 5th Errors if there are values there
		if (input.length > 1) {
			
			//#6: 4th Error Statement
			String invalidCellLabel = validateAllCellLabels (input, lastCol, lastRow); 
			if (invalidCellLabel != null) {
				System.out.print ("Invalid cell label: " + invalidCellLabel + "\n"); 
				return;
			} 

			//#7: 5th Error Statement
			String invalidCellValue = validateAllCellValues (input); 
			if (invalidCellValue != null) {
				System.out.print ("Invalid cell value: " + invalidCellValue + "\n");
				return;
			} 
		}

		//#8: Print from 'A' until ColumnRange and add in spaces before rows begin.
		printColumnHeaders (lastCol, lastRow);

		//#9:
		for (int i = 1; i <= lastRow; i++) {
			System.out.print (i);
			//Check each column in the row
			for (char j = 'A'; j <= lastCol; j++) {
				String cellValue = getCellValue (j, i, input);
				System.out.print ("\t" + cellValue);
			}
			//I added in the tab - that should be correct now. 
			System.out.print ("\t" + "\n");
		}
	}

	public static String getCellValue(char col, int row, String[] input) {
		String returnValue = " ";
		for (int i = 0; i < input.length; i += 2) {
			boolean trueOrFalse = isCurrent (col, row, input [i]);
			if (trueOrFalse == true) {
				returnValue = input [i + 1];
			}
		} 
		return returnValue;
	}

	public static boolean isCurrent (char col, int row, String cellLabel) {
		int inputRow = Integer.parseInt (cellLabel.substring (1));
		char inputCol = cellLabel.charAt (0); 
		if (col == inputCol && row == inputRow) {
			return true;
		} 
		return false;
	}

	public static void printColumnHeaders (char lastColumn, int lastRow) {	
		//This is super pretty
		for (int i = 1; i <= lastRow; i *= 10) {
			System.out.print (" ");
		}
		for (char i = 'A'; i <= lastColumn; i++) {
			System.out.print ("\t" + i);
		}
		System.out.print ("\n");
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
		} 
		return null;
	} 

	public static String validateAllCellValues (String[] input) {
		String invalidCellValue = "";
		for (int i = 1; i < input.length; i += 2) {
			try {
				if (isValidDouble (input [i]) != true) {
					invalidCellValue = input [i];
					return invalidCellValue;
				}
			} catch (NumberFormatException e) {
				invalidCellValue = input [i];
				return invalidCellValue;
			}
		} 
		return null;
	}

	public static boolean validateRange (String[] args) {
		try {
			char column = args [0].charAt (0);
			String columnLength = args [0];
			int ifValidInteger = Integer.parseInt (args [1]);
			if (column >= 'A' && column <= 'Z' && ifValidInteger >= 1 && columnLength.length() < 2) {
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		} 
	}

	//This was supposed to go into validateRange but I didn't add it in and my code ran anyways. 
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