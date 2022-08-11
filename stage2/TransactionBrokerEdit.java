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

import java.lang.Math;
public class TransactionBrokerEdit { 
//import java.util.Arrays;

	public static void main(String[] args) { 

		double currentBalance = 500;
		//QUESTION - Is it an issue that it equals zero?
		//Should this be a double?
		int totalPenalties = 0;

		for (int balanceChange = 0; balanceChange < args.length; balanceChange++) {
			
			double additionToBalance = 0;

			//Check if args0 is a double
			if (isValidDouble (args [balanceChange])) {	

				//Check if args1 is a double 
				if (balanceChange + 1 < args.length && isValidDouble (args [balanceChange + 1])) {
					
					/*Add balanceChange*/
					
					additionToBalance = Double.parseDouble (args [balanceChange]);

					if (currentBalance >= 500 && (currentBalance + additionToBalance < 500)) {
 
						currentBalance += additionToBalance;

						System.out.println ("Your balance: $" + currentBalance);					
						System.out.println ("Your last transaction lowered your balance to $" + currentBalance);
						System.out.println ("You have been charged a low-balance penalty of $20.0");
						
						//calculate
						currentBalance -= 20;
						totalPenalties += 20;
	
						System.out.println ("Your balance: $" + currentBalance);

				 	} else {
				 		currentBalance += additionToBalance;
						System.out.println ("Your balance: $" + currentBalance);
				 	}

				//Check if arg1 is an OPERATOR
				} else if (balanceChange + 1 < args.length && isValidOperator (args [balanceChange + 1])) {
									
					/*If it is a valid operator, check if the next args is a valid double
					If it is not valid, then inform them of the rules
					*/
					if (balanceChange + 2 < args.length && isValidDouble (args [balanceChange + 2])) { 

						//If it is, then compute the equation
						double op1 = Double.parseDouble (args [balanceChange]);
						double op2 = Double.parseDouble (args [balanceChange + 2]);

						/*Add balanceChange*/
						additionToBalance = operation (op1, op2, args [balanceChange + 1]);

						if (currentBalance >= 500 && (currentBalance + additionToBalance < 500)) {
 
						currentBalance += additionToBalance;

						System.out.println ("Your balance: $" + currentBalance);					
						System.out.println ("Your last transaction lowered your balance to $" + currentBalance);
						System.out.println ("You have been charged a low-balance penalty of $20.0");
						
						//calculate
						currentBalance -= 20;
						totalPenalties += 20;
	
						System.out.println ("Your balance: $" + currentBalance);

				 		} else {
					 		currentBalance += additionToBalance;
							System.out.println ("Your balance: $" + currentBalance);
				 		}

				 		balanceChange += 2;
				 	}
				} else {
					if (isValidDouble (args [balanceChange])) {
					
						additionToBalance = Double.parseDouble (args [balanceChange]);

						if (currentBalance >= 500 && (currentBalance + additionToBalance < 500)) {
 
							currentBalance += additionToBalance;

							System.out.println ("Your balance: $" + currentBalance);					
							System.out.println ("Your last transaction lowered your balance to $" + currentBalance);
							System.out.println ("You have been charged a low-balance penalty of $20.0");
							
							//calculate
							currentBalance -= 20;
							totalPenalties += 20;
		
							System.out.println ("Your balance: $" + currentBalance);					
						
						} else {	
							//print message
							System.out.println ("The \"" + args [balanceChange + 1] + "\" operator must be preceded by, and followed, by, numeric operands");
						}
					}
				}
			}
		}

		for (int i = 1; i <= 19 ; i++) {
			System.out.print ("*");
		}
		
		System.out.println ("*");
		System.out.println ("Your final balance: $" + currentBalance);
		System.out.println ("The total you were charged in penalties: $" + totalPenalties);
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
		//RETURN!!!
		if (str.equals("add") || str.equals("sub") || str.equals("mul") || str.equals("div") || str.equals("mod") || str.equals("pow")) {
			return true;
		} else {
			return false;
		} 
	}

	public static double operation (double op1, double op2, String str) {
	//
		switch(str){
			case "add":
				return (op1 + op2);
				//break;
			case "sub":
				return (op1 - op2);
				//break;
			case "mul":
				return (op1 * op2);
				//break;
			case "div":
				return (op1 / op2);
				//break;
			case "pow":
				return exponentiate (op1, op2);
			default:
				return (op1 % op2);
		}
	}

	//Use a if and if else statement
	//Did I use the Math.pow correctly?
	//CHECK INTO MATH.POW 
	public static double exponentiate (double op1, double op2) {
		return (Math.pow (op1, op2));
	}
}























