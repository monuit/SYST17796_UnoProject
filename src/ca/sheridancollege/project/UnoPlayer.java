/**
 * @author Mohammad Abdulhussain, February 28, 2021
 */

package ca.sheridancollege.project;

import java.util.ArrayList;

public interface UnoPlayer {

    public enum Color { RED, YELLOW, GREEN, BLUE, NONE }
    public enum Rank { NUMBER, SKIP, REVERSE, DRAW_TWO, WILD, WILD_D4 }

    public int play(ArrayList<Card> hand, Card upCard, Color calledColor,
                    GameState state);

    public Color callColor(ArrayList<Card> hand);

