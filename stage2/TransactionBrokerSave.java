/*
Assighnment #3
*/

/*
To do list: 
	- Make negative sign (-) apear before the dolar sign
	- Check into Math.pow - Are we allowed to use it? 

*/

import java.lang.Math;
public class TransactionBrokerSave { 
//import java.util.Arrays;

	public static void main(String[] args) { 

		double currentBalance = 500;
		//QUESTION - Is it an issue that it equals zero?
		//Should this be a double?
		double totalPenalties = 0;

		for (int balanceChange = 0; balanceChange < args.length; balanceChange++) {
			
			double additionToBalance = 0;

			//Check if args0 is a double
			if (isValidDouble (args [balanceChange])) {	

				//Check if args1 is a double 
				if (balanceChange + 1 < args.length && isValidDouble (args [balanceChange + 1])) {
					
					/*args0: Calculate & Print */
					
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

						//If it is, compute the equation
						double op1 = Double.parseDouble (args [balanceChange]);
						double op2 = Double.parseDouble (args [balanceChange + 2]);

						/*Calculate & Print*/
						additionToBalance = operation (op1, op2, args [balanceChange + 1]);

						if (currentBalance >= 500 && (currentBalance + additionToBalance < 500)) {
 
						currentBalance += additionToBalance;

						System.out.println ("Your balance: $" + currentBalance);					
						System.out.println ("Your last transaction lowered your balance to $" + currentBalance);
						System.out.println ("You have been charged a low-balance penalty of $20.0");
						
						currentBalance -= 20;
						totalPenalties += 20;
	
						System.out.println ("Your balance: $" + currentBalance);

				 		} else {
					 		currentBalance += additionToBalance;
							System.out.println ("Your balance: $" + currentBalance);
				 		}

				 		balanceChange += 2;

				 	//Broke rule
				 	} else {
				 		System.out.println ("The \"" + args [balanceChange + 1] + "\" operator must be preceded by, and followed, by, numeric operands");
						return;
				 	}

				//Check for invalid Operator
				} else if (balanceChange + 2 < args.length && isValidDouble (args [balanceChange + 2])) {
					System.out.println ("\"" + args [balanceChange + 1] + "\"" + " is not a valid operator. Transactions can’t be processed.");
					return;

				//When the last transaction is a single double 
				} else {
					if (isValidDouble (args [balanceChange])) {
					
						additionToBalance = Double.parseDouble (args [balanceChange]);

						//Calculate & Print
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
					} 
				}
			} else {
				System.out.println ("\"" + args [balanceChange] + "\"" + " is not a valid operator. Transactions can’t be processed.");
				return;
			}
		}
		for (int i = 0; i < 20 ; i++) {
			System.out.print ("*");
		}
		System.out.println ("");
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
	//CHECK INTO MATH.POW 
	public static double exponentiate (double op1, double op2) {
		return (Math.pow (op1, op2));
	}
}























