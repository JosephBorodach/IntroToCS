/*
Assighnment #3
*/

/* 
Make a tax machine that can do the following
1) Look at input line
2) Assess data from input line
3) Save data to make calculations
4) Use those calculations to keep track of larger balance. 
5) Constantly inform user of current balance 
6) Report final balance to user
*/

/*
Remaining things to add to Pseudo Code: 
	
	- 
	- 
	-  

Remaining parts of Code that need work: 
	- Operation Methods
	- Actual operations
	- 

*/

/*
Pseudo Code: 
1) Declare the Variables which will be reused throughout the code

2) Check to see if the next space on the input line is a valid digit

	3) If it is a valid digit, save the digit to op1 and move to (5)
	4) If it is not a valid digit, then go to LASTSTEP and end the program. 

5) Check to see if there is a valid operator

	6) If there was not a valid operator, then skip to (12).
	7) If there was a valid operator, save it to operator and then go to (8). 

8) Check to see if there is another digit: 

8a)	If there is not another valid digit, print the following
	
	"The " + (fill in whatever operator was input) + " operator must be preceded by, and followed by, numeric operands"

8b) If there was another valid digit, save it to op2

9) Perform the calculation

10) Add the total calculation to the currentBalance

There is supposed to be an exponentiate method here, but I do not really understand that

11) If there was no operator, then calculate op1 into the currentBalance. 

12) Check to see if there the balance went below $500
Now, no matter what the current "calculation" was computed into currentBalance 
so regardless of if there was an operator or not, we are here. 

	13) If it did go below $500, compute two calculations and print three strings:
		Inform user: 
			(a) bellow 500 
			(b) 20$ penalty
			(c) subtract 20$ from his currentBalance and report the balance.  
			current balance

		Compute two calculations:
			a) Update the current balance by subtracting 20$
			b) Add the $20 penalty to the totalPenalties 

		After this, return to (2)

	14) If it did not go bellow $500, inform the user only of his updated balance. 

15) return to (2)



LAST STEP) Provide reports and end program
	a) Output 20 stars
	b) Report final balance to the user
	c) Report to the user total charges in penalties
	d) End program
*/

/*
Code Begins Here
*/

//import java.util.Arrays;
//Question: Why don't I need this ^?

public class TransactionBrokerEditAgain { 
	public static void main(String[] args) { 

		//import java.util.Arrays;
		double currentBalance = 500;
		//QUESTION - Is it an issue that it equals zero?
		//Should this be a double?
		int totalPenalties = 0;
		
		for (int balanceChange = 0; balanceChange < args.length; balanceChange++) {
			
			//Is is
			if (isValidDouble (args [balanceChange])) {	

				//Check args1 is a valid double 
				if (isValidDouble (args [balanceChange + 1])) {
					/*if it is a valid double, then add balanceChange*/
					currentBalance += args [balanceChange];

					//checkAndPrint
					checkAndPrint (currentBalance, totalPenalties);
				 
				//Check if args1 it is a valid OPERATOR
				} else if (isValidOperator (args [balanceChange + 1])) {
									
					/*If args1 is a valid operator, check if the next args is a valid double
					If it is not valid, then inform them of the rules
					*/
					if (isValidDouble (args [balanceChange + 2])) { 

						//If it is, then compute the equation
						operation (args [balanceChange], args [balanceChange + 2], args [balanceChange + 1]);
							
						//Add operation to currentBalance
						currentBalance += operation;

						//checkAndPrint
						checkAndPrint (currentBalance, totalPenalties);

					} else {
						//print message
						System.out.println ("The " + args [balanceChange + 1] + " operator must be preceded by, and followed, by, numeric operands");
					}

				/*If arg 1 is not a valid operator, check if it is an Exponent
				If that does not work, print invalid operator informing message.*/
				} else if (isValidExponentiate (args [balanceChange + 1])) {
						
					/*If it is a valid exponentiate, check if the next args is a valid double
					*/
					if (isValidDouble (args [balanceChange + 2])) {					

						//If it is, then compute the equation
						exponentiate (args [balanceChange], args [balanceChange + 2]);
							
							//Add operation to currentBalance
							currentBalance += exponentiate;

							//checkAndPrint
							checkAndPrint (currentBalance, totalPenalties);

					} else {
						//print message
						System.out.println ("The " + args [balanceChange + 1] + " operator must be preceded by, and followed, by, numeric operands");
					}
				//All checks for args 1 failed
				} else {
					
					//What am I supposed to be doing with the quotation marks?
					//REMEMBER - Add in "" around the args
					System.out.println (args [balanceChange + 1] + " is not a valid operator Transactions can’t be processed.");
				}
			}
		}
	//Figure out where to put this and how to end the method 
	lastStep (currentBalance, totalPenalties);
	}

	public static boolean isValidDouble (String str) {
		try {
			Double.parseDouble (str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isValidOperator (String str) {
		//Check if it is a specific written symbol, if it is return true; otherwise return false
		//QUESTION: The only one I am leaving out here is exponiante because it is not an operator, correct? 
		
		switch(str){
			case "add":
				isValidOperator = true;
				break;
			case "sub":
				isValidOperator = true;
				break;
			case "mul":
				isValidOperator = true;
				break;
			case "div":
				isValidOperator = true;
				break;
			default:
				isValidOperator = false;
			}
		

		/* This hopefully will be trash
		if (str = "add" || str == "sub" || str == "mul" || str == "div" || str == "mod") {
			return true;

		} if else (operator == "sub") {
			return true;
		} if else (operator == "mul") {
			return true;
		} if else (operator == "div") {
			return true;
		} if else (operator == "mod") {
			return true;
		else {
			return false;
		} 
		*/

	}

	public static double operation (double op1, double op2, String str) {
	//Use an if else statement to determine what symbol to use
	//Do I need another else statement here? 
		
		switch(str){
			case "add":
				operation = op1 + op2;
				break;
			case "sub":
				operation = op1 - op2;
				break;
			case "mul":
				operation = op1 * op2;
				break;
			case "div":
				operation = op1 / op2;
				break;
			default:
				operation = op1 % op2;
}


		/*
		if (str == "add") {
			operation = (op1 + op2);
		} if else (str == "sub") {
			operation = (op1 - op2);
		} if else (str == "mul") {
			operation = (op1 * op2);
		} if else (str == "div") {
			operation = (op1 / op2);
		//The last one is mod and  there is no need to check it
		} else {
			operation = (op1 % op2); 
		}
		*/

	}

	public static boolean isValidExponentiate (String str) {
		//Check if it is a specific written symbol, if it is return true; otherwise return false
		//QUESTION: The only one I am leaving out here is exponiante because it is not an operator, correct? 
		if (str == "pow") {
			return true;
		} else {
			return false;
		} 
	}

	//Use a if and if else statement
	//Did I use the Math.pow correctly? 
	public static double exponentiate (double op1, double op2) {
		exponentiate = Math.pow(op1, op2);
	}


	public static double checkAndPrint (double currentBalance, double totalPenalties) {
		if (currentBalance < 500) {
			
			/*Inform user that their balance went below $500 
			and that they are accordingly charged a $20 fee.*/
			System.out.println ("Your last transaction lowered your balance to $" + currentBalance);
			System.out.println ("You have been charged a low-balance penalty of 20.0$");
			
			//calculate
			currentBalance -= 20;
			totalPenalties += 20;
		}
		//Regardless, print thier updated currentBalance
		System.out.println ("Your balance: $" + currentBalance);
	}

/*
LAST STEPS
	a) Output 20 stars
	b) Report final balance to the user
	c) Report to the user total charges in penalties
	d) End program
*/

	public static double lastStep (double currentBalance, double totalPenalties) {
		for (int i = 1; i <= 19 ; i++) {
			System.out.print ("*");
		}
		System.out.println ("*");
		System.out.println ("Your final balance: $" + currentBalance);
		System.out.println ("The total you were charged in penalties: $" + totalPenalties);
	}
}






















