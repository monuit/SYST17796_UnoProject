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

}
