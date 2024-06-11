package com.ninetyninepercentcasino.net;

/**
 * DTO for a client's chosen action
 * @author Grant Liang
 */
public class BJActionUpdate extends DTO {
	private BJAction chosenAction;

	/**
	 * initializes a BJActionUpdate with a given action
	 * @param action the chosen action
	 */
	public BJActionUpdate(BJAction action) {
		chosenAction = action;
	}

	/**
	 * @return the chosen action
	 */
	public BJAction getChosenAction() {
		return chosenAction;
	}
}
