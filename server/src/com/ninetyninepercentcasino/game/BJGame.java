package com.ninetyninepercentcasino.game;

import com.ninetyninepercentcasino.game.bj.BJDealer;
import com.ninetyninepercentcasino.game.bj.BJPlayer;
import com.ninetyninepercentcasino.game.gameparts.Card;
import com.ninetyninepercentcasino.game.gameparts.Deck;
import com.ninetyninepercentcasino.net.BJAction;

import java.util.Stack;

/**
 * Runs logic for a bj game
 * @author Grant Liang
 */
public class BJGame {
    private Deck deck;
    private BJPlayer player;
    private BJDealer dealer;
    private Stack<BJHand> hands;
    private Stack<BJHand> resolved;

    public BJGame(BJPlayer player){
        this.player = player;
        hands = new Stack<>();
        resolved = new Stack<>();
    }
    public void startRound(){
        getInitialBet();
        deck = new Deck();
        deck.shuffle();
        dealer = new BJDealer(deck);

        dealer.setup();
        //if(dealer.hasAce()) //send player a BJActionUpdate object with BJAction.INSURANCE available

        BJHand firstHand = new BJHand(player);
        hands.push(new BJHand(player));

        drawCardUpdate(firstHand.drawCard(deck), player);
        drawCardUpdate(firstHand.drawCard(deck), player);

        while(!hands.isEmpty()){
            BJHand currentHand = hands.peek();
            BJAction clientAction = currentHand.act();
            switch(clientAction){
                case HIT:
                    currentHand.drawCard(deck);
                    break;
                case STAND:
                    resolved.push(hands.pop());
                    break;
                case SPLIT:
                    Card card1 = deck.drawCard();
                    Card card2 = deck.drawCard();
                    BJHand hand1 = new BJHand(player, currentHand.getCard(0), card1);
                    BJHand hand2 = new BJHand(player, currentHand.getCard(1), card2);
                    hands.push(hand1);
                    hands.push(hand2);
                    break;
                case DOUBLE_DOWN:
                    currentHand.drawCard(deck);
                    currentHand.doubleBet();
                    resolved.push(hands.pop());
                    break;
            }
        }
        dealer.play();
        int dealerScore = dealer.getScore();
        while (!resolved.isEmpty()) {
            BJHand currentHand = resolved.pop();
        }

    }
    public BJDealer getDealer(){
        return dealer;
    }
    /**
     * simulates the action of the dealer
     */
    private void dealerAction(){

    }
    private void getInitialBet(){
        //TODO send to client a BJBet request object and get the amount they bet in return
    }

    /**
     * determines the winner of a blackjack game between the dealer and a player hand
     * @param playerHand the hand of the player to be compared with the dealer
     * @param dealer the dealer's hand
     * @return true if the player won the hand, false otherwise
     */
    private boolean playerWonHand(BJHand playerHand, BJDealer dealer){
//        if((playerScore > 21 && dealerScore > 21)) winner = dealer;
//        else if(playerScore == dealerScore) winner = dealer;
//        else if(playerScore > 21) winner = dealer;
//        else if(dealerScore > 21) winner = player;
//        else if(playerScore > dealerScore) winner = player;
//        else winner = dealer;
//        return winner;
        return true;
    }

    /**
     * called when the client needs to be updated about a card that was drawn
     */
    private void drawCardUpdate(Card card, BJPlayer player){
    }
}
