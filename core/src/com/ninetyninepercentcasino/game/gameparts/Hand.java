package com.ninetyninepercentcasino.game.gameparts;

import java.util.ArrayList;

/**
 * Models a player hand that manages Cards
 * @author Grant Liang
 */
public class Hand {
    private ArrayList<Card> hand;
    /**
     * Constructor that initializes a new empty player hand
     * pre: none
     * post: initializes a new empty player hand
     */
    public Hand(){
        hand = new ArrayList<>();
    }
    public Hand(ArrayList<Card> hand){
        this.hand = hand;
    }
    /**
     * Method that adds a Card to the hand
     * pre: none
     * post: adds the Card to the hand
     */
    public void addCard(Card card){
        hand.add(card);
    }
    /**
     * Method that removes a Card from the hand
     * pre: none
     * post: removes the Card from the hand
     */
    public void removeCard(Card card){
        hand.remove(card);
    }
    public void drawCard(Deck deck){
        hand.add(deck.drawCard());
    }
    public ArrayList<Card> getCards(){
        return hand;
    }


}
