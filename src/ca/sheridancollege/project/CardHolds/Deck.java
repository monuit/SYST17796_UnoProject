/**
 * SYST 17796 Project
 * @author Mohammad Abdulhussain
 * @date March 28, 2021
 */

package CardHolds;
import java.util.ArrayList;
import java.util.Random;

import Cards.Card;

public class Deck {
	
	private ArrayList<Card> deck;

	public Deck()
	{
		deck = new ArrayList<Card>();
		buildDeck();
	}
	
	/**
	 * Picks a random integer and draws the card at that index from the deck
	 * Returns that card and removes it from deck
	 */
	public Card drawCard()
	{
		if (deck.size() < 1) {
			buildDeck(); // If no cards left refill the deck
		}
		Random num = new Random();
		int drawn = num.nextInt(deck.size());//Picks integer up to one less than deck size
		Card temp = deck.get(drawn);//Saves card to temporary card at that generated index
		deck.remove(drawn);//Removes card from deck
		return temp;
	}
	
	/**
	 * Prints all cards available in deck at that time
	 */
	public void printDeck()
	{
	}
	
	/**
	 * Generates all cards needed for a full deck
	 */
	public void buildDeck()
	{
	}
}
