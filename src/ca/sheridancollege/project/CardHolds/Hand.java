/**
 * SYST 17796 Project
 * @author Mohammad Abdulhussain
 * @date March 28, 2021
 */

package ca.sheridancollege.project.CardHolds;
import java.util.ArrayList;
import ca.sheridancollege.project.Cards.Card;

public class Hand {
	
	private String name;
	
	private ArrayList<Card> HandArray;
	
	/**
	 * This is a hand
	 */
	public Hand(String name)
	{
		this.name = name;
		HandArray = new ArrayList<Card>();
	}
	
	/**Return the name of this hand's owner*/
	public String getName()
	{
		return name;
	}
	
	/**
	 * Shows the card at that index in your hand without removing it
	 */
        
	public Card getCard(int index)
	{
		return HandArray.get(index);
	}
	
	/**
	 * Adds a card to the array that is your hand
	 */
	public void addToHand(Card q)
	{
            
		HandArray.add(q);
            
	}
	
	/**
	 * Removes card from the array that is your hand
	 */
	public void removeFromHand(int q)
	{
		HandArray.remove(q);
	}
	
	public int getSize()
	{
		return HandArray.size();
	}

	/**
	 * Prints what your hand contains at that time
	 */
        @Override
	public String toString() {
		return null;
	}

}
