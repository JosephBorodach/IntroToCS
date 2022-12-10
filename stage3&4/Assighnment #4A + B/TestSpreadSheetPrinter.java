

import java.util.Arrays;
import java.util.Scanner;

public class TestSpreadSheetPrinter {	
	public static void main (String[] args) {
/*
		//2nd Error Statement
		if (args.length < 2) {
			System.out.println ("Invalid input: must specify at least highest column and row.");
			return;
		}

		//1st Error Statement
		Boolean validRange = validateRange (args);
		if (validRange == false) {
			System.out.println ("Please specify a valid spreadsheet range, with highest column between A and Z and highest row as an integer");
			return;
		}

		//#1: 3rd Error Statement
		if (args.length % 2 != 0) {
			//error with new line 
			System.out.println ("Invalid input: must specify the spreadsheet range, followed by cell-value pairs. You entered an odd number of inputs.");
			return;
		}
*/
		String [] input = Arrays.copyOfRange (args, 2, args.length);
		char lastCol = args[0].charAt(0);
		int lastRow = Integer.parseInt (args [1]); 
/*
		//4th Error Statement
		String invalidCellLabel = validateAllCellLabels (input, lastCol, lastRow); 
		if (invalidCellLabel != null) {
			System.out.println ("Invalid cell label: " + invalidCellLabel); 
			return;
		} 

		//5th Error Statement
		String invalidCellValue = validateAllCellValues (input); 
		if (invalidCellValue != null) {
			System.out.println ("Invalid cell value: " + invalidCellValue);
			return;
		} 


		//#7: Print from 'A' until ColumnRange and add in spaces before rows begin.
		printColumnHeaders (lastCol, lastCol);
*/
		for (int i = 0; i < input.length; i++) {
			System.out.println (getCellValue (input[i].charAt (0), input[i].subsString (1), input));
		}
		//Print row headers
		//printRowHeaders ();

	}

	public static String getCellValue(char col, int row, String[] input) {
		//Take the col and row and return the next cell value [input i +1]
		String columnStrung = Character.toString (col);
		String rowStrung = String.valueOf (row);
		String cellLabel = columnStrung + rowStrung;
		for (int i = 0; i < input.length; i += 2) {
			String returnValue = " ";
			if (cellLabel == input[i]) {
				returnValue = input [i];
				return returnValue;
			} else {
				return returnValue;
			}
		}
		return "";
	}
}