/**
 * SYST 17796 Project
 * @author Mohammad Abdulhussain
 * @date March 28, 2021
 */

package ca.sheridancollege.project.RunGame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import ca.sheridancollege.project.CardHolds.Deck;
import ca.sheridancollege.project.CardHolds.Hand;
import ca.sheridancollege.project.Cards.*;

/**
 * This class will run the game of UNO
 * 	Enforce the rules
 * 	Take care of player hands
 * 	Control computers
 * Player 1 is user, player 2 is comp1 and so on clockwise for 4 total players
 */

public class GameLogic {
	
	//input;
	BufferedReader userInput;
	/**Hand of each player*/
	private Hand user, comp1, comp2, comp3;
	/**The deck we draw from (Not infinite)*/
	private Deck deck1;
	private Card centerCard;

	public Hand getUser() {
		return user;
	}

	public void setUser(Hand user) {
		this.user = user;
	}

	public Hand getComp1() {
		return comp1;
	}

	public void setComp1(Hand comp1) {
		this.comp1 = comp1;
	}

	public Hand getComp2() {
		return comp2;
	}

	public void setComp2(Hand comp2) {
		this.comp2 = comp2;
	}

	public Hand getComp3() {
		return comp3;
	}

	public void setComp3(Hand comp3) {
		this.comp3 = comp3;
	}

	public Deck getDeck1() {
		return deck1;
	}

	public void setDeck1(Deck deck1) {
		this.deck1 = deck1;
	}

	public Card getCenterCard() {
		return centerCard;
	}

	public void setCenterCard(Card centerCard) {
		this.centerCard = centerCard;
	}

	public boolean isHasWon() {
		return hasWon;
	}

	public void setHasWon(boolean hasWon) {
		this.hasWon = hasWon;
	}

	public boolean isClockWise() {
		return clockWise;
	}

	public void setClockWise(boolean clockWise) {
		this.clockWise = clockWise;
	}

	public int getUpNext() {
		return upNext;
	}

	public void setUpNext(int upNext) {
		this.upNext = upNext;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	/**Keeps track if anyone won*/
	private boolean hasWon = false;
	/**Keeps track of the direction*/
	private boolean clockWise = true; 
	/**The player to play next: 1 is user going clockwise*/
	private int upNext = 1;
	/**Keeps track of who won*/
	private String winner;
	
	public GameLogic()
	{
		deck1 = new Deck();
//		comp1 = new Hand("Computer 1");
//		comp2 = new Hand("Computer 2");
//		comp3 = new Hand("Computer 3");
//		user = new Hand("The user");
//		//input = new Scanner(System.in);
//		userInput = new BufferedReader(new InputStreamReader(System.in));
//		
//		new SetUpCards();
//		runGame();
	}

	/**This will print if someone wins. It will get the game started with user playing first.*/
	private void runGame()
	{
		try {
			new UserTurn();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(winner + " has won");
		System.out.println("END");
	}
	

	/**Checks if any player has won or if they have UNO*/
	private boolean checkForWinner(Hand player)
	{
		if(player.getSize() == 1)
		{
			System.out.println(player.getName() + " has UNO!");;
		}
		
		return player.getSize() == 0;
	}


	/**
	 * Draws a 7 card hand
	 * @param x user who gets the cards
	 * @param y deck the cards are drawn from
	 */
	private void drawHand(Hand x, Deck y)
	{
		for(int i = 0; i < 7; i++)
		{
			x.addToHand(y.drawCard());
		}
	}
	
	
}
