/**
 * SYST 17796 Project
 *
 * @author Mohammad Abdulhussain
 * @date March 28, 2021
 */

package ca.sheridancollege.project.RunGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import ca.sheridancollege.project.CardHolds.Deck;
import ca.sheridancollege.project.CardHolds.Hand;
import ca.sheridancollege.project.Cards.*;

public class GameLogic {

	//input of the User
	BufferedReader userInput;
	// Hand of each player in the game
	private final Hand userPlayer;
	private final Hand computerOne;
	// The deck to extract from; keep in mind this is not an infinite deck
	private final Deck firstDeck;
	private Card centerCardAffected;
	// In order to keep track of any winners, either Computer or Player
	private boolean hasAnyPlayerWon = false;
	// Keeps track of the direction of the game, either clockWise or anti-clockWise, depending on special cards
	private boolean clockWiseDirection = true;
	// The player to play next: 1 is user going clockwise
	private int nextPlayer = 1;
	// Winner of the game
	private String winnerOfGame;

	// This will start up the game
	public GameLogic()
	{
		firstDeck = new Deck();
		computerOne = new Hand("Computer 1");
		userPlayer = new Hand("The user");

		userInput = new BufferedReader(new InputStreamReader(System.in));

		SetUpTheGame();
		StartGame();
	}
	// This will print if someone wins. It will get the game started with user playing first.
	private void StartGame()
	{
		try {
			userTurnToPlay();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String s : Arrays.asList(winnerOfGame + " has won", "END")) {
			System.out.println(s);
		}
	}

	/**
	 * Lets the user go through their turn and checks if they win.
	 * At beginning of turn checks if player was affected by any special cards
	 */
	private void userTurnToPlay() throws IOException {
		if (centerCardAffected instanceof DrawFour && !centerCardAffected.getUsed()) {
			for (int i = 0; i < 4; i++) {
				userPlayer.addToHand(firstDeck.drawRandomCard());
			}
			centerCardAffected.setUsed();
			System.out.println("Next player drew 4 cards");
		}
		if (centerCardAffected instanceof DrawTwo && !centerCardAffected.getUsed()) {
			for (int i = 0; i < 2; i++) {
				userPlayer.addToHand(firstDeck.drawRandomCard());
			}
			centerCardAffected.setUsed();
			System.out.println("Next player drew 2 cards");
		}
		int choiceOfCardUserPicked = 0; //The index of the card the user picks
		try {
			choiceOfCardUserPicked = askForUserInput();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (choiceOfCardUserPicked == -1) { //User wants to draw a card from the deck which is random
			userPlayer.addToHand(firstDeck.drawRandomCard());
			System.out.println("You drew a card!");
			nextPlayer = checkNextPlayer();
			computersPlay();

		} else if (choiceOfCardUserPicked == -2) { //User wants to quit the game
			winnerOfGame = "No one";
			try {
				userInput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("You quit the game!");

		} else //User wants to play a card from their index (hand)
			if (!validUserInput(userPlayer, choiceOfCardUserPicked)) {
				userTurnToPlay();
			} else {

				centerCardAffected = userPlayer.getCard(choiceOfCardUserPicked);
				userPlayer.removeFromHand(choiceOfCardUserPicked);
				System.out.println("You played: " + centerCardAffected);

				if (centerCardAffected instanceof WildCard || centerCardAffected instanceof DrawFour) {
					System.out.println("Pick a color from: Yellow, Green, Blue, Red");
					String newColorChosen = "Yellow";

					if (userInput.ready()) {
						newColorChosen = userInput.readLine();
					} else {
						System.out.println("Game Ended - Idle");
						userInput.close();
						System.exit(0);
					}

					if (!newColorChosen.equalsIgnoreCase("Red") && !newColorChosen.equalsIgnoreCase("Green")
							&& !newColorChosen.equalsIgnoreCase("Blue") &&
							!newColorChosen.equalsIgnoreCase("Yellow")) {
						do { // Makes sure color input is valid
							System.out.println(newColorChosen + " is not a valid color!\nInput a new color:");
							if (userInput.ready()) {
								newColorChosen = userInput.readLine();
							} else {
								System.out.println("Game Ended - Idle");
								userInput.close();
								System.exit(0);
							}
						} while (!newColorChosen.equalsIgnoreCase("Red") &&
								!newColorChosen.equalsIgnoreCase("Green")
								&& !newColorChosen.equalsIgnoreCase("Blue") &&
								!newColorChosen.equalsIgnoreCase("Yellow"));
					}

					System.out.println("The color is now: " + newColorChosen);
					centerCardAffected.setColorOfCard(newColorChosen);
				}

				// Special card effects
				if (centerCardAffected instanceof Skip && !centerCardAffected.getUsed()) {
					nextPlayer++;
					centerCardAffected.setUsed();
				}
				if (centerCardAffected instanceof Reverse && !centerCardAffected.getUsed()) {
					clockWiseDirection = !clockWiseDirection;
					centerCardAffected.setUsed();
				}

				hasAnyPlayerWon = checkForWinner(userPlayer);
				if (!hasAnyPlayerWon) //Checks if the user has not won, then lets the computer play
				{
					nextPlayer = checkNextPlayer();
					computersPlay();
				} else {
					try {
						userInput.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					winnerOfGame = "The user";
				}

			}
	}
	// Checks if the card chosen can be played this turn.
	private boolean validUserInput(Hand player, int choice)
	{
		//Conditions to check for
		boolean sameNum = (player.getCard(choice).getNumberOfCard() == centerCardAffected.getNumberOfCard()) &&
				(centerCardAffected.getNumberOfCard() != 0);
		boolean sameColor = (player.getCard(choice).getColorOfCard().equalsIgnoreCase(centerCardAffected.getColorOfCard())) &&
				!(centerCardAffected.getColorOfCard().equals("none")) ;
		boolean wild = (player.getCard(choice) instanceof WildCard) || (player.getCard(choice) instanceof DrawFour);
		//DrawTwo or Skip or Reverse - Different Color
		boolean sameType = (player.getCard(choice) instanceof DrawTwo && centerCardAffected instanceof DrawTwo) ||
				(player.getCard(choice) instanceof Skip && centerCardAffected instanceof Skip) ||
				(player.getCard(choice) instanceof Reverse && centerCardAffected instanceof Reverse);
		if(sameNum || sameColor || wild || sameType)
		{
			return true;
		}
		//If this card couldn't be played ask for new input
		System.out.println("That card can't be played here! Pick a new card!");
		return false;
	}
	// Asks the user to pick a card from their hand, draw a card, or quit the game.
	private int askForUserInput() throws IOException {
		System.out.println("\nIt is your turn\n" + userPlayer);
		System.out.println("***\n" +
				"The center card is: " + centerCardAffected +"\n***");
		if (centerCardAffected instanceof WildCard || centerCardAffected instanceof DrawFour) {
			System.out.println("The color is " + centerCardAffected.getColorOfCard());
		}
		System.out.println("Which card will you play?\n" +
				"Type number next to the card to play that card.\n" +
				"Type \"-1\" to draw a card.\n" +
				"Or type \"-2\" to quit the game");

		int indexOfCardInHand = 0;

		if (userInput.ready()) {
			indexOfCardInHand = Integer.parseInt(userInput.readLine());
		} else {
			System.out.println("Game Ended - Idle");
			userInput.close();
			System.exit(0);
		}
		System.out.println("Input: "+indexOfCardInHand);
		if(indexOfCardInHand <= userPlayer.getSize() && indexOfCardInHand > 0)
		{
			return indexOfCardInHand - 1;
		}
		else if (indexOfCardInHand == -1) {
			return -1;
		}
		else if (indexOfCardInHand == -2) {
			return -2;
		}
		//If that number was out of bounds ask for another input
		System.out.println("That was not a valid choice. You don't have a card at " + indexOfCardInHand);
		return askForUserInput();
	}
	/**Lets the computers play and checks if any of them win. Keeps track of which computer won*/
	private void computersPlay()
	{
		String compPlaying = null; //This is the computer playing at that moment
		//Chooses who gets to play next
		if(nextPlayer == 2)
		{
			compPlaying = "Computer One";
			ComputerLogic(computerOne);
			hasAnyPlayerWon = checkForWinner(computerOne);
		}
		//If we have no winner check if a computer or player is next else declare the winner
		if(!hasAnyPlayerWon)
		{
			nextPlayer = checkNextPlayer();
			switch (nextPlayer) {
				case 1:
					try {
						userTurnToPlay();
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				default:
					computersPlay();
					break;
			}
		}
		else
		{
			try {
				userInput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			winnerOfGame = compPlaying;
		}

	}

	/**Checks all conditions to see whose turn is next.*/
	private int checkNextPlayer()
	{
		if(this.nextPlayer == 1 && !clockWiseDirection)
		{
			return 2;
		}
		else if(this.nextPlayer == 2 && clockWiseDirection)
		{
			return 1;
		}
		else
		{
			if(clockWiseDirection)
			{
				return this.nextPlayer +1;
			}
			else
			{
				return this.nextPlayer -1;
			}
		}

	}
	/**Checks if any player has won or if they have UNO*/
	private boolean checkForWinner(Hand player)
	{
		if (player.getSize() == 1) {
			System.out.println(player.getNameofOwner() + " has UNO!");
		}

		return player.getSize() == 0;
	}
	/**Decides for each computer what card would best be played here and plays it*/
	private void ComputerLogic(Hand player)
	{
		if (centerCardAffected instanceof DrawFour && !centerCardAffected.getUsed()) {
			for (int i = 0; i < 4; i++) {
				player.addToHand(firstDeck.drawRandomCard());
			}
			centerCardAffected.setUsed();
			System.out.println("Next player drew 4 cards");
		}
		if (centerCardAffected instanceof DrawTwo && !centerCardAffected.getUsed()) {
			for (int i = 0; i < 2; i++) {
				player.addToHand(firstDeck.drawRandomCard());
			}
			centerCardAffected.setUsed();
			System.out.println("Next player drew 2 cards");
		}

		System.out.println("\n"+"It is " + player.getNameofOwner() + "'s turn!");
		/*
		 * Play any special cards
		 * Try to play a number card
		 * Wild card
		 * Draw
		 */
		boolean cardPicked = false; //Sees if a card can be played
		int bestIndexOfComputerCard = 0; //The card we will play
		String bestColorOfComputerCard = "Blue";

		//Start off picking the best number card
		for(int i = 0; i < player.getSize(); i++)
		{
			if (player.getCard(i).getColorOfCard().equalsIgnoreCase(centerCardAffected.getColorOfCard())
					&& !player.getCard(i).getColorOfCard().equals("none")) {
				//Pick the card with the biggest number
				if (!cardPicked || player.getCard(i).getNumberOfCard() < centerCardAffected.getNumberOfCard()) {
					bestIndexOfComputerCard = i;
					cardPicked = true;
				}
			} else if (player.getCard(i).getNumberOfCard() ==
					centerCardAffected.getNumberOfCard() && player.getCard(i).getNumberOfCard() != 0) {
				//Pick the card with the biggest number
				if (!cardPicked || player.getCard(i).getNumberOfCard() < centerCardAffected.getNumberOfCard()) {
					bestIndexOfComputerCard = i;
					cardPicked = true;
				}
			}
		}
		//Go through and see if there is a better choice which is a special card
		for(int i = 0; i < player.getSize(); i++)
		{
			if (player.getCard(i) instanceof DrawTwo) {
				if (player.getCard(i).getColorOfCard().equalsIgnoreCase(centerCardAffected.getColorOfCard())) {
					bestIndexOfComputerCard = i;
					cardPicked = true;
				}
			} else if (player.getCard(i) instanceof Skip || player.getCard(i) instanceof Reverse) {
				if (player.getCard(i).getColorOfCard().equalsIgnoreCase(centerCardAffected.getColorOfCard())) {
					bestIndexOfComputerCard = i;
					cardPicked = true;
				}
			}
		}
		/*
		 * Check if a card got picked
		 * If one wasn't then check for wild cards
		 * Else draw a card
		 */
		if(!cardPicked)
		{
			//Check for wild card
			for(int i = 0; i < player.getSize(); i++)
			{
				if(player.getCard(i) instanceof WildCard || player.getCard(i) instanceof DrawFour)
				{
					bestIndexOfComputerCard = i;
					cardPicked = true;
					if ((int) (Math.random() * 4) + 1 == 1) {
						bestColorOfComputerCard = "Blue";
					}
					if ((int) (Math.random() * 4) + 1 == 2) {
						bestColorOfComputerCard = "Yellow";
					}
					if ((int) (Math.random() * 4) + 1 == 3) {
						bestColorOfComputerCard = "Green";
					}
					if ((int) (Math.random() * 4) + 1 == 4) {
						bestColorOfComputerCard = "Red";
					}
				}
			}
			//Nothing can be played so draw a card
			if(!cardPicked)
			{
				player.addToHand(firstDeck.drawRandomCard());
				System.out.println(player.getNameofOwner() + " drew a card!");
			}
		}
		//Actually play the card
		if(cardPicked)
		{
			centerCardAffected = player.getCard(bestIndexOfComputerCard);
			player.removeFromHand(bestIndexOfComputerCard);
			System.out.println(player.getNameofOwner() + " played " + centerCardAffected);
			if (centerCardAffected instanceof Skip && !centerCardAffected.getUsed()) {
				nextPlayer = checkNextPlayer();
				centerCardAffected.setUsed();
			}
			if (centerCardAffected instanceof Reverse && !centerCardAffected.getUsed()) {
				clockWiseDirection = !clockWiseDirection;
				centerCardAffected.setUsed();
			}
			if (centerCardAffected instanceof WildCard || centerCardAffected instanceof DrawFour) {
				centerCardAffected.setColorOfCard(bestColorOfComputerCard);
				System.out.println("The color is now: " + bestColorOfComputerCard);
			}
		}
	}

}
