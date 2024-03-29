/**
 * SYST 17796 Project
 *
 * @author Mohammad Abdulhussain
 * @date March 28, 2021, @modified April 11, 2021
 */

package ca.sheridancollege.project.CardHolds;

import java.util.ArrayList;

import ca.sheridancollege.project.Cards.Card;

public class Hand {

    private String name;

    private ArrayList<Card> HandArray;


    //This is a hand

    public Hand(String name) {
        this.name = name;
        HandArray = new ArrayList<>();
    }

    // Return the name of this hand's owner
    public String getPlayerName() {
        return name;
    }


    // Shows the card at that index in your hand without removing it

    public Card getCardIndex(int index) {
        return HandArray.get(index);
    }


    // Adds a card to the array that is your hand

    public void addToHand(Card Card) {

        HandArray.add(Card);

    }

    // Removes card from the array that is your hand

    public void removeFromHand(int Card) {
        HandArray.remove(Card);
    }

    public int getHandSize() {
        return HandArray.size();
    }


    //Prints what your hand contains at that time

    public String toString() {
        {
            StringBuilder returned = new StringBuilder("Your hand contains: \n");
            int counter = 1;

            for (Card Card : HandArray) {
                if (counter == HandArray.size()) {
                    returned.append("[").append(counter).append("]").append(Card);
                } else {
                    returned.append("[").append(counter).append("]").append(Card).append("\n");
                    counter++;
                }
            }

            return returned.toString();
        }
    }

}
