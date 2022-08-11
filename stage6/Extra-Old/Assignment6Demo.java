public class Assignment6Demo { 
	public static void main(String[] args) { 
		ArrayBasedSpreadSheet abss = new ArrayBasedSpreadSheet(6,6); 
		//set a few values, print it out 

		

		abss.setValue('A',1,"100"); 
		abss.setValue('F',5,"55"); 
		System.out.println(abss.getSpreadSheetAsCSV(true)); 

		//I placed this here because it checks if the copy is longer than original version 
		//and if I did it in the end than it would be hard to tell if it extended the rows.
		System.out.println("----------------I placed this here because it allows for visualization if the copy is longer than original version----------------" + "\n"); 
		Object [] getCopy4 = abss.getCopyOfColumnThroughRow ('A',15);
		System.out.println ('A');
		for (int i = 0; i < 15; i++) {
			System.out.println (getCopy4 [i]);
		} System.out.print ("\n");
		System.out.println("-------------------and if I did it in the end of the test code it would be hard to tell and I was having fun----------------------" + "\n"); 



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



		//My addatives
		System.out.println("------------------------------------------------------------------------------------------" + "\n"); 
		System.out.println("**************************************** My TESTS ****************************************" + "\n"); 
		System.out.println("------------------------------------------------------------------------------------------" + "\n"); 

		//TEST: Expand column range through setValue
		abss.setValue('K',1,"100");
		System.out.println(abss.getSpreadSheetAsCSV(true)); 

		//TEST: Tries to shorten the columnRange, should be ignored
		abss.expandColumnRange('B'); 
		System.out.println(abss.getSpreadSheetAsCSV(true)); 

		//TEST: Expand row range	
		abss.setValue ('B',7,"15");
		System.out.println(abss.getSpreadSheetAsCSV(true)); 

		abss.setValue('C',8,"15"); 
		System.out.println(abss.getSpreadSheetAsCSV(true)); 

		abss.setValue('B',9,"15"); 
		System.out.println(abss.getSpreadSheetAsCSV(true)); 

		abss.setValue('C',10,"15"); 
		System.out.println(abss.getSpreadSheetAsCSV(true)); 

		//Printing the copy version
		System.out.println(copy[0][0] + "\n");
		System.out.println(copy[3][5] + "\n");  

		//TEST:
		//If copy is shorter than original version
		Object [] getCopy = abss.getCopyOfColumnThroughRow ('A',5);
		System.out.println ('A');
		for (int i = 0; i < 5; i++) {
			System.out.println (getCopy [i]);
		} System.out.print ("\n");

		Object [] getCopy2 = abss.getCopyOfColumnThroughRow ('C',5);
		System.out.println ('C');
		for (int i = 0; i < 5; i++) {
			System.out.println (getCopy2 [i]);
		} System.out.print ("\n");

		//IMPORTANT!!!
		//If copy is longer than original version
		Object [] getCopy3 = abss.getCopyOfColumnThroughRow ('C',15);
		System.out.println ('C');
		for (int i = 0; i < 15; i++) {
			System.out.println (getCopy3 [i]);
		} System.out.print ("\n");

		//TEST: that throwing exception in evaluateFormula works
		abss.setValue('A',4,"C1 + C2"); 
		abss.setValue('A',5,"A4 + C2"); 
		System.out.println(abss.getSpreadSheetAsCSV(true)); 
		System.out.println(abss.getSpreadSheetAsCSV(false)); 

		//TEST: When both cells contain a formula 
		abss.setValue('G',1,"C1 + C2"); 
		abss.setValue('G',2,"C3 + C4"); 
		abss.setValue('G',3,"G1 + G2"); 
		System.out.println(abss.getSpreadSheetAsCSV(true)); 
		System.out.println(abss.getSpreadSheetAsCSV(false)); 

		//Bellow are various tests for throwing exceptions - only 1 can be tested at a time! 

		//TEST: that throwing exception in evaluateFormula works when no formula is there - MUST REMOVE PREVIOUS EXCEPTION TEST FOR THIS TO RUN
		System.out.println("The value of the formula stored in D1 is: " + abss.evaluateFormula('A',8) + "\n"); 

		//TEST: When the formula contains less than 6
		abss.setValue('G',4,"G1 + 2"); 
		System.out.println("The value of the formula stored in D1 is: " + abss.evaluateFormula('G',4) + "\n"); 
		//System.out.println(abss.getSpreadSheetAsCSV(true)); 
		//System.out.println(abss.getSpreadSheetAsCSV(false)); 


		//TEST: that throwing exception in evaluateFormula works when no formula is put
		//abss.setValue('A',7,"C1 % F5");
		//System.out.println("The value of the formula stored in D1 is: " + abss.evaluateFormula('A',7) + "\n"); 




	} 
}