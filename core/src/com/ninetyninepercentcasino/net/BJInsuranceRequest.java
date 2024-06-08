package com.ninetyninepercentcasino.net;

/**
 * DTO for insurance bets in a blackjack game
 * @author Grant Liang
 */
public class BJInsuranceRequest extends DTO {
	private boolean insuranceChosen;
	public BJInsuranceRequest(){

	}
	public void chooseInsurance(){
		insuranceChosen = true;
	}
	public void denyInsurance(){
		insuranceChosen = false;
	}
	public boolean getInsuranceChosen(){
		return insuranceChosen;
	}
}
