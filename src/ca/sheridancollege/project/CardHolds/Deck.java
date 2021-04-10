/**
 * SYST 17796 Project
 *
 * @author Mohammad Abdulhussain
 * @date March 28, 2021
 * Sheena Manuel
 * @date April 10, 2021
 */
package ca.sheridancollege.project.CardHolds;

import java.util.ArrayList;
import java.util.Random;
import java.util.*;

import ca.sheridancollege.project.Cards.Card;
import ca.sheridancollege.project.Cards.Card.Order;
import ca.sheridancollege.project.Cards.NumberCard;
import ca.sheridancollege.project.Cards.DrawTwo;
import ca.sheridancollege.project.Cards.Reverse;
import ca.sheridancollege.project.Cards.Skip;
import ca.sheridancollege.project.Cards.WildCard;
import ca.sheridancollege.project.Cards.DrawFour;

public class Deck {

    private ArrayList<Card> deck;
    //private int cardsInDeck;

    public Deck() {
        deck = new ArrayList<Card>();
        buildDeck();
        printDeck();
    }

    /**
     * Picks a random integer and draws the card at that index from the deck
     * Returns that card and removes it from deck
     */
    public Card drawCard() {
        if (deck.size() < 1) {
            buildDeck(); // If no cards left refill the deck
        }
        Random num = new Random();
        int drawn = num.nextInt(deck.size()) - 1;//Picks integer up to one less than deck size
        Card temp = deck.get(drawn);//Saves card to temporary card at that generated index
        deck.remove(drawn);//Removes card from deck
        return temp;
    }

    /**
     * Prints all cards available in deck at that time
     */
    public void printDeck() {
        System.out.println("Total Cards:" + this.deck.size());
        for (Card card : this.deck) {
            System.out.println(card);
        }
    }

    /**
     * Generates all cards needed for a full deck
     */
    public void buildDeck() {
        int[] numbers = {0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9};
        String[] colors = {"red", "yellow", "green", "blue"};

        // Number Cards
        for (int number : numbers) {
            for (String color : colors) {
                NumberCard nCard = new NumberCard(number, color);
                this.deck.add(nCard);
            }
        }

        // Action Cards
        for (int i = 0; i <= 1; i++) {
            for (String color : colors) {
                DrawTwo d2Card = new DrawTwo(color);
                Reverse reverseCard = new Reverse(color);
                Skip skipCard = new Skip(color);

                this.deck.add(d2Card);
                this.deck.add(reverseCard);
                this.deck.add(skipCard);
            }
        }

        // Wild Cards
        for (int i = 0; i <= 3; i++) {
            DrawFour d4Card = new DrawFour();
            WildCard wildCard = new WildCard();

            this.deck.add(d4Card);
            this.deck.add(wildCard);
        }
    }

}
