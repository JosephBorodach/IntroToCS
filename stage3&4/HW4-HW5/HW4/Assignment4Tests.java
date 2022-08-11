public class Assignment4Tests {

    private final TestHelper helper;
    private int points;
    private String output;

    public Assignment4Tests(TestHelper helper) {
        this.helper = helper;
        this.points = 0;
        this.output = "";
        this.helper.setCharDifLimit(5);
    }

    public String getOutput() {
        return this.output;
    }

    public int getPoints() {
        return this.points;
    }

    public String run() {
        this.output += "*****************************************************************************\n";
        this.output += "**Testing the output from calling your main method with valid spread sheets**\n";
        this.output += "*****************************************************************************\n\n";
        sheet1(6);
        sheet2(6);
        sparseSheet(6);
        fullSheet(6);
        emptySheet(6);
        this.output += "\n\n";

        this.output += "*****************************************************************************\n";
        this.output += "****************Testing your defensive programming***************************\n";
        this.output += "*****************************************************************************\n\n";
        onlyOneInput(3);
        oddNumerOfInputs(3);
        invalidCellLabel(3);
        invalidCellLabelAndValue(3);
        invalidCellValue(3);
        invalidRangeColumn(3);
        invalidRangeRow(3);
        this.output += "\n\n";

        this.output += "*****************************************************************************\n";
        this.output += "********************Testing Individual Method LOGIC**************************\n";
        this.output += "*****************************************************************************\n\n";
        getCellValueLogic(3);
        isCurrentLogic(3);
        printColumnHeadersLogic(3);
        validateAllCellLabelsLogic(3);
        validateAllCellValuesLogic(3);
        validateRangeLogic(3);
        getIntegerLogic(3);
        isValidDoubleLogic(3);
        this.output += "\n\n";

        this.output += "*****************************************************************************\n";
        this.output += "********************Testing Method Signatures********************************\n";
        this.output += "*****************************************************************************\n\n";
        getCellValueSignature(1);
        isCurrentSignature(1);
        printColumnHeadersSignature(1);
        validateAllCellLabelsSignature(1);
        validateAllCellValuesSignature(1);
        validateRangeSignature(1);
        getIntegerSignature(1);
        isValidDoubleSignature(1);

        return this.output;
    }

    private void testSheet(String name, int points, String[] args, String expected, String input) {
        this.output += "TEST: " + name + "\n";
        this.output += "POINTS: " + points + "\n";
        this.output += "INPUT: " + input + "\n";
        this.output += "EXPECTED OUTPUT: " + expected + "\n";

        String actual = this.helper.runMain(SpreadSheetPrinter.class, args);
        this.output += "ACTUAL OUTPUT: " + actual + "\n";
        String compareResult = helper.compareOutput(expected, actual, input);
        if (!(compareResult == null)) {
            this.output += "-----FAILED-----" + "\n";
        }
        else {
            this.output += "+++++PASSED+++++" + "\n";
            this.points += points;
        }
    }

    //***********************Correct sheets************************
    private void sheet1(int points) {
        String[] args = {"F", "10", "D7", "20", "A3", "15", "E6", "3.75", "A10", "598", "B9", "200", "F10", "1000", "C4", "50"};
        String expected = "  \tA\tB\tC\tD\tE\tF\n1\t \t \t \t \t \t \t\n2\t \t \t \t \t \t \t\n3\t15\t \t \t \t \t \t\n4\t \t \t50\t \t \t \t\n5\t \t \t \t \t \t \t\n6\t \t \t \t \t3.75\t \t\n7\t \t \t \t20\t \t \t\n8\t \t \t \t \t \t \t\n9\t \t200\t \t \t \t \t\n10\t598\t \t \t \t \t1000\t\n";
        String input = "F,10,D7,20,A3,15,E6,3.75,A10,598,B9,200,F10,1000,C4,50";
        testSheet("Sheet #1", points, args, expected, input);
    }

    private void sheet2(int points) {
        String[] args = {"F", "10", "D7", "20", "A3", "15", "C5", "50", "F10", "61"};
        String expected = "  \tA\tB\tC\tD\tE\tF\n1\t \t \t \t \t \t \t\n2\t \t \t \t \t \t \t\n3\t15\t \t \t \t \t \t\n4\t \t \t \t \t \t \t\n5\t \t \t50\t \t \t \t\n6\t \t \t \t \t \t \t\n7\t \t \t \t20\t \t \t\n8\t \t \t \t \t \t \t\n9\t \t \t \t \t \t \t\n10\t \t \t \t \t \t61\t\n";
        String input = "F,10,D7,20,A3,15,C5,50,F10,61";
        testSheet("Sheet #2", points, args, expected, input);
    }

    private void sparseSheet(int points) {
        String[] args = {"F", "10", "D7", "20", "A3", "15"};
        String expected = "  \tA\tB\tC\tD\tE\tF\n1\t \t \t \t \t \t \t\n2\t \t \t \t \t \t \t\n3\t15\t \t \t \t \t \t\n4\t \t \t \t \t \t \t\n5\t \t \t \t \t \t \t\n6\t \t \t \t \t \t \t\n7\t \t \t \t20\t \t \t\n8\t \t \t \t \t \t \t\n9\t \t \t \t \t \t \t\n10\t \t \t \t \t \t \t\n";
        String input = "F,10,D7,20,A3,15";
        testSheet("Sparse Sheet", points, args, expected, input);
    }

    private void fullSheet(int points) {
        String[] args = {"C", "3", "A1", "10", "B1", "20", "C1", "30", "A2", "11", "B2", "21", "C2", "31", "A3", "13", "B3", "23", "C3", "33"};
        String expected = " \tA\tB\tC\n1\t10\t20\t30\t\n2\t11\t21\t31\t\n3\t13\t23\t33\t\n";
        String input = "C,3,A1,10,B1,20,C1,30,A2,11,B2,21,C2,31,A3,13,B3,23,C3,33";
        testSheet("Full Sheet", points, args, expected, input);
    }

    private void emptySheet(int points) {
        String[] args = {"K", "5"};
        String expected = " \tA\tB\tC\tD\tE\tF\tG\tH\tI\tJ\tK\n1\t \t \t \t \t \t \t \t \t \t \t \t\n2\t \t \t \t \t \t \t \t \t \t \t \t\n3\t \t \t \t \t \t \t \t \t \t \t \t\n4\t \t \t \t \t \t \t \t \t \t \t \t\n5\t \t \t \t \t \t \t \t \t \t \t \t\n";
        String input = "CK,5";
        testSheet("Empty Sheet", points, args, expected, input);
    }

    //***********************Erroneous sheets - defensive programming************************
    private void onlyOneInput(int points) {
        String[] args = {"F"};
        String expected = "Invalid input: must specify the spreadsheet range, followed by cell-value pairs. You entered an odd number of inputs.";
        String input = "F";
        testSheet("Only One Input", points, args, expected, input);
    }

    private void oddNumerOfInputs(int points) {
        String[] args = {"F", "10", "D7", "20", "A3", "15", "E6", "3.75", "A10", "598", "B9", "200", "F10", "1000", "C4", "50", "D5"};
        String expected = "Invalid input: must specify the spreadsheet range, followed by cell-value pairs. You entered an odd number of inputs.";
        String input = "F,10,D7,20,A3,15,E6,3.75,A10,598,B9,200,F10,1000,C4,50,D5";
        testSheet("Odd Number of Inputs", points, args, expected, input);
    }

    private void invalidCellLabel(int points) {
        String[] args = {"F", "10", "D7", "20", "A3", "15", "E6", "3.75", "A11", "598", "B9", "200", "F10", "1000", "C4", "50"};
        String expected = "Invalid cell label: A11";
        String input = "F,10,D7,20,A3,15,E6,3.75,A11,598,B9,200,F10,1000,C4,50";
        testSheet("Invalid Cell Label", points, args, expected, input);
    }

    private void invalidCellLabelAndValue(int points) {
        String[] args = {"F", "10", "D7", "20", "A3", "15", "E6", "Hello", "A11", "598", "B9", "200", "F10", "1000", "C4", "50"};
        String expected = "Invalid cell label: A11";
        String input = "F,10,D7,20,A3,15,E6,Hello,A11,598,B9,200,F10,1000,C4,50";
        testSheet("Invalid Cell Label AND Invalid Value", points, args, expected, input);
    }

    private void invalidCellValue(int points) {
        String[] args = {"F", "10", "D7", "20", "A3", "15", "E6", "Hello", "A10", "598", "B9", "200", "F10", "1000", "C4", "50"};
        String expected = "Invalid cell value: Hello";
        String input = "F,10,D7,20,A3,15,E6,Hello,A10,598,B9,200,F10,1000,C4,50";
        testSheet("Invalid Cell Value", points, args, expected, input);
    }

    private void invalidRangeColumn(int points) {
        String[] args = {"a", "5"};
        String expected = "Please specify a valid spreadsheet range, with highest column between A and Z and highest row as an integer";
        String input = "a,5";
        testSheet("Invalid Column Range", points, args, expected, input);
    }

    private void invalidRangeRow(int points) {
        String[] args = {"Z", "A"};
        String expected = "Please specify a valid spreadsheet range, with highest column between A and Z and highest row as an integer";
        String input = "Z,A";
        testSheet("Invalid Row Range", points, args, expected, input);
    }

    //********************************testing method logic

    private void getCellValueLogic(int points) {
        this.output +="TEST: getCellValueLogic\n";
        this.output +="POINTS: "+points +"\n";

        String[] args = {"A1", "10", "Z999", "9990", "Q55555", "555550", "B2", "20"};
        String val = SpreadSheetPrinter.getCellValue('A', 1, args);
        if (!val.equals("10")) {
            this.output += "Given the label-value array of 'A1 10 Z999 9990 Q55555 555550 B2 20', getCellValue was called with 'A', 1, and the array, and instead of returning '10', it returned " + val;
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        val = SpreadSheetPrinter.getCellValue('Z', 999, args);
        if (!val.equals("9990")) {
            this.output += "Given the label-value array of 'A1 10 Z999 9990 Q55555 555550 B2 20', getCellValue was called with 'Z', 999, and the array, and instead of returning '9990', it returned " + val;
            this.output += "-----FAILED-----" + "\n";
            return;
        }

        val = SpreadSheetPrinter.getCellValue('A', 2, args);
        if (!val.equals(" ")) {
            this.output += "Given the label-value array of 'A1 10 Z999 9990 Q55555 555550 B2 20', getCellValue was called with 'A', 2, and the array, and instead of returning a blank space, it returned " + val;
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        this.output += "+++++PASSED+++++" + "\n";
        this.points += points;
    }

    private void isCurrentLogic(int points) {
        this.output +="TEST: isCurrentLogic\n";
        this.output +="POINTS: "+points +"\n";
        boolean isIt = SpreadSheetPrinter.isCurrent('A',1,"A1");
        if(!isIt){
            this.output += "isCurrent incorrectly returned false when called with the following 3 arguments: 'A', 1, 'A1'";
            this.output += "-----FAILED-----" + "\n";
            return;
        }

        isIt = SpreadSheetPrinter.isCurrent('A',2,"A1");
        if(isIt){
            this.output += "isCurrent incorrectly returned true when called with the folloiwng 3 arguments: 'A', 2, 'A1'";
            this.output += "-----FAILED-----" + "\n";
            return;
        }

        isIt = SpreadSheetPrinter.isCurrent('Z',1,"A1");
        if(isIt){
            this.output += "isCurrent incorrectly returned true when called with the folloiwng 3 arguments: 'Z', 1, 'A1'";
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        this.output += "+++++PASSED+++++" + "\n";
        this.points += points;
    }

    private void printColumnHeadersLogic(int points) {
        this.output +="TEST: printColumnHeaders Logic\n";
        this.output +="POINTS: "+points +"\n";

        this.helper.beginOutputRedirect();
        SpreadSheetPrinter.printColumnHeaders('D',10000);
        String output = this.helper.endOutputRedirect();
        String expected = "     \tA\tB\tC\tD\n";
        if(!output.equals(expected)){
            this.output += "EXPECTED OUTPUT:\n" + expected + "\nACTUAL OUTPUT: " + output;
            this.output += "-----FAILED-----" + "\n";
            return;
        }

        this.helper.beginOutputRedirect();
        SpreadSheetPrinter.printColumnHeaders('F',1);
        output = this.helper.endOutputRedirect();
        expected = " \tA\tB\tC\tD\tE\tF\n";
        if(!output.equals(expected)){
            this.output += "EXPECTED OUTPUT:\n" + expected + "\nACTUAL OUTPUT: " + output;
            this.output += "-----FAILED-----" + "\n";
            return;
        }

        this.output += "+++++PASSED+++++" + "\n";
        this.points += points;
    }

    private void validateAllCellLabelsLogic(int points) {
        this.output +="TEST: validateAllCellLabels Logic\n";
        this.output +="POINTS: "+points +"\n";
        //all valid
        String[] args = {"A1","10","Z999","9990","Q55555","555550","B2","20"};
        String val = SpreadSheetPrinter.validateAllCellLabels(args,'Z',66666);
        if(val != null){
            this.output += "for the inputs 'A1 10 Z999 9990 Q55555 555550 B2 20','Z',66666, all values should've been seen as valid, but validateAllCellLabels returned the following as invalid: " + val;
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        //out of range - letter after last specified column
        val = SpreadSheetPrinter.validateAllCellLabels(args,'B',66666);
        if(val == null || (!val.equals("Z999") && !val.equals("Q55555")) ){
            this.output += "for the inputs 'A1 10 Z999 9990 Q55555 555550 B2 20','B',66666, Z999 and Q55555 should've been seen as invalid, but validateAllCellLabels returned the following: " + val;
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        //our of range - lowercase letter
        args[4] = "q55555";
        val = SpreadSheetPrinter.validateAllCellLabels(args,'Z',66666);
        if(val == null || !val.equals("q55555")){
            this.output += "for the inputs 'A1 10 Z999 9990 qQ55555 555550 B2 20','Z',66666, qQ55555 should've been seen as invalid, but validateAllCellLabels returned the following: " + val;
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        //out of range numerically
        args[4] = "Q55555";
        val = SpreadSheetPrinter.validateAllCellLabels(args,'Z',10);
        if(val == null || (!val.equals("Z999") && !val.equals("Q55555"))){
            this.output += "for the inputs 'A1 10 Z999 9990 Q55555 555550 B2 20','Z',10, Z999 and Q55555 should've been seen as invalid, but validateAllCellLabels returned the following:" + val;
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        //invalid label with two letters
        args[6] = "BB";
        val = SpreadSheetPrinter.validateAllCellLabels(args,'Z',66666);
        if(val == null || (!val.equals("BB")) ){
            this.output += "for the inputs 'A1 10 Z999 9990 Q55555 555550 BB 20','B',66666, BB should've been seen as invalid, but validateAllCellLabels returned the following: " + val;
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        //invalid label with one letter and no number
        args[6] = "B2";
        args[4] = "Q";
        val = SpreadSheetPrinter.validateAllCellLabels(args,'Z',66666);
        if(val == null || (!val.equals("Q")) ){
            this.output += "for the inputs 'A1 10 Z999 9990 Q 555550 BB 20','B',66666, Q should've been seen as invalid, but validateAllCellLabels returned the following: " + val;
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        //invalid label with one number and no letter
        args[4] = "Q55555";
        args[0] = "1";
        val = SpreadSheetPrinter.validateAllCellLabels(args,'Z',66666);
        if(val == null || (!val.equals("1")) ){
            this.output += "for the inputs '1 10 Z999 9990 Q 555550 BB 20','B',66666, 1 should've been seen as invalid, but validateAllCellLabels returned the following: " + val;
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        //invalid label with two numbers and no letter
        args[0] = "19";
        val = SpreadSheetPrinter.validateAllCellLabels(args,'Z',66666);
        if(val == null || (!val.equals("19")) ){
            this.output += "for the inputs '19 10 Z999 9990 Q 555550 BB 20','B',66666, 19 should've been seen as invalid, but validateAllCellLabels returned the following: " + val;
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        this.output += "+++++PASSED+++++" + "\n";
        this.points += points;
    }

    private void validateAllCellValuesLogic(int points) {
        this.output +="TEST: validateAllCellValues Logic\n";
        this.output +="POINTS: "+points +"\n";
        String[] args = {"A1","10","Z999","9990","Q55555","555550","B2","20"};
        String val = SpreadSheetPrinter.validateAllCellValues(args);
        if(val != null){
            this.output += "for the inputs 'A1 10 Z999 9990 Q55555 555550 B2 20','Z',66666, all values should've been seen as valid, but validateAllCellValues returned the following as invalid: " + val;
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        //invalid value - number followed by letter
        args[3] = "9B";
        val = SpreadSheetPrinter.validateAllCellValues(args);
        if(val == null || (!val.equals("9B")) ){
            this.output += "for the inputs 'A1 10 Z999 9B Q55555 555550 BB 20','B',66666, 9B should've been seen as invalid, but validateAllCellValues returned the following: " + val;
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        //invalid value - a letter instead of a number
        args[3] = "b";
        val = SpreadSheetPrinter.validateAllCellValues(args);
        if(val == null || (!val.equals("b")) ){
            this.output += "for the inputs 'A1 10 Z999 b Q 555550 BB 20','B',66666, b should've been seen as invalid, but validateAllCellValues returned the following: " + val;
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        //handles decimals?
        args[3] = "9";
        args[7] = "0.65";
        val = SpreadSheetPrinter.validateAllCellValues(args);
        if(val != null){
            this.output += "for the inputs 'A1 10 Z999 9990 Q55555 555550 B2 20','Z',0.65, all values should've been seen as valid, but validateAllCellValues returned the following as invalid: " + val;
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        this.output += "+++++PASSED+++++" + "\n";
        this.points += points;
    }

    private void validateRangeLogic(int points) {
        this.output +="TEST: validateRange Logic\n";
        this.output +="POINTS: "+points +"\n";

        String[] args = {"A","10","A1","10"};
        if(!SpreadSheetPrinter.validateRange(args)){
            this.output += "for the inputs 'A 10 A1 10', the range should be seen as valid but validateRange returned false";
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        //column invalid - two letters
        args[0]="AA";
        if(SpreadSheetPrinter.validateRange(args)){
            this.output += "for the inputs 'AA 10 A1 10', the column should be seen as invalid but validateRange returned true";
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        //column invalid - lowercase
        args[0]="a";
        if(SpreadSheetPrinter.validateRange(args)){
            this.output += "for the inputs 'a 10 A1 10', the column should be seen as invalid but validateRange returned true";
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        //column invalid - numeric
        args[0]="1";
        if(SpreadSheetPrinter.validateRange(args)){
            this.output += "for the inputs '1 10 A1 10', the column should be seen as invalid but validateRange returned true";
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        args[0]="A";
        //row invalid - is a letter
        args[1]="A";
        if(SpreadSheetPrinter.validateRange(args)){
            this.output += "for the inputs 'A A A1 10', the row should be seen as invalid but validateRange returned true";
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        //row invalid - < 1
        args[1]="0";
        if(SpreadSheetPrinter.validateRange(args)){
            this.output += "for the inputs 'A 0 A1 10', the row should be seen as invalid but validateRange returned true";
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        //row invalid - has a letter in it
        args[1]="1C";
        if(SpreadSheetPrinter.validateRange(args)){
            this.output += "for the inputs 'A 1C A1 10', the row should be seen as invalid but validateRange returned true";
            this.output += "-----FAILED-----" + "\n";
            return;
        }

        this.output += "+++++PASSED+++++" + "\n";
        this.points += points;
    }

    private void getIntegerLogic(int points) {
        this.output +="TEST: getInteger Logic\n";
        this.output +="POINTS: "+points +"\n";
        //valid int
        int i = SpreadSheetPrinter.getInteger("1");
        if(i != 1){
            this.output += "for the input '1', getInteger should've returned 1 but instead returned " + i;
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        //invalid = has a letter
        i = SpreadSheetPrinter.getInteger("1B");
        if(i != -1){
            this.output += "for the input '1B', getInteger should've returned -1 but instead returned " + i;
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        this.output += "+++++PASSED+++++" + "\n";
        this.points += points;
    }


    private void isValidDoubleLogic(int points) {
        this.output +="TEST: isValidDouble  Logic\n";
        this.output +="POINTS: "+points +"\n";
        //valid
        if(!SpreadSheetPrinter.isValidDouble ("1")){
            this.output += "for the input '1', isValidDouble should've returned true but instead returned false";
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        //valid
        if(!SpreadSheetPrinter.isValidDouble ("1.5")){
            this.output += "for the input '1.5', isValidDouble should've returned true but instead returned false";
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        //valid
        if(!SpreadSheetPrinter.isValidDouble ("0.523")){
            this.output += "for the input '0.523', isValidDouble should've returned true but instead returned false";
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        //invalid
        if(SpreadSheetPrinter.isValidDouble ("0.523b")){
            this.output += "for the input '0.523b', isValidDouble should've returned false but instead returned true";
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        //invalid
        if(SpreadSheetPrinter.isValidDouble ("1b")){
            this.output += "for the input '1b', isValidDouble should've returned false but instead returned true";
            this.output += "-----FAILED-----" + "\n";
            return;
        }
        this.output += "+++++PASSED+++++" + "\n";
        this.points += points;
    }

    //***************************testing method signatures****************************
    private void testSignature(String methodName, Class returnType, Class[] expectedParameters, Class toBeTested, int points){
        if(this.helper.methodSignature(methodName,returnType,expectedParameters,toBeTested)){
            this.output += "+++++PASSED+++++" + "\n";
            this.points += points;
        }else{
            this.output += "Method declaration was not as specified:\n" + this.helper.getErrorMessages();
            this.output += "-----FAILED-----" + "\n";
            return;
        }
    }
    private void getCellValueSignature(int points) {
        this.output +="TEST: getCellValue Signature\n";
        this.output +="POINTS: "+points +"\n";
        Class[] clazzes = {char.class,int.class,String[].class};
        testSignature("getCellValue",String.class,clazzes,SpreadSheetPrinter.class, points);
    }

    private void isCurrentSignature(int points) {
        this.output +="TEST: isCurrent Signature\n";
        this.output +="POINTS: "+points +"\n";
        Class[] clazzes = {char.class,int.class,String.class};
        testSignature("isCurrent",boolean.class,clazzes,SpreadSheetPrinter.class, points);
    }

    private void printColumnHeadersSignature(int points) {
        this.output +="TEST: printColumnHeaders Signature\n";
        this.output +="POINTS: "+points +"\n";
        Class[] clazzes = {char.class,int.class};
        testSignature("printColumnHeaders",void.class,clazzes,SpreadSheetPrinter.class, points);
    }

    private void validateAllCellLabelsSignature(int points) {
        this.output +="TEST: validateAllCellLabels Signature\n";
        this.output +="POINTS: "+points +"\n";
        Class[] clazzes = {String[].class,char.class,int.class};
        testSignature("validateAllCellLabels",String.class,clazzes,SpreadSheetPrinter.class, points);
    }

    private void validateAllCellValuesSignature(int points) {
        this.output +="TEST: validateAllCellValues Signature\n";
        this.output +="POINTS: "+points +"\n";
        Class[] clazzes = {String[].class};
        testSignature("validateAllCellValues",String.class,clazzes,SpreadSheetPrinter.class, points);
    }

    private void validateRangeSignature(int points) {
        this.output +="TEST: validateRange Signature\n";
        this.output +="POINTS: "+points +"\n";
        Class[] clazzes = {String[].class};
        testSignature("validateRange",boolean.class,clazzes,SpreadSheetPrinter.class, points);
    }

    private void getIntegerSignature(int points) {
        this.output +="TEST: getInteger Signature\n";
        this.output +="POINTS: "+points +"\n";
        Class[] clazzes = {String[].class};
        testSignature("getInteger",int.class,clazzes,SpreadSheetPrinter.class, points);
    }

    private void isValidDoubleSignature(int points) {
        this.output +="TEST: isValidDouble Signature\n";
        this.output +="POINTS: "+points +"\n";
        Class[] clazzes = {String[].class};
        testSignature("isValidDouble",boolean.class,clazzes,SpreadSheetPrinter.class, points);
    }
}