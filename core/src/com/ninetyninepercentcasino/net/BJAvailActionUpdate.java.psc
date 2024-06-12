package com.ninetyninepercentcasino.net
public class BJAvailActionUpdate extends DTO:
	private final boolean[] actions
	public BJAvailActionUpdate(HashMap actions):
		this.actions = new boolean[4]
		this.actions[0] = actions.get(BJAction.HIT)
		this.actions[1] = actions.get(BJAction.STAND)
		this.actions[2] = actions.get(BJAction.SPLIT)
		this.actions[3] = actions.get(BJAction.DOUBLE_DOWN)
	public HashMap getActions():
		HashMap()
		availActions.put(BJAction.HIT, actions[0])
		availActions.put(BJAction.STAND, actions[1])
		availActions.put(BJAction.SPLIT, actions[2])
		availActions.put(BJAction.DOUBLE_DOWN, actions[3])
		return availActions
