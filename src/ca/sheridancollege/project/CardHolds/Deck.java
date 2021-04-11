/**
 * SYST 17796 Project
 *
 * @author Mohammad Abdulhussain
 * @date March 28, 2021
 * Sheena Manuel
 * @date April 10, 2021
 */

package CardHolds;
import java.util.ArrayList;
import java.util.Random;

import Cards.Card;
import Cards.DrawFour;
import Cards.DrawTwo;
import Cards.NumberCard;
import Cards.Reverse;
import Cards.Skip;
import Cards.WildCard;

public class Deck {

    private ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<Card>();
        buildDeck();
    }

    /**
     * Picks a random integer and draws the card at that index from the deck
     * Returns that card and removes it from deck
     */
    public Card drawCard() {
        if (deck.size() < 1) {
            buildDeck(); // If no cards left refill the deck
        }
        Random number = new Random();
        int drawn = number.nextInt(deck.size());//Picks integer up to one less than deck size
        Card temporary = deck.get(drawn);//Saves card to temporary card at that generated index
        deck.remove(drawn);//Removes card from deck
        return temporary;
    }

    /**
     * Prints all cards available in deck at that time
     */
    public void printDeck() {
        for (Card card : deck)
        {
            System.out.println(card);
        }
    }

    /**
     * Generates all cards needed for a full deck
     */
    public void buildDeck() {
        //Two cards of 0 - 9 for every color
        for(int i = 0; i < 10; i++)
        {
            deck.add(new NumberCard(i,"Green"));
            deck.add(new NumberCard(i,"Green"));
        }
        for(int i = 0; i < 10; i++)
        {
            deck.add(new NumberCard(i,"Blue"));
            deck.add(new NumberCard(i,"Blue"));
        }
        for(int i = 0; i < 10; i++)
        {
            deck.add(new NumberCard(i,"Yellow"));
            deck.add(new NumberCard(i,"Yellow"));
        }
        for(int i = 0; i < 10; i++)
        {
            deck.add(new NumberCard(i,"Red"));
            deck.add(new NumberCard(i,"Red"));
        }
        //Two Draw Two cards; two Skip cards; and two Reverse cards.
        for (int i = 0; i < 2; i++) {
            deck.add(new DrawTwo("Green"));
        }
        for (int i = 0; i < 2; i++) {
            deck.add(new DrawTwo("Blue"));
        }
        for (int i = 0; i < 2; i++) {
            deck.add(new DrawTwo("Yellow"));
        }
        for (int i = 0; i < 2; i++) {
            deck.add(new DrawTwo("Red"));
        }
        for (int i = 0; i < 2; i++) {
            deck.add(new Reverse("Yellow"));
        }
        for (int i = 0; i < 2; i++) {
            deck.add(new Reverse("Green"));
        }
        for (int i = 0; i < 2; i++) {
            deck.add(new Reverse("Blue"));
        }
        for (int i = 0; i < 2; i++) {
            deck.add(new Skip("Green"));
        }
        for (int i = 0; i < 2; i++) {
            deck.add(new Skip("Red"));
        }
        for (int i = 0; i < 2; i++) {
            deck.add(new Skip("Yellow"));
        }
        for (int i = 0; i < 2; i++) {
            deck.add(new Skip("Blue"));
        }
        //In addition there are four Wild cards and four Wild Draw Four cards.
        for (int i = 0; i < 4; i++) {
            deck.add(new WildCard());
        }
        for (int i = 0; i < 4; i++) {
            deck.add(new DrawFour());
        }
    }
}
