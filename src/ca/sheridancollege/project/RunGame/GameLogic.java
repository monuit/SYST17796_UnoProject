/**
 * SYST 17796 Project
 *
 * @author Mohammad Abdulhussain
 * @date March 28, 2021, @modified April 11, 2021
 */

package ca.sheridancollege.project.RunGame;

import ca.sheridancollege.project.CardHolds.Hand;
import ca.sheridancollege.project.CardHolds.*;
import ca.sheridancollege.project.Cards.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class GameLogic {

    //input of the User
    BufferedReader userInput;
    // Hand of each player in the game
    private Hand userPlayer;
    private Hand computerOne;
    // The deck to extract from; keep in mind this is not an infinite deck
    private Deck firstDeck;
    private Card centerCardAffected;
    // In order to keep track of any winners, either Computer or Player
    private boolean hasAnyPlayerWon = false;
    // Keeps track of the direction of the game, either clockWise or anti-clockWise, depending on special cards
    private boolean clockWiseDirection = true;
    // The player to play next: 1 is user going clockwise
    private int nextPlayer = 1;
    // Winner of the game
    private String winnerOfGame;

    public GameLogic() {
        firstDeck = new Deck();
        computerOne = new Hand("Computer one");
        BufferedReader userName = new BufferedReader(new InputStreamReader(System.in));
        String userPlayerInput;
        do {
            System.out.println("Please enter a valid username by using only letters");
            try {
                String line = userName.readLine();
                // Accept a line with alphabetic characters delimited with space.
                if (line.matches("[A-Za-z ]+$")) {
                    userPlayerInput = line;
                    break;
                }
            } catch (IOException e) {
                userPlayerInput = "";
                e.printStackTrace();
                break;
            }
        } while (true);
        userPlayer = new Hand(userPlayerInput);

        userInput = new BufferedReader(new InputStreamReader(System.in));

        SetUpTheGame();
        StartGame();
    }


    // This will print if someone wins. It will get the game started with user playing first.

    private void StartGame() {
        try {
            userTurnToPlay();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        for (String winner : Arrays.asList(winnerOfGame + " has won", "END")) {
            System.out.println(winner);
        }
    }

    /**
     * Lets the user go through their turn and checks if they win.
     * At beginning of turn checks if player was affected by any special cards
     *
     * @throws IOException
     */
    private void userTurnToPlay() throws IOException, InterruptedException {
        if (centerCardAffected instanceof DrawFour && !centerCardAffected.getCardUsed()) {
            for (int i = 0; i < 4; i++) {
                userPlayer.addToHand(firstDeck.drawCard());
            }
            centerCardAffected.setCardUsed();
            System.out.println("Next player drew 4 cards");
        }
        if (centerCardAffected instanceof DrawTwo && !centerCardAffected.getCardUsed()) {
            for (int i = 0; i < 2; i++) {
                userPlayer.addToHand(firstDeck.drawCard());
            }
            centerCardAffected.setCardUsed();
            System.out.println("Next player drew 2 cards");
        }
        int choiceOfCardUserPicked = 0; //The index of the card the user picks
        try {
            choiceOfCardUserPicked = askForUserInput();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (choiceOfCardUserPicked == -1) { //User wants to draw a card from the deck which is random
            userPlayer.addToHand(firstDeck.drawCard());
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

                centerCardAffected = userPlayer.getCardIndex(choiceOfCardUserPicked);
                userPlayer.removeFromHand(choiceOfCardUserPicked);
                System.out.println("You played: " + centerCardAffected);

                if (centerCardAffected instanceof WildCard || centerCardAffected instanceof DrawFour) {
                    System.out.println("Pick a color from: Yellow, Green, Blue, Red");
                    String newColorChosen = "Yellow";
                    long startedInputTimer = System.currentTimeMillis();
                    while (((System.currentTimeMillis() - startedInputTimer) < (60 * 1000)) && !userInput.ready()) {
                        Thread.sleep(1200);
                    }
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
                        do { // Makes sure color input is correct
                            System.out.println(newColorChosen + " is not a valid color!\nInput a new color:");
                            startedInputTimer = System.currentTimeMillis();
                            while (((System.currentTimeMillis() - startedInputTimer) < (60 * 1000)) && !userInput.ready()) {
                                Thread.sleep(1200);
                            }
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

                    System.out.println("The color is now: " + newColorChosen); // Declares the color of the game
                    centerCardAffected.setColorOfCard(newColorChosen);
                }

                // Special card impacts on the game
                if ((centerCardAffected instanceof Skip) && !centerCardAffected.getCardUsed()) {
                    nextPlayer++;
                    centerCardAffected.setCardUsed();
                }
                if ((centerCardAffected instanceof Reverse) && !centerCardAffected.getCardUsed()) {
                    clockWiseDirection = !clockWiseDirection;
                    centerCardAffected.setCardUsed();
                }

                hasAnyPlayerWon = checkForWinner(userPlayer);
                if (!hasAnyPlayerWon) // Checks if the user has not won, then lets the computer play
                {
                    nextPlayer = checkNextPlayer();
                    computersPlay();
                } else {
                    try {
                        userInput.close();
                    } catch (IOException e) { // catches all errors not handled previously
                        e.printStackTrace();
                    }
                    winnerOfGame = "The user";
                }

            }
    }

    /**
     * Checks if the card chosen can be played this turn.
     *
     * @param player
     * @param choice
     * @return
     */
    private boolean validUserInput(Hand player, int choice) {
        //Conditions to check for, otherwise returns false
        boolean sameNumber = (player.getCardIndex(choice).getNumberOfCard() == centerCardAffected.getNumberOfCard()) &&
                (centerCardAffected.getNumberOfCard() != 0);
        boolean sameColor = (player.getCardIndex(choice).getColorOfCard().equalsIgnoreCase(centerCardAffected.getColorOfCard())) &&
                !(centerCardAffected.getColorOfCard().equals("none"));
        boolean wildCard = (player.getCardIndex(choice) instanceof WildCard) || (player.getCardIndex(choice) instanceof DrawFour);
        //DrawTwo or Skip or Reverse - Different Color
        boolean sameType = (player.getCardIndex(choice) instanceof DrawTwo && centerCardAffected instanceof DrawTwo) ||
                (player.getCardIndex(choice) instanceof Skip && centerCardAffected instanceof Skip) ||
                (player.getCardIndex(choice) instanceof Reverse && centerCardAffected instanceof Reverse);
        if (sameNumber || sameColor || wildCard || sameType) {
            return true;
        }
        //If this card couldn't be played ask for new input
        System.out.println("That card can't be played here! Pick a new card!");
        return false;
    }

    /**
     * Asks the user to pick a card from their hand, draw a card, or quit the game.
     *
     * @return
     * @throws IOException
     */
    private int askForUserInput() throws IOException, InterruptedException {
        System.out.println("\nIt is your turn\n" + userPlayer);
        System.out.println("***\n" +
                "The center card is: " + centerCardAffected + "\n***");
        if (centerCardAffected instanceof WildCard || centerCardAffected instanceof DrawFour) {
            System.out.println("The color is " + centerCardAffected.getColorOfCard());
        }
        System.out.println("Which card will you play?\n" +
                "Type number next to the card to play that card.\n" +
                "Type \"-1\" to draw a card.\n" +
                "Or type \"-2\" to quit the game");

        int indexOfCardInHand = 0;
        long startedInputTimer = System.currentTimeMillis();
        while (((System.currentTimeMillis() - startedInputTimer) < (60 * 1000)) && !userInput.ready()) {
            Thread.sleep(1200);
        }
        if (userInput.ready()) {
            indexOfCardInHand = Integer.parseInt(userInput.readLine());
        } else {
            System.out.println("Game Ended - Idle");
            userInput.close();
            System.exit(0);
        }
        System.out.println("Input: " + indexOfCardInHand);
        if (indexOfCardInHand <= userPlayer.getHandSize() && indexOfCardInHand > 0) {
            return indexOfCardInHand - 1;
        } else if (indexOfCardInHand == -1) {
            return -1;
        } else if (indexOfCardInHand == -2) {
            return -2;
        }
        //If that number was out of bounds ask for another input from the user until user inputs correct choice
        System.out.println("That was not a valid choice. You don't have a card at " + indexOfCardInHand);
        return askForUserInput();
    }

    // Lets the computers play and checks if it's won

    private void computersPlay() throws IOException, InterruptedException {
        String compPlaying = null; //This is the computer playing at that moment
        // Chooses who gets to play next
        if (nextPlayer == 2) {
            compPlaying = "Computer One";
            ComputerLogic(computerOne);
            hasAnyPlayerWon = checkForWinner(computerOne);
        }
        //If we have no winner check if a computer or player is next, otherwise declare the winner
        if (!hasAnyPlayerWon) {
            nextPlayer = checkNextPlayer();
            switch (nextPlayer) {
                case 1:
                    userTurnToPlay();
                    break;
                default:
                    computersPlay();
                    break;
            }
        } else {
            userInput.close();
            winnerOfGame = compPlaying;
        }

    }

    /**
     * Checks all conditions to see whose turn is next.
     *
     * @return
     */
    private int checkNextPlayer() {
        if (this.nextPlayer == 1 && !clockWiseDirection) {
            return 2;
        } else if (this.nextPlayer == 2 && clockWiseDirection) {
            return 1;
        } else {
            if (clockWiseDirection) {
                return this.nextPlayer + 1;
            } else {
                return this.nextPlayer - 1;
            }
        }

    }

    /**
     * Checks if any player has won or if they have UNO
     *
     * @param player
     * @return
     */
    private boolean checkForWinner(Hand player) {
        if (player.getHandSize() == 1) {
            System.out.println(player.getPlayerName() + " has UNO!");
        }

        return player.getHandSize() == 0;
    }

    /**
     * Decides for the computer what card would best be played here, and then plays the card accordingly
     *
     * @param player
     */
    private void ComputerLogic(Hand player) {
        if (centerCardAffected instanceof DrawFour && !centerCardAffected.getCardUsed()) {
            for (int i = 0; i < 4; i++) {
                player.addToHand(firstDeck.drawCard());
            }
            centerCardAffected.setCardUsed();
            System.out.println("Next player drew 4 cards");
        }
        if (centerCardAffected instanceof DrawTwo && !centerCardAffected.getCardUsed()) {
            for (int i = 0; i < 2; i++) {
                player.addToHand(firstDeck.drawCard());
            }
            centerCardAffected.setCardUsed();
            System.out.println("Next player drew 2 cards");
        }

        System.out.println("\n" + "It is " + player.getPlayerName() + "'s turn!");
        /*
         * Play any special cards
         * Try to play a number card
         * Wild card
         * Draw
         */
        boolean cardPicked = false; // Sees if a card can be played
        int bestIndexOfComputerCard = 0; // The card we will play
        String bestColorOfComputerCard = "Blue";

        // Start off picking the best number card
        for (int i = 0; i < player.getHandSize(); i++) {
            if (player.getCardIndex(i).getColorOfCard().equalsIgnoreCase(centerCardAffected.getColorOfCard())
                    && !player.getCardIndex(i).getColorOfCard().equals("none")) {
                // Pick the card with the biggest number
                if (!cardPicked || player.getCardIndex(i).getNumberOfCard() < centerCardAffected.getNumberOfCard()) {
                    bestIndexOfComputerCard = i;
                    cardPicked = true;
                }
            } else if (player.getCardIndex(i).getNumberOfCard() ==
                    centerCardAffected.getNumberOfCard() && player.getCardIndex(i).getNumberOfCard() != 0) {
                //Pick the card with the biggest number
                if (!cardPicked || player.getCardIndex(i).getNumberOfCard() < centerCardAffected.getNumberOfCard()) {
                    bestIndexOfComputerCard = i;
                    cardPicked = true;
                }
            }
        }
        // Go through and see if there is a better choice which is a special card
        for (int i = 0; i < player.getHandSize(); i++) {
            if (player.getCardIndex(i) instanceof DrawTwo) {
                if (player.getCardIndex(i).getColorOfCard().equalsIgnoreCase(centerCardAffected.getColorOfCard())) {
                    bestIndexOfComputerCard = i;
                    cardPicked = true;
                }
            } else if (player.getCardIndex(i) instanceof Skip || player.getCardIndex(i) instanceof Reverse) {
                if (player.getCardIndex(i).getColorOfCard().equalsIgnoreCase(centerCardAffected.getColorOfCard())) {
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
        if (!cardPicked) {
            // Check for wild card
            for (int i = 0; i < player.getHandSize(); i++) {
                if (player.getCardIndex(i) instanceof WildCard || player.getCardIndex(i) instanceof DrawFour) {
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
            if (!cardPicked) {
                player.addToHand(firstDeck.drawCard());
                System.out.println(player.getPlayerName() + " drew a card!");
            }
        }
        //Actually play the card
        if (cardPicked) {
            centerCardAffected = player.getCardIndex(bestIndexOfComputerCard);
            player.removeFromHand(bestIndexOfComputerCard);
            System.out.println(player.getPlayerName() + " played " + centerCardAffected);
            if (centerCardAffected instanceof Skip && !centerCardAffected.getCardUsed()) {
                nextPlayer = checkNextPlayer();
                centerCardAffected.setCardUsed();
            }
            if (centerCardAffected instanceof Reverse && !centerCardAffected.getCardUsed()) {
                clockWiseDirection = !clockWiseDirection;
                centerCardAffected.setCardUsed();
            }
            if (centerCardAffected instanceof WildCard || centerCardAffected instanceof DrawFour) {
                centerCardAffected.setColorOfCard(bestColorOfComputerCard);
                System.out.println("The color is now: " + bestColorOfComputerCard);
            }
        }
    }

    // Draws all of the player's hands and picks the starting card in the center

    private void SetUpTheGame() {
        drawHand(userPlayer, firstDeck);
        drawHand(computerOne, firstDeck);
        centerCardAffected = firstDeck.drawCard();
        if (centerCardAffected instanceof WildCard || centerCardAffected instanceof DrawFour) {
            randomColor();
            centerCardAffected.setCardUsed();
            System.out.println("The color is " + centerCardAffected.getColorOfCard());
        }
        if (centerCardAffected instanceof DrawTwo) {
            centerCardAffected.setCardUsed();
        }
    }

    /**
     * Picks a color randomly to set center card to
     *
     * @throws IllegalStateException
     */
    private void randomColor() throws IllegalStateException {

         String bestColor;
        switch ((int) (Math.random() * 4) + 1) {
            case 1:
                bestColor = "Blue";
                break;
            case 2:
                bestColor = "Red";
                break;
            case 3:
                bestColor = "Green";
                break;
            case 4:
                bestColor = "Yellow";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + (int) (Math.random() * 4) + 1);
        };

        centerCardAffected.setColorOfCard(bestColor);
    }

    /**
     * Draws a 7 card hand
     *
     * @param player      user who gets the cards
     * @param deckOfCards deck the cards are drawn from
     */
    private void drawHand(Hand player, Deck deckOfCards) {
        for (int i = 0; i < 7; i++) {
            player.addToHand(deckOfCards.drawCard());
        }
    }

}
