/*The assighnment contains the following 7 requirements. I noted when each one was accounted for throughout the code. 
	1. The class which you submit as your solution must be named UnesanehTokef 
	2. The refrain "On Rosh Hashanah they will be inscribed and on Yom Kippur they will be sealed" may only be outputted by a method named refrain - not from anywhere else in your code
	3. The text of the refrain must always have a blank line before it and after it, and the logic to add those blank lines must appear ONLY ONCE in the class.
	4. There must be a method called whoWill which outputs the two words"who will". Those two words may not be outputted anywhere else in the class
	5. There must be a method called whoBy which outputs the two words "who by". Those two words may not be outputted anywhere else in the class
	6. The output of your program must EXACTLY match the sample output provided to you - no changes at all to capitalization, spaces, punctuation, new lines, or anything else.
	7. All methods must be public, static, and have a void return value.*/

//Requirement #1 accounted for - The class which you submit as your solution must be named UnesanehTokef
public class UnesanehTokef{
	public static void main (String[] args){
		refrain ();
		
		//For each of the following five stanzas, use the following outline: whoBy, print (space bar + word + and + space bar), whoBy, println (space bar + word). 
		//#1 who by water and who by fire
		whoBy();
		System.out.print (" water and ");
		whoBy();
		System.out.println (" fire");
		
		//#2 who by sword and who by beast
		whoBy();
		System.out.print (" sword and ");
		whoBy();
		System.out.println (" beast");
		
		//#3 who by famine and who by thirst
		whoBy();
		System.out.print (" famine and ");
		whoBy();
		System.out.println (" thirst");
		
		//#4 who by upheaval and who by plague
		whoBy();
		System.out.print (" upheaval and ");
		whoBy();
		System.out.println (" plague");
		
		//#5 who by strangling and who by stoning
		whoBy();
		System.out.print (" strangling and ");
		whoBy();
		System.out.println (" stoning");

		refrain ();
		
		//For each of the following five stanzas, use the following outline: whoWill, print (space bar + word + and + space bar), whoWill, println (space bar + word). 
		//#1 who will rest and who will wander
		whoWill();
		System.out.print (" rest and ");
		whoWill();
		System.out.println (" wander");
		
		//#2 who will live in harmony and who will be harried
		whoWill();
		System.out.print (" live in harmony and ");
		whoWill();
		System.out.println (" be harried");
		
		//#3 who will enjoy tranquility and who will suffer
		whoWill();
		System.out.print (" enjoy tranquility and ");
		whoWill();
		System.out.println (" suffer");
		
		//#4 who will be impoverished and who will be enriched
		whoWill();
		System.out.print (" be impoverished and ");
		whoWill();
		System.out.println (" be enriched");
		
		//#5 who will be degraded and who will be exalted
		whoWill();
		System.out.print (" be degraded and ");
		whoWill();
		System.out.println (" be exalted");

		//The last line must have a blank before and after it - the final text has one after as well!
		System.out.println ();
		System.out.println ("Repentance, Prayer, and Charity annul the severity of the Decree");
	}

	//Bellow are the 3 methods titled refrain, whoWIll, and willBe that are called by the main method. 
	//Requirement #2 accounted for - The refrain "On Rosh Hashanah they will be inscribed and on Yom Kippur they will be sealed" may only be outputted by a method named refrain - not from anywhere else in your code.
	//Requirement #3 accounted for - The text of the refrain must always have a blank line before it and after it, and the logic to add those blank lines must appear ONLY ONCE in the class.
	public static void refrain (){
		System.out.println ();
		System.out.println ("On Rosh Hashanah they will be inscribed and on Yom Kippur they will be sealed");
		System.out.println ();
	}
	
	//Requirement #4 accounted for - There must be a method called whoWill which outputs the two words "who will". Those two words may not be outputted anywhere else in the class.
	public static void whoWill (){
		System.out.print ("who will");
	}
	
	//Requirement #5 accounted for - There must be a method called whoBy which outputs the two words "who by". Those two words may not be outputted anywhere else in the class.
	public static void whoBy (){
		System.out.print ("who by");
	}
}




