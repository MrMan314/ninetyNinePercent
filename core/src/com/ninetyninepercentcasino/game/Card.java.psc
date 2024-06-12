package com.ninetyninepercentcasino.game
public class Card implements Serializable:
	private final int suit
	private final int cardNum
	private final static String[] suitNames =:"SPADES", "DIAMONDS", "CLUBS", "HEARTS"
	private final static String[] numberNames =:"ZERO_ERROR", "ACE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN", "JACK", "QUEEN", "KING"
	public Card(int cardNum, int suit):
		this.suit = suit
		this.cardNum = cardNum
	public int getNum():
		return cardNum
	public int getSuit():
		return suit
	public String getSuitName():
		return suitNames[suit]
