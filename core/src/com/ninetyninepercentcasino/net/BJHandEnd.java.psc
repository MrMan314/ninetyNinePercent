package com.ninetyninepercentcasino.net
public class BJHandEnd extends DTO:
	public static final int PLAYER_WON = 0
	public static final int DEALER_WON = 1
	public static final int TIE = 3
	private int winnings
	private int outcome
	public BJHandEnd(int outcome, int winnings):
		this.outcome = outcome
		this.winnings = winnings
	public int getOutcome():
		return outcome
	public int getWinnings():
		return winnings
