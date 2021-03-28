/**
 * SYST 17796 Project
 * @author Mohammad Abdulhussain
 * @date March 28, 2021
 */

package Cards;

public class DrawTwo extends Card{

	public DrawTwo(String color) {
		super(color);
	}
	
	public String toString()
	{
		return "A " + super.getColor() + " draw 2";
	}
}
