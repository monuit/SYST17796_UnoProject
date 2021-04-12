/**
 * SYST 17796 Project
 *
 * @author Mohammad Abdulhussain
 * @date March 28, 2021
 */

package ca.sheridancollege.project.Cards;

public class NumberCard extends Card {

	private int numberOfCard;

	public NumberCard(int number, String color) {
		super(color);
		this.numberOfCard = number;
	}

	/**
	 * Gets the number on this card
	 */
	public int getNumberOfCard() {
		return numberOfCard;
	}

	public String toString() {
		return "A " + super.getColorOfCard() + " " + numberOfCard;
	}
}
