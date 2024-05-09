package com.ninetyninepercentcasino.game.bj;

import com.ninetyninepercentcasino.game.gameparts.Card;
import com.ninetyninepercentcasino.game.gameparts.Deck;
import com.ninetyninepercentcasino.game.gameparts.Hand;

public class BJDealer {
    private Hand hand;
    private Deck deck;

    public BJDealer(Hand hand, Deck deck){
        this.hand = hand;
        this.deck = deck;
    }
    public void play(){
        while(calculateScore() < 17){
            hand.drawCard(deck);
        }
    }
    /**
     * calculates the highest possible bj score of a hand that doesn't bust
     * @return score of the hand
     */
    private int calculateScore(){
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
}
