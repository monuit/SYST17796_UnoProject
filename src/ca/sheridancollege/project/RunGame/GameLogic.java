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

}
