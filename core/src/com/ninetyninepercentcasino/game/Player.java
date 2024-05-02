package com.ninetyninepercentcasino.game;

import com.ninetyninepercentcasino.game.gameparts.Deck;
import com.ninetyninepercentcasino.game.gameparts.Hand;

/**
 * Models a player of a game
 * @author Grant Liang
 */
public class Player {
    protected double balance;
    Hand hand = new Hand(true, true);
    public Player(){

    }
    public Hand getHand(){
        return hand;
    }
    public void drawCard(Deck deck){
        hand.drawCard(deck);
    }
}
