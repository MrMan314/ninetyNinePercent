package com.ninetyninepercentcasino.game
public class CardGroup extends Table:
	private Hand hand
	private final boolean faceUp
	private boolean isLocalHand
	public CardGroup(boolean faceUp, boolean isLocalHand):
		this.faceUp = faceUp
		this.isLocalHand = isLocalHand
		if (isLocalHand) setTouchable(Touchable.enabled)
		else setTouchable(Touchable.disabled)
		hand = new Hand()
	public CardGroup(Hand hand, boolean faceUp, boolean isLocalHand):
		this(faceUp, isLocalHand)
		for Card card in hand.getCards():
			add(new CardActor(card, faceUp, isLocalHand))
		this.hand = hand
	public void addCard(CardActor card):
		if (isLocalHand) card.makeActive()
		hand.addCard(card.getCard())
		add(card)
	public void addCard(Card card):
		hand.addCard(card)
		CardActor newCard = new CardActor(card, faceUp, isLocalHand)
		add(newCard)
	public void removeCard(CardActor card):
		hand.removeCard(card.getCard())
		removeActor(card)
	public CardActor removeCard(Card card):
		int index = 0
		for int i = 0; i < hand.getCards().size(); i++:
			Card cardInHand = hand.getCard(i)
			if card.getNum() == cardInHand.getNum() && card.getSuit() == cardInHand.getSuit():
				index = i
				hand.removeCard(i)
		return (CardActor) removeActorAt(index, true)
	public CardActor removeCard(int index):
		return removeCard(hand.removeCard(index))
	public void clearCards():
		hand.getCards().clear()
		clearChildren()
	public void hide():
		for Actor cardActor in getChildren():
			((CardActor)cardActor).hide()
	public void reveal():
		for Actor cardActor in getChildren():
			((CardActor)cardActor).reveal()
	public ArrayList getCards():
		return hand.getCards()
	public Card getCard(int index):
		return hand.getCard(index)
