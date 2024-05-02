package com.ninetyninepercentcasino.game.poker;

import com.ninetyninepercentcasino.game.Player;
import com.ninetyninepercentcasino.game.gameparts.Deck;
import com.ninetyninepercentcasino.game.gameparts.Hand;

/**
 * Models a poker player
 * @author Grant Liang
 */
public class PokerPlayer extends Player {
    Hand hand;
    boolean folded;
    public PokerPlayer(){
        super();
        hand = new Hand(true, true);
        folded = false;
        balance = 0;
    }
    public Hand getHand(){
        return hand;
    }
    public void drawCard(Deck deck){
        hand.drawCard(deck);
    }
    /**
     * this method should return the action the player wants to perform in a poker game
     * it is called whenever it is the player's turn
     * available actions are to raise, call, or fold. maybe more, not sure yet
     * @return the player's action
     */
    public int getAction(){
        //TODO networking? try to return something other than an int.
        return 0;
    }

}
