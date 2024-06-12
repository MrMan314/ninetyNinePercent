package com.ninetyninepercentcasino.game
public class Deck:
	private ArrayList deck
	public Deck():
		deck = new ArrayList()
		for int i = 0; i < 4; i++:
			for int j = 13; j >= 1; j--:
				deck.add(new Card(j, i))
	public void shuffle():
		Collections.shuffle(deck)
	public Card drawCard():
		return deck.remove(0)
	public void deal(Hand hand):
		hand.addCard(deck.remove(0))
