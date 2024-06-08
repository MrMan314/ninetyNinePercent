package com.ninetyninepercentcasino.game.gameparts;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class to simulate a deck of cards
 * @author Grant Liang
 */
public class Deck {
	private ArrayList<Card> deck; //holds the cards in the deck

	/**
	 * initializes a new, unshuffled deck
	 */
	public Deck(){
		deck = new ArrayList<>();
		//creating all the cards in the deck
		for(int i = 0; i < 4; i++) { //loop through each available suit
			for (int j = 13; j >= 1; j--) { //loop through each available number
				deck.add(new Card(j, i)); //add a card with j number and i suit
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

	/**
	 * deals a Card to the given Hand
	 * @param hand the Hand that will receive the Card
	 */
	public void deal(Hand hand){
		hand.addCard(deck.remove(0));
	}
}
