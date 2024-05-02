package com.ninetyninepercentcasino.game.poker;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.ninetyninepercentcasino.game.gameparts.Card;
import com.ninetyninepercentcasino.game.gameparts.Deck;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Models a poker game
 * @author Grant Liang
 */
public class PokerGame {
    private Stage stage;
    private Deck deck;
    int numPlayers;
    int round;
    int dealerIndex;
    ArrayList<PokerPlayer> players;
    ArrayList<Card> communityCards = new ArrayList<>();
    PokerPlayer dealer;
    PokerPlayer smallBlind;
    PokerPlayer bigBlind;
    double pot;

    public PokerGame(ArrayList<PokerPlayer> players){
        round = 0;
        this.players = players;
        numPlayers = players.size();
        dealerIndex = MathUtils.random(numPlayers);
    }
    public void setupRound(){
        round++;
        deck = new Deck();
        deck.shuffle();
        pot = 0;

        Stack<PokerPlayer> tempPlayerStack = new Stack<>();
        for(int i = dealerIndex; i < numPlayers; i++){
            tempPlayerStack.push(players.get(i));
        }
        tempPlayerStack.addAll(players);
        if(numPlayers >= 3){
            dealer = tempPlayerStack.pop();
            smallBlind = tempPlayerStack.pop();
            bigBlind = tempPlayerStack.pop();
        }
        preFlop();
    }
    public void preFlop(){
        for(int i = 0; i < numPlayers; i++){
            players.get(i).drawCard(deck);
            players.get(i).drawCard(deck);
        }
        betPhase();
        flop();
    }
    public void flop(){
        communityCards.add(deck.drawCard());
        communityCards.add(deck.drawCard());
        communityCards.add(deck.drawCard());
        betPhase();
        turn();
    }
    public void turn(){
        communityCards.add(deck.drawCard());
        betPhase();
        river();
    }
    public void river(){
        communityCards.add(deck.drawCard());
        betPhase();
        endRound();
    }
    public void betPhase(){
        int numChecks = 0;
        while(numChecks < numPlayers){

        }
    }
    public void endRound(){
        dealerIndex++;
        if(dealerIndex >= numPlayers) dealerIndex = 0;
    }
    public Stage getStage(){
        return stage;
    }
}
