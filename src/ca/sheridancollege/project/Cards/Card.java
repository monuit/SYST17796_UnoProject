/**
 * SYST 17796 Project
 *
 * @author Mohammad Abdulhussain
 * @date March 28, 2021
 */

package ca.sheridancollege.project.Cards;

public class Card {

    // Color of the card
    private String colorOfCard = "none";
    //If the effect of the card was used
    private boolean wasCardUsed = false;

    public Card(String color) {
        this.colorOfCard = color;
    }

    public Card() {
    }

    //Sets the color of a card.

    public void setColorOfCard(String colorOfCard) {
        this.colorOfCard = colorOfCard;
    }

    /**
     * If card has a color this returns it.
     * Returns "none" if no color.
     */
    public String getColorOfCard() {
        return colorOfCard;
    }

    //Get if effect was used
    public boolean getCardUsed() {
        return wasCardUsed;
    }

    //Set the effect to have been used
    public void setCardUsed() {
        wasCardUsed = true;
    }

    //Returns the number of the card or 0 if it doesn't have one.

    public int getNumberOfCard() {
        return 0;
    }
}
