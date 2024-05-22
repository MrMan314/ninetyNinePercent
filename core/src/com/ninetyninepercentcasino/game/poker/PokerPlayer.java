package com.ninetyninepercentcasino.game.poker;

import com.ninetyninepercentcasino.game.gameparts.Player;
import com.ninetyninepercentcasino.game.gameparts.Deck;
import com.ninetyninepercentcasino.game.gameparts.Hand;

/**
 * Models a poker player
 * @author Grant Liang
 */
public class PokerPlayer extends Player {
    Hand hand;
    boolean folded;
    private double balanceInPot; //stores how much money the player has bet during a street
    public enum Actions{
        FOLD,
        CHECK,
        CALL,
        RAISE,
        BET;
    }
    public PokerPlayer(){
        super();
        hand = new Hand();
        folded = false;
        balance = 0;
        balanceInPot = 0;
    }
    public Hand getHand(){
        return hand;
    }
    public void drawCard(Deck deck){
        deck.deal(hand);
    }
    public void fold(){
        folded = true;
    }
    public double getBalanceInPot(){
        return balanceInPot;
    }
    /**
     * Called after a round of betting, this simulates the player putting their chips into the pot.
     */
    public void clearBalanceInPot(){
        balanceInPot = 0;
    }
    public double bet(double betAmount){
        balance -= betAmount;
        balanceInPot += betAmount;
        return betAmount;
    }
    public void addToBalance(double addAmount){
        balance += addAmount;
    }
    /**
     * this method should return the action the player wants to perform in a poker game
     * it is called whenever it is the player's turn
     * available actions are to raise, call, or fold. maybe more, not sure yet
     * @return the player's action
     */
    public Actions getAction(){
        //TODO networking? try to return something other than an int.
        return Actions.RAISE;
    }

}
