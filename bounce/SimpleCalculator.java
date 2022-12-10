public class SimpleCalculator {
    public static void main(String[] args) {
         //parse the first command line argument into an int
        int firstNumber = Integer.parseInt(args[0]);
        //parse the first command line argument into an int
        int secondNumber = Integer.parseInt(args[1]);
        int thirdNumber = firstNumber + secondNumber;
        //output the equation, converting numbers to characters 
        System.out.println(firstNumber + " + " + secondNumber + " = " + thirdNumber);
    }
}