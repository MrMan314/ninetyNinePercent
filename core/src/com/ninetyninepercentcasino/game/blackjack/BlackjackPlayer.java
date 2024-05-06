package com.ninetyninepercentcasino.game.blackjack;

import com.ninetyninepercentcasino.game.Player;
import com.ninetyninepercentcasino.game.gameparts.Deck;
import com.ninetyninepercentcasino.game.gameparts.CardGroup;

/**
 * Models a blackjack player that manages a CardGroup
 */
public class BlackjackPlayer extends Player {
    private CardGroup hand;
    private double balanceInPot;
    public BlackjackPlayer(boolean isLocalPlayer){
        super();
        hand = new CardGroup(isLocalPlayer, isLocalPlayer);
        balance = 0;
        balanceInPot = 0;
    }
    public CardGroup getHandGroup(){
        return hand;
    }
    public void drawCard(Deck deck){
        hand.addCard(deck.drawCard());
    }
}
