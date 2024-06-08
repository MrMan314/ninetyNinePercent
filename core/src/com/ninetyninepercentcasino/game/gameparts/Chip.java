package com.ninetyninepercentcasino.game.gameparts;

/**
 * Models a chip, an item commonly used in place of cash at a casino
 * @author Grant Liang
 */
public class Chip {
	private final int value;

	/**
	 * initializes a new chip
	 * @param value the value of the chip
	 */
	public Chip(int value){
		this.value = value;
	}

	/**
	 * @return the value of this chip
	 */
	public int getValue() {
		return value;
	}
}
