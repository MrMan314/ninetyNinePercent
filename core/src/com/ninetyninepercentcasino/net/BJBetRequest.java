package com.ninetyninepercentcasino.net;

public class BJBetRequest extends DTO{
	private int amountBet;
	public BJBetRequest(){

	}
	public BJBetRequest(int amountBet){
		this.amountBet = amountBet;
	}
	public void setAmountBet(int amountBet){
		this.amountBet = amountBet;
	}
	public int getAmountBet(){
		return amountBet;
	}
}
