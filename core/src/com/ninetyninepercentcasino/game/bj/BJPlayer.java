package com.ninetyninepercentcasino.game.bj;

import com.ninetyninepercentcasino.game.gameparts.Player;

/**
 * Models a blackjack player that manages a CardGroup
 */
public class BJPlayer extends Player {

    public BJPlayer(){
        super();
        balance = 0;
    }

    public void addBalance(double amountAdded){
        balance += amountAdded;
    }
    public void withdraw(double withdrawAmount){
        if(withdrawAmount < 0) System.out.println("NEGATIVE WITHDRAWAL");
        else balance -= withdrawAmount;
    }
}
