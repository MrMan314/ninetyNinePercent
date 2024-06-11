package com.ninetyninepercentcasino.game.bj;

import com.ninetyninepercentcasino.game.Card;
import com.ninetyninepercentcasino.game.Deck;
import com.ninetyninepercentcasino.game.Hand;
import com.ninetyninepercentcasino.net.BJAction;

import java.util.HashMap;

/**
 * Models a blackjack hand that the player bets on
 * Calculates its own score and available actions for the player
 * @author Grant Liang
 */
public class BJHand extends Hand {
	private final BJPlayer player; //owner of the hand
	private HashMap<BJAction, Boolean> availableActions; //stores the actions and whether they can be performed on this hand
	private int amountBet; //the amount that has been bet on this hand by a player

	/**
	 * initialize a new empty blackjack hand
	 * @param player the BJPlayer this is connected to
	 */
	public BJHand(BJPlayer player) {
		this.player = player;
		availableActions = new HashMap<>();
		for(BJAction action : BJAction.values()) {
			availableActions.put(action, false);
		}
	}

	/**
	 * initialize a new blackjack hand with a card in it
	 * @param player the BJPlayer that this is connected to
	 * @param card1 the card that will start off in this hand
	 */
	public BJHand(BJPlayer player, Card card1) {
		this(player);
		addCard(card1);
	}

	/**
	 * draws a card
	 * @param deck the deck that the card is to be drawn from
	 * @return the card drawn
	 */
	public Card drawCard(Deck deck) {
		return addCard(deck.drawCard());
	}

	/**
	 * calculates the highest possible blackjack score of a hand that doesn't bust
	 * @return score of the hand
	 */
	public int getScore() {
		int score = 0;
		int numAces = 0;
		for(Card card : getCards()) {
			int cardValue = card.getNum();
			if(cardValue == 1) numAces++;
			else if(cardValue > 10) cardValue = 10;
			score += cardValue;
		}
		while(numAces > 0 && score + 10 <= 21) {
			numAces--;
			score += 10;
		}
		return score;
	}

	/**
	 * updates the available actions on this hand
	 * @return the available actions
	 */
	public HashMap<BJAction, Boolean> getOptions() {
		int score = getScore();
		for(BJAction action : availableActions.keySet()) {
			availableActions.replace(action, false);
		}
		if(score < 21) { //if the score is 21 or above the player has no more choice over their actions, so they are all false
			availableActions.replace(BJAction.STAND, true);
			availableActions.replace(BJAction.HIT, true);
			if(canSplit()) availableActions.replace(BJAction.SPLIT, true);
			if(canDoubleDown()) availableActions.replace(BJAction.DOUBLE_DOWN, true);
		}
		return availableActions;
	}

	/**
	 * @return whether this hand can be split
	 */
	private boolean canSplit() {
		return getCards().size() == 2 && getCards().get(0).getNum() == getCards().get(1).getNum();
	}

	/**
	 * @return whether doubling down is a legal action on this hand
	 */
	private boolean canDoubleDown() {
		if(getCards().size() != 2) return false;
		Card card1 = getCards().get(0);
		Card card2 = getCards().get(1);
		int score = getScore();
		return 9 <= score && score <= 11 && card1.getNum() != 1 && card2.getNum() != 1;
	}

	/**
	 * sets the amount bet on this hand
	 * @param amountBet the amount bet on this hand
	 */
	public void setBet(int amountBet) {
		player.withdraw(amountBet-this.amountBet);
		this.amountBet = amountBet;
	}

	public void doubleBet() {
		player.withdraw(amountBet);
		amountBet *= 2;
	}

	/**
	 * @return the amount bet on this hand
	 */
	public int getAmountBet() {
		return amountBet;
	}

}
