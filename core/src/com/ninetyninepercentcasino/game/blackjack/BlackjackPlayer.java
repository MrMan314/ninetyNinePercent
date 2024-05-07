package com.ninetyninepercentcasino.game.blackjack;

import com.ninetyninepercentcasino.game.Player;
import com.ninetyninepercentcasino.game.gameparts.Card;
import com.ninetyninepercentcasino.game.gameparts.Deck;
import com.ninetyninepercentcasino.game.gameparts.Hand;

/**
 * Models a blackjack player that manages a CardGroup
 */
public class BlackjackPlayer extends Player {
    private Hand hand;
    private double balanceInPot;
    public BlackjackPlayer(boolean isLocalPlayer){
        super();
        hand = new Hand();
        balance = 0;
        balanceInPot = 0;
    }
    public Hand getHand(){
        return hand;
    }
    public void drawCard(Deck deck){
        hand.addCard(deck.drawCard());
    }

    /**
     * calculates the highest possible blackjack score of a hand that doesn't bust
     * @return score of the hand
     */
    public int calculateScore(){
        int score = 0;
        int numAces = 0;
        for(Card card : hand.getCards()){
            int cardValue = card.getNum();
            if(cardValue == 1) numAces++;
            else if(cardValue > 10) cardValue = 10;
            score += cardValue;
        }
        while(numAces > 0 && score + 10 <= 21){
            numAces--;
            score += 10;
        }
        return score;
    }
    public void addBalance(double amountAdded){
        balance += amountAdded;
    }
}
