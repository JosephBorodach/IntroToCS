public class Assignment6Demo { 
	public static void main(String[] args) { 
		ArrayBasedSpreadSheet abss = new ArrayBasedSpreadSheet(6,6); 
		//set a few values, print it out 
		abss.setValue('A',1,"100"); 
		abss.setValue('F',5,"55"); 
		System.out.println(abss.getSpreadSheetAsCSV(true)); 
		//get copy of data 
		Object[][] copy = abss.getCopyOfData(); 
		copy[0][0] = "99"; 
		//print out again, showing was just a copy and actual spreadsheet has not been affected 
		System.out.println(abss.getSpreadSheetAsCSV(true)); 
		//fill in some data on column C 
		abss.setValue('C',1,"10"); 
		abss.setValue('C',2,"11"); 
		abss.setValue('C',3,"12"); 
		abss.setValue('C',4,"13"); 
		abss.setValue('C',5,"14"); 
		abss.setValue('C',6,"15"); 
		//print out again, showing new values 
		System.out.println(abss.getSpreadSheetAsCSV(true)); 
		abss.expandColumnRange('H'); 
		//print out again, showing expanded spreadsheet 
		System.out.println(abss.getSpreadSheetAsCSV(true)); 
		//print out some values 
		System.out.println("Value of C3 is: " + abss.getValue('C',3)); 
		System.out.println("Value of F5 is: " + abss.getValue('F',5)); 
		System.out.println("Value of B3 is: " + abss.getValue('B',3) + "\n"); 
		//set some cells to formulas 
		abss.setValue('D',1,"C1 * F5"); 
		abss.setValue('E',1,"D1 / C3"); 
		abss.setValue('A',2,"A1 + C3"); 
		abss.setValue('B',3,"C6 - C1"); 
		System.out.println("The value of the formula stored in D1 is: " + abss.evaluateFormula('D',1) + "\n"); 
		System.out.println(abss.getSpreadSheetAsCSV(true)); 
		System.out.println(abss.getSpreadSheetAsCSV(false)); 
	} 
}