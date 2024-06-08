package com.ninetyninepercentcasino.game.gameparts;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Models a player hand that manages Cards
 * @author Grant Liang
 */
public class Hand implements Serializable {
	private ArrayList<Card> hand;
	/**
	 * Constructor that initializes a new empty player hand
	 * pre: none
	 * post: initializes a new empty player hand
	 */
	public Hand(){
		hand = new ArrayList<>();
	}
	/**
	 * Method that adds a Card to the hand and returns the card added
	 * pre: none
	 * post: adds the Card to the hand
	 */
	public Card addCard(Card card){
		hand.add(card);
		return card;
	}
	/**
	 * removes a specified Card from the hand
	 * pre: none
	 * post: removes the Card from the hand
	 */
	public void removeCard(Card card){
		hand.remove(card);
	}

	/**
	 * removes the card at a specified index of the hand
	 * @param index the index of the card to be removed
	 */
	public void removeCard(int index){
		hand.remove(index);
	}

	/**
	 * draws a card from a given deck
	 * @param deck the deck that the card is to be drawn from
	 * @return the card drawn
	 */
	public Card drawCard(Deck deck){
		Card card = deck.drawCard();
		hand.add(card);
		return card;
	}
	/**
	 * @return all cards in the hand
	 */
	public ArrayList<Card> getCards(){
		return hand;
	}
	/**
	 * retrieves and returns the card at the given index of the hand
	 * @param index 0-indexed location of the card. 0 will always be the first card in
	 * @return the card at that index in the hand
	 */
	public Card getCard(int index){
		return hand.get(index);
	}

}
