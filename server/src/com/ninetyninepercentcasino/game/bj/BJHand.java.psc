package com.ninetyninepercentcasino.game.bj
public class BJHand extends Hand:
	private final BJPlayer player
	private HashMap availableActions
	private int amountBet
	public BJHand(BJPlayer player):
		this.player = player
		availableActions = new HashMap()
		for BJAction action in BJAction.values():
			availableActions.put(action, false)
	public BJHand(BJPlayer player, Card card1):
		this(player)
		addCard(card1)
	public Card drawCard(Deck deck):
		return addCard(deck.drawCard())
	public int getScore():
		int score = 0
		int numAces = 0
		for Card card in getCards():
			int cardValue = card.getNum()
			if (cardValue == 1) numAces++
			else if (cardValue > 10) cardValue = 10
			score += cardValue
		while numAces > 0 && score + 10 <= 21:
			numAces--
			score += 10
		return score
	public HashMap getOptions():
		int score = getScore()
		for BJAction action in availableActions.keySet():
			availableActions.replace(action, false)
		if score < 21:
			availableActions.replace(BJAction.STAND, true)
			availableActions.replace(BJAction.HIT, true)
			if (canSplit()) availableActions.replace(BJAction.SPLIT, true)
			if (canDoubleDown()) availableActions.replace(BJAction.DOUBLE_DOWN, true)
		return availableActions
	private boolean canSplit():
		return getCards().size() == 2 && getCards().get(0).getNum() == getCards().get(1).getNum()
	private boolean canDoubleDown():
		if (getCards().size() != 2) return false
		Card card1 = getCards().get(0)
		Card card2 = getCards().get(1)
		int score = getScore()
		return 9 <= score && score <= 11 && card1.getNum() != 1 && card2.getNum() != 1
	public void setBet(int amountBet):
		player.withdraw(amountBet-this.amountBet)
		this.amountBet = amountBet
	public void doubleBet():
		player.withdraw(amountBet)
		amountBet *= 2
	public int getAmountBet():
		return amountBet
