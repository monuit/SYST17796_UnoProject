/**
 * SYST 17796 Project
 * @author Mohammad Abdulhussain
 * @date March 28, 2021
 */

package ca.sheridancollege.project.Cards;

public class Skip extends Card{
	
	public Skip(String color)
	{
		super(color);
	}
	
        @Override
	public String toString()
	{
		return "A " + super.getColor() + " Skip card";
	}
	
}
