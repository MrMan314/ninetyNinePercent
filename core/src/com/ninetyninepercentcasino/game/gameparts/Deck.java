package com.ninetyninepercentcasino.game.gameparts;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class to simulate a deck of cards
 * @author Grant Liang
 */
public class Deck {
    private ArrayList<Card> deck;

    /**
     * initializes a new, unshuffled deck
     */
    public Deck(){
        deck = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            for (int j = 13; j >= 1; j--) {
                deck.add(new Card(j, i));
            }
        }
    }

    /**
     * shuffles the deck randomly
     */
    public void shuffle(){
        Collections.shuffle(deck);
    }

    /**
     * removes a card from the top of the deck and returns it
     * @return the card that was drawn
     */
    public Card drawCard(){
        return deck.remove(0);
    }

    public void deal(Hand hand){
        hand.addCard(deck.remove(0));
    }
}
