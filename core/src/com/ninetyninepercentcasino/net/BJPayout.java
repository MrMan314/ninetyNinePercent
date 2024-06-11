package com.ninetyninepercentcasino.net;

/**
 * DTO that notifies the client when they receive money
 * @author Grant Liang
 */
public class BJPayout extends DTO {
	private final int winnings;

	/**
	 * initialize a new payout message
	 * @param winnings the amount won
	 */
	public BJPayout(int winnings){
		this.winnings = winnings;
	}

	/**
	 * @return the amount of money won
	 */
	public int getWinnings(){
		return winnings;
	}
}
