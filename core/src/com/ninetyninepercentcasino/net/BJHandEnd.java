package com.ninetyninepercentcasino.net;

/**
 * DTO that updates client about the status of a completed blackjack hand
 * @author Grant Liang
 */
public class BJHandEnd extends DTO {
	//constants for different hand outcomes
	public static final int PLAYER_WON = 0;
	public static final int DEALER_WON = 1;
	public static final int TIE = 3;

	private int winnings; //the amount the user has won from this hand, excluding the original bet.
	private int outcome; //the outcome of the game

	/**
	 * constructor for the DTO
	 * @param outcome the outcome of the hand
	 * @param winnings the amount the client has won
	 */
	public BJHandEnd(int outcome, int winnings) {
		this.outcome = outcome;
		this.winnings = winnings;
	}

	/**
	 * @return the outcome of the game
	 */
	public int getOutcome() {
		return outcome;
	}

	/**
	 * @return the amount won by the player
	 */
	public int getWinnings() {
		return winnings;
	}
}
