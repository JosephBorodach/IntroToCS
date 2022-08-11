import java.lang.Double;
public class DoubleCell implements Cell {
	private double value; //have one constructor, which takes one parameter, a double, which is the value of this cell. 
	public DoubleCell (double value) { //DoubleCell must store this value in a private instance variable
		this.value = value;
	} 
	public double getNumericValue() { //must return the value that was passed to its constructor
		return this.value;
	}
	public String getStringValue() {
		return Double.valueOf(this.value).toString(); 		
	}
}