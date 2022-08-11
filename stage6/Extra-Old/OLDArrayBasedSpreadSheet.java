//Assighnment #6: ArrayBasedSpreadSheet
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.lang.reflect.Array;
import java.lang.instrument.Instrumentation;
import java.lang.Object;
public class ArrayBasedSpreadSheet {
	public Object [][] data;
	public ArrayBasedSpreadSheet (int rows, int columns) {
		this.data = new Object [columns][rows]; //Must be columns and then rows - normally it's the reverse
	}
	public String getSpreadSheetAsCSV(boolean showFormulas) {
		//If the user writes false, show the values generated by calculating the formulas.
		if (showFormulas == false) {
			//Step #1: Return column headers			
			String returnString = "";
			char charCounter = 'A';
			for (int i = 0; i < this.data.length - 1; i++) {
				returnString = returnString + charCounter++ + ",";
				if (1+i == this.data.length - 1) { //Last letter
					returnString = returnString + charCounter + "\n"; //Ends line with a "\n" and without a ","
				}
			}
			//Step #2: Return the actual data
			for (int i = 0; i < this.data[0].length; i++) { //Rows		
				char cellLabel = 'A'; 
				for (int j = 0; j < this.data.length - 1; j++) { //Columns
					int fakeRow = i + 1;
					returnString = returnString + (getValue (cellLabel, fakeRow) + ","); //Using getValue because we want to calculate the formulas
					if (j == this.data.length - 2) { //Last cell
						char tempCell = cellLabel;
						returnString = returnString + (getValue (++tempCell, fakeRow) + "\n"); //Each line ends with a \n and without a "," 
					} 
					cellLabel++;
				} 
			} return returnString;
		//If true, show the raw cell formulas
		} else {
			//Step #1: Return column headers		
			String returnString = "";
			char charCounter = 'A';
			for (int i = 0; i < this.data.length - 1; i++) { 
				returnString = returnString + charCounter++ + ",";
				if (1+i == this.data.length - 1) { //Last line
					returnString = returnString + charCounter + "\n"; //Each line ends with a \n and without a ","
				}
			}
			//Step #2: Return the actual data
			for (int i = 0; i < this.data[0].length; i++) {	//Rows	
				for (int j = 0; j < this.data.length - 1; j++) { //Columns - 
					//Confirm that I am allowed to set the null values to 0.0 here and does not need to be in getValue!!!					
					if (this.data[j][i] == null) { //If it's null, then replace it with 0.0 - this must be checked manually because can't use getValue
						returnString = returnString + 0.0 + ",";
					} else {
						returnString = returnString + String.valueOf(this.data[j][i]) + ","; //Otherwise, return the string value of the cell, even if it's a formula
					}
					if (j == this.data.length - 2) { //Last cell
						if (this.data[j + 1][i] == null) {
							returnString = returnString + 0.0 + "\n"; //Ends line with "\n" and without a "," 
						} else {
							returnString = returnString + String.valueOf(this.data[j + 1][i]) + "\n"; //Ends line with "\n" and not ","
						}
					}
				}
			} return returnString;
		}
	}
	public void setValue(char column, int row, String value) {
		int realRow = row - 1; //row is the user row which begins at 1; realRow, Java's row, begins at 0 and therefore is = to (row - 1)		
		int columnLength = 0; 
		for (char counter = 'A'; counter <= column; counter++) {
			columnLength++;
		}
		int realColumn = columnLength - 1; //columnLength is the user's column, which begins at A and is equated to 1; realColumn begins at 0 and is therefore = to (column - 1). 
		//Step #1: Check if the column length is too long - if it is, expand the spreadSheet's column length
		if (columnLength > this.data.length) { //Using columnLength
			expandColumnRange (column);		
		}
		//Step #2: Check if row length is too long - if it is expand the spreadSheet's row length
		if (row > this.data[0].length) { //Using row and not 
			Object [][] expandRowRange = new Object [this.data.length][row]; //Again, use row
			for (int i = 0; i < this.data[0].length; i++) { //row
				for (int j = 0; j < this.data.length; j++) { //column
					expandRowRange[j][i] = this.data[j][i]; //Saving the data
				}
			}
			this.data = expandRowRange;
		}
		//Step #3: Double or a cell formula? //Do I need to check this at all? 
		//If double, then set it
		try {
			double checkIfEquation = Double.parseDouble (value);
			this.data[realColumn][realRow] = Double.valueOf(getValue (value)); //I think this is unnecesary
			return;
		//Set Formula but do not perform it!
		} catch (NumberFormatException e) {
			this.data[realColumn][realRow] = value;
			return;
		}
	}
	
	//
	public Object[][] getCopyOfData() {
		Object [][] getCopyOfData = new Object [this.data.length][this.data[0].length];
		for (int i = 0; i < this.data[0].length; i++) {
			for (int j = 0; j < this.data.length; j++) {
				getCopyOfData[j][i] = this.data [j][i];
			}
		}
		return getCopyOfData;
	}		

	//Expand the spreadsheet to the given column.
	public void expandColumnRange(char column) {
		int columnLength = 0;
		for (char counter = 'A'; counter <= column; counter++) {
			columnLength++;
		}
		//Only run if the columnLength is being expanded not shortened
		if (columnLength > this.data.length) {
			Object [][] expandColumnRange = new Object [columnLength][this.data[0].length];
			for (int i = 0; i < this.data[0].length; i++) {
				for (int j = 0; j < this.data.length; j++) {
					expandColumnRange[j][i] = this.data[j][i];
				}
			}
			this.data = expandColumnRange;
		}
	}

	//If throughRow is larger than the current height of the column, all the values in the rows past the current column height must be returned as null.
	//@param c the column to get, between 'A' and 'Z'
	//@param throughRow the row to get a copy through. 
	//@return

	//CHECK!!!!
	//Returns a copy of a given column up to and including the given row.
	public Object[] getCopyOfColumnThroughRow(char c, int throughRow) {
		int columnLength = 0;
		for (char counter = 'A'; counter <= c; counter++) {
			columnLength++;
		}
		int realColumn = columnLength - 1;
		Object [] getCopyOfColumnThroughRow = new Object [throughRow];		
		for (int i = 0; i < throughRow; i++) {
			getCopyOfColumnThroughRow[i] = this.data[realColumn][i];
			if (i == this.data[realColumn].length - 1) {
				return getCopyOfColumnThroughRow;
			}
		} return getCopyOfColumnThroughRow;
	}

	//Evaluate the cell to discover its value
	public double getValue(char column, int row) {
		int realRow = row - 1;
		int columnLength = 0;
		for (char counter = 'A'; counter <= column; counter++) {
			columnLength++;
		}
		int realColumn = columnLength - 1;
		//If the value is null, change it to 0.0
		if (this.data[realColumn][realRow] == null) {
				//Should this be 0 or 0.0?!!!
				double returnZero = 0;
				return returnZero;
		} else {
			try {
				double checkIfDouble = Double.parseDouble (String.valueOf(this.data[realColumn][realRow]));
				return Double.valueOf(getValue (String.valueOf(this.data[realColumn][realRow]))); 
			//There is an equation:
			} catch (NumberFormatException e) {
				return evaluateFormula (column, row);
			}
		}
	}

	//Return the call value when it is a double
	public double getValue(String cell) {
		//There is no reason for a try/catch
		return Double.parseDouble (cell);
	}

	//Evaluate the formula held in the given cell 
	//return the numeric value produced by evaluating the formulae stored in the given cell 
	//throws if the cell does not contain a formula, throw an IllegalStateException
	public double evaluateFormula(char column, int row) {
		int realRow = row - 1; 
		int columnLength = 0;
		for (char counter = 'A'; counter <= column; counter++) {
			columnLength++;
		}
		int realColumn = columnLength - 1;

		String cell = String.valueOf(this.data[realColumn][realRow]);

		//Make sure that there is an equation 
		if (cell.length () < 7) {
			throw new IllegalStateException ();
		}

		//1st Cell
		char columnLabel = cell.charAt(0);
		int rowLabel =  Integer.parseInt ((cell.substring(1, 2)));
		//2nd Cell
		char secColumnLabel = cell.charAt(5);
		int secRowLabel =  Integer.parseInt ((cell.substring(6, 7)));
		
		String equationSymbol = cell.substring(3, 4);

		//Account for the chance that there is another formula in the cells
		double firstCellValue = getValue (columnLabel, rowLabel);
		double secondCellValue = getValue (secColumnLabel, secRowLabel);

		switch(equationSymbol) {
			case "+":
				return (firstCellValue + secondCellValue);
			case "-":
				return (firstCellValue - secondCellValue);
			case "*":
				return (firstCellValue * secondCellValue);
			case "/":
				return (firstCellValue / secondCellValue);
			default:
				//How do you throw an exception?
				throw new IllegalStateException ();
		}
	}
}



    


