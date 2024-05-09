package com.ninetyninepercentcasino.game.bj;

import com.ninetyninepercentcasino.game.Player;

/**
 * Models a bj player that manages a CardGroup
 */
public class BJPlayer extends Player {

    public BJPlayer(){
        super();
        balance = 0;
    }

    public void addBalance(double amountAdded){
        balance += amountAdded;
    }

}
