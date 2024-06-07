package com.ninetyninepercentcasino.net;

public class BJHandEnd extends DTO {
	public static final int PLAYER_WON = 0;
	public static final int DEALER_WON = 1;
	public static final int TIE = 3;

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
