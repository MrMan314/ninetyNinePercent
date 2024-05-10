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
public class BJHand extends Hand {
    private BJPlayer player;
    private HashMap<BJAction, Boolean> availableActions;
    private double amountBet;

    public BJHand(BJPlayer player){
        this.player = player;
        availableActions = new HashMap<>();
        for(BJAction action : BJAction.values()){
            availableActions.put(action, false);
        }
    }
    public BJHand(BJPlayer player, Card card1, Card card2){
        this(player);
        addCard(card1);
        addCard(card2);
    }
    public Card drawCard(Deck deck){
        return addCard(deck.drawCard());
    }
    public BJAction act(){
        giveOptions();
        BJAction playerAction = BJAction.SPLIT; //take in client input, temporarily permanently set to split
        return playerAction;
    }
    /**
     * calculates the highest possible bj score of a hand that doesn't bust
     * @return score of the hand
     */
    public int getScore(){
        int score = 0;
        int numAces = 0;
        for(Card card : getCards()){
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
        int score = getScore();
        for(BJAction action : availableActions.keySet()){
            availableActions.replace(action, false);
        }
        if(score < 21) {
            availableActions.replace(BJAction.STAND, true);
            availableActions.replace(BJAction.HIT, true);
            if(canSplit()) availableActions.replace(BJAction.SPLIT, true);
            if(canDoubleDown()) availableActions.replace(BJAction.DOUBLE_DOWN, true);
            //TODO send the player their available actions
        }
        //else resolve the hand bc player has busted.

    }
    private boolean canSplit(){
        return getCards().get(0).getNum() == getCards().get(1).getNum();
    }
    private boolean canDoubleDown(){
        Card card1 = getCards().get(0);
        Card card2 = getCards().get(1);
        int score = getScore();
        return 9 <= score && score <= 11 && card1.getNum() != 1 && card2.getNum() != 1;
    }
    public void setBet(double amountBet){
        player.withdraw(amountBet-this.amountBet);
        this.amountBet = amountBet;
    }
    public void doubleBet(){
        player.withdraw(amountBet);
        amountBet *= 2;
    }

}