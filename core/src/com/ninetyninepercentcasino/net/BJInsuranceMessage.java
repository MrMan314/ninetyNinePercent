package com.ninetyninepercentcasino.net;

/**
 * DTO for insurance bets in a blackjack game
 * @author Grant Liang
 */
public class BJInsuranceMessage extends DTO {
	private int insureAmount;

	/**
	 * initializes a new request for insurance
	 */
	public BJInsuranceMessage() {

	}

	/**
	 * initializes a response with the amount insured
	 * @param insureAmount the amount insured
	 */
	public BJInsuranceMessage(int insureAmount) {
		this.insureAmount = insureAmount;
	}

	/**
	 * @return the amount insured
	 */
	public int getInsureAmount() {
		return insureAmount;
	}
}
