/**
 * SYST 17796 Project
 * @author Mohammad Abdulhussain
 * @date March 28, 2021
 */

package ca.sheridancollege.project.Cards;

public class Reverse extends Card{

	public Reverse(String color)
	{
		super(color);
	}
	
	public String toString()
	{
		return "A " + super.getColor() + " Reverse card";
	}
}
