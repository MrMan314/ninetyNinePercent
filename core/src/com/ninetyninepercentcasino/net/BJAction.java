package com.ninetyninepercentcasino.net;

import java.io.Serializable;

/**
 * Available actions in a blackjack game
 * @author Grant Liang
 */
public enum BJAction implements Serializable {
	HIT,
	STAND,
	SPLIT,
	DOUBLE_DOWN,
}
