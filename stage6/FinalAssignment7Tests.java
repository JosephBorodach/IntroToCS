public class Assignment8Tests { 
        public static void main(String[] args) { 
                CellSpreadSheet abss = new CellSpreadSheet(6,6); 
                //set a few values, print it out 
                abss.setValue('A',1,"100");  
                abss.setValue('F',5,"55");  
                System.out.println(abss.getSpreadSheetAsCSV(true)); 

                System.out.println("----------------Does get copy work? Does set all extra values to null?----------------" + "\n"); 
                Object [] getCopy4 = abss.getCopyOfColumnThroughRow ('A',15);
                System.out.println ('A'); 
                for (int i = 0; i < 15; i++) {
                        System.out.println (getCopy4 [i]);
                } System.out.print ("\n"); 
                System.out.println("--------------------------------------------------------------------------------------" + "\n"); 

                //get copy of data 
                Cell[][] copy = abss.getCopyOfData(); 
                //copy[0][0] = "99"; 

                System.out.println("----------------print out again, showing that getCopyOfData just created a copy and actual spreadsheet has not been affected----------------" + "\n"); 
                System.out.println(abss.getSpreadSheetAsCSV(true)); 
                System.out.println("----------------Should print not have 99 as its first value----------------" + "\n"); 

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
                System.out.println("These next three lines should all be the same - testing both getvalue methods' ability to call evaluate formula");
                System.out.println("The value of the formula stored in D1 is: " + abss.evaluateFormula('D',1) + "\n");
                System.out.println("The value of the formula stored in D1 is: " + abss.getValue('D',1) + "\n");
                System.out.println("The value of the formula stored in D1 is: " + abss.getValue("D1") + "\n"); 
                System.out.println(abss.getSpreadSheetAsCSV(true)); 
                System.out.println(abss.getSpreadSheetAsCSV(false));

                System.out.println("------------------------------------------------------------------------------------------" + "\n"); 
                System.out.println("**************************************** My TESTS ****************************************" + "\n"); 
                System.out.println("------------------------------------------------------------------------------------------" + "\n"); 

                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                System.out.println ("TEST: Set a cell to a double value. Cell's Column & Row are beyond current bounds of sheet. Get the value using each getValue(char,int) and getValue(String).");
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");               
                abss.setValue('G',5,"10");
                System.out.println("Value of C3 is: " + abss.getValue('C',3)); 
                System.out.println("Value of C3 is: " + abss.getValue("C3")); 
                System.out.println("Value of G5 is: " + abss.getValue('G',5)); 
                System.out.println("Value of G5 is: " + abss.getValue("G5")); 
                System.out.println ("");

                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                System.out.println ("TEST: Set a cell to a formula value. Cell is within current bounds of sheet. Get the value using each getValue(char,int) and getValue(String).");
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");                 
                abss.setValue('G',6,"G5 * C3");
                System.out.println("Value of G6 is: " + abss.getValue('G',6)); 
                System.out.println("Value of G6 is: " + abss.getValue("G6")); 
                System.out.println ("");

                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                System.out.println ("TEST: Set a cell to a formula value. Cell is BEYOND current bounds of sheet, and formula references a cell beyond the bounds. Get the value using each getValue(char,int) and getValue(String).");
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                //TEST: Expand column range through setValue
                abss.setValue('K',1,"K5 + K4");
                abss.setValue('K',5,"1");
                abss.setValue('K',4,"1");
                System.out.println("Value of K1 is: " + abss.getValue('K',1)); 
                System.out.println("Value of K1 is: " + abss.getValue("K1")); 
                System.out.println ("");

                //Test for setting formula beyond bounds
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                System.out.println ("TEST: Expand columnRange by setting a cell to a formula with a column that does not exist yet. Should go to L");      
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                abss.setValue ('K',2,"L4 + L1");
                System.out.println(abss.getSpreadSheetAsCSV(true)); 

                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                System.out.println ("TEST: Expand columnRange by setting a cell to a formula with a column that does not exist yet. Should go to M");      
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                abss.setValue ('K',3,"M4 + M1");
                System.out.println(abss.getSpreadSheetAsCSV(true)); 

                //TEST: Tries to shorten the columnRange, should be ignored
                //abss.expandColumnRange('B'); 
                //System.out.println(abss.getSpreadSheetAsCSV(true)); 

                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                System.out.println ("TEST: Expand row range by setting a formula to a cell row that does not yet exists - should go to 8"); 
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                abss.setValue ('K',4,"M8 + M1");
                System.out.println(abss.getSpreadSheetAsCSV(true) + "\n"); 

                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                System.out.println ("TEST: Expand row range by setting a formula to a cell row that does not yet exists - should go to 10"); 
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                abss.setValue ('K',5,"M1 + M10");
                System.out.println(abss.getSpreadSheetAsCSV(true) + "\n"); 

                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                System.out.println ("TEST: Expand row range to 25"); 
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                abss.setValue ('B',25,"15");
                System.out.println(abss.getSpreadSheetAsCSV(true) + "\n"); 

                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                System.out.println ("TEST: Expand row range AND column range by setting a formula to a cell row and column that does not yet exists - should go to row to 40 and column to "); 
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                abss.setValue ('K',8,"Z40 + M10");
                System.out.println(abss.getSpreadSheetAsCSV(true) + "\n"); 


                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                System.out.println ("TEST: Does getCopy work? Should print out 99 and Null");
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");                 
                System.out.println(copy[0][0]);
                System.out.println(copy[3][5] + "\n");  

                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                System.out.println ("TEST: Does getCopyOfColumnThroughRow work? Should print A, followed by 5 rows");
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                Object [] getCopy = abss.getCopyOfColumnThroughRow ('A',5);
                System.out.println ('A');
                for (int i = 0; i < 5; i++) {
                        System.out.println (getCopy [i]);
                } 
                System.out.print ("\n");

                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                System.out.println ("TEST: Does getCopyOfColumnThroughRow expand columnRange if the cellRow is longer than originally? Set to 50");
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                Object [] getCopy3 = abss.getCopyOfColumnThroughRow ('C',50);
                System.out.println ('C');
                for (int i = 0; i < 50; i++) {
                        System.out.println (getCopy3 [i]);
                } System.out.print ("\n");

                
                /*
                //TEST: that throwing exception in evaluateFormula works
                abss.setValue('A',4,"C1 + C2"); 
                abss.setValue('A',5,"A4 + C2"); 
                System.out.println(abss.getSpreadSheetAsCSV(true)); 
                System.out.println(abss.getSpreadSheetAsCSV(false)); 
                */
                //TEST: expanding column range after setting values
                CellSpreadSheet sp = new CellSpreadSheet(4,5);
                sp.setValue('C',1,"10"); 
                sp.setValue('C',2,"11"); 
                sp.setValue('C',3,"12"); 
                sp.setValue('C',4,"13"); 
                sp.setValue('C',5,"14"); 
                sp.setValue('A',1,"C1 * C2");
                sp.setValue('A',3, "60");
                sp.setValue('D',4,"1");
                System.out.println(sp.getSpreadSheetAsCSV(true)); 
                sp.expandColumnRange('G');
                System.out.println(sp.getSpreadSheetAsCSV(true));

                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                System.out.println ("TEST: Expand row range by setting a formula to a cell row that does not yet exists - should go to 100"); 
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                abss.setValue ('K',6,"M1 + M100");
                System.out.println(abss.getSpreadSheetAsCSV(true) + "\n"); 

                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                System.out.println ("TEST: evaluateFormula containing cells >9: M1 should equal 250"); 
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                abss.setValue('M',66,"150"); 
                abss.setValue('M',67,"100"); 
                abss.setValue('M',1,"M66 + M67"); 
                System.out.println("The value of the formula stored in M1 is: " + abss.evaluateFormula('M',1) + "\n");

                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                System.out.println ("TEST: getValue (String) and getValue (column, row) larger than >9"); 
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                abss.setValue('M',77,"150"); 
                abss.setValue('M',79,"100"); 
                System.out.println("The value of the formula stored in M77 is: " + abss.getValue('M',77) + "\n");                                 
                System.out.println("The value of the formula stored in M79 is: " + abss.getValue('M',79) + "\n"); 
                System.out.println("The value of the formula stored in M77 is: " + abss.getValue("M77") + "\n");                                 
                System.out.println("The value of the formula stored in M79 is: " + abss.getValue("M79") + "\n");


                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                System.out.println ("TEST: getValue (String) and getValue (column, row) null value - should be 0.0"); 
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                System.out.println("The value of the formula stored in M78 is: " + abss.getValue('M',78) + "\n"); 
                System.out.println("The value of the formula stored in M78 is: " + abss.getValue("M78") + "\n");                                                 

                /*
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                System.out.println ("TEST: set Z1 to D4 + 11 and set Z2 to D1 + 1"); 
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                abss.setValue('Z',1,"D1 + 11"); 
                abss.setValue('Z',2,"D1 + 1"); 
                System.out.println(abss.getSpreadSheetAsCSV(true) + "\n");
                */ 

                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                System.out.println ("TEST: Does evaluateFormula properly throw an exception when there is no formula?");
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); 
                System.out.println("The value of the formula stored in D1 is: " + abss.evaluateFormula('A',7) + "\n");

        } 
}