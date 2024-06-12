package com.ninetyninepercentcasino.game.poker
public class PokerPlayer:
	Hand hand
	private int balance
	boolean folded
	private int balanceInPot
	public enum Actions:
		FOLD,
		CHECK,
		CALL,
		RAISE,
		BET
	public PokerPlayer():
		super()
		hand = new Hand()
		folded = false
		balance = 0
		balanceInPot = 0
	public Hand getHand():
		return hand
	public void drawCard(Deck deck):
		deck.deal(hand)
	public void fold():
		folded = true
	public int getBalanceInPot():
		return balanceInPot
	public void clearBalanceInPot():
		balanceInPot = 0
	public int bet(int betAmount):
		balance -= betAmount
		balanceInPot += betAmount
		return betAmount
	public void addToBalance(int addAmount):
		balance += addAmount
	public Actions getAction():
		return Actions.RAISE
