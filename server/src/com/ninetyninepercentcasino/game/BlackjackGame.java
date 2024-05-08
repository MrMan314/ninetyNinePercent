package com.ninetyninepercentcasino.game;

import com.ninetyninepercentcasino.game.gameparts.Card;
import com.ninetyninepercentcasino.game.gameparts.Deck;

/**
 * Runs logic for a blackjack game
 * @author Grant Liang
 */
public class BlackjackGame {
    private Deck deck;
    private double pot;
    BlackjackPlayer player;
    BlackjackPlayer dealer;

    public BlackjackGame(BlackjackPlayer player){
        this.player = player;
        dealer = new BlackjackPlayer(false);
    }
    public void playRound(){
        deck = new Deck();
        deck.shuffle();
        player.drawCard(deck);
        signalPlayerDraw(player.drawCard(deck));
        pause();
        dealer.drawCard(deck);

        int playerScore = player.calculateScore();
        int dealerScore = dealer.calculateScore();
        if(playerScore < 21 && dealerScore < 21) giveOptions(playerScore, dealerScore);
        else if(playerScore == 21 && dealerScore == 21){
            //TODO both get blackjack, player gets blackjack
        }
    }
    public BlackjackPlayer getDealer(){
        return dealer;
    }
    private void giveOptions(int playerScore, int dealerScore){
        if(playerScore > 21 || dealerScore > 21) endGame(); //someone has busted, so end the game
        //if(playerScore < 21) //Make hit button availble to player
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
        if(dealer.calculateScore() < 17){
            pause();
            dealer.drawCard(deck);
            //make casino buttons available
            giveOptions(player.calculateScore(), dealer.calculateScore());
            dealerAction();
        }
    }
    private void endGame(){
        BlackjackPlayer winner = findWinner();
        winner.addBalance(pot);
        if(winner == dealer) System.out.println("dealer won.");
        else System.out.println("i won.");
    }
    private BlackjackPlayer findWinner(){
        int dealerScore = dealer.calculateScore();
        int playerScore = player.calculateScore();
        BlackjackPlayer winner;
        if((playerScore > 21 && dealerScore > 21)) winner = dealer;
        else if(playerScore == dealerScore) winner = dealer;
        else if(playerScore > 21) winner = dealer;
        else if(dealerScore > 21) winner = player;
        else if(playerScore > dealerScore) winner = player;
        else winner = dealer;
        return winner;
    }
    private void pause(){
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * called when the player draws a card in the game
     * method should send a message to the client with the card drawn
     */
    private void signalPlayerDraw(Card card){

    }
}
