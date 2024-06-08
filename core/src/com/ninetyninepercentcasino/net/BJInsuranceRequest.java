package com.ninetyninepercentcasino.net;

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
