package com.ninetyninepercentcasino.game.bj;

import com.ninetyninepercentcasino.game.gameparts.Card;
import com.ninetyninepercentcasino.game.gameparts.Deck;
import com.ninetyninepercentcasino.net.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

/**
 * Runs logic for a blackjack game
 * @author Grant Liang
 */
public class BJGame extends Thread {
    private static final int PLAYER_WON = 0;
    private static final int DEALER_WON = 1;
    private static final int TIE = 3;
    private static final int PLAYER_BLACKJACK = 4;
    private static final long PAUSE_TIME = 1000;

    private final BJPlayer player;
    private BJDealer dealer;
    private Stack<BJHand> hands;
    private Stack<BJHand> resolved;

    private final BJSynchronizer bjSynchronizer;
    private double firstBet;
    private BJAction action;

    /**
     * initializes a new blackjack game
     * @param player the player of the game, who will play against the dealer
     */
    public BJGame(BJPlayer player) {
        this.player = player;
        hands = new Stack<>();
        resolved = new Stack<>();
        bjSynchronizer = new BJSynchronizer();
    }
    /**
     * main driver for a blackjack game
     */
    public void run() {
        try {
            getInitialBet();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Deck deck = new Deck();
        deck.shuffle();

        dealer = new BJDealer(deck);
        drawCardUpdate(dealer.drawCard(), false);
        drawCardUpdate(dealer.drawCard(), false);

        BJHand firstHand = new BJHand(player);
        firstHand.setBet(firstBet);
        hands.push(firstHand);

        if(dealer.hasVisibleAce()) dealer.setInsuranceBet(firstHand.getInsurance());

        drawCardUpdate(firstHand.drawCard(deck), true);
        drawCardUpdate(firstHand.drawCard(deck), true);

        while(!hands.isEmpty()){
            BJHand currentHand = hands.peek();
            sendOptions(currentHand.updateOptions());
            switch(action){
                case HIT:
                    drawCardUpdate(currentHand.drawCard(deck), true);
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
                    drawCardUpdate(currentHand.drawCard(deck), true);
                    currentHand.doubleBet();
                    resolved.push(hands.pop());
                    break;
            }
        }
        dealerAction();
        while (!resolved.isEmpty()) {
            BJHand currentHand = resolved.pop();
            int outcome = findWinner(currentHand, dealer);
            double winnings = 0; //net earnings for the player
            switch(outcome){
                case PLAYER_BLACKJACK:
                    player.addBalance(currentHand.getAmountBet()*2.5);
                    winnings = currentHand.getAmountBet()*1.5;
                    break;
                case PLAYER_WON:
                    player.addBalance(currentHand.getAmountBet()*2);
                    winnings = currentHand.getAmountBet();
                    break;
                case TIE:
                    player.addBalance(currentHand.getAmountBet());
                    winnings = 0;
                    break;
                case DEALER_WON:
                    if(dealer.getNumCards() == 2) player.addBalance(dealer.getInsuranceBet()*3);
                    winnings = dealer.getInsuranceBet()*2;
                    break;
            }
            sendHandEnd(outcome, winnings);
        }

    }
    public BJDealer getDealer(){
        return dealer;
    }
    /**
     * simulates the action of the dealer
     */
    private void dealerAction(){
        while(dealer.getScore() < 17){
            drawCardUpdate(dealer.drawCard(), false);
        }
    }

    private void getInitialBet() throws InterruptedException {
        NetMessage getBet = new NetMessage(NetMessage.MessageType.INFO, new BJBetRequest());
        try {
            player.getConnection().message(getBet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        synchronized(bjSynchronizer) {
            bjSynchronizer.wait(); //waits until the client returns the amount bet
        }
    }

    /**
     * determines the winner of a blackjack game between the dealer and a player hand
     * @param playerHand the hand of the player to be compared with the dealer
     * @param dealer the dealer's hand
     * @return 0 if the player won, 1 if the dealer won, 2 for a tie
     */
    private int findWinner(BJHand playerHand, BJDealer dealer){
        int playerScore = playerHand.getScore();
        int dealerScore = dealer.getScore();
        if(playerScore == 21 && dealerScore == 21){
            if(playerHand.getCards().size() == 2 && dealer.getNumCards() == 2) return TIE;
            else if(playerHand.getCards().size() == 2) return PLAYER_BLACKJACK;
            else return DEALER_WON;
        }
        else if((playerScore > 21 && dealerScore > 21)) return DEALER_WON;
        else if(playerScore == dealerScore) return TIE;
        else if(playerScore > 21) return DEALER_WON;
        else if(dealerScore > 21) return PLAYER_WON;
        else if(playerScore > dealerScore) return PLAYER_WON;
        else return DEALER_WON;
    }

    /**
     * called when the client needs to be updated about a card that was drawn
     */
    private void drawCardUpdate(Card card, boolean visible){
        NetMessage cardUpdate = new NetMessage(NetMessage.MessageType.INFO, new BJCardUpdate(card, visible));
        try {
            player.getConnection().message(cardUpdate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pause();
    }
    private void sendOptions(HashMap<BJAction, Boolean> availableActions){
        NetMessage actionUpdate = new NetMessage(NetMessage.MessageType.INFO, new BJAvailActionUpdate(availableActions));
        try {
            player.getConnection().message(actionUpdate);
            synchronized(bjSynchronizer) {
                bjSynchronizer.wait(); //waits until the client returns the action
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void sendHandEnd(int winner, double winnings){
        NetMessage handEndUpdate = new NetMessage(NetMessage.MessageType.INFO, new BJHandEnd(winner, winnings));
        try {
            player.getConnection().message(handEndUpdate);
            synchronized(bjSynchronizer) {
                bjSynchronizer.wait(); //waits until the client returns the action
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    public BJSynchronizer getBjSynchronizer() {
        return bjSynchronizer;
    }
    /**
     * called by the ServerConnection managing the game when it receives the client's first bet
     * @param firstBet the amount the client has chosen to bet
     */
    public void setFirstBet(double firstBet){
        this.firstBet = firstBet;
    }
    /**
     * called by the ServerConnection managing the game when it receives the client's action
     * @param action the action the client has chosen to perform
     */
    public void setAction(BJAction action){
        this.action = action;
    }
    /**
     * sleeps the thread to prevent multiple DTOs being sent to client at the same time
     */
    private void pause(){
        try { Thread.sleep(PAUSE_TIME);
        } catch (InterruptedException e) { throw new RuntimeException(e);}
    }
}
