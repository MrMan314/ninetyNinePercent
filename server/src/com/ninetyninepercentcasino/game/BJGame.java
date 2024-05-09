package com.ninetyninepercentcasino.game;

import com.ninetyninepercentcasino.game.bj.BJDealer;
import com.ninetyninepercentcasino.game.bj.BJPlayer;
import com.ninetyninepercentcasino.game.gameparts.Card;
import com.ninetyninepercentcasino.game.gameparts.Deck;

import java.util.Stack;

/**
 * Runs logic for a bj game
 * @author Grant Liang
 */
public class BJGame {
    private Deck deck;
    private double pot;
    private BJPlayer player;
    private BJDealer dealer;
    private Stack<BJHand> hands;

    public BJGame(BJPlayer player){
        this.player = player;
        dealer = new BJDealer();
        hands = new Stack<>();
    }
    public void setupRound(){
        deck = new Deck();
        deck.shuffle();

        BJHand hand1 = new BJHand(player);

        hands.add(hand1);

        drawCardUpdate(hand1.drawCard(deck), player);
        drawCardUpdate(playerHand.drawCard(deck), dealer);
        drawCardUpdate(hand1.drawCard(deck), player);
        drawCardUpdate(playerHand.drawCard(deck), dealer);


    }
    public BJDealer getDealer(){
        return dealer;
    }
    /**
     * precondition: hitting is a valid action at this moment in the game
     */
    public void hit(){
        player.drawCard(deck);
        dealerAction();
    }
    public void stand(){
        if(dealer.calculateScore() < 17) dealerAction();
        endGame();
    }
    /**
     * simulates the action of the dealer
     */
    private void dealerAction(){

    }
    private void getInitialBet(){

    }
    private void endGame(){
        BJPlayer winner = findWinner();
        winner.addBalance(pot);
        if(winner == dealer) System.out.println("dealer won.");
        else System.out.println("i won.");
    }
    private BJPlayer findWinner(){
        int dealerScore = dealer.calculateScore();
        int playerScore = player.calculateScore();
        BJPlayer winner;
        if((playerScore > 21 && dealerScore > 21)) winner = dealer;
        else if(playerScore == dealerScore) winner = dealer;
        else if(playerScore > 21) winner = dealer;
        else if(dealerScore > 21) winner = player;
        else if(playerScore > dealerScore) winner = player;
        else winner = dealer;
        return winner;
    }

    /**
     * called when the client needs to be updated about a card that was drawn
     */
    private void drawCardUpdate(Card card, BJPlayer player){
    }
}
