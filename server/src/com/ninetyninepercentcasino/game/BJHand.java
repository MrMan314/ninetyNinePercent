package com.ninetyninepercentcasino.game;

import com.ninetyninepercentcasino.game.bj.BJPlayer;
import com.ninetyninepercentcasino.game.gameparts.Card;
import com.ninetyninepercentcasino.game.gameparts.Deck;
import com.ninetyninepercentcasino.game.gameparts.Hand;
import com.ninetyninepercentcasino.net.BJAction;

import java.util.HashMap;

/**
 * models a bj hand that the player bets on
 */
public class BJHand {
    private BJPlayer player;
    private HashMap<BJAction, Boolean> availableActions = new HashMap<>();
    private Hand hand;
    private double amountBet;

    public BJHand(BJPlayer player){
        this.player = player;
        for(BJAction action : BJAction.values()){
            availableActions.put(action, false);
        }
    }
    public BJHand(BJPlayer player, Card card1, Card card2){
        this(player);
        hand.addCard(card1);
        hand.addCard(card2);
    }
    public Hand getHand(){
        return hand;
    }
    public Card drawCard(Deck deck){
        return hand.addCard(deck.drawCard());
    }
    public void play(){
        giveOptions();
    }
    /**
     * calculates the highest possible bj score of a hand that doesn't bust
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
    private void giveOptions(){
        int score = calculateScore();
        if(score >= 21) endHand(); //someone has busted, so end the game
        for(BJAction action : availableActions.keySet()){
            availableActions.replace(action, false);
        }
        availableActions.replace(BJAction.STAND, true);
        availableActions.replace(BJAction.HIT, true);
        if(canSplit()) availableActions.replace(BJAction.SPLIT, true);
        if(canDoubleDown()) availableActions.replace(BJAction.DOUBLE_DOWN, true);
    }
    public boolean canSplit(){
        return hand.getCards().get(0).getNum() == hand.getCards().get(1).getNum();
    }
    public boolean canDoubleDown(){
        Card card1 = hand.getCards().get(0);
        Card card2 = hand.getCards().get(1);
        int score = calculateScore();
        return 9 <= score && score <= 11 && card1.getNum() != 1 && card2.getNum() != 1;
    }
    private void endHand(){

    }

}
