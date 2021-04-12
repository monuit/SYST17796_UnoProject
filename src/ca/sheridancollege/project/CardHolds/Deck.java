/**
 * SYST 17796 Project
 *
 * @author Mohammad Abdulhussain
 * @date March 28, 2021, @modified April 11, 2021
 * Sheena Manuel
 * @date April 10, 2021
 */

package ca.sheridancollege.project.CardHolds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import ca.sheridancollege.project.Cards.Card;
import ca.sheridancollege.project.Cards.DrawFour;
import ca.sheridancollege.project.Cards.DrawTwo;
import ca.sheridancollege.project.Cards.NumberCard;
import ca.sheridancollege.project.Cards.Reverse;
import ca.sheridancollege.project.Cards.Skip;
import ca.sheridancollege.project.Cards.WildCard;

public class Deck {

    private ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<>();
        buildDeck();
    }

    /**
     * Picks a random integer and draws the card at that index from the deck
     * Returns that card and removes it from deck
     *
     * @return
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


    // Prints all cards available in deck at that time

    public void printDeck() {
        for (Card card : deck) {
            System.out.println(card);
        }
    }

    // Create an enum with Colors
    enum Colors {
        GREEN("Green"), BLUE("Blue"), YELLOW("Yellow"), RED("Red");

        public final String color;

        Colors(String color) {
            this.color = color;
        }

        @Override
        public String toString() {
            return this.color;
        }
    }

    //Generates all cards needed for a full deck

    /** Testing for enum Points
     *     enum Points {
     *         ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"),
     *         SEVEN("7"), EIGHT("8"), NINE("9"), TWENTY("20"), FIFTY("50");
     *
     *         public final String points;
     *
     *         Points(String point) {
     *             this.points = point;
     *         }
     *
     *         @Override
     *         public String toString() {
     *             return this.points;
     *         }
     *     }
     */


    public void buildDeck() {
        //Two cards of 1 - 9 for every color
        // List<Points> cardPoints = new ArrayList<>(); // Enum points above array
        for (int i = 0; i < 2; i++) {
            for (Colors colors : Arrays.asList(Colors.GREEN, Colors.BLUE, Colors.YELLOW, Colors.RED)) {
                IntStream.range(1, 10).forEach(i1 -> deck.add(new NumberCard(i1, colors.toString())));
            }
        }

        // One card of 0 for every color
        for (Colors colors : Arrays.asList(Colors.GREEN, Colors.BLUE, Colors.YELLOW, Colors.RED)) {
            IntStream.range(0, 1).forEach(i -> deck.add(new NumberCard(i, colors.toString())));
        }
        //Two Draw Two cards; two Skip cards; and two Reverse cards.
        IntStream.range(0, 2).forEach(i -> Arrays.asList(Colors.GREEN, Colors.BLUE, Colors.YELLOW, Colors.RED).forEach(colors -> {
            deck.add(new DrawTwo(colors.toString()));
            deck.add(new Reverse(colors.toString()));
            deck.add(new Skip(colors.toString()));
        }));
        //In addition there are four Wild cards and four Wild Draw Four cards.
        IntStream.range(0, 4).forEach(i -> deck.add(new WildCard()));
        IntStream.range(0, 4).forEach(i -> deck.add(new DrawFour()));
    }
}







