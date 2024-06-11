package com.ninetyninepercentcasino.net;

import com.ninetyninepercentcasino.game.Hand;

/**
 * DTO to signal a split in a blackjack game
 * @author Grant Liang
 */
public class BJSplit extends DTO {
	private final Hand hand1;
	private final Hand hand2;

	/**
	 * initializes a BJSplit with two given hands
	 * @param hand1 the hand that will be played out first
	 * @param hand2 the hand that will be played out second
	 */
	public BJSplit(Hand hand1, Hand hand2) {
		this.hand1 = hand1;
		this.hand2 = hand2;
	}

	/**
	 * @return the hand that will be played out first
	 */
	public Hand getHand1() {
		return hand1;
	}
	/**
	 * @return the hand that will be played out second
	 */
	public Hand getHand2() {
		return hand2;
	}
}
