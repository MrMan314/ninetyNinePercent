package com.ninetyninepercentcasino.net;

import com.ninetyninepercentcasino.game.Card;

/**
 * DTO for whenever a new card is introduced to the BJ game
 * @author Grant Liang
 */
public class BJCardUpdate extends DTO {
	private Card card; //the card introduced
	private boolean visible; //whether the card should be visible to the client or not
	private boolean isPlayerCard; //acts as an identifier for whether the player or dealer drew the card
	/**
	 * initializes a new BJCardUpdate
	 */
	public BJCardUpdate(Card card, boolean visible, boolean isPlayerCard) {
		this.card = card;
		this.visible = visible;
		this.isPlayerCard = isPlayerCard;
	}

	/**
	 * @return the card introduced to the game
	 */
	public Card getCard() {
		return card;
	}

	/**
	 * @return whether the card is visible or not to the client
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @return whether the player owns the card or not
	 */
	public boolean isPlayerCard() {
		return isPlayerCard;
	}
}
