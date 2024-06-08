package com.ninetyninepercentcasino.game.gameparts;

import java.io.Serializable;

/**
 * Models a card with a suit and card number
 * @author Grant Liang
 */
public class Card implements Serializable {
	private final int suit; //0 spades, 1 diamonds, 2 clubs, 3 hearts
	private final int cardNum;
	private final static String[] suitNames = {"SPADES", "DIAMONDS", "CLUBS", "HEARTS"};
	private final static String[] numberNames = {"ZERO_ERROR", "ACE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN", "JACK", "QUEEN", "KING"};
	/**
	 * Constructor that initializes a new card with a given suit and number
	 * pre: suit is number from 0-3
	 * post: initializes a new card object assigned to the given numberValue and suit
	 */
	public Card(int cardNum, int suit){
		this.suit = suit;
		this.cardNum = cardNum;

	}
	/**
	 * Accessor method that returns the number of a card (ace 1, king 13)
	 * pre: none
	 * post: returns the number of the card
	 */
	public int getNum(){
		return cardNum;
	}
	/**
	 * Accessor method that returns the suit of a card
	 * pre: none
	 * post: returns the suit of the card as a string
	 */
	public int getSuit(){
		return suit;
	}

	/**
	 * @return the string of the suit name
	 */
	public String getSuitName(){
		return suitNames[suit];
	}
}