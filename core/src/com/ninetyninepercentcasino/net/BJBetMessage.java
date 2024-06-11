package com.ninetyninepercentcasino.net;

/**
 * DTO that functions as a bet request message and the answer to the bet request
 * @author Grant Liang
 */
public class BJBetMessage extends DTO{
	private int amountBet; //the amount bet

	/**
	 * initializes a new empty bet request
	 */
	public BJBetMessage() {
	}

	/**
	 * initializes a bet
	 * @param amountBet the amount bet
	 */
	public BJBetMessage(int amountBet) {
		this.amountBet = amountBet;
	}

	/**
	 * @return the amount bet
	 */
	public int getAmountBet() {
		return amountBet;
	}
}
