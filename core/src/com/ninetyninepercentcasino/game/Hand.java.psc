package com.ninetyninepercentcasino.game
public class Hand implements Serializable:
	private ArrayList hand
	public Hand():
		hand = new ArrayList()
	public Hand(Card card):
		hand = new ArrayList()
		hand.add(card)
	public Card addCard(Card card):
		hand.add(card)
		return card
	public void removeCard(Card card):
		hand.remove(card)
	public Card removeCard(int index):
		return hand.remove(index)
	public Card drawCard(Deck deck):
		Card card = deck.drawCard()
		hand.add(card)
		return card
	public ArrayList getCards():
		return hand
	public Card getCard(int index):
		return hand.get(index)
