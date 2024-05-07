package com.ninetyninepercentcasino.game.blackjack;

import com.badlogic.gdx.utils.Timer;
import com.ninetyninepercentcasino.game.blackjack.blackjackbuttons.HitButton;
import com.ninetyninepercentcasino.game.gameparts.CasinoButton;
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
        float delay = 0.7f;
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                dealer.drawCard(deck);
                dealer.getCardGroup().reveal();
                Timer.schedule(new Timer.Task(){
                    @Override
                    public void run() {
                        player.drawCard(deck);
                        Timer.schedule(new Timer.Task(){
                            @Override
                            public void run() {
                                dealer.drawCard(deck);
                            }
                        }, delay);
                    }
                }, delay);
            }
        }, delay);
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
        if(playerScore < 21) HitButton.isAvailable = true;
        else HitButton.isAvailable = false;
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
        CasinoButton.isAvailable = false;
        if(dealer.calculateScore() < 17){
            final float delay = 1;
            Timer.schedule(new Timer.Task(){
                public void run(){
                    dealer.drawCard(deck);
                    CasinoButton.isAvailable = true;
                    giveOptions(player.calculateScore(), dealer.calculateScore());
                    dealerAction();
                }
            }, delay);
        }
        return dealer.calculateScore() < 17;
    }
    private void endGame(){
        CasinoButton.isAvailable = false;
        BlackjackPlayer winner = findWinner();
        dealer.getCardGroup().reveal();
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
}
