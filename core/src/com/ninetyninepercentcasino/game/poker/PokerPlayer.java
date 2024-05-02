package com.ninetyninepercentcasino.game.poker;

import com.ninetyninepercentcasino.game.Player;

/**
 * Models a poker player
 * @author Grant Liang
 */
public class PokerPlayer extends Player {
    boolean folded;
    public PokerPlayer(){
        super();
        folded = false;
        balance = 0;
    }

    /**
     * this method should return the action the player wants to perform in a poker game
     * available actions are to raise, call, or fold. maybe more, not sure yet
     * @return the player's action
     */
    public int getAction(){
        //TODO networking? try to return something other than an int.
        return 0;
    }

}
