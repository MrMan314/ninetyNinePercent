package com.ninetyninepercentcasino.net;

public class BJBetRequest extends DTO{
	private double amountBet;
	public BJBetRequest(){

	}
	public void setAmountBet(double amountBet){
		this.amountBet = amountBet;
	}
	public double getAmountBet(){
		return amountBet;
	}
}
