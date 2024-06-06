package com.ninetyninepercentcasino.game.gameparts;

/**
 * Models a chip, an item commonly used in place of cash at a casino
 * @author Grant Liang
 */
public class Chip {
	private final int value;
	public Chip(int value){
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}
