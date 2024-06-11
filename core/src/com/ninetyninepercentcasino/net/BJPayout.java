package com.ninetyninepercentcasino.net;

public class BJPayout extends DTO {
	private final int winnings;
	public BJPayout(int winnings) {
		this.winnings = winnings;
	}
	public int getWinnings() {
		return winnings;
	}
}
