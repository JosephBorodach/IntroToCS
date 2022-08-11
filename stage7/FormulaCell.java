import java.io.*;
public class FormulaCell implements Cell{ //Both parameters must be stored as private instance variables.
	private String getFormula; 
	private CellProvider cellSheet;
	public FormulaCell (String formula, CellProvider data) {
		this.getFormula = formula;
		this.cellSheet = data;
	}
	public String getStringValue() {
		return this.getFormula;
	}
	public double getNumericValue() {
		String [] seperatedFormula = this.getFormula.split(" "); //Get the values in each of the cells.
		char firstColumnLabel = seperatedFormula[0].charAt(0); //1st Cell
		int firstRowLabel = Integer.parseInt ((seperatedFormula[0].substring(1)));
		int realFirstRow = firstRowLabel - 1;
		int columnLength = 0;
		for (char counter = 'A'; counter <= firstColumnLabel; counter++) {
			columnLength++;
		}
		int realFirstColumn = columnLength - 1;
		char secColumnLabel = seperatedFormula[2].charAt(0); //2nd Cell
		int secRowLabel =  Integer.parseInt ((seperatedFormula[2].substring(1)));
		int realSecRow = secRowLabel - 1;
		int secColumnLength = 0;
		for (char counter = 'A'; counter <= secColumnLabel; counter++) {
			secColumnLength++;
		}
		int realSecColumn = secColumnLength - 1;
		String equationSymbol = seperatedFormula[1].substring(0);
		Double firstCellDouble; 
		if (this.cellSheet.getCell(firstColumnLabel, firstRowLabel) == null) { //Account for the 1st cell being null
			firstCellDouble = 0.0; 
		} else {
			firstCellDouble = this.cellSheet.getCell(firstColumnLabel, firstRowLabel).getNumericValue();
		}
		Double secondCellDouble;
		if (this.cellSheet.getCell(secColumnLabel, secRowLabel) == null) { //Accounts for the 2nd cell being null
			secondCellDouble = 0.0; 
		} else {
			secondCellDouble = this.cellSheet.getCell(secColumnLabel, secRowLabel).getNumericValue();
		}
		switch(equationSymbol) { //Perform the equation
			case "+":
				return firstCellDouble + secondCellDouble;
			case "-":
				return firstCellDouble - secondCellDouble;
			case "*":
				return firstCellDouble * secondCellDouble;
			case "/":
				return firstCellDouble / secondCellDouble;
			default:
				throw new IllegalStateException (); //throws if the cell does not contain a formula, throw an IllegalStateException
		}
	}
}