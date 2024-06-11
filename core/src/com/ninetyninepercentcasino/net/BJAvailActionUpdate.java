package com.ninetyninepercentcasino.net;

import java.util.HashMap;

/**
 * DTO that the server sends to client to inform them of the actions and their availability
 * @author Grant Liang
 */
public class BJAvailActionUpdate extends DTO {
	private final boolean[] actions;
	/**
	 * initializes a new BJAvailActionUpdate with a given HashMap of actions
	 * @param actions describes the available actions
	 */
	public BJAvailActionUpdate(HashMap<BJAction, Boolean> actions) {
		this.actions = new boolean[4];
		this.actions[0] = actions.get(BJAction.HIT);
		this.actions[1] = actions.get(BJAction.STAND);
		this.actions[2] = actions.get(BJAction.SPLIT);
		this.actions[3] = actions.get(BJAction.DOUBLE_DOWN);
	}

	/**
	 * @return HashMap of actions and their availability
	 */
	public HashMap<BJAction, Boolean> getActions() {
		HashMap<BJAction, Boolean> availActions = new HashMap<>();
		availActions.put(BJAction.HIT, actions[0]);
		availActions.put(BJAction.STAND, actions[1]);
		availActions.put(BJAction.SPLIT, actions[2]);
		availActions.put(BJAction.DOUBLE_DOWN, actions[3]);
		return availActions;
	}
}
