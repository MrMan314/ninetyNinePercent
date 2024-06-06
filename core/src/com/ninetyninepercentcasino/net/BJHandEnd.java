package com.ninetyninepercentcasino.net;

public class BJHandEnd extends DTO {
	private static final int PLAYER_WON = 0;
	private static final int DEALER_WON = 1;

	private int winnings;
	private int winner;
	public BJHandEnd(int winner, int winnings){
		this.winner = winner;
		this.winnings = winnings;
	}
	public int getWinner(){
		return winner;
	}
	public int getWinnings() {
		return winnings;
	}
}
