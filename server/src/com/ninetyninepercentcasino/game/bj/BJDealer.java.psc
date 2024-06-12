package com.ninetyninepercentcasino.game.bj
public class BJDealer:
	private Hand hand
	private Deck deck
	public BJDealer(Deck deck):
		this.deck = deck
		hand = new Hand()
	public Card drawCard():
		return hand.drawCard(deck)
	public int getScore():
		int score = 0
		int numAces = 0
		for Card card in hand.getCards():
			int cardValue = card.getNum()
			if (cardValue == 1) numAces++
			else if (cardValue > 10) cardValue = 10
			score += cardValue
		while numAces > 0 && score + 10 <= 21:
			numAces--
			score += 10
		return score
	public boolean hasVisibleAce():
		return hand.getCard(0).getNum() == 1
	public int getNumCards():
		return hand.getCards().size()
	public void addCard(Card card):
		hand.addCard(card)
