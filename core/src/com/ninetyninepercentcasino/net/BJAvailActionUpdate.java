package com.ninetyninepercentcasino.net;

import java.util.HashMap;

/**
 * DTO that the server sends to client to inform them of the actions and their availability
 * @author Grant Liang
 */
public class BJAvailActionUpdate extends DTO {
	private final HashMap<BJAction, Boolean> actions; //stores the available actions in key value pairs. key is the action, value is its availability

	/**
	 * initializes a new BJAvailActionUpdate with a given HashMap of actions
	 * @param actions describes the available actions
	 */
	public BJAvailActionUpdate(HashMap<BJAction, Boolean> actions){
		this.actions = actions;
	}

	/**
	 * @return HashMap of actions and their availability
	 */
	public HashMap<BJAction, Boolean> getActions(){
		return actions;
	}
}
