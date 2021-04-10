/**
 * SYST 17796 Project
 *
 * @author Mohammad Abdulhussain
 * @date March 28, 2021
 */
package ca.sheridancollege.project.Cards;

public class Card {

    public enum Order {
        NUMBER, DRAWTWO, REVERSE, SKIP, WILD, WILD_D4
    }

    /**
     * Color of the card
     */
    //red, green, blue, and yellow
    private String color = "none";
    /**
     * If the effect of the card was used
     */
    private boolean wasUsed = false;

    public Card(String color) {
        this.color = color;
    }

    public Card() {
    }

    /**
     * Sets the color of a card.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * If card has a color this returns it. Returns "none" if no color.
     */
    public String getColor() {
        return color;
    }

    /**
     * Get if effect was used
     */
    public boolean getUsed() {
        return wasUsed;
    }

    /**
     * Set the effect to have been used
     */
    public void setUsed() {
        wasUsed = true;
    }

    /**
     * Returns the number of the card or 999 if it doesn't have one.
     */
    public int getNumber() {
        return 999;
    }
}
